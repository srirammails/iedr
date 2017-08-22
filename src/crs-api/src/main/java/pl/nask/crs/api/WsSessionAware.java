package pl.nask.crs.api;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.token.TokenExpiredException;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class WsSessionAware {

    protected WsAuthenticationService authenticationService;

    public void setAuthenticationService(WsAuthenticationService authenticationService) {
        Validator.assertNotNull(authenticationService, "ws authentication service");
        this.authenticationService = authenticationService;
    }

    protected void validateSession(AuthenticatedUser user) throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        try {
            authenticationService.validateAndRefreshToken(user);
        } catch (TokenExpiredException e) {
            throw new SessionExpiredException();
        }
    }

    protected AuthenticatedUser validateSessionAndRetrieveFullUserInfo(AuthenticatedUser user) throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
    	return validateSessionAndRetrieveFullUserInfo(user, true);
    }
    
    protected AuthenticatedUser validateSessionAndRetrieveFullUserInfo(AuthenticatedUser user, boolean validatePasswordExpiry) throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        try {
            authenticationService.validateAndRefreshToken(user, validatePasswordExpiry);
            return authenticationService.getCompleteUser(user);
        } catch (TokenExpiredException e) {
            throw new SessionExpiredException();
        }
    }

}
