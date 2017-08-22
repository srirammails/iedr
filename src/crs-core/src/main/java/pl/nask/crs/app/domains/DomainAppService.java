package pl.nask.crs.app.domains;

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.country.Country;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.BulkExportAuthCodesException;
import pl.nask.crs.domains.exceptions.BulkExportAuthCodesTotalException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.TransferedDomainSearchCriteria;
import pl.nask.crs.entities.ClassCategoryEntity;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Kasia Fulara
 */
public interface DomainAppService extends AppSearchService<Domain, DomainSearchCriteria> {

	// replaced by registerGIBODomain in CommonAppService
	@Deprecated
    void create(AuthenticatedUser user, NewDomain domain)
            throws AccessDeniedException, NicHandleNotFoundException, NicHandleNotActiveException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, ClassCategoryPermissionException;

    ExtendedDomainInfo view(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainNotFoundException;
    
    Domain viewPlain(AuthenticatedUser user, String domainName)
    		throws AccessDeniedException, DomainNotFoundException;

	ExtendedDomainInfo edit(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainNotFoundException;

    void save(AuthenticatedUser user, Domain domain)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException;

    // Redeclare! it's needed for the permissions to work properly
    @Override
    LimitedSearchResult<Domain> search(AuthenticatedUser user,
    		DomainSearchCriteria criteria, long offset, long limit,
    		List<SortCriterion> orderBy) throws AccessDeniedException;

    LimitedSearchResult<Domain> searchFull(AuthenticatedUser user,
                                       DomainSearchCriteria criteria, long offset, long limit,
                                       List<SortCriterion> orderBy) throws AccessDeniedException;

    LimitedSearchResult<Domain> findTransferedDomains(AuthenticatedUser user, TransferedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

	boolean checkDomainExists(AuthenticatedUser user, String domainName);
	
	void enterVoluntaryNRP(AuthenticatedUser user, String... domainName);
	
	void removeFromVoluntaryNRP(AuthenticatedUser user, String... domainNames);

	boolean isEventValid(AuthenticatedUser user, String domainName, DsmEventName eventName);

	DomainAvailability checkAvailability(AuthenticatedUser user, String domainName);

    void pushQ(AuthenticatedUser user, OpInfo opInfo);

    void forceDSMEvent(AuthenticatedUser user, List<String> domainNames, DsmEventName eventName, String remark) throws DomainNotFoundException, EmptyRemarkException, DomainIllegalStateException;

    void forceDSMState(AuthenticatedUser user, List<String> domainNames, int state, String remark) throws DomainNotFoundException, EmptyRemarkException;

    public List<Integer> getDsmStates(AuthenticatedUser user);

	void updateHolderType(AuthenticatedUser user, String domainName, DomainHolderType newHolderType, String remark) throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException;

	void enterWipo(AuthenticatedUser user, String domainName, String hostmastersRemark) throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException;
	void exitWipo(AuthenticatedUser user, String domainName, String hostmastersRemark) throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException;

	void revertToBillable(AuthenticatedUser user, List<String> domainNames);

    void runNotificationProcess(AuthenticatedUser user);

    List<ClassCategoryEntity> getClassCategory(AuthenticatedUser user);

    List<String> checkPayAvailable(AuthenticatedUser user, List<String> domainNames);

    void modifyRenewalMode(AuthenticatedUser user, List<String> domainNames, RenewalMode renewalMode);

    List<Country> getCountries(AuthenticatedUser user);

	boolean isCharity(AuthenticatedUser user, String domainName) throws DomainNotFoundException;

    LimitedSearchResult<DeletedDomain> findDeletedDomains(AuthenticatedUser user, DeletedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy);

    void authCodeCleanup(OpInfo opInfo) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    void sendAuthCodeByEmail(AuthenticatedUser user, String domainName) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    void bulkExportAuthCodes(AuthenticatedUser user, List<String> list) throws BulkExportAuthCodesException, BulkExportAuthCodesTotalException;

}
