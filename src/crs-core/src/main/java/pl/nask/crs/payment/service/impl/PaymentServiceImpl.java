package pl.nask.crs.payment.service.impl;

import static pl.nask.crs.commons.MoneyUtils.getValueInLowestCurrencyUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.InvoiceExportConfiguration;
import pl.nask.crs.commons.config.NameFormatter;
import pl.nask.crs.commons.config.TargetFileInfo;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.config.VersionInfo;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.ResellerDefaultsService;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.dsm.PaymentInitiated;
import pl.nask.crs.domains.dsm.SettlementFailureEvent;
import pl.nask.crs.domains.dsm.events.PaymentSettledEvent;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.invoicing.service.InvoiceExporter;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.CardType;
import pl.nask.crs.payment.DepositInfo;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.ExtendedInvoice;
import pl.nask.crs.payment.ExtendedInvoiceSearchCriteria;
import pl.nask.crs.payment.ExtendedPaymentRequest;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.InvoiceSearchCriteria;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Payment;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.payment.ReauthoriseTransaction;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.ReservationTotals;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionDetails;
import pl.nask.crs.payment.TransactionInfo;
import pl.nask.crs.payment.TransactionSearchCriteria;
import pl.nask.crs.payment.dao.InvoiceDAO;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.ReservationHistDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.dao.TransactionHistDAO;
import pl.nask.crs.payment.email.InvoiceEmailParameters;
import pl.nask.crs.payment.email.PaymentEmailParameters;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.DomainIncorrectStateForPaymentException;
import pl.nask.crs.payment.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.payment.exceptions.DuplicatedDomainException;
import pl.nask.crs.payment.exceptions.InvoiceEmailException;
import pl.nask.crs.payment.exceptions.InvoiceNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.NotEnoughtDepositFundsException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.ReservationNotFoundException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.CardPaymentService;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.InvoiceNumberService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.DomainPricingDictionary;
import pl.nask.crs.price.dao.DomainPricingDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.VatDictionary;
import pl.nask.crs.vat.dao.VatDAO;

/**
 * @author: Marcin Tkaczyk
 */
public class PaymentServiceImpl implements PaymentService {

    private final static String TEST_ORDER_ID = "test-order-id";

   
    private final DomainSearchService domainSearchService;
    private final NicHandleSearchService nicHandleSearchService;
    private final ReservationDAO reservationDAO;
    private final DomainPricingDAO domainPricingDAO;
    private final VatDAO vatDAO;
    private final DomainStateMachine dsm;
    private final TransactionDAO transactionDAO;
    private final DepositService depositService;

    private static Logger LOG = Logger.getLogger(PaymentServiceImpl.class);
    private final AccountSearchService accountSearchService;
    private final InvoiceNumberService invoicingService;
    private final InvoiceDAO invoiceDAO;
    private final InvoiceExporter invoiceExporterChain;
    private final ApplicationConfig applicationConfig;
    private final EmailTemplateSender emailTemplateSender;
    private final TransactionHistDAO transactionHistDAO;
    private final ReservationHistDAO reservationHistDAO;
    private final ResellerDefaultsService resellerDefaultsService;
    private final CardPaymentService cardService;

    public PaymentServiceImpl(
                              DomainSearchService domainSearchService,
                              NicHandleSearchService nicHandleSearchService,
                              ReservationDAO reservationDAO,
                              DomainPricingDAO domainPricingDAO,
                              VatDAO vatDAO,
                              DomainStateMachine dsm,
                              TransactionDAO transactionDAO,
                              AccountSearchService accountSearchService,
                              InvoiceNumberService invoicingService,
                              InvoiceDAO invoiceDAO,
                              InvoiceExporter invoiceExporter,
                              ApplicationConfig applicationConfig,
                              EmailTemplateSender emailTemplateSender,
                              TransactionHistDAO transactionHistDAO,
                              ReservationHistDAO reservationHistDAO,
                              ResellerDefaultsService resellerDefaultsService,
                              DepositService depositService,
                              CardPaymentService cardService) {
        Validator.assertNotNull(domainSearchService, "domain search service");
        Validator.assertNotNull(nicHandleSearchService, "nicHandle search service");
        Validator.assertNotNull(reservationDAO, "reservation DAO");
        Validator.assertNotNull(domainPricingDAO , "pricing DAO");
        Validator.assertNotNull(vatDAO, "vat DAO");
        Validator.assertNotNull(dsm, "domainStateMachine");
        Validator.assertNotNull(transactionDAO, "transactionDAO");
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        Validator.assertNotNull(invoicingService, "invoicingService");
        Validator.assertNotNull(invoiceDAO, "invoiceDAO");
        Validator.assertNotNull(invoiceExporter, "invoiceExporterChain");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        Validator.assertNotNull(transactionHistDAO, "transactionHistDAO");
        Validator.assertNotNull(reservationHistDAO, "reservationHistDAO");
        Validator.assertNotNull(resellerDefaultsService, "resellerDefaultsService");
        Validator.assertNotNull(depositService, "depositService");
        Validator.assertNotNull(cardService, "cardService");
        this.domainSearchService = domainSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.reservationDAO = reservationDAO;
        this.domainPricingDAO = domainPricingDAO ;
        this.vatDAO = vatDAO;
        this.dsm = dsm;
        this.transactionDAO = transactionDAO;
        this.accountSearchService = accountSearchService;
        this.invoicingService = invoicingService;
        this.invoiceDAO = invoiceDAO;
        this.invoiceExporterChain = invoiceExporter;
        this.applicationConfig = applicationConfig;
        this.emailTemplateSender = emailTemplateSender;
        this.transactionHistDAO = transactionHistDAO;
        this.reservationHistDAO = reservationHistDAO;
        this.resellerDefaultsService = resellerDefaultsService;
        this.depositService = depositService;
        this.cardService = cardService;
    }

    private void validateAndLockDomains(Collection<Domain> domains, NicHandle nicHandle) throws DomainNotFoundException,
            DuplicatedDomainException, NicHandleNotFoundException, DomainManagedByAnotherResellerException, DomainIncorrectStateForPaymentException {
        if (domains == null || domains.size() == 0)
            throw new IllegalArgumentException("Domains list cannot be empty");
        Domain duplicatedDomain = Validator.getDuplicates(domains);
        if (duplicatedDomain != null)
            throw new DuplicatedDomainException(duplicatedDomain.getName());
        Long accId = nicHandle.getAccount().getId();
        for (Domain domain: domains) {
            checkDomainName(domain, accId);
            checkDomainState(domain.getName());
        }
    }

    private void checkDomainName(Domain domain, long accountId) throws DomainNotFoundException, NicHandleNotFoundException, DomainManagedByAnotherResellerException {        
        if (accountId != domain.getResellerAccount().getId())
            throw new DomainManagedByAnotherResellerException(domain.getName());
    }

    private void checkDomainState(String domainName) throws DomainIncorrectStateForPaymentException, DomainNotFoundException {
        if (!dsm.validateEventAndLockDomain(domainName, DsmEventName.PaymentInitiated)) {
            throw new DomainIncorrectStateForPaymentException(domainName, domainSearchService.getDomain(domainName).getDsmState().getNRPStatus().shortDescription());
        } 
    }

    @Override
    public double getVatRate(String nicHandleId) {
        return getVat(nicHandleId).getVatRate();
    }

