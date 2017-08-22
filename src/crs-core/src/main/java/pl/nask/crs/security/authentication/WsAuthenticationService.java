package pl.nask.crs.security.authentication;

import pl.nask.crs.token.TokenExpiredException;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public interface WsAuthenticationService extends AuthenticationService {

    public void validateAndRefreshToken(AuthenticatedUser user) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException;

    public void validateAndRefreshToken(AuthenticatedUser user, boolean validatePasswordExpiry) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException;

    /**
     * Switches from super user to standard user
     *
     * @param userName
     * @param superUser
     * @return authentication token of switched user
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws TokenExpiredException
     * @throws InvalidUsernameException
     * @throws NoLoginPermissionException
     */
    public AuthenticatedUser switchUser(AuthenticatedUser superUser, String userName)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException, InvalidUsernameException, NoLoginPermissionException;

    /**
     * Switches back to super user
     *
     * @param user
     * @return authentication token of super user
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws TokenExpiredException
     * @throws UserNotSwitchedException
     */
    public AuthenticatedUser unswitch(AuthenticatedUser user) throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException, UserNotSwitchedException;

    public boolean isUserSwitched(AuthenticatedUser user) throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException;

    /**
     * Returns new AuthenticatedUser with superUserName field filled if user is switched, otherwise returns user.
     *
     * @param user
     * @return
     */
    public AuthenticatedUser getCompleteUser(AuthenticatedUser user);

    /**
     * Authenticates super user in system and switches to standard user.
     * If authentication passes, switched AuthenticatedUser is returned.
     * Otherwise exception is thrown.
     *
     *
     * @param superUserName
     * @param userName
     * @param superUserPassword
     * @param validateExpiration
     * @param useLoginLock
     * @return
     * @throws AuthenticationException
     * @throws IllegalArgumentException
     */
    public AuthenticatedUser authenticateAndSwitchUser(String superUserName, String userName, String superUserPassword, boolean validateExpiration, String remoteAddress, boolean useLoginLock)
            throws AuthenticationException, IllegalArgumentException, AccessDeniedException;

    public AuthenticatedUser getAuthenticatedUser(String userName, String tokenId) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException;
    
    public void logout(AuthenticatedUser user) throws UserNotAuthenticatedException, InvalidTokenException, TokenExpiredException;
}
