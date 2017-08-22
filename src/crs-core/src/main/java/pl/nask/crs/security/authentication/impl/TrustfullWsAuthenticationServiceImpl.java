package pl.nask.crs.security.authentication.impl;

import java.util.List;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.LoginLockException;
import pl.nask.crs.security.authentication.NoLoginPermissionException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.LoginPermission;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class TrustfullWsAuthenticationServiceImpl extends AbstractWsAuthenticationService {

    public TrustfullWsAuthenticationServiceImpl(UserDAO userDAO, List<HashAlgorithm> algorithms, ApplicationConfig applicationConfig, LoginAttemptDAO loginAttemptDAO) {
        super(userDAO, algorithms, applicationConfig, loginAttemptDAO);
    }

    @Override
    public AuthenticatedUser authenticate(String username, String password, boolean validateExpiration, String remoteAddress, boolean useLoginLock, String code, boolean useTfa, String systemDiscriminator) throws IllegalArgumentException, AuthenticationException {
        if (username == null || "".equals(username))
            username = "IDL2-IEDR";
        if ("wrong".equalsIgnoreCase(username))
        	throw new InvalidUsernameException(username);
        if ("locked".equalsIgnoreCase(password))
        	throw new LoginLockException(200);
        if ("noSalt".equalsIgnoreCase(username))
        	throw new IllegalArgumentException("salt cannot be empty");
        if (validateExpiration && "expired".equalsIgnoreCase(password))
        	throw new PasswordExpiredException();

        username = username.trim().toUpperCase();
        User u = userDAO.get(username);
        if (u == null)
        	throw new InvalidUsernameException(username);

        if (!LoginPermissionChecker.checkLoginPermission(u, LoginPermission.WS))
            throw new NoLoginPermissionException(u.getUsername());

        cleanupTokens();
        String token = registerToken(username);
        return new AuthenticatedUserImpl(username, token);
    }

    @Override
    public AuthenticatedUser authenticateAndSwitchUser(String superUserName, String userName, String superUserPassword, boolean validateExpiration, String remoteAddress, boolean useLoginLock)
            throws AuthenticationException, IllegalArgumentException, AccessDeniedException {
        try {
            AuthenticatedUser authenticatedSuperUser = this.authenticate(superUserName, superUserPassword, validateExpiration, remoteAddress, useLoginLock, null, true, "ws");
            return this.switchUser(authenticatedSuperUser, userName);
        } catch (UserNotAuthenticatedException e) {
            //should never happen
            throw new IllegalStateException(e);
        } catch (InvalidTokenException e) {
            //should never happen
            throw new IllegalStateException(e);
        } catch (TokenExpiredException e) {
            //should never happen
            throw new IllegalStateException(e);
        }
    }    
    
    @Override
    protected boolean isPasswordActive(String userName) {
    	return true;
    }
}
