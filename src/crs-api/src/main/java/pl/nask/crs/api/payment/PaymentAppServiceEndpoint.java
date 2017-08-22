package pl.nask.crs.api.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.converter.Converter;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.DomainIncorrectStateForPaymentException;
import pl.nask.crs.payment.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.payment.exceptions.DuplicatedDomainException;
import pl.nask.crs.payment.exceptions.InvoiceEmailException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.NotEnoughtDepositFundsException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

/**
 * @author: Marcin Tkaczyk
 */

@WebService(serviceName = "CRSPaymentService", endpointInterface="pl.nask.crs.api.payment.CRSPaymentAppService")
public class PaymentAppServiceEndpoint extends WsSessionAware implements CRSPaymentAppService {
	private Logger log = Logger.getLogger(CRSPaymentAppService.class);
	
    private PaymentAppService service;
    
    public void setService(PaymentAppService service) {
        this.service = service;
    }
    
    @Override
    public DepositVO viewDeposit(AuthenticatedUserVO user)
            throws AccessDeniedException, DepositNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
        return new DepositVO(service.viewUserDeposit(completeUser));
    }

    @Override
    public DepositSearchResultVO findUserHistoricalDeposits(AuthenticatedUserVO user, DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        ValidationHelper.validate(user);
        validateSession(user);
        return new DepositSearchResultVO(service.findUserHistoricalDeposits(user, criteria, offset, limit, sortBy));
    }

    @Override
	public LimitsPairVO getDepositLimits(AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
        return new LimitsPairVO(service.getDepositLimits(completeUser));
    }

    @Override
	public DepositVO depositFunds(AuthenticatedUserVO user, PaymentRequestVO pRequest)
            throws AccessDeniedException, PaymentException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, ExportException {
        ValidationHelper.validate(user);
        ValidationHelper.validate(pRequest);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return new DepositVO(service.depositFunds(completeUser, pRequest.toPaymentRequest()));
    }
	

	@Override
	public double getVatRate(AuthenticatedUserVO user)
			throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
		ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
		return service.getVatRate(completeUser);
	}

    @Override
    public DepositTopUpSearchResultVO getTopUpHistory(AuthenticatedUserVO user, Date fromDate, Date toDate, long offset, long limit) throws AccessDeniedException {
        ValidationHelper.validate(user);
        Validator.assertNotNull(fromDate, "from name");
        Validator.assertNotNull(toDate, "to date");
        return new DepositTopUpSearchResultVO(service.getTopUpHistory(user, fromDate, toDate, offset, limit));
    }

    @Override
    public PaymentSummaryVO pay(AuthenticatedUserVO user, List<DomainWithPeriodVO> domainsWithPeriods, PaymentMethod paymentMethod, PaymentRequestVO paymentRequest, boolean test)
            throws AccessDeniedException, DomainNotFoundException, DuplicatedDomainException, DomainManagedByAnotherResellerException, DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException, NotEnoughtDepositFundsException, PaymentException {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(domainsWithPeriods, "domains names list");
        ValidationHelper.validate(paymentMethod, paymentRequest);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return new PaymentSummaryVO(service.pay(completeUser, Converter.convertDomainsWithPeriodToMap(domainsWithPeriods), paymentMethod, paymentRequest == null ? null : paymentRequest.toPaymentRequest(), test));
    }

    @Override
    public ReservationSearchResultVO getNotSettledReservations(AuthenticatedUserVO user, ReservationCriteriaVO criteriaVO, long offset, long limit, List<SortCriterion> sortBy) {
        ValidationHelper.validate(user);
        ValidationHelper.validate(criteriaVO);
        ReservationSearchCriteria criteria = null;
        criteria = ReservationSearchCriteria.newSettledInstance(false);
        criteria.setCancelled(false);
        criteria.setBillingNH(user.getUsername());
        criteria.setPaymentMethod(criteriaVO.getPaymentMethod());
        criteria.setOperationType(criteriaVO.getOperationType());
        criteria.setDomainName(criteriaVO.getDomainName());
        LimitedSearchResult<Reservation> result = service.getNotSettledReservations(user, criteria, offset, limit, sortBy);
        return new ReservationSearchResultVO(result);
    }
    
    @Override
    public ReservationTotalsVO getNotSettledReservationsTotals(AuthenticatedUserVO user, boolean adp) {
    	ValidationHelper.validate(user);
    	ReservationTotals totals = service.getNotSettledReservationsTotals(user, adp);
    	return new ReservationTotalsVO(totals);
    }

    @Override
    public ReservationSearchResultVO findHistoricalReservations(AuthenticatedUserVO user, ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        ValidationHelper.validate(user);
        validateSession(user);
        return new ReservationSearchResultVO(service.findHistoricalReservations(user, criteria, offset, limit, sortBy));
    }

    @Override
    public InvoiceSearchResultVO findInvoices(AuthenticatedUserVO user, InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        ValidationHelper.validate(user);
        validateSession(user);
        return new InvoiceSearchResultVO(service.findUserInvoices(user, criteria, offset, limit, sortBy));
    }

    @Override
    public List<DomainInfoVO> getInvoiceInfo(AuthenticatedUserVO user, String invoiceNumber) {
        ValidationHelper.validate(user);
        validateSession(user);
        return toDomainInfoVO(service.getInvoiceInfo(user, invoiceNumber));
    }

    @Override
    public List<DomainInfoVO> getTransactionInfo(AuthenticatedUserVO user, long transactionId) {
        ValidationHelper.validate(user);
        validateSession(user);
        return toDomainInfoVO(service.getTransactionInfo(user, transactionId));
    }

    private List<DomainInfoVO> toDomainInfoVO(List<DomainInfo> domainInfos) {
        if (Validator.isEmpty(domainInfos)) {
            return Collections.emptyList();
        } else {
            List<DomainInfoVO> ret = new ArrayList<DomainInfoVO>();
            for (DomainInfo info : domainInfos) {
                ret.add(new DomainInfoVO(info));
            }
            return ret;
        }
    }

    @Override
    public ReauthoriseTransactionSearchResultVO getTransactionToReauthorise(AuthenticatedUserVO user, long offset, long limit, List<SortCriterion> sortBy) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new ReauthoriseTransactionSearchResultVO(service.getTransactionToReauthorise(user, offset, limit, sortBy));
    }

    @Override
    public void sendEmailWithInvoices(AuthenticatedUserVO user, String invoiceNumber)
            throws InvoiceEmailException {
        ValidationHelper.validate(user);
        validateSession(user);
        service.sendEmailWithInvoices(user, invoiceNumber);
    }

    @Override
    public TransactionSearchResultVO findHistoricalTransactions(AuthenticatedUserVO user, TransactionSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        ValidationHelper.validate(user);
        validateSession(user);
        return new TransactionSearchResultVO(service.findHistoricalTransactions(user, criteria, offset, limit, sortBy));
    }

    @Override
    public List<DomainPriceVO> getDomainPricing(AuthenticatedUserVO user) throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, NicHandleNotFoundException {
        ValidationHelper.validate(user);
        validateSession(user);
        return toDomainPricingVOList(service.getDomainPricing(user));
    }

    private List<DomainPriceVO> toDomainPricingVOList(List<DomainPrice> domainPriceList) {
        if (Validator.isEmpty(domainPriceList))
            return new ArrayList<DomainPriceVO>(0);
        List<DomainPriceVO> ret = new ArrayList<DomainPriceVO>();
        for (DomainPrice domainPrice : domainPriceList) {
            ret.add(new DomainPriceVO(domainPrice));
        }
        return ret;
    }

}
