package pl.nask.crs.security.authentication.impl;

import java.util.List;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.dao.UserDAO;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class WsAuthenticationServiceImpl extends AbstractWsAuthenticationService {

    public WsAuthenticationServiceImpl(UserDAO userDAO, List<HashAlgorithm> algorithms, ApplicationConfig applicationConfig, LoginAttemptDAO loginAttemptDAO) {
        super(userDAO, algorithms, applicationConfig, loginAttemptDAO);
    }

    /**
     * Method to authentitace a user in the system.
     *
     *
     *
     *
     * @param username username given by the user who wants to authenticate in the system
     * @param password password given by the user who wants to authenticate in the system
     * @param useLoginLock
     * @param code @return AuthenticatedUser which represents authenticated user in the system
     * @throws AuthenticationException  general exception
     * @throws InvalidUsernameException when the username is not found in the system
     * @throws pl.nask.crs.security.authentication.InvalidPasswordException when given password doesn't match the username's password in the system
     * @throws PasswordExpiredException when the password has expired
     * @throws IllegalArgumentException when the username or the password is null
     */
    @Override
    public AuthenticatedUser authenticate(String username, String password, boolean validateExpiration, String remoteAddress, boolean useLoginLock, String code, boolean useTfa, String systemDiscriminator)
            throws AuthenticationException, IllegalArgumentException {
        AuthenticatedUser authenticatedUser = super.authenticate(username, password, validateExpiration, remoteAddress, useLoginLock, code, useTfa, systemDiscriminator);
        cleanupTokens();
        String token = registerToken(authenticatedUser.getUsername());
        return new AuthenticatedUserImpl(authenticatedUser.getUsername(), token);
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
}
