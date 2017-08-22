package pl.nask.crs.api.payment;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

/**
 * @author: Marcin Tkaczyk
 */

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSPaymentAppService {
    /**
     * Returns user deposit data.
     *
     * @param user authentication token, required
     * @return <code>DepositVO</code> object that contans deposit data : <code>nicHandleId</code>, <code>trnasactionDate</code>, <code>openBal</code>, <code>closeBal</code>.
     * @throws AccessDeniedException
     * @throws DepositNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    DepositVO viewDeposit(
            @WebParam(name = "user")AuthenticatedUserVO user)
            throws AccessDeniedException, DepositNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Returns historical deposits for given criteria. For all deposits search set offset=0 limit=0.
     *
     * @param user
     * @param criteria
     * @param offset
     * @param limit
     * @param sortBy
     * @return
     */
    @WebMethod
    DepositSearchResultVO findUserHistoricalDeposits(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") DepositSearchCriteria criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortBy") List<SortCriterion> sortBy);

    /**
     * Returns system deposit limits
     *
     * @param user authentication token, required
     * @return <code>LimitsPairVO</code> object containing <code>min</code> and <code>max</code> values.
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    LimitsPairVO getDepositLimits(
            @WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Deposits user funds using online payment. All data required to online payment contains pRequest param.
     *
     * @param user authentication token, required
     * @param pRequest <code>PaymentRequestVO</code> object containing data required by online payment, for example <code>amount</code>, <code>cardNumber</code>, required
     * @return <code>DepositVO</code> object that contains current deposit data
     * @throws AccessDeniedException
     * @throws PaymentException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     * @throws ExportException 
     */
    @WebMethod
    DepositVO depositFunds(
            @WebParam(name = "user")AuthenticatedUserVO user,
            @WebParam(name = "pRequest")PaymentRequestVO pRequest)
            throws AccessDeniedException, PaymentException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, ExportException;

    /**
     * Returns system vat rate.
     *
     * @param user authentication token, required
     * @return
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    double getVatRate(
            @WebParam(name = "user") AuthenticatedUserVO user)
    throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;


    @WebMethod
    public DepositTopUpSearchResultVO getTopUpHistory(
            @WebParam(name = "user")AuthenticatedUserVO user,
            @WebParam(name = "fromDate") Date fromDate,
            @WebParam(name = "toDate") Date toDate,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit
    ) throws AccessDeniedException;

    /**
     * Pays domains to be renew.
     *
     *
     * @param user authentication token, required
     * @param domains
     * @param domains list of {@link DomainWithPeriodVO} containg domain names and period(time in years determines for how long domain will be renew), required
     * @param paymentMethod payment method(ADP,CC,DEB), required
     * @param paymentRequest parameter containing CC information, used only with CC,DEB payment method, optional    @return
     * @throws AccessDeniedException
     * @throws DomainNotFoundException
     * @throws DomainManagedByAnotherResellerException
     * @throws DomainIncorrectStateForPaymentException
     * @throws NicHandleNotFoundException
     * @throws NotAdmissiblePeriodException
     * @throws NotEnoughtDepositFundsException
     * @throws PaymentException
     */
    @WebMethod
    public PaymentSummaryVO pay(
            @WebParam(name = "user")AuthenticatedUserVO user,
            @WebParam(name = "domains") List<DomainWithPeriodVO> domains,
            @WebParam(name = "paymentMethod") PaymentMethod paymentMethod,
            @WebParam(name = "paymentRequest") PaymentRequestVO paymentRequest,
            @WebParam(name = "test") boolean test
    ) throws AccessDeniedException, DomainNotFoundException, DuplicatedDomainException, DomainManagedByAnotherResellerException, DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException, NotEnoughtDepositFundsException, PaymentException;

    @WebMethod
    public ReservationSearchResultVO findHistoricalReservations(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") ReservationSearchCriteria criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria") List<SortCriterion> sortBy
    );

    /**
     * Returns not settled reservations for given user.
     *
     * @param user
     * @param criteria
     * @param offset
     * @param limit
     * @param sortBy
     * @return
     */
    @WebMethod
    public ReservationSearchResultVO getNotSettledReservations(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") ReservationCriteriaVO criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria") List<SortCriterion> sortBy
    );

    @WebMethod
    public ReservationTotalsVO getNotSettledReservationsTotals(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "isADP") boolean adp
    );

    @WebMethod
    InvoiceSearchResultVO findInvoices(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") InvoiceSearchCriteria criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortBy") List<SortCriterion> sortBy);

    @WebMethod
    List<DomainInfoVO> getInvoiceInfo(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "invoiceId") String invoiceNumber);

    @WebMethod
    List<DomainInfoVO> getTransactionInfo(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "transactionId") long transactionId);

    @WebMethod
    ReauthoriseTransactionSearchResultVO getTransactionToReauthorise(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortBy") List<SortCriterion> sortBy) throws NotAdmissiblePeriodException, NicHandleNotFoundException;

    @WebMethod
    void sendEmailWithInvoices(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "invoiceNumber") String invoiceNumber) throws InvoiceEmailException;

    @WebMethod
    TransactionSearchResultVO findHistoricalTransactions(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") TransactionSearchCriteria criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortBy") List<SortCriterion> sortBy);

    @WebMethod
    public abstract List<DomainPriceVO> getDomainPricing(
            @WebParam(name = "user") AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, NicHandleNotFoundException;
}
