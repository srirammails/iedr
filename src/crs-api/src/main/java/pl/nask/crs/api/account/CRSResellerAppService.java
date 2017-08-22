package pl.nask.crs.api.account;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AccountVO;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.InternalRegistrarVO;
import pl.nask.crs.api.vo.NicHandleEditVO;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSResellerAppService {
    /**
     * Returns account
     *
     * @param user authentication token, required
     * @param id account id to be returned, required
     * @return
     * @throws AccessDeniedException
     * @throws AccountNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public AccountVO get(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "accountId") long id)
            throws AccessDeniedException, AccountNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;
  
    @WebMethod
    public List<InternalRegistrarVO> getRegistrarsForLogin(
            @WebParam(name = "user") AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;
    
    /**
     * Allows the user to create a Direct account. This does not check any privileges since the user dies not exist yet.
     * 
     * @return ID of created NicHandle
     * @throws ExportException 
     * @throws InvalidCountyException 
     * @throws InvalidCountryException 
     * @throws PasswordAlreadyExistsException 
     * @throws EmptyRemarkException 
     * @throws AccountNotActiveException 
     * @throws NicHandleEmailException 
     * @throws NicHandleNotFoundException 
     * @throws AccountNotFoundException 
     * @throws PasswordTooEasyException 
     * @throws PasswordsDontMatchException 
     * @throws EmptyPasswordException 
     */
    @WebMethod
    public NewAccountVO createDirectAccount(
    		@WebParam(name="nicHandleDetails") NicHandleEditVO nicHandleDetails,
    		@WebParam(name="newPassword") String password,
    		@WebParam(name="useTfa") boolean useTfa) throws AccountNotFoundException, NicHandleNotFoundException, NicHandleEmailException, AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, ExportException, EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException;
}