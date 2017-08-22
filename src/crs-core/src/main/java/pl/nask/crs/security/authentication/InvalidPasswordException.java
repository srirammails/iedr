package pl.nask.crs.security.authentication;

/** 
 * Exception thrown when password given during authentication doesn't match user's password in the system.
 * 
 * @author Marianna Mysiorska
 */
public class InvalidPasswordException extends AuthenticationException{
    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(Throwable cause) {
        super(cause);
    }
}
