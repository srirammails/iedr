package pl.nask.crs.app.payment.impl;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.DomainIncorrectStateForPaymentException;
import pl.nask.crs.payment.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.payment.exceptions.DuplicatedDomainException;
import pl.nask.crs.payment.exceptions.InvoiceEmailException;
import pl.nask.crs.payment.exceptions.InvoiceNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.NotEnoughtDepositFundsException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.ProductPriceNotFoundException;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.PriceNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.exceptions.NextValidVatNotFoundException;
import pl.nask.crs.vat.exceptions.VatFromDuplicationException;
import pl.nask.crs.vat.exceptions.VatNotFoundException;

/**
 * @author: Marcin Tkaczyk
 */
public class PaymentAppServiceImpl implements PaymentAppService {

    private ServicesRegistry services;
	private PaymentService paymentService;
	
    protected final static Logger log = Logger.getLogger(PaymentAppServiceImpl.class);

    public PaymentAppServiceImpl(ServicesRegistry services) {
    	Validator.assertNotNull(services, "services registry");
        this.services = services;
        this.paymentService = services.getPaymentService();
    }

    @Override
    @Transactional(readOnly=true)
    public DepositInfo viewUserDeposit(AuthenticatedUser user) throws AccessDeniedException, DepositNotFoundException {
        validateLoggedIn(user);
        return services.getDepositService().viewDeposit(user.getUsername());
    }

