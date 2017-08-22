package pl.nask.crs.security.authentication;

/**
 * General exception for AuthenticationService interface.
 *
 * @author Marianna Mysiorska
 */
public class AuthenticationException extends Exception {

    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
