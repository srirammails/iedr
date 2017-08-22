package pl.nask.crs.api.nichandle;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.NicHandleEditVO;
import pl.nask.crs.api.vo.NicHandleSearchResultVO;
import pl.nask.crs.api.vo.NicHandleVO;
import pl.nask.crs.api.vo.ResellerDefaultsVO;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSNicHandleAppService {
    /**
     * Returns NicHandleVO object identified by nicHandleId param
     *
     * @param user authentication token, required
     * @param nicHandleId nic handle identifier, required
     * @return
     * @throws AccessDeniedException
     * @throws NicHandleNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract NicHandleVO get(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "nicHandle") String nicHandleId)
            throws AccessDeniedException, NicHandleNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Updates nic handle identified by nicHandleId param.
     *
     * @param user authentication token, required
     * @param nicHandleId nic handle identifier, required
     * @param nicHandleData nic handle data to be updated, required
     * @param hostmastersRemark remark to be added, required
     * @throws AccessDeniedException
     * @throws NicHandleNotFoundException
     * @throws EmptyRemarkException
     * @throws AccountNotFoundException
     * @throws AccountNotActiveException
     * @throws NicHandleIsAccountBillingContactException
     * @throws NicHandleEmailException
     * @throws InvalidCountryException
     * @throws InvalidCountyException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     * @throws ExportException 
     */
    public abstract void save(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "nicHandleId") String nicHandleId,
            @WebParam(name = "nicHandleData") NicHandleEditVO nicHandleData,
            @WebParam(name = "remark") String hostmastersRemark) throws AccessDeniedException,
            NicHandleNotFoundException, EmptyRemarkException,
            AccountNotFoundException, AccountNotActiveException,
            NicHandleIsAccountBillingContactException, NicHandleEmailException, InvalidCountryException, InvalidCountyException,
            UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, ExportException, VatModificationException;

    /**
     * Changes password for nic handle identified by nicHandleId param.
     *
     * @param user authentication token, required
     * @param nicHandleId nic handle identifier, required
     * @param oldPassword nic handle old password, required
     * @param newPassword1 new password, required
     * @param newPassword2 repeated new password, required
     * @throws EmptyPasswordException
     * @throws PasswordsDontMatchException
     * @throws PasswordTooEasyException
     * @throws NicHandleNotFoundException
     * @throws AccessDeniedException
     * @throws NicHandleEmailException
     * @throws PasswordAlreadyExistsException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract void changePassword(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "nicHandle") String nicHandleId,
            @WebParam(name = "oldPassword") String oldPassword,
            @WebParam(name = "password") String newPassword1,
            @WebParam(name = "repeatedPassword") String newPassword2)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException,
            AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidOldPasswordException,
            UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Creates new nic handle.
     *
     * @param user authentication token, required
     * @param nicHandleCreateWrapper new nic handle data
     * @param hostmastersRemark remark to be added, required
     * @return
     * @throws AccessDeniedException
     * @throws AccountNotFoundException
     * @throws AccountNotActiveException
     * @throws NicHandleNotFoundException
     * @throws EmptyRemarkException
     * @throws NicHandleEmailException
     * @throws PasswordAlreadyExistsException
     * @throws InvalidCountryException
     * @throws InvalidCountyException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     * @throws ExportException 
     */
    @WebMethod
    public abstract String create(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "newNicHandle") NicHandleEditVO nicHandleCreateWrapper,
            @WebParam(name = "remark") String hostmastersRemark)
            throws AccessDeniedException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException,
            EmptyRemarkException, NicHandleEmailException,
            PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException,
            UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, ExportException;

    /**
     * Searches for nic handles matching given criteria in the given order.
     * Result is limited by offset and limit parameters.
     *
     * @param user authentication token, required
     * @param criteria nic ahndle search criteria, required
     * @param offset, required
     * @param limit, maximum number of nic handles to be retuned, required
     * @param orderBy sorting criteria, optional
     * @return
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public NicHandleSearchResultVO find(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name="criteria") NicHandleSearchCriteria criteria, 
            @WebParam(name="offset") long offset, 
            @WebParam(name="limit") long limit, 
            @WebParam(name="sortCriteria") List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Return reseller defaults(tech contact, nameservers)
     *
     * @param user authentication token, required
     * @param nicHandle nic handle identifier, required
     * @return
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public ResellerDefaultsVO getDefaults(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "nicHandle") String nicHandle)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, DefaultsNotFoundException;

    
    @WebMethod
    public void saveDefaults(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "techContactId") String techContactId,
            @WebParam(name = "nameservers") List<String> nameservers,
            @WebParam(name = "dnsNotificationPeriod") Integer dnsNotificationPeriod, EmailInvoiceFormat emailInvoiceFormat)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, DefaultsNotFoundException;

    @WebMethod
    public String changeTfaFlag(
    		@WebParam(name = "user") AuthenticatedUserVO user,
    		@WebParam(name = "useTwoFactorAuthentication") boolean useTwoFactorAuthentication);
    
    @WebMethod
    public boolean isTfaUsed(@WebParam(name="user") AuthenticatedUserVO user);
    
    @WebMethod
    public void requestPasswordReset(
    		@WebParam(name="username") String hicHandleId, 
    		@WebParam(name="ipAddress") String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException;
    
    @WebMethod
    public String resetPassword(
    		@WebParam(name="token") String token,
    		@WebParam(name="username") String nicHandleId,
    		@WebParam(name="newPassword") String password) throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException, TokenExpiredException;
    
    @WebMethod
    public String getSecret(@WebParam(name = "user") AuthenticatedUserVO user) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException;
}