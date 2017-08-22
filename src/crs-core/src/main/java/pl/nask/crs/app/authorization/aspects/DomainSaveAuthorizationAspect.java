package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.permissions.DomainSavePermission;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

/**
 * @author Kasia Fulara
 */
public class DomainSaveAuthorizationAspect implements AuthorizationAspect {

    private AuthorizationService authorizationService;

    public DomainSaveAuthorizationAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");
        this.authorizationService = authorizationService;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        Domain domain = (Domain) joinPoint.getArgs()[1];
        if (domain == null) {
            throw new IllegalArgumentException("empty domain");
        }
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String permissionName = typeName + "." + methodName;
        try {
            authorizationService.authorize(user, new DomainSavePermission("query", permissionName, domain, user));
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException(e);
        }
    }
}
