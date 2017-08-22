package pl.nask.crs.payment.service.impl;

import static pl.nask.crs.commons.MoneyUtils.add;
import static pl.nask.crs.commons.MoneyUtils.getRoudedAndScaledValue;
import static pl.nask.crs.commons.MoneyUtils.substract;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.CardType;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositInfo;
import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.payment.DepositTopUp;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.ExtendedPaymentRequest;
import pl.nask.crs.payment.LimitsPair;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.dao.DepositDAO;
import pl.nask.crs.payment.dao.PaymentUtilsDAO;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.email.DepositCorrectionParams;
import pl.nask.crs.payment.email.PaymentEmailParameters;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotEnoughtDepositFundsException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.service.CardPaymentService;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class DepositServiceImpl implements DepositService {
    private static Logger LOG = Logger.getLogger(DepositServiceImpl.class);
	
    private final DepositDAO depositDAO;
    private final PaymentUtilsDAO paymentUtilsDAO;
    private final DepositFundsExporter doaExporter;
    private final EmailTemplateSender emailTemplateSender;
    private final NicHandleSearchService nicHandleSearchService;
    private final ReservationDAO reservationDAO;
    private final CardPaymentService cardPaymentService;

	public DepositServiceImpl(DepositDAO depositDAO,
			PaymentUtilsDAO paymentUtilsDAO, DepositFundsExporter doaExporter,
			EmailTemplateSender emailTemplateSender,
			NicHandleSearchService nicHandleSearchService,
			ReservationDAO reservationDAO, CardPaymentService cardPaymentService) {
		super();
		this.depositDAO = depositDAO;
		this.paymentUtilsDAO = paymentUtilsDAO;
		this.doaExporter = doaExporter;
		this.emailTemplateSender = emailTemplateSender;
		this.nicHandleSearchService = nicHandleSearchService;
		this.reservationDAO = reservationDAO;
		this.cardPaymentService = cardPaymentService;
	}

	@Override
    public DepositInfo initDeposit(String nicHandleId) {
        Deposit deposit = depositDAO.get(nicHandleId);
        if (deposit == null) {
            Deposit newDeposit = Deposit.newInstance(nicHandleId, new Date(), 0, 0, 0, DepositTransactionType.INIT, "", null, "deposit initialization");
            depositDAO.create(newDeposit);
            return new DepositInfo(newDeposit, BigDecimal.ZERO);
        } else {
            throw new IllegalStateException("Deposit exists for nicHandle: " + nicHandleId);
        }
    }
	
    @Override
    public LimitedSearchResult<DepositInfo> findDeposits(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<Deposit> searchResult = depositDAO.find(criteria, offset, limit, sortBy);
        List<DepositInfo> depositInfoList = new ArrayList<DepositInfo>(searchResult.getResults().size());
        for (Deposit deposit : searchResult.getResults()) {
            DepositInfo depositInfo = getDepositIncludingReservations(deposit);
            depositInfoList.add(depositInfo);
        }
        return new LimitedSearchResult<DepositInfo>((SearchCriteria) criteria, sortBy, offset, limit, depositInfoList, searchResult.getTotalResults());
    }

    @Override
    public LimitedSearchResult<Deposit> findHistoricalDeposits(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return depositDAO.findHistory(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<Deposit> findDepositWithHistory(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return depositDAO.findDepositWithHistory(criteria, offset, limit, sortBy);
    }
    
    @Override
    public LimitsPair getDepositLimits() {
        return paymentUtilsDAO.getDepositLimits();
    }

    @Override
    public DepositInfo depositFunds(AuthenticatedUser user, String nicHandle, PaymentRequest request) throws PaymentException, ExportException {
        Deposit deposit = null;
        ExtendedPaymentRequest extRequest = null;
        try {
            extRequest = cardPaymentService.authorisePaymentTransaction(request, CardType.CREDIT);
            double valueInStandardCurrencyUnit = request.getAmountInStandardCurrencyUnit();
            deposit = increaseDeposit(nicHandle, valueInStandardCurrencyUnit, extRequest.getOrderId(), DepositTransactionType.TOPUP, null, null);
            LOG.debug("after changes applied");
            cardPaymentService.settleRealexAuthorisation(extRequest);
            LOG.debug("after commit");

            doaExporter.exportDOA(nicHandle, valueInStandardCurrencyUnit, deposit.getTransactionDate(), extRequest.getOrderId());

            sendTopUpEmail(user, extRequest.getOrderId(), valueInStandardCurrencyUnit, nicHandle);
            LOG.debug("after sent email");
        } catch (PaymentException e) {
            if (isPaymentTransactionAuthenticated(extRequest)) {
                try {
                	cardPaymentService.cancelRealexAuthorisation(extRequest);
                } catch (PaymentException e1) {
                    LOG.error("Can't void Payment Transaction: " + e1.getMessage());
                }
            }
            throw new PaymentException(e.getMessage(), e);
        } catch (NicHandleNotFoundException e) {
            //should never happen
            LOG.error("Cannot send top up confirmation mail: " + e);
        }
        return getDepositIncludingReservations(deposit);
    }
    
    @Override
    public DepositInfo viewDeposit(String nicHandleId) throws DepositNotFoundException {
        Deposit deposit = depositDAO.get(nicHandleId);
        if (deposit == null)
            throw new DepositNotFoundException(nicHandleId);
        return getDepositIncludingReservations(deposit);
    }
    
    @Override
    public LimitedSearchResult<DepositTopUp> getTopUpHistory(String nicHandle, Date fromDate, Date toDate, long offset, long limit) {
        return depositDAO.getTopUpHistory(nicHandle, fromDate, toDate, offset, limit);
    }
    
    @Override
    public Deposit correctDeposit(String nicHandle, int amountInLowestCurrencyUnit, String username, String remark) throws NotEnoughtDepositFundsException, NicHandleNotFoundException, ExportException {
        Deposit deposit;
        double amountInStandardCurrencyUnit = MoneyUtils.getValueInStandardCurrencyUnit(amountInLowestCurrencyUnit);
        String orderId = generateADPOrderId();
        if (amountInLowestCurrencyUnit > 0) {
            deposit = increaseDeposit(nicHandle, amountInStandardCurrencyUnit, orderId, DepositTransactionType.MANUAL, username, remark);
        } else {
        	try {
				DepositInfo old = viewDeposit(nicHandle);
				if (old.getCloseBalIncReservaions().multiply(BigDecimal.valueOf(100)).intValue() + amountInLowestCurrencyUnit < 0) {
					throw new NotEnoughtDepositFundsException("Cannot reduce deposit below the available funds (check unsettled reservations)");
				}
				deposit = reduceDeposit(nicHandle, -amountInStandardCurrencyUnit, orderId, DepositTransactionType.MANUAL, username, remark);
			} catch (DepositNotFoundException e) {
				throw new NotEnoughtDepositFundsException("The deposit is empty", e);
			}
        }
        doaExporter.exportDOA(nicHandle, amountInStandardCurrencyUnit, deposit.getTransactionDate(), orderId);
        sendCorrectionEmail(orderId, amountInStandardCurrencyUnit, nicHandle, remark, username);
        return deposit;
    }
    
    @Override
    public Deposit depositFundsOffline(AuthenticatedUser user, String nicHandle, int amountInLowestCurrencyUnit, String username, String remark)
            throws NotEnoughtDepositFundsException, NicHandleNotFoundException, ExportException {
        Deposit deposit;
        double amountInStandardCurrencyUnit = MoneyUtils.getValueInStandardCurrencyUnit(amountInLowestCurrencyUnit);
        String orderId = generateADPOrderId();
        if (amountInLowestCurrencyUnit > 0) {
            deposit = increaseDeposit(nicHandle, amountInStandardCurrencyUnit, orderId, DepositTransactionType.TOPUP, username, remark);
        } else {
            throw new IllegalStateException("Amount cannot be negative: amount=" + amountInLowestCurrencyUnit);
        }
        doaExporter.exportDOA(nicHandle, amountInStandardCurrencyUnit, deposit.getTransactionDate(), orderId);
        sendTopUpEmail(user, orderId, amountInStandardCurrencyUnit, nicHandle);
        return deposit;
    }
    
    private void sendCorrectionEmail(String orderId, double valueInStandardUnit, String nicHandleName, String description, String username) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleName);
            EmailParameters params = new DepositCorrectionParams(orderId, getRoudedAndScaledValue(valueInStandardUnit), nicHandle, description, username);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DEPOSIT_CORRECTION.getId(), params);
        } catch (Exception e) {
            LOG.error("Cannot send correction confirmation mail: " + e);
        }
    }    

    private DepositInfo getDepositIncludingReservations(Deposit deposit) {
        List<Reservation> reservations = getReadyForSettlementNotSettledReservations(deposit.getNicHandleId());
        BigDecimal totalReservations = BigDecimal.ZERO;
        for (Reservation reservation : reservations) {
            totalReservations = add(totalReservations, reservation.getTotal());
        }

        return new DepositInfo(deposit, totalReservations);
    }
    
    private void sendTopUpEmail(AuthenticatedUser user, String orderId, double valueInStandardUnit, String nicHandleName) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleName);
            String username = (user == null) ? null : user.getUsername();
            PaymentEmailParameters params = new PaymentEmailParameters(username, orderId, getRoudedAndScaledValue(valueInStandardUnit), nicHandle, null, null);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.TOP_UP.getId(), params);
        } catch (Exception e) {
            LOG.error("Cannot send top up confirmation mail: " + e);
        }
    }
    
    private List<Reservation> getReadyForSettlementNotSettledReservations(String billingNH) {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(true);
        criteria.setBillingNH(billingNH);
        criteria.setPaymentMethod(PaymentMethod.ADP);
        return reservationDAO.getAllReservations(criteria);
    }
    
    private Deposit increaseDeposit(String nicHandleId, double amount, String orderId, DepositTransactionType transType, String correctorNH, String remark) {
        Date transactionDate = new Date();
        if (!depositDAO.lock(nicHandleId)) {
            Deposit newDeposit = Deposit.newInstance(nicHandleId, transactionDate, amount, amount, amount, transType, orderId, correctorNH, remark);
            depositDAO.create(newDeposit);
            return newDeposit;
        } else {
            Deposit deposit = depositDAO.get(nicHandleId);
            Deposit increasedDeposit = Deposit.newInstance(
                    nicHandleId,
                    transactionDate,
                    amount,
                    add(deposit.getCloseBal(), amount),
                    amount,
                    transType,
                    orderId,
                    correctorNH,
                    remark);
            depositDAO.update(increasedDeposit);
            return increasedDeposit;
        }
    }
    
    public static String generateADPOrderId() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Random r = new Random();
        return formatter.format(new Date()) + "-D-" + r.nextInt(9999999);
    }
    
    @Override
    public Deposit reduceDeposit(String nicHandleId, double amountInStandardCurrencyUnit, String orderId, DepositTransactionType transType, String correctorNH, String remark) throws NotEnoughtDepositFundsException {
        if (depositDAO.lock(nicHandleId)) {
            Deposit deposit = depositDAO.get(nicHandleId);
            if (Double.compare(deposit.getCloseBal(), amountInStandardCurrencyUnit) < 0)
                throw new NotEnoughtDepositFundsException();
            Deposit reducedDeposit = Deposit.newInstance(deposit.getNicHandleId(),
                    new Date(),
                    deposit.getOpenBal(),
                    substract(deposit.getCloseBal(), amountInStandardCurrencyUnit),
                    -amountInStandardCurrencyUnit,
                    transType,
                    orderId,
                    correctorNH,
                    remark);
            depositDAO.update(reducedDeposit);
            return reducedDeposit;
        } else {
            throw new NotEnoughtDepositFundsException();
        }
    }
    
    
    private boolean isPaymentTransactionAuthenticated(ExtendedPaymentRequest extRequest) {
        return (extRequest != null && extRequest.getAuthcode() != null);
    }
}
