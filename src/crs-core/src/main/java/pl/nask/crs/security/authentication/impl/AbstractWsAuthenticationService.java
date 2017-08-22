package pl.nask.crs.security.authentication.impl;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.LoginPermission;

import java.util.*;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
abstract class AbstractWsAuthenticationService extends AuthenticationServiceImpl implements WsAuthenticationService {

    UserDAO userDAO;
    final List<HashAlgorithm> algorithms;
    final Map<String, SessionToken> tokens;
    long lastTokensCleanup;
    final static long TOKEN_CLEANUP_INTERVAL = 60*60*1000;
    {
        tokens = Collections.synchronizedMap(new HashMap<String, SessionToken>());
        lastTokensCleanup = new Date().getTime();
    }

    public AbstractWsAuthenticationService(UserDAO userDAO, List<HashAlgorithm> algorithms, ApplicationConfig applicationConfig, LoginAttemptDAO loginAttemptDAO) {
        super(userDAO, algorithms, applicationConfig, loginAttemptDAO);
        this.algorithms = algorithms;
        this.userDAO = userDAO;
    }

    // session timeout in millis
    private long getSessionTimeout() {
    	return 60L * 1000 * applicationConfig.getUserSessionTimeout();
    }

    String registerToken(String userName) {
    	synchronized (tokens) {
            SessionToken token = new SessionToken(userName, getNewUUID());
    		tokens.put(token.getTokenId(), token);
    		return token.getTokenId();
    	}
    }

    String getNewUUID() {
        return UUID.randomUUID().toString();
    }

    void cleanupTokens() {
    	long sessionTimeout = getSessionTimeout();
        long currDateInMilis = new Date().getTime();
        if (isCleanupNecessary(currDateInMilis)) {
            synchronized (tokens) {
                Iterator<Map.Entry<String, SessionToken>> iterator = tokens.entrySet().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getValue().isExpired(sessionTimeout)) {
                        iterator.remove();
                    }
                }
            }
            lastTokensCleanup = currDateInMilis;
        }
    }

    boolean isCleanupNecessary(long currDate) {
        return lastTokensCleanup + TOKEN_CLEANUP_INTERVAL < currDate;
    }

    @Override
    public void validateAndRefreshToken(AuthenticatedUser user) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {
        validateAndRefreshToken(user, true);
    }
    
    @Override
    public void validateAndRefreshToken(AuthenticatedUser user, boolean validatePasswordExpiry) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {
        validateToken(user.getUsername(), user.getAuthenticationToken());
        refreshToken(user.getAuthenticationToken());
        if (validatePasswordExpiry) {
            SessionToken token = tokens.get(user.getAuthenticationToken());
            String loggedInUser = isSwitched(token) ? token.getSuperUserName() : token.getUserName();
            if (!isPasswordActive(loggedInUser)) {
                throw new AccessDeniedException("Your password is expired");
            }
        }
    }

	void validateToken(String userName, String tokenId) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {
        synchronized (tokens) {
            SessionToken token = tokens.get(tokenId);
            if (token == null) {
                throw new UserNotAuthenticatedException();
            }
            if (!userName.equals(token.getUserName())) {
                throw new InvalidTokenException();
            }
            if (token.isExpired(getSessionTimeout())) {
                tokens.remove(tokenId);
                throw new TokenExpiredException();
            }
        }
    }

    void refreshToken(String tokenId) {
        synchronized (tokens) {
            SessionToken token = tokens.get(tokenId);
            token.refresh();
        }
    }

    @Override
    public AuthenticatedUser switchUser(AuthenticatedUser superUser, String userName)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException, InvalidUsernameException, NoLoginPermissionException {

        Validator.assertNotNull(userName, "new user name");

        validateAndRefreshToken(superUser);

        userName = userName.trim().toUpperCase();
        User user = userDAO.get(userName);
        if (user == null)
            throw new InvalidUsernameException(userName);
        if (!LoginPermissionChecker.checkLoginPermission(user, LoginPermission.WS))
            throw new NoLoginPermissionException(user.getUsername());

        String token = registerToken(userName, superUser.getUsername(), superUser.getAuthenticationToken());
        return new AuthenticatedUserImpl(user.getUsername(), superUser.getUsername(), token);
    }

    String registerToken(String userName, String superUserName ,String superUserTokenId) {
    	synchronized (tokens) {
            SessionToken token = new SessionToken(userName, getNewUUID(), superUserName, superUserTokenId);
            tokens.put(token.getTokenId(), token);
    		return token.getTokenId();
    	}
    }

    @Override
    public AuthenticatedUser unswitch(AuthenticatedUser user) throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException, UserNotSwitchedException {
        SessionToken token = tokens.get(user.getAuthenticationToken());
        validateToken(user.getUsername(), user.getAuthenticationToken());
        if (!isSwitched(token))
            throw new UserNotSwitchedException();
        String newToken = registerToken(token.getSuperUserName());
        return new AuthenticatedUserImpl(token.getSuperUserName(), newToken);
    }

    private boolean isSwitched(SessionToken token) {
        return token.getSuperUserName() != null;
    }

    @Override
    public boolean isUserSwitched(AuthenticatedUser user) throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {
        SessionToken token = tokens.get(user.getAuthenticationToken());
        validateToken(user.getUsername(), user.getAuthenticationToken());
        return isSwitched(token);
    }

    @Override
    public AuthenticatedUser getCompleteUser(AuthenticatedUser user) {
        SessionToken token = tokens.get(user.getAuthenticationToken());
        if (isSwitched(token)) {
            return new AuthenticatedUserImpl(user.getUsername(), token.getSuperUserName(), user.getAuthenticationToken());
        } else {
            return user;
        }
    }

    @Override
    public AuthenticatedUser getAuthenticatedUser(String userName, String tokenId) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {
        validateToken(userName, tokenId);
        return new AuthenticatedUserImpl(userName, tokenId);
    }
    
    @Override
    public void logout(AuthenticatedUser user) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {    
    	removeToken(user);
    }

	private void removeToken(AuthenticatedUser user) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException {
		validateToken(user.getUsername(), user.getAuthenticationToken());
		tokens.remove(user.getAuthenticationToken());
	}
}
