package pl.nask.crs.app;

import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Patrycja Wegrzynowicz
 */
public class NoPermissionException extends AccessDeniedException {

    private AuthenticatedUser user;

    private String action;

    public NoPermissionException(AuthenticatedUser user, String action) {
        this(user, action, "no permission to perform the action [" + action + "]");
    }

    public NoPermissionException(AuthenticatedUser user, String action, String message) {
        super(message);
        this.user = user;
        this.action = action;
    }

    public AuthenticatedUser getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }

}
