package pl.nask.crs.security.authentication;

/**
 * Interface for authenticate user in the system.
 *
 * @author Marianna Mysiorska
 */
public interface AuthenticationService {

    /**
     * Authenticates user in system.
     * If authentication passes, AuthenticatedUser is returned.
     * Otherwise exception is thrown.
     *
     *
     *
     * @param username username given by the user which wants to authenticate in the system
     * @param password password given by the user which wants to authenticate in the system
     * @param validateExpiration
     * @param useLoginLock
     * @param code @return AuthenticatedUser which represents authenticated user in the system
     * @param useTfa
     * @throws AuthenticationException general exception
     * @throws InvalidUsernameException the username is not found in the system
     * @throws InvalidPasswordException when the password doesn't match the username's password in the system
     * @throws IllegalArgumentException when the username or the password is null
     * @throws PasswordExpiredException when the password has expired
     */
    public AuthenticatedUser authenticate(String username, String password, boolean validateExpiration, String remoteAddress, boolean useLoginLock, String code, boolean useTfa, String systemDiscriminator)
            throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException;	
}
