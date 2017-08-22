package pl.nask.crs.api.nichandle;

import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.NicHandleEditVO;
import pl.nask.crs.api.vo.NicHandleSearchResultVO;
import pl.nask.crs.api.vo.NicHandleVO;
import pl.nask.crs.api.vo.ResellerDefaultsVO;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

@WebService(serviceName="CRSNicHandleAppService", endpointInterface="pl.nask.crs.api.nichandle.CRSNicHandleAppService")
public class NicHandleAppServiceEndpoint extends WsSessionAware implements CRSNicHandleAppService {

	private NicHandleAppService service;
    private NicHandleSearchService searchService;
    private UserAppService userAppService;
    private CountryFactory countryFactory;

    public void setUserAppService(UserAppService userAppService) {
		this.userAppService = userAppService;
	}

	public void setService(NicHandleAppService service) {
		this.service = service;
	}
	
	public void setSearchService(NicHandleSearchService service) {
	    this.searchService = service;
	}

    public void setCountryFactory(CountryFactory countryFactory) {
        this.countryFactory = countryFactory;
    }

    /* (non-Javadoc)
    * @see pl.nask.crs.api.nichandle.CRSNicHandleAppService#get(pl.nask.crs.api.vo.AuthenticatedUserVO, java.lang.String)
    */
	@Override
	public NicHandleVO get(AuthenticatedUserVO user, String nicHandleId)
    throws AccessDeniedException, NicHandleNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException
    {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
		return new NicHandleVO(service.get(completeUser, nicHandleId));
	}

	/* (non-Javadoc)
     * @see pl.nask.crs.api.nichandle.CRSNicHandleAppService#save(pl.nask.crs.api.vo.AuthenticatedUserVO, java.lang.String, pl.nask.crs.api.vo.NicHandleEditVO, java.lang.String)
     */
	@Override
	public void save(AuthenticatedUserVO user, String nicHandleId, NicHandleEditVO nicHandleData,String hostmastersRemark)
            throws AccessDeniedException, NicHandleNotFoundException, EmptyRemarkException,
            AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException,
            NicHandleEmailException, InvalidCountryException, InvalidCountyException,
            UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, ExportException, VatModificationException {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        ValidationHelper.validate(nicHandleData);
        Validator.assertNotEmpty(hostmastersRemark, "hostmaster remark");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
		// FIXME: refactoring needed
		NicHandle nh = service.get(completeUser, nicHandleId);
        validateCountryChange(nicHandleData, nh);
		nicHandleData.copyTo(nh);
		service.save(completeUser, nh, hostmastersRemark);
    }

    /**
     * Vat change validation cannot be moved to app service since permissions model can deny to access save method
     * when modifying country
     *
     * @param nicHandleData
     * @param nh
     * @throws VatModificationException
     */
    private void validateCountryChange(NicHandleEditVO nicHandleData, NicHandle nh) throws VatModificationException, AccountNotFoundException {
    	String newCountry = nicHandleData.getCountry();
    	if (Validator.isEqual(newCountry, nh.getCountry()))
    		return; // no country change
        String oldVatCategory = nh.getVatCategory();
        String newVatCategory = countryFactory.getCountryVatCategory(newCountry);
        if (!Validator.isEqual(oldVatCategory, newVatCategory)) {
            throw new VatModificationException();
        }
    }

    /* (non-Javadoc)
    * @see pl.nask.crs.api.nichandle.CRSNicHandleAppService#changePassword(pl.nask.crs.api.vo.AuthenticatedUserVO, java.lang.String, java.lang.String, java.lang.String)
    */
    @Override
	public void changePassword(AuthenticatedUserVO user, String nicHandleId, String oldPassword, String newPassword1, String newPassword2)
    throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException,
            AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidOldPasswordException,
            UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException
    {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotEmpty(oldPassword, "old password");
        Validator.assertNotEmpty(newPassword1, "new password 1");
        Validator.assertNotEmpty(newPassword2, "new password 2");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
		service.changePassword(completeUser, oldPassword, newPassword1, newPassword2, nicHandleId);
	}

	
	/* (non-Javadoc)
     * @see pl.nask.crs.api.nichandle.CRSNicHandleAppService#create(pl.nask.crs.api.vo.AuthenticatedUserVO, pl.nask.crs.api.vo.NicHandleEditVO, java.lang.String)
     */
	@Override
	public String create (AuthenticatedUserVO user, NicHandleEditVO nicHandleCreateWrapper, String hostmastersRemark)
    throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException,
            EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, ExportException
    {
        ValidationHelper.validate(user);
        ValidationHelper.validate(nicHandleCreateWrapper);
        Validator.assertNotEmpty(hostmastersRemark, "hostmaster remark");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
		return service.create(completeUser, nicHandleCreateWrapper.toNewNicHandle(), hostmastersRemark, true).getNicHandleId();
	}

    @Override
    public NicHandleSearchResultVO find(AuthenticatedUserVO user, NicHandleSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException{
        ValidationHelper.validate(user);
        Validator.assertNotNull(criteria, "criteria");
        validateSession(user);
        LimitedSearchResult<NicHandle> searchRes = searchService.findNicHandle(criteria, offset, limit, orderBy);
        NicHandleSearchResultVO res = new NicHandleSearchResultVO(searchRes);
        return res ;
    }

    @Override
    public ResellerDefaultsVO getDefaults(AuthenticatedUserVO user, String nicHandle) throws UserNotAuthenticatedException,
            InvalidTokenException, SessionExpiredException, DefaultsNotFoundException {
        ValidationHelper.validate(user);
        AuthenticatedUser u = validateSessionAndRetrieveFullUserInfo(user, false);
        ResellerDefaults defaults = service.getDefaults(u, nicHandle);
        return new ResellerDefaultsVO(defaults);
    }   

    @Override
    public void saveDefaults(AuthenticatedUserVO user, String techContactId, List<String> nameservers, Integer dnsNotificationPeriod, EmailInvoiceFormat emailInvoiceFormat)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, DefaultsNotFoundException {
        ValidationHelper.validate(user);
        validateSession(user);
        service.saveDefaults(user, techContactId, nameservers, dnsNotificationPeriod, emailInvoiceFormat);
    }
    
    @Override
    public String changeTfaFlag(AuthenticatedUserVO user, boolean useTwoFactorAuthentication) {
        ValidationHelper.validate(user);
        validateSession(user);
        return userAppService.changeTfa(user, user.getUsername(), useTwoFactorAuthentication);
    }
    
    @Override
    public boolean isTfaUsed(AuthenticatedUserVO user) {
        ValidationHelper.validate(user);
        validateSession(user);
        return userAppService.getUser(user.getUsername()).isUseTwoFactorAuthentication();
    }

    @Override
    public String getSecret(AuthenticatedUserVO user) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return userAppService.generateSecret(user.getUsername());
    }
    
    @Override
    public void requestPasswordReset(String nicHandleId, String ipAddress) throws NicHandleNotFoundException, NicHandleEmailException {
    	service.resetPassword(nicHandleId, ipAddress);
    }
    
    @Override
    public String resetPassword(String token, String nicHandleId, String password) throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException, TokenExpiredException {    	
    	return service.changePassword(token, nicHandleId, password);
    }
}
