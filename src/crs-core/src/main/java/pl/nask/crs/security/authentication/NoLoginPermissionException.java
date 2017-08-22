package pl.nask.crs.security.authentication;

/**
 * @author Marianna Mysiorska
 */
public class NoLoginPermissionException extends AuthenticationException{

    private String username;

    public NoLoginPermissionException(String username) {
        this.username = username;
    }

    public NoLoginPermissionException(String message, String username) {
        super(message);
        this.username = username;
    }

    public NoLoginPermissionException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }

    public NoLoginPermissionException(Throwable cause, String username) {
        super(cause);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
