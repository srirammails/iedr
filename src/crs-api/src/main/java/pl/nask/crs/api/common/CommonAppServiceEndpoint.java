package pl.nask.crs.api.common;

import static pl.nask.crs.api.converter.Converter.toNameserversList;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
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
import pl.nask.crs.app.commons.CommonAppService;
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
import pl.nask.crs.domains.AuthCode;
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
 */

@WebService(serviceName = "CRSCommonAppService", endpointInterface = "pl.nask.crs.api.common.CRSCommonAppService")
public class CommonAppServiceEndpoint extends WsSessionAware implements CRSCommonAppService {
    private static final Logger LOG = Logger.getLogger(CommonAppServiceEndpoint.class);

    private CommonAppService commonAppService;

    public void setCommonAppService(CommonAppService commonAppService) {
        this.commonAppService = commonAppService;
    }

    @Override
    public long registerDomain(AuthenticatedUserVO user, RegistrationRequestVO request, PaymentRequestVO paymentRequest)
            throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException, InvalidTokenException,
            NicHandleNotFoundException, NotAdmissiblePeriodException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDontMatchCategoryException, DomainNotFoundException, ValidationException, NicHandleRecreateException,
            AccountNotFoundException, DomainIsNotCharityException, PaymentException, DomainIsCharityException,
            DuplicatedAdminContact, NicHandleNotActiveException, EmptyRemarkException {
        ValidationHelper.validate(user);
        validateSession(user);
        if (paymentRequest != null)
            ValidationHelper.validate(paymentRequest);
        return commonAppService.registerDomain(user, request, paymentRequest == null ? null : paymentRequest.toPaymentRequest());
    }

    @Override
    public long transferDomain(AuthenticatedUserVO user, TransferRequestVO request, PaymentRequestVO paymentRequest)
            throws TicketNotFoundException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, PaymentException, DomainIsCharityException, InvalidAuthCodeException,
            IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            AccessDeniedException, NicHandleNotActiveException, EmptyRemarkException {
        ValidationHelper.validate(user);
        validateSession(user);
        return commonAppService.transfer(user, request, paymentRequest == null ? null : paymentRequest.toPaymentRequest());
    }

    @Override
    public void cancel(AuthenticatedUserVO user, long ticketId) throws AccessDeniedException, TicketNotFoundException, CancelTicketException, PaymentException {
        ValidationHelper.validate(user);
        validateSession(user);
        commonAppService.cancel(user, ticketId);
    }

    @Override
    public Long modifyDomain(AuthenticatedUserVO user, String domainName, String domainHolder, String domainClass, String domainCategory, List<String> adminContacts, List<String> techContacts, List<NameserverVO> nameservers, RenewalMode renewalMode, String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException, DnsCheckProcessingException, HostNotConfiguredException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, DuplicatedAdminContact, NameserversValidationException {
        ValidationHelper.validate(user);
        AuthenticatedUser fullUser = validateSessionAndRetrieveFullUserInfo(user);
        return commonAppService.modifyDomain(fullUser, domainName, domainHolder, domainClass, domainCategory, adminContacts, techContacts, toNameserversList(nameservers), renewalMode, customerRemark);
    }

    @Override
    public void modifyNameservers(AuthenticatedUserVO user, List<String> domainNames, List<NameserverVO> nameservers, String hostmasterRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException, DuplicatedAdminContact, NameserversValidationException {
        ValidationHelper.validate(user);
        AuthenticatedUser fullUser = validateSessionAndRetrieveFullUserInfo(user);
        commonAppService.modifyNameservers(fullUser, domainNames, toNameserversList(nameservers), hostmasterRemark);
    }

    @Override
    public NameserverValidationVO validateNameservers(AuthenticatedUserVO user, List<String> domainNames, List<NameserverVO> nameservers)
            throws AccessDeniedException {
        ValidationHelper.validate(user);
        try {
            for (String domainName: domainNames)
                commonAppService.validateNameservers(validateSessionAndRetrieveFullUserInfo(user), domainName, toNameserversList(nameservers));
            return new NameserverValidationVO(NameserverValidationVO.Status.OK, "");
        } catch (HostNotConfiguredException e) {
            return new NameserverValidationVO(NameserverValidationVO.Status.DNS_CONFIGURATION_ERROR, e.getFullOutputMessage());
        } catch (DnsCheckProcessingException e) {
            return new NameserverValidationVO(NameserverValidationVO.Status.DNS_EXEC_FAILED, e.getMessage());
        }
    }

    @Override
    public boolean isTransferPossible(AuthenticatedUserVO user, String domainName) {
        ValidationHelper.validate(user);
        validateSession(user);
        return commonAppService.isTransferPossible(user, domainName);
    }

    @Override
    public PaymentSummaryVO reauthoriseTransaction(AuthenticatedUserVO user, int transactionId, PaymentRequestVO paymentRequest)
            throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException, InvalidTokenException,
            TransactionNotFoundException, TicketNotFoundException, NotAdmissiblePeriodException, PaymentException, DomainNotFoundException, NicHandleNotFoundException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new PaymentSummaryVO(commonAppService.reauthoriseTransaction(user, transactionId, paymentRequest.toPaymentRequest()));
    }

    @Override
    public DomainSettingsVO getDomainSettings(AuthenticatedUserVO user) throws AccessDeniedException {
        return new DomainSettingsVO(commonAppService.getDomainSettings(user));
    }

    @Override
    public boolean verifyAuthCode(AuthenticatedUserVO user, String domainName, String authCode, int failureCount) throws AccessDeniedException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
            try {
                commonAppService.verifyAuthCode(user, domainName, authCode, failureCount);
                return true;
            } catch (InvalidAuthCodeException e) {
                return false;
            }
    }

    @Override
    public AuthCodeVO generateOrProlongAuthCode(AuthenticatedUserVO user, String domainName) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        final AuthCode authCode = commonAppService.generateOrProlongAuthCode(user, domainName);
        return new AuthCodeVO(authCode.getAuthcode(), authCode.getValidUntil());
    }

    @Override
    public void sendAuthCodeByEmail(AuthenticatedUserVO user, String domainName) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        commonAppService.sendAuthCodeByEmail(user, domainName);
    }

    @Override
    public void sendAuthCodeFromPortal(String domainName, String emailAddress) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException, AuthCodePortalEmailException, AuthCodePortalLimitException {
        commonAppService.sendAuthCodeFromPortal(domainName, emailAddress);
    }

}
