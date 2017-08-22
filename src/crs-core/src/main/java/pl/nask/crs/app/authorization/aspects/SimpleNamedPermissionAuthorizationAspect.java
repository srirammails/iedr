package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.PermissionDeniedException;

/**
 * @author Kasia Fulara
 * @author Patrycja Wegrzynowicz
 */
public class SimpleNamedPermissionAuthorizationAspect implements AuthorizationAspect {

    private final AuthorizationService authorizationService;

    public SimpleNamedPermissionAuthorizationAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");
        this.authorizationService = authorizationService;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {    	
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];        
        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);
        try {
            authorizationService.authorize(user, new NamedPermissionQuery(permissionName));
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException("permission denied: " + permissionName, e);
        }
    }
}
