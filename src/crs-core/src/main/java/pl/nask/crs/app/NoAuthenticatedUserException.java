package pl.nask.crs.app;

import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * @author Patrycja Wegrzynowicz
 */
public class NoAuthenticatedUserException extends AccessDeniedException {

    public NoAuthenticatedUserException() {
        this("no authenticated user present");
    }

    public NoAuthenticatedUserException(String message) {
        super(message);
    }
    
}
