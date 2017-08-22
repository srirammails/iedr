package pl.nask.crs.domains.services;

import java.util.List;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.country.Country;
import pl.nask.crs.domains.AuthCode;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.NotificationType;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.AuthCodePortalLimitException;
import pl.nask.crs.domains.exceptions.DomainEmailException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Kasia Fulara
 */
public interface DomainService {

    void create(AuthenticatedUser user, NewDomain domain, OpInfo opInfo)
            throws NicHandleNotFoundException, NicHandleNotActiveException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, ClassCategoryPermissionException;

    void save(Domain domain, OpInfo opInfo)
            throws DomainNotFoundException, EmptyRemarkException, DuplicatedAdminContact, NicHandleNotFoundException, NicHandleNotActiveException;

	void enterVoluntaryNRP(AuthenticatedUser user, OpInfo opInfo, String... domainNames);

	void removeFromVoluntaryNRP(AuthenticatedUser user, OpInfo opInfo, String... domainNames);

	boolean isEventValid(String domainName, DsmEventName eventName);

	void zonePublished(List<String> domainNames);

	void zoneUnpublished(List<String> domainNames);

	void zoneCommit();

    void runRenewalDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo);

    void runSuspensionDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo);

    void runDeletionDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo);

    void runDeletedDomainRemoval(AuthenticatedUser user, String domainName, OpInfo opInfo);

    void forceDSMEvent(AuthenticatedUser user, List<String> domainNames, DsmEventName eventName, OpInfo opInfo) throws DomainNotFoundException, EmptyRemarkException, DomainIllegalStateException;

    void forceDSMState(List<String> domainNames, int state, OpInfo opInfo) throws DomainNotFoundException, EmptyRemarkException;

    List<Integer> getDsmStates();

	void updateHolderType(AuthenticatedUser user, String domainName, DomainHolderType newHolderType, OpInfo opInfo) throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException;

	void exitWipo(AuthenticatedUser user, String domainName, OpInfo opInfo) throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException;;
	void enterWipo(AuthenticatedUser user, String domainName, OpInfo opInfo) throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException;

	void revertToBillable(AuthenticatedUser user, List<String> domainNames, OpInfo opInfo);
    
    void sendNotification(Domain domain, NotificationType type, AuthenticatedUser user, int period) throws DomainEmailException;

    void sendNotification(Domain domain, NotificationType type, AuthenticatedUser user) throws DomainEmailException;

    List<String> checkEventAvailable(List<String> domainNames, DsmEventName eventName);

    void modifyRenewalMode(AuthenticatedUser user, List<String> domainNames, RenewalMode renewalMode, OpInfo opInfo);

    List<Country> getCountries();

    void modifyRemark(AuthenticatedUser user, String domainName, String remark) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    void sendAuthCodeByEmail(AuthenticatedUser user, String domainName)
            throws IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    void sendAuthCodeFromPortal(String domainName, String emailAddress)
            throws IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException, AuthCodePortalLimitException;

    void verifyAuthCode(AuthenticatedUser user, String domainName,
            String authCode) throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException;

    void verifyAuthCode(AuthenticatedUser user, String domainName,
            String authCode, int failureCount) throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException;

    AuthCode getOrCreateAuthCode(String username, String domainName)
            throws DuplicatedAdminContact, DomainNotFoundException,
            NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException;

    void clearAuthCode(String domainName, OpInfo opInfo)
            throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException,
            NicHandleNotActiveException, EmptyRemarkException;

    void clearAuthCodePortalCount(String domainName, OpInfo opInfo)
            throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException,
            NicHandleNotActiveException, EmptyRemarkException;

    void sendBulkAuthCodesByEmail(AuthenticatedUser user, Contact billingContact,
            Contact adminContact, List<Domain> domainList) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException;

}
