package pl.nask.crs.app.payment;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.*;
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
public interface PaymentAppService {

    DepositInfo viewUserDeposit(AuthenticatedUser user) throws AccessDeniedException, DepositNotFoundException;

    DepositInfo viewDeposit(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException, DepositNotFoundException;

    LimitedSearchResult<DepositInfo> findDeposits(AuthenticatedUser user, DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<Deposit> findUserHistoricalDeposits(AuthenticatedUser user, DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<Deposit> findDepositWithHistory(AuthenticatedUser user, DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitsPair getDepositLimits(AuthenticatedUser user) throws AccessDeniedException;

    DepositInfo depositFunds(AuthenticatedUser user, PaymentRequest requst) throws AccessDeniedException, PaymentException, ExportException;

    double getVatRate(AuthenticatedUser user) throws AccessDeniedException;

    BigDecimal getProductPrice(AuthenticatedUser user, int periodInYears, OperationType type) throws AccessDeniedException, ProductPriceNotFoundException, NicHandleNotFoundException;

    List<DomainPrice> getDomainPricing(AuthenticatedUser user) throws NicHandleNotFoundException;

    LimitedSearchResult<DepositTopUp> getTopUpHistory(AuthenticatedUser user, Date fromDate, Date toDate, long offset, long limit) throws AccessDeniedException;

    PaymentSummary pay(AuthenticatedUser user, Map<String, Period> domainsWithPeriods, PaymentMethod paymentMethod, PaymentRequest paymentRequest, boolean test) throws AccessDeniedException, DuplicatedDomainException, DomainNotFoundException, DomainManagedByAnotherResellerException, DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException, NotEnoughtDepositFundsException, PaymentException;

    PaymentSummary autorenew(AuthenticatedUser user, String domainName) throws AccessDeniedException, DomainNotFoundException, DomainIncorrectStateForPaymentException, NotEnoughtDepositFundsException, NicHandleNotFoundException, DomainManagedByAnotherResellerException, NotAdmissiblePeriodException, PaymentException;

    void autorenewAll(AuthenticatedUser user);

    LimitedSearchResult<Reservation> getNotSettledReservations(AuthenticatedUser user, ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

	ReservationTotals getNotSettledReservationsTotals(AuthenticatedUser user, boolean adp);

    LimitedSearchResult<Reservation> findHistoricalReservations(AuthenticatedUser user, ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    List<Transaction> findAllTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy);

    List<Transaction> getSettledTransactionHistory(AuthenticatedUser user, PaymentMethod paymentMethod);

    LimitedSearchResult<ReauthoriseTransaction> getTransactionToReauthorise(AuthenticatedUser user, long offset, long limit, List<SortCriterion> sortBy) throws NotAdmissiblePeriodException, NicHandleNotFoundException;

    List<TransactionInfo> getReadyADPTransactionsReport(AuthenticatedUser user, String billingNH) throws DepositNotFoundException;

    LimitedSearchResult<Transaction> findHistoricalTransactions(AuthenticatedUser user, TransactionSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);


    int addVatRate(AuthenticatedUser user, String category, Date from, double vatRate) throws AccessDeniedException, VatFromDuplicationException, ThirdDecimalPlaceException;

    void invalidate(AuthenticatedUser user, int vatId) throws AccessDeniedException, VatNotFoundException, NextValidVatNotFoundException;

    List<Vat> getValid(AuthenticatedUser user) throws AccessDeniedException;

    List<String> getVatCategories(AuthenticatedUser user) throws AccessDeniedException;

    public LimitedSearchResult<DomainPrice> findAllPrices(AuthenticatedUser user, long offset, long limit, List<SortCriterion> sortBy);

    DomainPrice getPrice(AuthenticatedUser user, String code) throws AccessDeniedException, PriceNotFoundException;

    void addPrice(AuthenticatedUser user, String code, String decription, BigDecimal price, int duration, Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct) throws ThirdDecimalPlaceException;

    void modifyPrice(AuthenticatedUser user, String code, String decription, BigDecimal price, int duration, Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct) throws PriceNotFoundException, ThirdDecimalPlaceException;


    LimitedSearchResult<Invoice> findInvoices(AuthenticatedUser user, InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<Invoice> findUserInvoices(AuthenticatedUser user, InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<ExtendedInvoice> findExtendedInvoices(AuthenticatedUser user, ExtendedInvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    List<DomainInfo> getInvoiceInfo(AuthenticatedUser user, String invoiceNumber);

    List<DomainInfo> getTransactionInfo(AuthenticatedUser user, long transactionId);

    List<DomainInfo> getTransactionInfo(AuthenticatedUser user, String orderId);

    Deposit correctDeposit(AuthenticatedUser user, String nicHandle, int amountInLowestCurrencyUnit, String remark) throws NotEnoughtDepositFundsException, NicHandleNotFoundException, ExportException;

    Deposit depositFundsOffline(AuthenticatedUser user, String nicHandle, int amountInLowestCurrencyUnit, String remark) throws NotEnoughtDepositFundsException, NicHandleNotFoundException, ExportException;

    InputStream viewXmlInvoice(AuthenticatedUser user, String invoiceNumber) throws InvoiceNotFoundException;

    InputStream viewPdfInvoice(AuthenticatedUser user, String invoiceNumber) throws InvoiceNotFoundException;

    void sendEmailWithInvoices(AuthenticatedUser user, String invoiceNumber) throws InvoiceEmailException;
}
