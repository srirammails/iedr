package pl.nask.crs.security.authentication;

/**
 * Exception thrown during authentication when the password has expired.
 * 
 * @author Marianna Mysiorska
 */
public class PasswordExpiredException extends AuthenticationException {
    public PasswordExpiredException() {
    }

    public PasswordExpiredException(String message) {
        super(message);
    }

    public PasswordExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordExpiredException(Throwable cause) {
        super(cause);
    }
}
