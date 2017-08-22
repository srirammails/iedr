package pl.nask.crs.security.authentication;

/**
 * This exception is thrown when a user is not logged in or it has
 * no permission to perform an operation.
 *
 * @author Patrycja Wegrzynowicz
 */
public class AccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = 4986577653250791937L;

	public AccessDeniedException() {
		super("Permission denied");
	}
	
	public AccessDeniedException(String message) {
        super(message);
    }

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(Throwable cause) {
		super("Permission denied", cause);
	}

    
}
