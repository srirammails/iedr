package pl.nask.crs.app.nichandles.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.NoAuthenticatedUserException;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.defaults.ResellerDefaultsService;
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
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.nichandle.service.HistoricalNicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleAppServiceImpl implements NicHandleAppService {

    private NicHandleService nicHandleService;
    private NicHandleSearchService nicHandleSearchService;
    private HistoricalNicHandleSearchService historicalNicHandleSearchService;
    private UserAppService userAppService;
    private ResellerDefaultsService resellerDefaultsService;

    public NicHandleAppServiceImpl(NicHandleService nicHandleService, NicHandleSearchService nicHandleSearchService,
                                   HistoricalNicHandleSearchService historicalNicHandleSearchService,
                                   UserAppService userAppService, ResellerDefaultsService resellerDefaultsService) {
        Validator.assertNotNull(nicHandleService, "nic handle service");
        Validator.assertNotNull(nicHandleSearchService, "nic handle search service");
        Validator.assertNotNull(historicalNicHandleSearchService, "historical nic handle search service");
        Validator.assertNotNull(userAppService, "user app service");
        Validator.assertNotNull(resellerDefaultsService, "resellerDefaultsService");
        this.nicHandleService = nicHandleService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.historicalNicHandleSearchService = historicalNicHandleSearchService;
        this.userAppService = userAppService;
        this.resellerDefaultsService = resellerDefaultsService;
    }

    @Override
    public UserAppService getUserAppService() {
        return userAppService;
    }

    private void validateLoggedIn(AuthenticatedUser user)
            throws NoAuthenticatedUserException {
        if (user == null) {
            throw new NoAuthenticatedUserException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<NicHandle> search(AuthenticatedUser user, NicHandleSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return nicHandleSearchService.findNicHandle(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public NicHandle get(AuthenticatedUser user, String nicHandleId)
            throws AccessDeniedException, NicHandleNotFoundException {
        validateLoggedIn(user);
        return nicHandleSearchService.getNicHandle(nicHandleId);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<HistoricalObject<NicHandle>> history(AuthenticatedUser user, String nicHandleId, int offset, int limit)
            throws AccessDeniedException, NicHandleNotFoundException {
        validateLoggedIn(user);
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId(nicHandleId);
        return historicalNicHandleSearchService.findHistoricalNicHandle(criteria, offset, limit);
    }

    @Override
    public HistoricalObject<NicHandle> getHistorical(AuthenticatedUser user, String nicHandleId, Long changeId) throws AccessDeniedException {
        validateLoggedIn(user);
        return historicalNicHandleSearchService.get(nicHandleId, changeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterStatus(AuthenticatedUser user, String nicHandleId, NicHandle.NHStatus status, String hostmastersRemark)
            throws AccessDeniedException, EmptyRemarkException, NicHandleNotFoundException, NicHandleAssignedToDomainException,
            NicHandleIsAccountBillingContactException, NicHandleIsTicketContactException {
        validateLoggedIn(user);
        nicHandleService.alterStatus(nicHandleId, status, user.getUsername(), user.getSuperUserName(), hostmastersRemark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public NicHandle save(AuthenticatedUser user, NicHandle nicHandle, String hostmastersRemark)
            throws AccessDeniedException, NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            InvalidCountryException, InvalidCountyException, ExportException {
        validateLoggedIn(user);
        String hostmaster = user.getUsername();
        String superHostmaster = user.getSuperUserName();
        nicHandleService.save(nicHandle, hostmastersRemark, hostmaster, superHostmaster, hostmaster);
        return nicHandleSearchService.getNicHandle(nicHandle.getNicHandleId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public NicHandle saveNewPassword(AuthenticatedUser user, String newPassword1, String newPassword2, String nicHandleId)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException,
            AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException {
    	validateLoggedIn(user);
        String hostmaster = user.getUsername();
        String superHostmaster = user.getSuperUserName();
        nicHandleService.saveNewPassword(newPassword1, newPassword2, nicHandleId, hostmaster, superHostmaster, hostmaster);
        return nicHandleSearchService.getNicHandle(nicHandleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public void changePassword(AuthenticatedUser user, String oldPassword, String newPassword1, String newPassword2, String nicHandleId)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException,
            AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidOldPasswordException {
        validateLoggedIn(user);
        nicHandleService.changePassword(oldPassword, newPassword1, newPassword2, nicHandleId, user.getUsername(), user.getSuperUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public NicHandle create(AuthenticatedUser user, NewNicHandle nicHandleCreateWrapper, String hostmastersRemark, boolean sendNotificationEmail)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException,
            NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException,
            PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, ExportException {
        Validator.assertNotNull(nicHandleCreateWrapper, "nic handle create wrapper");
        validateLoggedIn(user);
        return nicHandleService.createNicHandle(
                nicHandleCreateWrapper.getName(),
                nicHandleCreateWrapper.getCompanyName(),
                nicHandleCreateWrapper.getEmail(),
                nicHandleCreateWrapper.getAddress(),
                nicHandleCreateWrapper.getCounty(),
                nicHandleCreateWrapper.getCountry(),
                nicHandleCreateWrapper.getAccountNumber(),
                "",
                nicHandleCreateWrapper.getPhones(),
                nicHandleCreateWrapper.getFaxes(),
                hostmastersRemark,
                user.getUsername(),
                nicHandleCreateWrapper.getVatNo(),
                sendNotificationEmail,
                user.getSuperUserName(), true, true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NicHandle.NHStatus> getStatusList() {
        ArrayList<NicHandle.NHStatus> list = new ArrayList<NicHandle.NHStatus>();
        list.add(NicHandle.NHStatus.Active);
        list.add(NicHandle.NHStatus.Deleted);
        list.add(NicHandle.NHStatus.Suspended);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public ResellerDefaults getDefaults(AuthenticatedUser user,
    		String nicHandleId) throws DefaultsNotFoundException {
    	return resellerDefaultsService.get(nicHandleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDefaults(AuthenticatedUser user, String techContactId, List<String> nameservers, Integer dnsNotificationPeriod, EmailInvoiceFormat emailInvoiceFormat) {
        validateLoggedIn(user);
        try {
            ResellerDefaults defaults = resellerDefaultsService.get(user.getUsername());
            defaults.setTechContactId(techContactId);
            defaults.setNameservers(nameservers);
            defaults.setDnsNotificationPeriod(dnsNotificationPeriod);
            if (emailInvoiceFormat != null) {
                defaults.setEmailInvoiceFormat(emailInvoiceFormat);
            }
            resellerDefaultsService.save(defaults);
        } catch (DefaultsNotFoundException e) {
            ResellerDefaults newDefaults = new ResellerDefaults(user.getUsername(), techContactId, nameservers, dnsNotificationPeriod, emailInvoiceFormat);
            resellerDefaultsService.create(newDefaults);
        }
    }

    @Override
    @Transactional
    public void removeDeletedNichandles() {
    	nicHandleService.removeDeletedNichandles();
    }

    @Override
    @Transactional
    public void addUserPermission(AuthenticatedUser user, String nicHandleId, String permissionName) {
    	nicHandleService.addUserPermission(nicHandleId, permissionName);
    }
    
    @Override
    @Transactional
    public void removeUserPermission(AuthenticatedUser user,
    		String nicHandleId, String permissionName) {
    	nicHandleService.removeUserPermission(nicHandleId, permissionName);
    }
    
    @Transactional
    @Override
    public void resetPassword(String nicHandleId, String ipAddress) throws NicHandleNotFoundException, NicHandleEmailException {
         this.resetPassword(null, nicHandleId, nicHandleId, ipAddress);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resetPassword(AuthenticatedUser user, String nicHandleId, String ipAddress)
            throws AccessDeniedException, PasswordAlreadyExistsException, NicHandleEmailException, NicHandleNotFoundException {
        validateLoggedIn(user);
        this.resetPassword(user, nicHandleId, nicHandleId, ipAddress);
    }

    @Transactional(noRollbackFor=Exception.class)
    @Override
    public String changePassword(String token, String nicHandleId, String password) throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException, TokenExpiredException {    
    	String nicHandle = nicHandleService.useToken(token, nicHandleId);
    	nicHandleService.saveNewPassword(password, password, nicHandle, nicHandle, nicHandle, null);
    	return userAppService.generateSecret(nicHandle);
    }
    
    @Override
    public void triggerNhExport(AuthenticatedUser user, String nicHandleId)
    		throws AccessDeniedException, NicHandleNotFoundException,
    		ExportException {
    	validateLoggedIn(user);
    	nicHandleService.triggerExport(nicHandleId);
    }

    private void resetPassword(AuthenticatedUser user, String nicHandleId, String hostmasterHandle, String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException {
        nicHandleService.resetPassword(user, nicHandleId, hostmasterHandle, ipAddress);
    }
    
}
