package pl.nask.crs.api.common;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthCodeVO;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.DomainSettingsVO;
import pl.nask.crs.api.vo.NameserverVO;
import pl.nask.crs.api.vo.NameserverValidationVO;
import pl.nask.crs.api.vo.PaymentRequestVO;
import pl.nask.crs.api.vo.PaymentSummaryVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.app.commons.impl.NameserversValidationException;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.exceptions.AuthCodePortalEmailException;
import pl.nask.crs.domains.exceptions.AuthCodePortalLimitException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 * <p/>
 * Every service method may throw:
 * <ul>
 * <li>AccessDeniedException - in case if the user calling this method is not authorized to use the method (or is trying to use parameters, he is not allowed to)</li>
 * <li>UserNotAuthenticatedException - if the user is not authenticated</li>
 * <li>SessionExpiredException - if the user session is expired</li>
 * <li>InvalidTokenException - if the method call uses invalid token</li>
 * </ul>
 */

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSCommonAppService {

    /**
     * @param user
     * @param paymentRequest
     * @throws NotAdmissiblePeriodException
     * @throws PaymentException
     * @throws EmptyRemarkException
     * @throws NicHandleNotActiveException
     * @throws DuplicatedAdminContact
     * @returns Id of the registration ticket
     */
    @WebMethod
    public long registerDomain(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "registrationRequest") RegistrationRequestVO request,
            @WebParam(name = "paymentRequest") PaymentRequestVO paymentRequest
    ) throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException, InvalidTokenException,
            NicHandleNotFoundException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException, DomainIsNotCharityException, PaymentException, DomainIsCharityException, DuplicatedAdminContact, NicHandleNotActiveException, EmptyRemarkException;


    @WebMethod
    public long transferDomain(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "registrationRequest") TransferRequestVO request,
            @WebParam(name = "paymentRequest") PaymentRequestVO paymentRequest
    ) throws TicketNotFoundException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException,
            AccountNotFoundException, DomainIsNotCharityException, PaymentException, DomainIsCharityException,
            InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException, AccessDeniedException, NicHandleNotActiveException,
            EmptyRemarkException;

    @WebMethod
    public boolean isTransferPossible(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName);

    @WebMethod
    public void cancel(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "ticketId") long ticketId
    ) throws AccessDeniedException, TicketNotFoundException, CancelTicketException, PaymentException;

    @WebMethod
    public Long modifyDomain(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName,
            @WebParam(name = "domainHolder") String domainHolder,
            @WebParam(name = "domainClass") String domainClass,
            @WebParam(name = "domainCategory") String domainCategory,
            @WebParam(name = "adminContacts") List<String> adminContacts,
            @WebParam(name = "techContacts") List<String> techContacts,
            @WebParam(name = "nameservers") List<NameserverVO> nameservers,
            @WebParam(name = "renewalMode") RenewalMode renewalMode,
            @WebParam(name = "customerRemark") String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, DuplicatedAdminContact, NameserversValidationException;

    @WebMethod
    public void modifyNameservers(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainName,
            @WebParam(name = "nameservers") List<NameserverVO> nameservers,
            @WebParam(name = "hostmasterRemark") String hostmasterRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException, DuplicatedAdminContact, NameserversValidationException;

    @WebMethod
    public NameserverValidationVO validateNameservers(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainName,
            @WebParam(name = "nameservers") List<NameserverVO> nameservers)
            throws AccessDeniedException;

    @WebMethod
    public PaymentSummaryVO reauthoriseTransaction(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "transactionId") int transactionId,
            @WebParam(name = "paymentRequest") PaymentRequestVO paymentRequest
    ) throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException, InvalidTokenException, NicHandleNotFoundException,
            TransactionNotFoundException, TicketNotFoundException, NotAdmissiblePeriodException, PaymentException, DomainNotFoundException;

    @WebMethod
    public DomainSettingsVO getDomainSettings(
            @WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException;

    @WebMethod
    public boolean verifyAuthCode(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName,
            @WebParam(name = "authCode") String authCode,
            @WebParam(name = "failureCount") int failureCount
    ) throws AccessDeniedException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException;

    @WebMethod
    AuthCodeVO generateOrProlongAuthCode(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName
    ) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    @WebMethod
    void sendAuthCodeByEmail(
            @WebParam(name = "user") AuthenticatedUserVO user, 
            @WebParam(name = "domainName") String domainName
    ) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    @WebMethod
    void sendAuthCodeFromPortal(
            @WebParam(name = "domainName") String domainName,
            @WebParam(name = "emailAddress") String emailAddress
    ) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException, AuthCodePortalEmailException, AuthCodePortalLimitException;

}
