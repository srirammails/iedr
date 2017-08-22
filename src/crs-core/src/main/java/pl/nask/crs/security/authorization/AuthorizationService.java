package pl.nask.crs.security.authorization;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface AuthorizationService {

    void authorize(AuthenticatedUser user, PermissionQuery permission) throws PermissionDeniedException;

}
