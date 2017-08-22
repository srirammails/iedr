package pl.nask.crs.api.authentication;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSAuthenticationService {

	/**
	 * Authenticates the user into the system
	 * 
	 * @param username user nic handle
	 * @param password user password
     * @param remoteAddress user ip
	 * @return authentication token to be used when calling service methods that require user authentication
	 * 	
	 * @throws InvalidUsernameException 
	 * @throws InvalidPasswordException 
	 * @throws PasswordExpiredException 
	 * @throws IllegalArgumentException if username is null
	 * @throws AuthenticationException general authentication failure
	 */
    @WebMethod
    public abstract AuthenticatedUserVO authenticate(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "remoteAddress") String remoteAddress,
            @WebParam(name = "pin") String pin)
            throws InvalidUsernameException, InvalidPasswordException,
            IllegalArgumentException, AuthenticationException;

    /**
     * Switches super user to user defined by userName param.
     *
     * @param superUser super user authentication token, required
     * @param userName user name of user to be switched to
     * @return
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws TokenExpiredException
     * @throws InvalidUsernameException
     * @throws NoLoginPermissionException
     */
    @WebMethod
    public abstract AuthenticatedUserVO switchUser(
            @WebParam(name = "superUser") AuthenticatedUserVO superUser,
            @WebParam(name = "userName") String userName)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException,
            InvalidUsernameException, NoLoginPermissionException;

    /**
     * Switches back to super user.
     *
     * @param user user authentication token
     * @return
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws TokenExpiredException
     * @throws UserNotSwitchedException
     */
    @WebMethod
    public abstract AuthenticatedUserVO unswitch(
            @WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException, UserNotSwitchedException;

    /**
     * Checks if user is switched.
     *
     * @param user user authentication token
     * @return
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws TokenExpiredException
     */
    @WebMethod
    public abstract boolean isUserSwitched(
            @WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException;

    /**
     * Authenticates super user in system and switches to standard user.
     * If authentication passes, switched AuthenticatedUser is returned.
     * Otherwise exception is thrown.
     *
     * @param superUserName
     * @param userName
     * @param superUserPassword
     * @param remoteAddress
     * @return
     * @throws AuthenticationException
     * @throws IllegalArgumentException
     */
    @WebMethod
    public abstract AuthenticatedUserVO authenticateAndSwitchUser(
            @WebParam(name = "superUserName") String superUserName,
            @WebParam(name = "userName") String userName,
            @WebParam(name = "superUserPassword") String superUserPassword,
            @WebParam(name = "remoteAddress") String remoteAddress)
            throws AuthenticationException, IllegalArgumentException, AccessDeniedException;

    @WebMethod
    public AuthenticatedUserVO authenticateAndChangePasswd(
    		@WebParam(name = "userName") String userName,
            @WebParam(name = "password") String password,
            @WebParam(name = "newPassword") String newPassword,
            @WebParam(name = "remoteAddress") String remoteAddress,
            @WebParam(name = "pin") String pin)
            throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException, EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException;
    
    @WebMethod
    public void logout(@WebParam (name="user") AuthenticatedUserVO user) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException;
}