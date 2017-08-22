package pl.nask.crs.api.authentication;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.security.authentication.InvalidPasswordException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.NoLoginPermissionException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.security.authentication.UserNotSwitchedException;
import pl.nask.crs.security.authentication.WsAuthenticationService;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

/**
 * Endpoint for the {@link AuthenticationService} 
 *  
 * @author Artur Gniadzik
 *
 */
@WebService(serviceName="CRSAuthenticationService", endpointInterface="pl.nask.crs.api.authentication.CRSAuthenticationService")
public class AuthenticationServiceEndpoint extends WsSessionAware implements CRSAuthenticationService {
	private static final Logger log = Logger.getLogger(AuthenticationServiceEndpoint.class);

	private NicHandleAppService nicHandleAppService;
	  
    public void setNicHandleAppService(NicHandleAppService nicHandleAppService) {
		this.nicHandleAppService = nicHandleAppService;
	}

	/* (non-Javadoc)
     * @see pl.nask.crs.api.CRSAuthenticationService#authenticate(java.lang.String, java.lang.String)
     */
	public AuthenticatedUserVO authenticate (String username, String password, String remoteAddress, String pin)
				throws InvalidUsernameException, InvalidPasswordException,
						IllegalArgumentException, AuthenticationException {
		log.info("called authenticate with username: "+username+" and passwd: "+password);
		log.debug("calling service method");
		try {
			AuthenticatedUser u = authenticationService.authenticate(username, password, true, remoteAddress, true, pin, true, "ws");
			log.debug("service method returned " + u);
			return new AuthenticatedUserVO(u);		
		} catch (PasswordExpiredException e) {
			AuthenticatedUser u = authenticationService.authenticate(username, password, false, remoteAddress, true, pin, true, "ws");
			log.debug("retrying due to password expiry, service method returned " + u);
			AuthenticatedUserVO au = new AuthenticatedUserVO(u);
			au.setPasswordChangeRequired(true);
			return au;
		}
	}
	
	@Override
	public AuthenticatedUserVO authenticateAndChangePasswd(String userName, String password, String newPassword, String remoteAddress, String pin)
			throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException, EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException {
		AuthenticatedUser u = authenticationService.authenticate(userName, password, false, remoteAddress, true, pin, true, "ws");
		
		nicHandleAppService.saveNewPassword(u, newPassword, newPassword, u.getUsername());
		
		return new AuthenticatedUserVO(u);		
	}

    @Override
    public AuthenticatedUserVO switchUser(AuthenticatedUserVO superUser, String userName)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException, InvalidUsernameException, NoLoginPermissionException {
    	validateSession(superUser);
    	AuthenticatedUser realSuperUser = superUser;
    	try {
    		if (authenticationService.isUserSwitched(superUser)) {
    			realSuperUser = authenticationService.unswitch(superUser);
    		} 
    	} catch (UserNotSwitchedException e) {
    		log.warn("User was supposed to be switched, a bug here?");
    	}

    	AuthenticatedUser u = authenticationService.switchUser(realSuperUser, userName);
        return new AuthenticatedUserVO(u);
    }

    @Override
    public AuthenticatedUserVO unswitch(AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException, UserNotSwitchedException {
    	validateSession(user);
        AuthenticatedUser u = authenticationService.unswitch(user);
        return new AuthenticatedUserVO(u);
    }

    @Override
    public boolean isUserSwitched(AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {
    	validateSession(user);
        return authenticationService.isUserSwitched(user);
    }

    @Override
    public AuthenticatedUserVO authenticateAndSwitchUser(String superUserName, String userName, String superUserPassword, String remoteAddress)
            throws AuthenticationException, IllegalArgumentException, AccessDeniedException {
        AuthenticatedUser u = authenticationService.authenticateAndSwitchUser(superUserName, userName, superUserPassword, false, remoteAddress, true);
        return new AuthenticatedUserVO(u);
    }
    
    @Override
    public void logout(AuthenticatedUserVO user) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {
    	authenticationService.logout(user);
    }

}
