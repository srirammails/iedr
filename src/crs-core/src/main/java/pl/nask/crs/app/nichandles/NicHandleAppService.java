package pl.nask.crs.app.nichandles;

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleIsTicketContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

/**
 * @author Marianna Mysiorska
 */
public interface NicHandleAppService extends AppSearchService<NicHandle, NicHandleSearchCriteria> {

    public UserAppService getUserAppService();

    public NicHandle get(AuthenticatedUser user, String nicHandleId)
            throws AccessDeniedException, NicHandleNotFoundException;

    public LimitedSearchResult<HistoricalObject<NicHandle>> history(AuthenticatedUser user, String nicHandleId, int offset, int limit)
            throws AccessDeniedException, NicHandleNotFoundException;

    public HistoricalObject<NicHandle> getHistorical(AuthenticatedUser user, String nicHandleId, Long changeId)
            throws AccessDeniedException;

    public void alterStatus(AuthenticatedUser user, String nicHandleId, NicHandle.NHStatus status, String hostmastersRemark)
            throws AccessDeniedException, EmptyRemarkException, NicHandleNotFoundException, NicHandleAssignedToDomainException, NicHandleIsAccountBillingContactException, NicHandleIsTicketContactException;

    public NicHandle save(AuthenticatedUser user, NicHandle nicHandle, String hostmastersRemark)
            throws AccessDeniedException, NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException, InvalidCountryException, InvalidCountyException, ExportException;

    public void triggerNhExport(AuthenticatedUser user, String nicHandleId)
            throws AccessDeniedException, NicHandleNotFoundException, ExportException;
    
    public NicHandle saveNewPassword(AuthenticatedUser user, String newPassword1, String newPassword2, String nicHadnleId)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException,
            AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException;

    public void changePassword(AuthenticatedUser user, String oldPassword, String newPassword1, String newPassword2, String nicHandleId)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException,
            AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidOldPasswordException;

    public NicHandle create(AuthenticatedUser user, NewNicHandle nicHandleCreateWrapper, String hostmastersRemark, boolean sendNotificationEmail)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, ExportException;
    
    @Override
    /* 
     * redeclare to allow aspect to 'catch' this invocation with declaringTypeName of NicHandleAppService
     */
	public LimitedSearchResult<NicHandle> search(AuthenticatedUser user, NicHandleSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException;
    
    public List<NicHandle.NHStatus> getStatusList();
    
    public ResellerDefaults getDefaults(AuthenticatedUser user, String nicHandleId) throws DefaultsNotFoundException;

    /**
     * create or update
     */
    void saveDefaults(AuthenticatedUser user, String techContactId, List<String> nameservers, Integer dnsNotificationPeriod, EmailInvoiceFormat emailInvoiceFormat);

    public void removeDeletedNichandles();

	public void removeUserPermission(AuthenticatedUser user, String nicHandleId, String permissionName);

	public void addUserPermission(AuthenticatedUser user, String nicHandleId,
			String permissionName);

	public String changePassword(String token, String nicHandleId, String password) throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException, TokenExpiredException;

    public void resetPassword(String nicHadnleId, String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException;

	public void resetPassword(AuthenticatedUser user, String nicHandleId, String ipAddress) 
			throws AccessDeniedException, PasswordAlreadyExistsException, NicHandleEmailException, NicHandleNotFoundException;

}