    @Transactional(readOnly = true)
    @Override
    public DepositInfo viewDeposit(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException, DepositNotFoundException {
        validateLoggedIn(user);
        return services.getDepositService().viewDeposit(nicHandleId);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<DepositInfo> findDeposits(AuthenticatedUser user, DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getDepositService().findDeposits(criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Deposit> findUserHistoricalDeposits(AuthenticatedUser user, DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        criteria.setNicHandleId(user.getUsername());
        return services.getDepositService().findDepositWithHistory(criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Deposit> findDepositWithHistory(AuthenticatedUser user, DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getDepositService().findDepositWithHistory(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly=true)
    public LimitsPair getDepositLimits(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getDepositService().getDepositLimits();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DepositInfo depositFunds(AuthenticatedUser user, PaymentRequest requst) throws AccessDeniedException, PaymentException, ExportException {
        validateLoggedIn(user);
        return services.getDepositService().depositFunds(user, user.getUsername(), requst);
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Deposit correctDeposit(AuthenticatedUser user, String nicHandle, int amountInLowestCurrencyUnit, String remark) throws NotEnoughtDepositFundsException, NicHandleNotFoundException, ExportException {
    	validateLoggedIn(user);
    	Validator.assertNotEmpty(remark,  "remark");
    	return services.getDepositService().correctDeposit(nicHandle, amountInLowestCurrencyUnit, user.getUsername(), remark);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Deposit depositFundsOffline(AuthenticatedUser user, String nicHandle, int amountInLowestCurrencyUnit, String remark)
            throws NotEnoughtDepositFundsException, NicHandleNotFoundException, ExportException {
        validateLoggedIn(user);
        Validator.assertNotEmpty(remark,  "remark");
        return services.getDepositService().depositFundsOffline(user, nicHandle, amountInLowestCurrencyUnit, user.getUsername(), remark);
    }

	@Override
    @Transactional(readOnly=true)
	public BigDecimal getProductPrice(AuthenticatedUser user, int periodInYears, OperationType type)
			throws AccessDeniedException, ProductPriceNotFoundException, NicHandleNotFoundException {
		validateLoggedIn(user);
        try {
            return paymentService.getProductPrice(Period.fromYears(periodInYears), type, user.getUsername()).getPrice();
        } catch (NotAdmissiblePeriodException e) {
            throw new ProductPriceNotFoundException("Product price not found for period=" + periodInYears + " ,operationType=" + type);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<DomainPrice> getDomainPricing(AuthenticatedUser user) throws NicHandleNotFoundException {
        validateLoggedIn(user);
        return paymentService.getDomainPricing(user.getUsername());
    }

    @Override
    @Transactional(readOnly=true)
	public double getVatRate(AuthenticatedUser user)
			throws AccessDeniedException {
		validateLoggedIn(user);
		return paymentService.getVatRate(user.getUsername());
	}

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<DepositTopUp> getTopUpHistory(AuthenticatedUser user, Date fromDate, Date toDate, long offset, long limit) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getDepositService().getTopUpHistory(user.getUsername(), fromDate, toDate, offset, limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentSummary pay(AuthenticatedUser user, Map<String, Period> domainsWithPeriods, PaymentMethod paymentMethod, PaymentRequest paymentRequest, boolean test) throws AccessDeniedException, DuplicatedDomainException, DomainNotFoundException, DomainManagedByAnotherResellerException, DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException, NotEnoughtDepositFundsException, PaymentException {
        validateLoggedIn(user);
        switch (paymentMethod) {
            case ADP:
                return paymentService.payADP(user, user.getUsername(), user.getSuperUserName(), domainsWithPeriods, test);
            case CC:
                return paymentService.payCC(user, user.getUsername(), user.getSuperUserName(), domainsWithPeriods, paymentRequest, test);
            case DEB:
                return paymentService.payDEB(user, user.getUsername(),  user.getSuperUserName(), domainsWithPeriods, paymentRequest, test);
            default:
                throw new IllegalArgumentException("payment method not allowed");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
	public PaymentSummary autorenew(AuthenticatedUser user, String domainName)
			throws AccessDeniedException, DomainNotFoundException,
			DomainIncorrectStateForPaymentException,
			NotEnoughtDepositFundsException, NicHandleNotFoundException, DomainManagedByAnotherResellerException, NotAdmissiblePeriodException, PaymentException {
		
		// check, if the domain should be autorenew, which means if it's RenewalMode allows for autorenewal and there are no renewal payments waiting for settlement
		return paymentService.autorenew(user, domainName);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autorenewAll(AuthenticatedUser user) {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setRenewTo(new Date());
        criteria.setDomainRenewalModes(RenewalMode.Autorenew, RenewalMode.RenewOnce);
        int offset = 0;
        final int limit = 100;
        while (true) {
            List<String> domains = services.getDomainSearchService().findDomainNames(criteria, offset, limit);
            if (domains.isEmpty()) {
                break; // no more domains in autorenew state
            }
            offset += limit;
            for (String domainName : domains) {
                try {
                    log.debug("Trying to auto renew domain: " + domainName);
                    paymentService.autorenew(user, domainName);
                } catch (DomainNotFoundException e) {
                    logFailure(domainName, "Domain not found");
                } catch (DomainIncorrectStateForPaymentException e) {
                    logFailure(domainName, "Domain state dissalows payments");
                } catch (NotEnoughtDepositFundsException e) {
                    logFailure(domainName, "Not enough founds to perform ADP payment");
                } catch (NicHandleNotFoundException e) {
                    logError(domainName, "No such nicHandle: " + e.getNicHandleId());
                } catch (DomainManagedByAnotherResellerException e) {
                    logError(domainName, "Domain managed by another reseller");
                } catch (NotAdmissiblePeriodException e) {
                    logError(domainName, "Not admissible period for the renewal operation: " + e.getPeriod());
                } catch (Exception e) {
                    log.error("Domain renewal failed for " + domainName, e);
                }
            }
        }
    }

    private void logFailure(String domainName, String message) {
        log.warn("Domain renewal failed for " + domainName + " : " + message);
    }

    private void logError(String domainName, String message) {
        log.error("Domain renewal failed (this should newer happen!) for " + domainName + " : " + message);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Reservation> getNotSettledReservations(AuthenticatedUser user, ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return paymentService.findReservations(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationTotals getNotSettledReservationsTotals(AuthenticatedUser user, boolean adp) {
        validateLoggedIn(user);
    	return paymentService.getNotSettledReservationsTotals(user.getUsername(), adp);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Reservation> findHistoricalReservations(AuthenticatedUser user, ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        return paymentService.findHistoricalReservations(user.getUsername(), criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> findAllTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy) {
        return paymentService.findAllTransactions(criteria, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> getSettledTransactionHistory(AuthenticatedUser user, PaymentMethod paymentMethod) {
        validateLoggedIn(user);
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH(user.getUsername());
        criteria.setSettlementEnded(true);
        criteria.setPaymentMethod(paymentMethod);
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("financiallyPassedDate", false));
        return paymentService.findAllTransactions(criteria, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<ReauthoriseTransaction> getTransactionToReauthorise(AuthenticatedUser user, long offset, long limit, List<SortCriterion> sortBy) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        validateLoggedIn(user);
        return paymentService.getTransactionToReauthorise(user.getUsername(), offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionInfo> getReadyADPTransactionsReport(AuthenticatedUser user, String billingNH) throws DepositNotFoundException {
        validateLoggedIn(user);
        return paymentService.getReadyADPTransactionsReport(billingNH);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Transaction> findHistoricalTransactions(AuthenticatedUser user, TransactionSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        return  paymentService.findHistoricalTransactions(user.getUsername(), criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addVatRate(AuthenticatedUser user, String category, Date from, double vatRate) throws AccessDeniedException, VatFromDuplicationException, ThirdDecimalPlaceException {
        validateLoggedIn(user);
        return services.getVatService().addVatRate(category, from, vatRate, user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invalidate(AuthenticatedUser user, int vatId) throws AccessDeniedException, VatNotFoundException, NextValidVatNotFoundException {
        validateLoggedIn(user);
        services.getVatService().invalidate(vatId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vat> getValid(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getVatService().getValid();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getVatCategories(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getVatService().getCategories();
    }

    @Override
    public LimitedSearchResult<DomainPrice> findAllPrices(AuthenticatedUser user, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        return services.getPriceService().findAll(offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public DomainPrice getPrice(AuthenticatedUser user, String code) throws AccessDeniedException, PriceNotFoundException {
        validateLoggedIn(user);
        return services.getPriceService().get(code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPrice(AuthenticatedUser user, String code, String decription, BigDecimal price, int duration, Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct)
            throws ThirdDecimalPlaceException {
        validateLoggedIn(user);
        services.getPriceService().addPrice(user, code, decription, price, duration, validFrom, validTo, forRegistration, forRenewal, direct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyPrice(AuthenticatedUser user, String code, String decription, BigDecimal price, int duration, Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct)
            throws PriceNotFoundException, ThirdDecimalPlaceException {
        validateLoggedIn(user);
        DomainPrice domainPrice = services.getPriceService().get(code);
        domainPrice.setDescription(decription);
        domainPrice.setPrice(price);
        domainPrice.setDuration(duration);
        domainPrice.setValidFrom(validFrom);
        domainPrice.setValidTo(validTo);
        domainPrice.setForRegistration(forRegistration);
        domainPrice.setForRenewal(forRenewal);
        domainPrice.setDirect(direct);
        services.getPriceService().save(domainPrice, user);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Invoice> findInvoices(AuthenticatedUser user, InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        return services.getPaymentService().findInvoices(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Invoice> findUserInvoices(AuthenticatedUser user, InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        criteria.setBillingNhId(user.getUsername());
        return services.getPaymentService().findSimpleInvoices(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<ExtendedInvoice> findExtendedInvoices(AuthenticatedUser user, ExtendedInvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        return services.getPaymentService().findExtendedInvoices(criteria, offset, limit,sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DomainInfo> getInvoiceInfo(AuthenticatedUser user, String invoiceNumber) {
        validateLoggedIn(user);
        return services.getPaymentService().getInvoiceInfo(invoiceNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DomainInfo> getTransactionInfo(AuthenticatedUser user, long transactionId) {
        validateLoggedIn(user);
        return services.getPaymentService().getTransactionInfo(transactionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DomainInfo> getTransactionInfo(AuthenticatedUser user, String orderId) {
        validateLoggedIn(user);
        return services.getPaymentService().getTransactionInfo(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public InputStream viewXmlInvoice(AuthenticatedUser user, String invoiceNumber) throws InvoiceNotFoundException {
        validateLoggedIn(user);
        return services.getPaymentService().viewXmlInvoice(invoiceNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public InputStream viewPdfInvoice(AuthenticatedUser user, String invoiceNumber) throws InvoiceNotFoundException {
        validateLoggedIn(user);
        return services.getPaymentService().viewPdfInvoice(invoiceNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public void sendEmailWithInvoices(AuthenticatedUser user, String invoiceNumber) throws InvoiceEmailException {
        validateLoggedIn(user);
        services.getPaymentService().sendEmailWithInvoices(invoiceNumber, user);
    }
}