    @Override
    public long createADPReservation(String nicHandleId, String domainName, Period period, OperationType operationType, Long ticketId)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        Reservation reservation = null;
        PriceWithVat priceWithVat = preparePriceWithVat(nicHandleId, period, operationType);
        long transactionId = createADPTransaction(priceWithVat.getTotal(), priceWithVat.getNetAmount(), priceWithVat.getVatAmount(), DepositServiceImpl.generateADPOrderId());
        if (ticketId != null) {
            reservation = Reservation.newInstanceForTicket(nicHandleId, domainName, period.getMonths(), priceWithVat, ticketId, transactionId, operationType, PaymentMethod.ADP);
        } else {//GIBO registration
            reservation = Reservation.newInstanceForGIBORegistration(nicHandleId, domainName, priceWithVat, transactionId, PaymentMethod.ADP);
        }
        return reservationDAO.createReservation(reservation);
    }

    @Override
    public long createCCReservation(AuthenticatedUser user, String nicHandleId, String domainName, String domainHolder, Period period, OperationType operationType, PaymentRequest request, Long ticketId)
            throws NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException {
        Reservation reservation = null;
        PriceWithVat priceWithVat = preparePriceWithVat(nicHandleId, period, operationType);
        request.setAmount(priceWithVat.getTotal());
        ExtendedPaymentRequest extendedRequest = cardService.authorisePaymentTransaction(request, CardType.CREDIT);
        String realexAuthcode = extendedRequest.getAuthcode();
        String realexPassref = extendedRequest.getPassref();
        String orderId = extendedRequest.getOrderId();
        long transactionId = createCCTransaction(priceWithVat.getTotal(), priceWithVat.getNetAmount(), priceWithVat.getVatAmount(), orderId, realexAuthcode, realexPassref);
        if (ticketId != null) {
            reservation = Reservation.newInstanceForTicket(nicHandleId, domainName, period.getMonths(), priceWithVat, ticketId, transactionId, operationType, PaymentMethod.CC);
        } else {//GIBO registration
            reservation = Reservation.newInstanceForGIBORegistration(nicHandleId, domainName, priceWithVat, transactionId, PaymentMethod.CC);
        }
        long newReservationId = reservationDAO.createReservation(reservation);
        sendCCPreAuthorisationEmail(user, nicHandleId, domainName, domainHolder, period.getYears(), priceWithVat.getTotal(), orderId, operationType);
        return newReservationId;
    }

    private void sendCCPreAuthorisationEmail(AuthenticatedUser user, String nicHandleId, String domainName, String domainHolder, int years, BigDecimal transactionValue, String orderId, OperationType operationType) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
            TransactionDetails details = new TransactionDetails(domainName, domainHolder, years, operationType, transactionValue);
            String username = (user == null) ? null : user.getUsername();
            PaymentEmailParameters parameters = new PaymentEmailParameters(username, orderId, transactionValue, nicHandle, domainName, details);
            switch (operationType) {
                case REGISTRATION:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.CC_NREG_PRE_AUTH.getId(), parameters);
                    break;
                case RENEWAL:
                case TRANSFER:
                	if (nicHandle.getAccount().getId() == applicationConfig.getGuestAccountId()) {
                		emailTemplateSender.sendEmail(EmailTemplateNamesEnum.CC_XFER_PRE_AUTH_DIRECT.getId(), parameters);
                	} else {
                		emailTemplateSender.sendEmail(EmailTemplateNamesEnum.CC_XFER_PRE_AUTH_REG.getId(), parameters);
                	}
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation type: " + operationType);
            }
        } catch (Exception e) {
            LOG.error("Cannot send registration cc pre auth mail: " + e);
        }
    }

    @Override
    public DomainPrice getProductPrice(Period period, OperationType operationType, String nicHandleId) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
    	return getProductPrice(period, operationType, nicHandleSearchService.getNicHandle(nicHandleId), new DomainPricingDictionary(domainPricingDAO));
    }
    
    private DomainPrice getProductPrice(Period period, OperationType operationType, NicHandle nicHandle, DomainPricingDictionary domainPricingDictionary) throws NotAdmissiblePeriodException {
        int periodInYears = period.getYears();
        List<DomainPrice> domainPrices = domainPricingDictionary.getEntries();
        boolean isRenewal = false;
        boolean isRegistration = false;
        boolean isDirect = isDirect(nicHandle);

        switch (operationType) {
            case REGISTRATION:
                isRegistration = true;
                break;
            case RENEWAL:
            case TRANSFER:
                isRenewal = true;
                break;
            default:
                throw new IllegalStateException("Illegal operation type: " + operationType);
        }

        for (DomainPrice domainPrice : domainPrices) {
            if (domainPrice.getDuration() == periodInYears && ((isRegistration && domainPrice.isForRegistration()) || (isRenewal && domainPrice.isForRenewal()))) {
                if ((isDirect && domainPrice.isDirect()) || (!isDirect && !domainPrice.isDirect())) {
                    return domainPrice;
                }
            }        
        }
        throw new NotAdmissiblePeriodException(periodInYears);    	
    }

    private boolean isDirect(String nicHandleId) throws NicHandleNotFoundException {
        NicHandle nh = nicHandleSearchService.getNicHandle(nicHandleId);
        return isDirect(nh);
    }

    private boolean isDirect(NicHandle nicHandle) {
        long guestAccountId = applicationConfig.getGuestAccountId();
        return nicHandle.getAccount().getId() == guestAccountId;
    }

    @Override
    public List<DomainPrice> getDomainPricing(String nicHandleId) throws NicHandleNotFoundException {
        List<DomainPrice> allPrices = new DomainPricingDictionary(domainPricingDAO).getEntries();
        List<DomainPrice> directPrices = new ArrayList<DomainPrice>();
        List<DomainPrice> registrarPrices = new ArrayList<DomainPrice>();
        for (DomainPrice price : allPrices) {
            if (price.isDirect()) {
                directPrices.add(price);
            } else {
                registrarPrices.add(price);
            }
        }
        return isDirect(nicHandleId) ? directPrices : registrarPrices;
    }

    private Vat getVat(String nicHandleId) {
        String vatCategory = getVatCategory(nicHandleId);
        Dictionary<String, Vat> vatDictionary = new VatDictionary(vatDAO, new Date());
        Vat vat = vatDictionary.getEntry(vatCategory);
        if (vat == null) {
            throw new IllegalStateException("vat not defined for operation!");
        }
        return vat;
    }

    private String getVatCategory(String nicHandleId) {
        try {
            NicHandle nh = nicHandleSearchService.getNicHandle(nicHandleId);
            String vatCategory = nh.getVatCategory();
            if (Validator.isEmpty(vatCategory)) {
                throw new IllegalStateException("Vat category not defined for nicHandle: " + nicHandleId);
            } else {
                return vatCategory;
            }
        } catch (NicHandleNotFoundException e) {
            //should never happen
            throw new IllegalStateException("NicHandle not found");
        }
    }

    @Override
    public Reservation getReservationForTicket(long ticketId) {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newInstance();
        criteria.setTicketId((int) ticketId);
        //FIXME for reauthenticated transaction there is many reservation and only last inserted is valid(others has invalidated transaction)!!!
        LimitedSearchResult<Reservation> ret = reservationDAO.getReservations(criteria, 0, 100, Arrays.asList(new SortCriterion("creationDate", true)));
        int lastReservationIndex = ret.getResults().size() - 1;
        return ret.getTotalResults() == 0 ? null : ret.getResults().get(lastReservationIndex);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        reservationDAO.update(reservation);
    }

    @Override
    public Reservation lockForUpdate(long id) throws ReservationNotFoundException {
        if (reservationDAO.lock(id)) {
            return reservationDAO.get(id);
        } else {
            throw new ReservationNotFoundException();
        }
    }

    @Override
    public PaymentSummary payADP(AuthenticatedUser user, String nicHandleId, String superNicHandleId, Map<String, Period> domainsWithPeriods, boolean test)
            throws DomainNotFoundException, DuplicatedDomainException, DomainManagedByAnotherResellerException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            NotEnoughtDepositFundsException {
    	OpInfo opInfo = new OpInfo(nicHandleId, superNicHandleId, null);
    	Map<Domain, Period> richDomainsWithPeriods = loadDomainsForPayment(domainsWithPeriods);
    	NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        validateAndLockDomains(richDomainsWithPeriods.keySet(), nicHandle);
        Map<Domain, PriceWithVat> pricePerDomain = preparePricePerDomain(nicHandle, richDomainsWithPeriods);
        Payment totalPayment = new Payment(pricePerDomain);
        String orderId = TEST_ORDER_ID;
        if (hasRegistrarSufficientFunds(nicHandleId, totalPayment.getTotal())) {
            if (!test) {
                orderId = DepositServiceImpl.generateADPOrderId();
                createADPTransactionAndReservations(nicHandleId, pricePerDomain, totalPayment, orderId);
                TransactionDetails transactionDetails = new TransactionDetails(pricePerDomain, OperationType.RENEWAL);
                runPaymentInitEvent(user, domainsWithPeriods, opInfo, PaymentMethod.ADP, transactionDetails, totalPayment, orderId);
                sendRenewalInitializationEmail(user, nicHandleId, transactionDetails, totalPayment, orderId, PaymentMethod.ADP);
            }
        } else {
            throw new NotEnoughtDepositFundsException();
        }

        return new PaymentSummary(pricePerDomain, totalPayment, orderId);
    }

    private Map<Domain, Period> loadDomainsForPayment(Map<String, Period> domainsWithPeriods) throws DomainNotFoundException {
		Map<Domain, Period> res = new HashMap<Domain, Period>();
		for (Map.Entry<String, Period> e: domainsWithPeriods.entrySet()) {
			res.put(domainSearchService.getDomain(e.getKey()), e.getValue());
		}
		return res ;
	}

	private void sendRenewalInitializationEmail(AuthenticatedUser user, String nicHandleId, TransactionDetails transactionDetails, Payment totalPayment, String orderId, PaymentMethod paymentMethod) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
            String username = (user == null) ? null : user.getUsername();
            PaymentEmailParameters parameters = new PaymentEmailParameters(username, orderId, totalPayment.getTotal(), nicHandle, null, transactionDetails);
            switch (paymentMethod) {
                case ADP:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INIT_RENEWAL_ADP.getId(), parameters);
                    break;
                case CC:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INIT_RENEWAL_CC.getId(), parameters);
                    break;
                case DEB:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INIT_RENEWAL_DEB.getId(), parameters);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
            }
        } catch (Exception e) {
            LOG.error("Cannot send renewal payment initialization email: " + e);
        }
    }

    private Map<Domain, PriceWithVat> preparePricePerDomain(NicHandle nicHandle, Map<Domain, Period> domainsWithPeriods)
            throws DomainNotFoundException, NotAdmissiblePeriodException, NicHandleNotFoundException {
        Map<Domain, PriceWithVat> pricePerDomain = new HashMap<Domain, PriceWithVat>();
        DomainPricingDictionary pricingDictionary = new DomainPricingDictionary(domainPricingDAO);
        for (Map.Entry<Domain, Period> entry : domainsWithPeriods.entrySet()) {
            PriceWithVat productPrice = preparePriceWithVat(nicHandle, entry.getValue(), getVat(nicHandle.getNicHandleId()), OperationType.RENEWAL, pricingDictionary);
            pricePerDomain.put(entry.getKey(), productPrice);
        }
        return pricePerDomain;
    }

    private PriceWithVat preparePriceWithVat(NicHandle nicHandle, Period period, Vat vat, OperationType operationType, DomainPricingDictionary pricingDictionary) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        DomainPrice productPrice = getProductPrice(period, operationType, nicHandle, pricingDictionary);
        PriceWithVat pwv = new PriceWithVat(period, productPrice.getId(), productPrice.getPrice(), vat);
        return pwv;
    }
    
    private PriceWithVat preparePriceWithVat(String nicHandleId, Period period, OperationType operationType, DomainPricingDictionary pricingDictionary) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
    	return preparePriceWithVat(nicHandleSearchService.getNicHandle(nicHandleId), period, getVat(nicHandleId), operationType, pricingDictionary);
    }
    
    private PriceWithVat preparePriceWithVat(String nicHandleId, Period period, OperationType operationType) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
    	return preparePriceWithVat(nicHandleSearchService.getNicHandle(nicHandleId), period, getVat(nicHandleId), operationType, new DomainPricingDictionary(domainPricingDAO));
    }

    private boolean hasRegistrarSufficientFunds(String billingNH, BigDecimal amountWithVat) {
        try {
            DepositInfo deposit = depositService.viewDeposit(billingNH);
            BigDecimal closeIncludingReservations = deposit.getCloseBalIncReservaions();
            return closeIncludingReservations.compareTo(amountWithVat) == 0 || closeIncludingReservations.compareTo(amountWithVat) == 1;
        } catch (DepositNotFoundException e) {
            return false;
        }
    }

    private void createADPTransactionAndReservations(String nicHandleId, Map<Domain, PriceWithVat> pricePerDomain, Payment totalPayment, String orderId) {
        try {
            long transactionId = createADPTransaction(totalPayment.getTotal(), totalPayment.getFee(), totalPayment.getVat(), orderId);
            for (Map.Entry<Domain, PriceWithVat> entry : pricePerDomain.entrySet()) {
                Domain domain = entry.getKey();
                Period period = entry.getValue().getPeriod();
                Reservation reservation = Reservation.newInstanceForRenewal(nicHandleId, domain.getName(), period.getMonths(), pricePerDomain.get(domain), transactionId, PaymentMethod.ADP);
                reservationDAO.createReservation(reservation);
            }
            setTransactionFinanciallyPassed(transactionId);
        } catch (TransactionNotFoundException e) {
            throw new IllegalStateException("Transaction not found", e);
        }
    }

    private long createCCTransaction(BigDecimal total, BigDecimal net, BigDecimal vat, String orderId, String realexAuthcode, String realexPassref) {
        return transactionDAO.createTransaction(Transaction.newInstance(getValueInLowestCurrencyUnit(total), getValueInLowestCurrencyUnit(net), getValueInLowestCurrencyUnit(vat), orderId, null, realexAuthcode, realexPassref));
    }
    
    private long createADPTransaction(BigDecimal total, BigDecimal net, BigDecimal vat, String orderId) {
        return transactionDAO.createTransaction(Transaction.newInstance(getValueInLowestCurrencyUnit(total), getValueInLowestCurrencyUnit(net), getValueInLowestCurrencyUnit(vat), orderId, null));
    }

    @Override
    public PaymentSummary payCC(AuthenticatedUser user, String nicHandleId, String superNicHandleId, Map<String, Period> domainsWithPeriods, PaymentRequest paymentRequest, boolean test)
            throws DomainNotFoundException, DuplicatedDomainException, DomainManagedByAnotherResellerException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException, PaymentException {
    	Map<Domain, Period> richDomainsWithPeriods = loadDomainsForPayment(domainsWithPeriods);
    	NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        validateAndLockDomains(richDomainsWithPeriods.keySet(), nicHandle);
        OpInfo opInfo = new OpInfo(nicHandleId, superNicHandleId, null);
        Map<Domain, PriceWithVat> pricePerDomain = preparePricePerDomain(nicHandle, richDomainsWithPeriods);
        Payment totalPayment = new Payment(pricePerDomain);

        String orderId = TEST_ORDER_ID;
        if (!test) {
            paymentRequest.setAmount(totalPayment.getTotal());
            ExtendedPaymentRequest extendedRequest = cardService.authorisePaymentTransaction(paymentRequest, CardType.CREDIT);
            String realexAuthcode = extendedRequest.getAuthcode();
            String realexPassref = extendedRequest.getPassref();
            orderId = extendedRequest.getOrderId();            
            createCCTransactionAndReservations(nicHandleId, pricePerDomain, totalPayment, realexAuthcode, realexPassref, orderId, PaymentMethod.CC);
            TransactionDetails transactionDetails = new TransactionDetails(pricePerDomain, OperationType.RENEWAL);
            runPaymentInitEvent(user, domainsWithPeriods, opInfo, PaymentMethod.CC, transactionDetails, totalPayment, orderId);
            sendRenewalInitializationEmail(user, nicHandleId, transactionDetails, totalPayment, orderId, PaymentMethod.CC);
        }

        return new PaymentSummary(pricePerDomain, totalPayment, orderId);
    }

    private void runPaymentInitEvent(AuthenticatedUser user, Map<String, Period> domainsNames, OpInfo opInfo, PaymentMethod payMethod, TransactionDetails transactionDetails, Payment transactionValue, String orderId) throws DomainNotFoundException {
    	DsmEvent event = new PaymentInitiated(payMethod, transactionDetails, transactionValue.getTotal(), orderId);
        for (String domainName : domainsNames.keySet()) {
            dsm.handleEvent(user, domainName, event, opInfo);
        }
    }

    private long createCCTransactionAndReservations(String nicHandleId, Map<Domain, PriceWithVat> pricePerDomain, Payment totalPayment, String realexAuthcode, String realexPassref, String orderId, PaymentMethod paymentMethod) {
        try {
            long transactionId = createCCTransaction(totalPayment.getTotal(), totalPayment.getFee(), totalPayment.getVat(), orderId, realexAuthcode, realexPassref);
            for (Map.Entry<Domain, PriceWithVat> entry : pricePerDomain.entrySet()) {
                Domain domain = entry.getKey();
                Period period = entry.getValue().getPeriod();
                Reservation reservation = Reservation.newInstanceForRenewal(nicHandleId, domain.getName(), period.getMonths(), pricePerDomain.get(domain), transactionId, paymentMethod);
                reservationDAO.createReservation(reservation);
            }
            setTransactionFinanciallyPassed(transactionId);
            return transactionId;
        } catch (TransactionNotFoundException e) {
            throw new IllegalStateException("Transaction not found", e);
        }
    }

    @Override
    public PaymentSummary payDEB(AuthenticatedUser user, String nicHandleId, String superNicHandleId, Map<String, Period> domainsWithPeriods, PaymentRequest paymentRequest, boolean test) throws DomainNotFoundException, DuplicatedDomainException, DomainManagedByAnotherResellerException, DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException, PaymentException {
    	Map<Domain, Period> richDomainsWithPeriods = loadDomainsForPayment(domainsWithPeriods);
    	NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        validateAndLockDomains(richDomainsWithPeriods.keySet(), nicHandle);

        Map<Domain, PriceWithVat> pricePerDomain = preparePricePerDomain(nicHandle, richDomainsWithPeriods);
        Payment totalPayment = new Payment(pricePerDomain);
        OpInfo opInfo = new OpInfo(nicHandleId, superNicHandleId, null);
        String orderId = TEST_ORDER_ID;
        if (!test) {
            paymentRequest.setAmount(totalPayment.getTotal());
            ExtendedPaymentRequest extendedRequest = cardService.authorisePaymentTransaction(paymentRequest, CardType.DEBIT);
            String realexAuthcode = extendedRequest.getAuthcode();
            String realexPassref = extendedRequest.getPassref();
            orderId = extendedRequest.getOrderId();
            long transactionId = createCCTransactionAndReservations(nicHandleId, pricePerDomain, totalPayment, realexAuthcode, realexPassref, orderId, PaymentMethod.DEB);

            TransactionDetails transactionDetails = new TransactionDetails(pricePerDomain, OperationType.RENEWAL);
            runPaymentInitEvent(user, domainsWithPeriods, opInfo, PaymentMethod.DEB, transactionDetails, totalPayment, orderId);

            //generate invoices (transaction is settled already, should be at least)
            runInvoicingForDebitPayment(user, opInfo, nicHandleId, transactionId);
            sendRenewalInitializationEmail(user, nicHandleId, transactionDetails, totalPayment, orderId, PaymentMethod.DEB);
        }

        return new PaymentSummary(pricePerDomain, totalPayment, orderId);
    }

    private void runInvoicingForDebitPayment(AuthenticatedUser user, OpInfo opInfo, String nicHandleId, long transactionId) {
        try {
            Transaction transaction = transactionDAO.get(transactionId);
            setTransactionStartedSettlement(transaction);
            settleReservationsAndRunPaymentSettledEvent(user, opInfo, transaction.getReservations());
            setTransactionSettlementEnded(transaction);

            int invoiceId = createInvoiceAndAssociateWithTransactions(nicHandleId, Arrays.asList(transaction));
            Invoice invoice = getInvoice(invoiceId);
            invoiceExporterChain.export(invoice);
            updateInvoice(invoice);
            sendEmailWithInvoices(EmailTemplateNamesEnum.INVOICE_SUMMARY_DEB.getId(), invoice.getInvoiceNumber(), user, true);
        } catch (Exception e) {
            LOG.error("Exception occured during invoicing debit payment!", e);
            throw new IllegalStateException("Exception during invoicing debit payment!", e);
        }
    }

    @Override
    public LimitedSearchResult<Reservation> findReservations(ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return reservationDAO.getReservations(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<Reservation> findHistoricalReservations(String billingNH, ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        criteria.setBillingNH(billingNH);
        return reservationHistDAO.find(criteria, offset, limit, sortBy);
    }

    @Override
    public PaymentSummary autorenew(AuthenticatedUser user, String domainName) throws NicHandleNotFoundException, DomainNotFoundException, DomainManagedByAnotherResellerException, DomainIncorrectStateForPaymentException, NotAdmissiblePeriodException, NotEnoughtDepositFundsException {
        try {
            Domain domain = domainSearchService.getDomain(domainName);
            RenewalMode rm = domain.getDsmState().getRenewalMode();

            if (rm == RenewalMode.Autorenew || rm == RenewalMode.RenewOnce) {
                Account acc = accountSearchService.getAccount(domain.getResellerAccount().getId());
                String billingNh = acc.getBillingContact().getNicHandle();
                Reservation res = getReadyReservation(billingNh, domainName);
                if (res != null && res.getOperationType() == OperationType.RENEWAL) {
                    LOG.debug("Domain " + domainName + " has a readyForSettlement reservation for a RENEWAL operation (id: " + res.getId());
                    throw new DomainIncorrectStateForPaymentException(domainName, "Pending reservation");
                } else {
                    return payADP(user, billingNh, null, Collections.singletonMap(domainName, Period.fromYears(1)), false);
                }
            } else {
                LOG.debug("Domain " + domainName + " in incorrect DSM state: " + domain.getDsmState().getId());
                throw new DomainIncorrectStateForPaymentException(domainName, domain.getDsmState().getNRPStatus().shortDescription());
            }
        } catch (DuplicatedDomainException e) {
            //should never happen
            throw new IllegalStateException(e);
        }
    }

    

	@Override
    public Reservation getReadyReservation(String billingNH, String domainName) {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(true);
        criteria.setBillingNH(billingNH);
        criteria.setDomainName(domainName);
        LimitedSearchResult<Reservation> ret = reservationDAO.getReservations(criteria, 0, 1, null);
        return ret.getTotalResults() == 0 ? null : ret.getResults().get(0);
    }

    @Override
    public Reservation getNotReadyReservation(String billingNH, String domainName) {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        criteria.setBillingNH(billingNH);
        criteria.setDomainName(domainName);
        LimitedSearchResult<Reservation> ret = reservationDAO.getReservations(criteria, 0, 1, null);
        return ret.getTotalResults() == 0 ? null : ret.getResults().get(0);
    }

    @Override
    public ReservationTotals getNotSettledReservationsTotals(String nicHandle, boolean adp) {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newSettledInstance(false);
        criteria.setCancelled(false);
        criteria.setBillingNH(nicHandle);
        if (adp) {
            criteria.setPaymentMethod(PaymentMethod.ADP);
        } else {
            criteria.setPaymentMethod(PaymentMethod.CC);
        }
        return reservationDAO.getTotals(criteria);
    }

    @Override
    public Transaction getTransaction(long transactionId) throws TransactionNotFoundException {
        Transaction transaction = transactionDAO.get(transactionId);
        if (transaction == null) {
            throw new TransactionNotFoundException();
        }
        return transaction;
    }

    @Override
    public void cancelTransaction(AuthenticatedUser user, long transactionId) throws TransactionNotFoundException, PaymentException {
        if (transactionDAO.lock(transactionId)) {
            Transaction transaction = transactionDAO.get(transactionId);
            cancelTransaction(user, transaction);
        } else {
            throw new TransactionNotFoundException();
        }
    }

    private void cancelTransaction(AuthenticatedUser user, Transaction transaction) throws PaymentException {
        if (transaction.isCancelled()) {
            LOG.warn("Skipping transaction cancellation. Transaction id=" + transaction.getId() + " is cancelled already.");
            return;
        }
        if (!transaction.isADPTransaction()) {
            cancelCCTransaction(user, transaction);
        }
        markReservationsCancelled(transaction.getReservations());
        setTransactionCancelled(transaction);
    }

    private void cancelCCTransaction(AuthenticatedUser user, Transaction transaction) throws PaymentException {
        cardService.cancelRealexAuthorisation(transaction.getOrderId(), new CardAuthDetails(transaction.getRealexAuthCode(), transaction.getRealexPassRef()));
        sendCancellationEmail(user, transaction);
    }

    private void sendCancellationEmail(AuthenticatedUser user, Transaction transaction) {
        try {
            Reservation reservation = transaction.getReservations().get(0);//there should be exactly one reservation for this kind of transaction TODO what if not (renewal transaction)
            String username = (user == null) ? null : user.getUsername();
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(transaction.getBillNicHandleId());  
            TransactionDetails transactionDetails = null;
            PaymentEmailParameters parameters = null;
            switch (reservation.getOperationType()) {
                case REGISTRATION:
                    String holder = domainSearchService.getDomainHolderForTicket(reservation.getTicketId());
                    transactionDetails = new TransactionDetails(reservation.getDomainName(), holder, Period.fromMonths(reservation.getDurationMonths()).getYears(), reservation.getOperationType(), reservation.getTotal());
                    parameters = new PaymentEmailParameters(username, transaction.getOrderId(), reservation.getTotal(), nicHandle, reservation.getDomainName(), transactionDetails);
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.NREG_CC_PREAUTH_RELEASED.getId(), parameters);
                    break;
                case TRANSFER:
                    Domain d = domainSearchService.getDomain(reservation.getDomainName());
                    transactionDetails = new TransactionDetails(reservation.getDomainName(), d.getHolder(), Period.fromMonths(reservation.getDurationMonths()).getYears(), reservation.getOperationType(), reservation.getTotal());
                    parameters = new PaymentEmailParameters(username, transaction.getOrderId(), reservation.getTotal(), nicHandle, reservation.getDomainName(), transactionDetails);
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.XFER_CC_PREAUTH_RELEASED.getId(), parameters);
                    break;
                case RENEWAL:
                    //skip
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation type: " + reservation.getOperationType());
            }
        } catch (Exception e) {
            LOG.error("Cannot send CC cancellation email: " + e);
        }
    }

    private void markReservationsCancelled(List<Reservation> reservations) {
        for (Reservation r : reservations) {
            final long reservationId = r.getId();
            if (reservationDAO.lock(reservationId)) {
                Reservation reservation = reservationDAO.get(reservationId);
                if (reservation.isSettled()) {
                    throw new IllegalStateException("Reservation is settled");
                }
                reservation.setReadyForSettlement(false);
                reservationDAO.update(reservation);
            }
        }
    }

    private void setTransactionCancelled(Transaction transaction) {
        transaction.markCancelled();
        transactionDAO.update(transaction);
    }

    @Override
    public void setTransactionFinanciallyPassed(long transactionId) throws TransactionNotFoundException {
        if (transactionDAO.lock(transactionId)) {
            Transaction transaction = transactionDAO.get(transactionId);
            transaction.setFinanciallyPassedDate(new Date());
            transactionDAO.update(transaction);
        } else {
            throw new TransactionNotFoundException();
        }
    }

    @Override
    public void setTransactionStartedSettlement(long transactionId) throws TransactionNotFoundException {
        if (transactionDAO.lock(transactionId)) {
            Transaction transaction = transactionDAO.get(transactionId);
            setTransactionStartedSettlement(transaction);
        } else {
            throw new TransactionNotFoundException();
        }
    }

    private void setTransactionStartedSettlement(Transaction transaction) throws TransactionNotFoundException {
        transaction.setSettlementStarted(true);
        transactionDAO.update(transaction);
    }

    @Override
    public List<Transaction> findAllTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy) {
        return transactionDAO.findAllTransactions(criteria, sortBy);
    }

    @Override
    public LimitedSearchResult<Transaction> findHistoricalTransactions(String billingNH, TransactionSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        criteria.setBillingNH(billingNH);
        return transactionHistDAO.find(criteria, offset, limit, sortBy);
    }

    @Override
    public void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId) throws TransactionNotFoundException, TransactionInvalidStateForSettlement {
        if (transactionDAO.lock(transactionId)) {
            Transaction transaction = transactionDAO.get(transactionId);
            validateTransactionToSettle(transaction);
            settleTransaction(user, opInfo, transaction);
        } else {
            throw new TransactionNotFoundException();
        }
    }

    private void validateTransactionToSettle(Transaction transaction) throws TransactionInvalidStateForSettlement {
        if (transaction.isCancelled()
                || !transaction.isSettlementStarted()
                || transaction.isSettlementEnded()
                || !transaction.isFinanciallyPassed()) {
            throw new TransactionInvalidStateForSettlement(transaction.toString());
        }
    }

    private void settleTransaction(AuthenticatedUser user, OpInfo opInfo, Transaction transaction) {
        List<Reservation> reservations = transaction.getReservations();
        if (Validator.isEmpty(reservations)) {
        	throw new IllegalArgumentException("Transaction (transactionId=" + transaction.getId() + ") does not have any reservations");        	
        }
        boolean settlementSucceed = false;
        if (transaction.isADPTransaction()) {
            settlementSucceed = settleADPTransaction(transaction.getTotalCost(), getBillingNH(reservations), transaction.getOrderId());
        } else {
            //cannot rollback settled Realex tranaction
            settlementSucceed = settleCCTransaction(transaction);
        }

        if (settlementSucceed) {
            try {
                settleReservationsAndRunPaymentSettledEvent(user, opInfo, reservations);
                setTransactionSettlementEnded(transaction);
            } catch (RuntimeException e) {
                if (!transaction.isADPTransaction()) {
                    LOG.error("Realex transaction has been settled and will not be rollback!", e);
                }
                throw e;
            }
        } else {
            runPaymentFailedEvent(user, opInfo, reservations);
        }
    }

    private String getBillingNH(List<Reservation> reservations) {
    	return reservations.get(0).getNicHandleId();
    }

    private boolean settleADPTransaction(int total, String billingNH, String orderId) {
        try {
            depositService.reduceDeposit(billingNH, MoneyUtils.getValueInStandardCurrencyUnit(total), orderId, DepositTransactionType.SETTLEMENT, null, null);
        } catch (NotEnoughtDepositFundsException e) {
            LOG.warn("Not enought deposit founds to settle transaction, orderId=" + orderId + ", billingNH=" + billingNH, e);
            return false;
        }
        return true;
    }

    private void settleReservationsAndRunPaymentSettledEvent(AuthenticatedUser user, OpInfo opInfo, List<Reservation> reservations) {
        Date currDate = new Date();
        for (Reservation r : reservations) {
            final long reservationId = r.getId();
            if (reservationDAO.lock(reservationId)) {
                Reservation reservation = reservationDAO.get(reservationId);
                if (reservation.isSettled()) {
                    throw new IllegalStateException("Reservation already settled! reservationId=" + reservation.getId());
                }
                if (!reservation.isReadyForSettlement()) {
                    throw new IllegalStateException("Reservation not ready for settlement! reservationId=" + reservation.getId());
                }
                reservation.markSettled(currDate);

                Domain d;
                try {
                    d = domainSearchService.getDomain(reservation.getDomainName());
                } catch (DomainNotFoundException e) {
                    LOG.error("Data inconsistent! Cannot find domain " + reservation.getDomainName(), e);
                    throw new IllegalStateException("Data inconsistent! Cannot find domain " + reservation.getDomainName(), e);
                }
                reservation.setStartDate(d.getRenewDate());
                if (!isGIBOReservation(reservation)) {
                    dsm.handleEvent(user, d, new PaymentSettledEvent(Period.fromMonths(reservation.getDurationMonths()).getYears()), opInfo);
                }
                reservation.setEndDate(d.getRenewDate());

                reservationDAO.update(reservation);
            }
        }
    }

    private boolean isGIBOReservation(Reservation reservation) {
        return reservation.getTicketId() == null && reservation.getOperationType() == OperationType.REGISTRATION;
    }

    private boolean settleCCTransaction(Transaction transaction) {
        try {
            cardService.settleRealexAuthorisation(transaction.getOrderId(), new CardAuthDetails(transaction.getRealexAuthCode(),transaction.getRealexPassRef()));
        } catch (PaymentException e) {
            LOG.warn("Cannot settle Realex transaction, orderId=" + transaction.getOrderId(), e);
            return false;
        }
        return true;
    }

    private void setTransactionSettlementEnded(Transaction transaction) {
        transaction.setSettlementEnded(true);
        transactionDAO.update(transaction);
    }

    private void runPaymentFailedEvent(AuthenticatedUser user, OpInfo opInfo, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
        	DsmEvent event = new SettlementFailureEvent(reservation.getPaymentMethod());
            String domainName = reservation.getDomainName();
            dsm.handleEvent(user, domainName, event, opInfo);
        }
    }

    @Override
    public int createInvoiceAndAssociateWithTransactions(String nicHandleId, List<Transaction> transactions) throws NicHandleNotFoundException, TransactionNotFoundException {
        int totalCost = 0;
        int totalNetAmount = 0;
        int totalVatAmount = 0;
        for (Transaction transaction : transactions) {
            totalCost = totalCost + transaction.getTotalCost();
            totalNetAmount = totalNetAmount + transaction.getTotalNetAmount();
            totalVatAmount = totalVatAmount + transaction.getTotalVatAmount();
        }
        int newInvoiceId = createInvoice(nicHandleId, VersionInfo.getRevision(), totalCost, totalNetAmount, totalVatAmount);
        associateTransactionsWithInvoice(newInvoiceId, transactions);
        moveToHistory(transactions);
        return newInvoiceId;
    }

    private void moveToHistory(List<Transaction> transactions) {
        for (Transaction t : transactions) {
            if (transactionDAO.lock(t.getId())) {
                Transaction transaction = transactionDAO.get(t.getId());
                transactionHistDAO.create(transaction);
                for (Reservation r : transaction.getReservations()) {
                    if (reservationDAO.lock(r.getId())) {
                        Reservation reservation = reservationDAO.get(r.getId());
                        reservationHistDAO.create(reservation);
                        reservationDAO.deleteById(reservation.getId());
                    }
                }
                transactionDAO.deleteById(transaction.getId());
            }
        }
    }

    private void associateTransactionsWithInvoice(int invoiceId, List<Transaction> transactions) throws TransactionNotFoundException {
        for (Transaction transaction : transactions) {
            associateTransactionWithInvoice(transaction.getId(), invoiceId);
        }
    }

    private void associateTransactionWithInvoice(long transactionId, int invoiceId) throws TransactionNotFoundException {
        if (transactionDAO.lock(transactionId)) {
            Transaction transaction = transactionDAO.get(transactionId);
            if (transaction.getInvoiceId() != null) {
                throw new IllegalStateException("Transaction already associated with invoice! transactionId=" + transaction.getId());
            }
            transaction.setInvoiceId(invoiceId);
            transactionDAO.update(transaction);
        } else {
            throw new TransactionNotFoundException();
        }
    }

    private int createInvoice(String nicHandleId, String CRSRevision, int totalCost, int totalNetAmount, int totalVatAmount) throws NicHandleNotFoundException {
        Date currentDate = new Date();
        int invoiceNumber = invoicingService.getNextInvoiceNumber(currentDate);
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        Invoice newInvoice = Invoice.newInstance(
                invoiceNumber,
                nicHandle.getAccount().getName(),
                nicHandle.getAccount().getId(),
                nicHandle.getAddress(),
                null,
                null,
                nicHandleId,
                nicHandle.getCountry(),
                nicHandle.getCounty(),
                CRSRevision,
                currentDate,
                "TODO: MD5",
                totalCost,
                totalNetAmount,
                totalVatAmount);
        LOG.info("Creating new invoice, number=" + invoiceNumber);
        int id = invoiceDAO.createInvoice(newInvoice);
        LOG.info("Invoice created, number=" + invoiceNumber + ", id=" + id);
        return id;
    }

    @Override
    public boolean invalidateTransactionsIfNeeded(AuthenticatedUser user, long transactionId) throws TransactionNotFoundException, NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException {
        if (transactionDAO.lock(transactionId)) {
            Transaction transaction = transactionDAO.get(transactionId);
            if (transaction.isCancelled() || transaction.isFinanciallyPassed() || transaction.isSettlementStarted() || transaction.isSettlementEnded() || transaction.isInvalidated()) {
                throw new IllegalStateException("Transaction invalid state for invalidation transactionId=" + transactionId);
            }
            if (isTransactionInvalidationNeeded(transaction)) {
                invalidateTransaction(user, transaction);
                return true;
            } else {
                return false;
            }
        } else {
            throw new TransactionNotFoundException();
        }
    }

    private boolean isTransactionInvalidationNeeded(Transaction transaction) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        if (transaction.getReservations() == null || transaction.getReservations().size() == 0) {
            throw new IllegalStateException("There is no reservations for transaction id=" + transaction.getId());
        }
        DomainPricingDictionary pricingDictionary = new DomainPricingDictionary(domainPricingDAO);
        for (Reservation reservation : transaction.getReservations()) {
            PriceWithVat priceWithVat = preparePriceWithVat(reservation.getNicHandleId(), Period.fromMonths(reservation.getDurationMonths()), reservation.getOperationType(), pricingDictionary);
            if (isVatOrProductPriceChanged(reservation, priceWithVat)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVatOrProductPriceChanged(Reservation reservation, PriceWithVat priceWithVat) {
        return reservation.getVatId() != priceWithVat.getVat().getId()
                || reservation.getNetAmount().compareTo(priceWithVat.getNetAmount()) != 0;
    }

    private void invalidateTransaction(AuthenticatedUser user, Transaction transaction) throws TransactionNotFoundException, PaymentException {
        cancelTransaction(user, transaction);
        transaction.markInvalidated();
        transactionDAO.update(transaction);
    }

    @Override
    public Invoice getInvoice(int invoiceId) throws InvoiceNotFoundException {
        Invoice invoice = invoiceDAO.get(invoiceId);
        if (invoice == null) {
            throw new InvoiceNotFoundException("Cannot fond invoice with id :" + invoiceId);
        }
        return invoice;
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        invoiceDAO.update(invoice);
    }

    @Override
    public LimitedSearchResult<Invoice> findInvoices(InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return invoiceDAO.find(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<Invoice> findSimpleInvoices(InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return invoiceDAO.findSimple(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<ExtendedInvoice> findExtendedInvoices(ExtendedInvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return invoiceDAO.findExtended(criteria, offset, limit, sortBy);
    }

    @Override
    public List<DomainInfo> getInvoiceInfo(String invoiceNumber) {
        return invoiceDAO.getInvoiceInfo(invoiceNumber);
    }

    @Override
    public List<TransactionInfo> getReadyADPTransactionsReport(String billingNH) throws DepositNotFoundException {
        Validator.assertNotEmpty(billingNH, "billingNH");
        TransactionSearchCriteria readyNotSettledCriteria = new TransactionSearchCriteria();
        readyNotSettledCriteria.setBillingNH(billingNH);
        readyNotSettledCriteria.setReadyForSettlement(true);
        readyNotSettledCriteria.setSettlementEnded(false);
        readyNotSettledCriteria.setPaymentMethod(PaymentMethod.ADP);
        List<Transaction> transactions = findAllTransactions(readyNotSettledCriteria, Arrays.asList(new SortCriterion("financiallyPassedDate", true)));

        DepositInfo depositInfo = depositService.viewDeposit(billingNH);
        BigDecimal availableBalance = MoneyUtils.getRoudedAndScaledValue(depositInfo.getCloseBal());

        List<TransactionInfo> ret = new ArrayList<TransactionInfo>(transactions.size());
        for (Transaction transaction : transactions) {
            BigDecimal transactionTotal = MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(transaction.getTotalCost());
            availableBalance = availableBalance.subtract(transactionTotal);
            ret.add(new TransactionInfo(
                    transaction.getFinanciallyPassedDate(),
                    transaction.getOrderId(),
                    transaction.getOperationType(),
                    transactionTotal,
                    availableBalance
            ));
        }
        return ret;
    }

    @Override
    public LimitedSearchResult<ReauthoriseTransaction> getTransactionToReauthorise(String billingNH, long offset, long limit, List<SortCriterion> sortBy) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH(billingNH);
        criteria.setInvalidated(true);
        criteria.setReauthorised(false);
        criteria.setPaymentMethod(PaymentMethod.CC);
        criteria.setTicketExists(true);
        LimitedSearchResult<Transaction> transactions = transactionDAO.find(criteria, offset, limit, sortBy);
        return prepareTransactionsToReauthorise(transactions, sortBy);
    }

    private LimitedSearchResult<ReauthoriseTransaction> prepareTransactionsToReauthorise(LimitedSearchResult<Transaction> transactions, List<SortCriterion> sortBy) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        if (transactions.getResults().size() == 0) {
            List<ReauthoriseTransaction> empty = Collections.emptyList();
            return new LimitedSearchResult<ReauthoriseTransaction>(null, null, transactions.getLimit(), transactions.getOffset(), empty, 0);
        } else {
            List<ReauthoriseTransaction> reauthoriseTransactions = new ArrayList<ReauthoriseTransaction>(transactions.getResults().size());
            DomainPricingDictionary pricingDictionary = new DomainPricingDictionary(domainPricingDAO);
            for (Transaction t : transactions.getResults()) {
                if (t.getReservations().size() > 1)
                    throw new IllegalStateException("Transaction to reauthorise has to many reservations. Transaction id=" + t.getId());
                Reservation oldReservation = t.getReservations().get(0);
                PriceWithVat priceWithVat = preparePriceWithVat(oldReservation.getNicHandleId(), Period.fromMonths(oldReservation.getDurationMonths()), oldReservation.getOperationType(), pricingDictionary);
                ReauthoriseTransaction rt = new ReauthoriseTransaction(
                        t.getId(),
                        t.getOperationType(),
                        oldReservation.getDomainName(),
                        oldReservation.getTotal(),
                        oldReservation.getNetAmount(),
                        oldReservation.getVatAmount(),
                        t.getOrderId(),
                        priceWithVat.getTotal(),
                        priceWithVat.getNetAmount(),
                        priceWithVat.getVatAmount(),
                        Period.fromMonths(oldReservation.getDurationMonths()).getYears());
                reauthoriseTransactions.add(rt);
            }
            return new LimitedSearchResult<ReauthoriseTransaction>(null, sortBy, transactions.getLimit(), transactions.getOffset(), reauthoriseTransactions, transactions.getTotalResults());
        }
    }

    @Override
    public PaymentSummary reauthoriseTransaction(Transaction transaction, Ticket ticket, PaymentRequest paymentRequest)
            throws DomainNotFoundException, NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException {
        Reservation oldReservation = transaction.getReservations().get(0);
        PriceWithVat priceWithVat = preparePriceWithVat(oldReservation.getNicHandleId(), Period.fromMonths(oldReservation.getDurationMonths()), oldReservation.getOperationType());

        paymentRequest.setAmount(priceWithVat.getTotal());
        ExtendedPaymentRequest extendedRequest = cardService.authorisePaymentTransaction(paymentRequest, CardType.CREDIT);
        String realexAuthcode = extendedRequest.getAuthcode();
        String realexPassref = extendedRequest.getPassref();
        String orderId = extendedRequest.getOrderId();

        Reservation newReservation = null;
        String domainName = oldReservation.getDomainName();
        long newTransactionId;
        if (isTriplePassed(ticket)) {
            newTransactionId = recreateTransaction(priceWithVat.getTotal(), priceWithVat.getNetAmount(), priceWithVat.getVatAmount(), orderId, true, realexAuthcode, realexPassref);
            newReservation = Reservation.recreatedInstanceForTicket(oldReservation.getNicHandleId(), domainName, oldReservation.getDurationMonths(), priceWithVat,
                    oldReservation.getTicketId(), newTransactionId, oldReservation.getOperationType(), PaymentMethod.CC, true);
        } else {
            newTransactionId = recreateTransaction(priceWithVat.getTotal(), priceWithVat.getNetAmount(), priceWithVat.getVatAmount(), orderId, false, realexAuthcode, realexPassref);
            newReservation = Reservation.recreatedInstanceForTicket(oldReservation.getNicHandleId(), domainName, oldReservation.getDurationMonths(), priceWithVat,
                    oldReservation.getTicketId(), newTransactionId, oldReservation.getOperationType(), PaymentMethod.CC, false);
        }
        reservationDAO.createReservation(newReservation);
        markTransactionReauthorised(transaction, newTransactionId);
        return new PaymentSummary(domainName, null, null, priceWithVat.getPeriod().getYears(), priceWithVat.getNetAmount(), priceWithVat.getVatAmount(), priceWithVat.getTotal(), orderId);
    }

    private void markTransactionReauthorised(Transaction oldTransaction, long newTransactionId) {
        oldTransaction.setReauthorisedId(newTransactionId);
        transactionDAO.update(oldTransaction);
    }

    private boolean isTriplePassed(Ticket ticket) {
        return ticket.getAdminStatus().getId() == AdminStatus.PASSED && ticket.getTechStatus().getId() == TechStatus.PASSED && ticket.getFinancialStatus().getId() == FinancialStatus.PASSED;
    }

    private long recreateTransaction(BigDecimal total, BigDecimal net, BigDecimal vat, String orderId, boolean financiallyPassed, String realexAuthcode, String realexPassref) {
        return transactionDAO.createTransaction(Transaction.recreatedInstance(getValueInLowestCurrencyUnit(total), getValueInLowestCurrencyUnit(net), getValueInLowestCurrencyUnit(vat),
                orderId, financiallyPassed ? new Date() : null, realexAuthcode, realexPassref));
    }

    @Override
    public InputStream viewXmlInvoice(String invoiceNumber) throws InvoiceNotFoundException {
        return getInvoiceStream(invoiceNumber, NameFormatter.NamePostfix.xml);
    }

    @Override
    public InputStream viewPdfInvoice(String invoiceNumber) throws InvoiceNotFoundException {
        return getInvoiceStream(invoiceNumber, NameFormatter.NamePostfix.pdf);
    }

    private InputStream getInvoiceStream(String invoiceNumber, NameFormatter.NamePostfix postfix) throws InvoiceNotFoundException {
        Invoice invoice = getInvoiceByNumber(invoiceNumber);
        InvoiceExportConfiguration exportConfiguration = getExportConfiguration(postfix);
        String formattedName = NameFormatter.getFormattedName(invoice.getInvoiceNumber(), postfix);
        TargetFileInfo config = exportConfiguration.archiveFileConfig(formattedName, invoice.getInvoiceDate());
        try {
            InputStream stream = FileUtils.openInputStream(config.getTargetFile(false));
            return stream;
        } catch (IOException e) {
            LOG.error("Error when trying to read invoice file : " + config.toString(), e);
            throw new InvoiceNotFoundException("Problem wile reading invoice file : " + config.toString());
        }
    }

    private Invoice getInvoiceByNumber(String invoiceNumber) throws InvoiceNotFoundException {
        Invoice invoice = invoiceDAO.getByNumber(invoiceNumber);
        if (invoice == null) {
            throw new InvoiceNotFoundException("Cannot find invoice with number :" + invoiceNumber);
        }
        return invoice;
    }

    private InvoiceExportConfiguration getExportConfiguration(NameFormatter.NamePostfix postfix) {
        switch (postfix) {
            case xml:
                return (InvoiceExportConfiguration) applicationConfig.getXmlInvoiceExportConfig();
            case pdf:
                return (InvoiceExportConfiguration) applicationConfig.getPdfInvoiceExportConfig();
            default:
                throw new IllegalArgumentException("Invalid file type : " + postfix);
        }
    }

    @Override
    public void sendEmailWithInvoices(String invoiceNumber, AuthenticatedUser user) throws InvoiceEmailException {
        sendEmailWithInvoices(EmailTemplateNamesEnum.SEND_INVOICE.getId(), invoiceNumber, user, false);
    }
    
    private void sendEmailWithInvoices(int templateId, String invoiceNumber, AuthenticatedUser user, boolean useLocalStorage) throws InvoiceEmailException {
        try {
            String username = (user == null) ? null : user.getUsername();
            Invoice invoice = getInvoiceByNumber(invoiceNumber);
            NicHandle billingNH = nicHandleSearchService.getNicHandle(invoice.getBillingNicHandle());
            List<File> attachments = getAttachments(invoice, billingNH, useLocalStorage);
            InvoiceEmailParameters parameters = new InvoiceEmailParameters(billingNH, invoice.getInvoiceDate(), username);
            emailTemplateSender.sendEmail(templateId, parameters, attachments);
        } catch (Exception e) {
            throw new InvoiceEmailException(e);
        }
    }

    private List<File> getAttachments(Invoice invoice, NicHandle billingNH, boolean useLocal) throws FileNotFoundException {
        boolean includePdf;
        boolean includeXml;
        if (isDirect(billingNH)) {
            includePdf = true;
            includeXml = false;
        } else {//is registrar
            ResellerDefaults defaults = null;
            try {
                defaults = resellerDefaultsService.get(billingNH.getNicHandleId());
                switch (defaults.getEmailInvoiceFormat()) {
                    case XML:
                        includePdf = false;
                        includeXml = true;
                        break;
                    case PDF:
                        includePdf = true;
                        includeXml = false;
                        break;
                    case BOTH:
                        includePdf = true;
                        includeXml = true;
                        break;
                    case NONE:
                    default:
                        throw new IllegalArgumentException("Invalid email invoice format: " + defaults.getEmailInvoiceFormat());
                }
            } catch (DefaultsNotFoundException e) {
                LOG.info("Defaults not found for registrar: " + billingNH.getNicHandleId());
                includePdf = true;
                includeXml = false;
            }
        }
        List<File> attachments = new ArrayList<File>();
        if (includePdf) {
            File pdfFile = getAttachment(invoice, NameFormatter.NamePostfix.pdf, useLocal);
            attachments.add(pdfFile);
        }
        if (includeXml) {
            File xmlFile = getAttachment(invoice, NameFormatter.NamePostfix.xml, useLocal);
            attachments.add(xmlFile);
        }
        return attachments;
    }

    private File getAttachment(Invoice invoice, NameFormatter.NamePostfix postfix, boolean useLocal) throws FileNotFoundException {
        InvoiceExportConfiguration exportConfiguration = getExportConfiguration(postfix);
        String formattedName = NameFormatter.getFormattedName(invoice.getInvoiceNumber(), postfix);
        TargetFileInfo config;
        if (useLocal) {
            config = exportConfiguration.fileConfig(formattedName, invoice.getInvoiceDate());
        } else {
            config = exportConfiguration.archiveFileConfig(formattedName, invoice.getInvoiceDate());
        }
        File file = config.getTargetFile(false);
        if(!file.isFile()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        return file;
    }

    @Override
    public void sendInvoicingSummaryEmail(String invoiceNumber, AuthenticatedUser user) {
        try {
            Invoice invoice = getInvoiceByNumber(invoiceNumber);
            NicHandle billingNH = nicHandleSearchService.getNicHandle(invoice.getBillingNicHandle());
            String username = (user == null) ? null : user.getUsername();
            InvoiceEmailParameters parameters = new InvoiceEmailParameters(billingNH, invoice.getInvoiceDate(), username);
            List<File> attachments = getAttachments(invoice, billingNH, true);
            PaymentMethod paymentMethod = invoice.getPaymentMethod();
            switch (paymentMethod) {
                case ADP:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INVOICE_SUMMARY_ADP.getId(), parameters, attachments);
                    break;
                case CC:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INVOICE_SUMMARY_CC.getId(), parameters, attachments);
                    break;
                case DEB:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INVOICE_SUMMARY_DEB.getId(), parameters, attachments);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
            }
        } catch (Exception e) {
            LOG.error("Problem with sending invoice summary email for invoice number: " + invoiceNumber, e);
        }
    }

    @Override
    public List<DomainInfo> getTransactionInfo(long transactionId) {
        return transactionHistDAO.getTransactionInfo(transactionId);
    }

    @Override
    public List<DomainInfo> getTransactionInfo(String orderId) {
        return transactionHistDAO.getTransactionInfo(orderId);
    }
}