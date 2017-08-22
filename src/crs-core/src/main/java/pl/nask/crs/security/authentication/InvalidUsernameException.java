package pl.nask.crs.security.authentication;

/**
 * Exception thrown when username given during authentication in not found in the system.
 *
 * @author Marianna Mysiorska
 */
public class InvalidUsernameException extends AuthenticationException {

    private String username;

    public InvalidUsernameException(String username) {
        this.username = username;
    }

    public InvalidUsernameException(String message, String username) {
        super(message);
        this.username = username;
    }

    public InvalidUsernameException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }

    public InvalidUsernameException(Throwable cause, String username) {
        super(cause);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
