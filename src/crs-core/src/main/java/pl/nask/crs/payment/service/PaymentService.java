package pl.nask.crs.payment.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.ExtendedInvoice;
import pl.nask.crs.payment.ExtendedInvoiceSearchCriteria;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.InvoiceSearchCriteria;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.payment.ReauthoriseTransaction;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.ReservationTotals;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionInfo;
import pl.nask.crs.payment.TransactionSearchCriteria;
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
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;

/**
 * @author: Marcin Tkaczyk
 */
public interface PaymentService {
    double getVatRate(String nicHandleId);

    DomainPrice getProductPrice(Period period, OperationType type, String nicHandleId) throws NotAdmissiblePeriodException, NicHandleNotFoundException;

    List<DomainPrice> getDomainPricing(String nicHandleId) throws NicHandleNotFoundException;

    long createADPReservation(String nicHandleId, String domainName, Period period, OperationType operationType, Long ticketId)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException;

    long createCCReservation(AuthenticatedUser user, String nicHandleId, String domainName, String domainHolder, Period period, OperationType operationType, PaymentRequest request, Long ticketId)
            throws NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException;

    Reservation getReadyReservation(String billingNH, String domainName);

    Reservation getNotReadyReservation(String billingNH, String domainName);
        
    Reservation getReservationForTicket(long ticketId);

    void updateReservation(Reservation reservation);

    Reservation lockForUpdate(long id) throws ReservationNotFoundException;

    PaymentSummary payADP(AuthenticatedUser user, String nicHandleId, String superNicHandleId, Map<String, Period> domainsWithPeriods, boolean test)
            throws DomainNotFoundException, DuplicatedDomainException, DomainManagedByAnotherResellerException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException, NotEnoughtDepositFundsException;

    PaymentSummary payCC(AuthenticatedUser user, String nicHandleId, String superNicHandleId, Map<String, Period> domainsWithPeriods, PaymentRequest paymentRequest, boolean test)
            throws DomainNotFoundException, DuplicatedDomainException, DomainManagedByAnotherResellerException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException, PaymentException;

    PaymentSummary payDEB(AuthenticatedUser user, String nicHandleId, String superNicHandleId, Map<String, Period> domainsWithPeriods, PaymentRequest paymentRequest, boolean test)
            throws DomainNotFoundException, DuplicatedDomainException, DomainManagedByAnotherResellerException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException, PaymentException;

    LimitedSearchResult<Reservation> findReservations(ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<Reservation> findHistoricalReservations(String billingNH, ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    ReservationTotals getNotSettledReservationsTotals(String username, boolean adp);

	PaymentSummary autorenew(AuthenticatedUser user, String domainName) throws NicHandleNotFoundException, DomainNotFoundException, DomainManagedByAnotherResellerException, DomainIncorrectStateForPaymentException, NotAdmissiblePeriodException, NotEnoughtDepositFundsException;

    Transaction getTransaction(long transactionId) throws TransactionNotFoundException;

    void cancelTransaction(AuthenticatedUser user, long transactionId) throws TransactionNotFoundException, PaymentException;

    void setTransactionFinanciallyPassed(long transactionId) throws TransactionNotFoundException;

    void setTransactionStartedSettlement(long transactionId) throws TransactionNotFoundException;

    List<Transaction> findAllTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy);

    LimitedSearchResult<Transaction> findHistoricalTransactions(String billingNH, TransactionSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId) throws TransactionNotFoundException, TransactionInvalidStateForSettlement;

    boolean invalidateTransactionsIfNeeded(AuthenticatedUser user, long transactionId) throws TransactionNotFoundException, NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException;

    List<TransactionInfo> getReadyADPTransactionsReport(String billingNH) throws DepositNotFoundException;

    int createInvoiceAndAssociateWithTransactions(String nicHandleId, List<Transaction> transactions) throws NicHandleNotFoundException, TransactionNotFoundException;

    Invoice getInvoice(int invoiceId) throws InvoiceNotFoundException;

    void updateInvoice(Invoice invoice);

    LimitedSearchResult<Invoice> findInvoices(InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<Invoice> findSimpleInvoices(InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<ExtendedInvoice> findExtendedInvoices(ExtendedInvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    List<DomainInfo> getInvoiceInfo(String invoiceNumber);

    List<DomainInfo> getTransactionInfo(long transactionId);

    List<DomainInfo> getTransactionInfo(String orderId);

    LimitedSearchResult<ReauthoriseTransaction> getTransactionToReauthorise(String billingNH, long offset, long limit, List<SortCriterion> sortBy) throws NotAdmissiblePeriodException, NicHandleNotFoundException;

    PaymentSummary reauthoriseTransaction(Transaction transaction, Ticket ticket, PaymentRequest paymentRequest)
            throws DomainNotFoundException, NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException;

    InputStream viewXmlInvoice(String invoiceNumber) throws InvoiceNotFoundException;

    InputStream viewPdfInvoice(String invoiceNumber) throws InvoiceNotFoundException;

    void sendEmailWithInvoices(String invoiceNumber, AuthenticatedUser user) throws InvoiceEmailException;

    void sendInvoicingSummaryEmail(String invoiceNumber, AuthenticatedUser user);
}

