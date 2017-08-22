package pl.nask.crs.app.commons;

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.app.commons.impl.NameserversValidationException;
import pl.nask.crs.app.commons.register.CharityRegistrationNotPossibleException;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainNameExistsOrPendingException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.commons.OpInfo;
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
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

public interface CommonAppService {

    public long registerDomain(AuthenticatedUser user, TicketRequest request, PaymentRequest paymentRequest)
            throws NicHandleNotFoundException, AccessDeniedException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, PaymentException, DomainIsCharityException, DuplicatedAdminContact,
            NicHandleNotActiveException, EmptyRemarkException;

    public void registerGIBODomain(AuthenticatedUser user, TicketRequest request) throws CharityRegistrationNotPossibleException,
            NotAdmissiblePeriodException, DomainNameExistsOrPendingException,
            NicHandleNotFoundException, NicHandleNotActiveException, HolderClassNotExistException,
            HolderCategoryNotExistException, ClassDontMatchCategoryException, ClassCategoryPermissionException;

    public long transfer(AuthenticatedUser user, TicketRequest request, PaymentRequest paymentRequest)
            throws TicketNotFoundException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException,
            AccountNotFoundException, DomainIsNotCharityException, PaymentException, DomainIsCharityException,
            InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException, AccessDeniedException, NicHandleNotActiveException,
            EmptyRemarkException;

    public void cancel(AuthenticatedUser user, long ticketId) throws TicketNotFoundException, CancelTicketException, PaymentException;

    /**
     * Modify domain identified by domainName param. If modified field is null no modification will be applied.
     * If success and holder or contacts are modified returns new modification ticket id.
     *
     * @param user
     * @param domainName
     * @param domainHolder
     * @param domainClass
     * @param domainCategory
     * @param adminContacts
     * @param techContacts
     * @param nameservers
     * @param renewalMode
     * @param customerRemark
     * @throws AccessDeniedException
     * @throws DomainNotFoundException
     * @throws EmptyRemarkException
     * @throws AccountNotFoundException
     * @throws AccountNotActiveException
     * @throws NicHandleNotFoundException
     * @throws NicHandleNotActiveException
     * @throws ValidationException
     * @throws DnsCheckProcessingException
     * @throws HostNotConfiguredException
     * @throws HolderClassNotExistException
     * @throws HolderCategoryNotExistException
     *
     * @throws ClassDontMatchCategoryException
     * @throws NameserversValidationException 
     * @throws DuplicatedAdminContact 
     *
     */
    public Long modifyDomain(AuthenticatedUser user, String domainName, String domainHolder,
                             String domainClass, String domainCategory, List<String> adminContacts, List<String> techContacts,
                             List<Nameserver> nameservers, RenewalMode renewalMode, String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, DuplicatedAdminContact, NameserversValidationException;

    public void modifyNameservers(AuthenticatedUser user, List<String> domainNames, List<Nameserver> nameservers, String hostmasterRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException, DuplicatedAdminContact, NameserversValidationException;

    public void validateNameservers(AuthenticatedUser user, String domain, List<Nameserver> nameservers) throws HostNotConfiguredException, DnsCheckProcessingException;

    public void zonePublished(AuthenticatedUser user, List<String> domainNames);

    public void zoneUnpublished(AuthenticatedUser user, List<String> domainNames);

    void zoneCommit(AuthenticatedUser user);

    /**
     * Checks, if the domain may be transferred to the users account (which means, that the domain is assigned to a different account and no transfer tickets are open)
     * Does not check the DSM state
     * @param user
     * @param domainName
     * @return
     */
    public boolean isTransferPossible(AuthenticatedUser user, String domainName);

    public void cleanupTickets(AuthenticatedUser user, OpInfo opInfo);

    PaymentSummary reauthoriseTransaction(AuthenticatedUser user, int transactionId, PaymentRequest paymentRequest)
            throws DomainNotFoundException, TransactionNotFoundException, TicketNotFoundException, NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException;

    public DomainSettings getDomainSettings(AuthenticatedUser user);

    public void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode, int failureCount) throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException;

    public AuthCode generateOrProlongAuthCode(AuthenticatedUser user, String domainName) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    public void sendAuthCodeByEmail(AuthenticatedUser user, String domainName) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    public void sendAuthCodeFromPortal(String domainName, String emailAddress) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException, AuthCodePortalEmailException, AuthCodePortalLimitException;

}
