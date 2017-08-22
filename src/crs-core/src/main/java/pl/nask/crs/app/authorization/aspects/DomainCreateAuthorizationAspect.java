package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.PermissionDeniedException;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainCreateAuthorizationAspect  implements AuthorizationAspect {

    private AuthorizationService authorizationService;

    public DomainCreateAuthorizationAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");
        this.authorizationService = authorizationService;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        NewDomain domain = (NewDomain) joinPoint.getArgs()[1];
        if (domain == null) {
            throw new IllegalArgumentException("empty domain");
        }
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String permissionName = typeName + "." + methodName;
        try {
            authorizationService.authorize(user, new NamedPermissionQuery(permissionName));
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException(e);
        }
    }
}
