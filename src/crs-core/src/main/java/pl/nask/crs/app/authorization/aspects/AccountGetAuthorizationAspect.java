package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;
import pl.nask.crs.app.authorization.queries.AccountPartGetPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class AccountGetAuthorizationAspect implements AuthorizationAspect {

    private AuthorizationService authorizationService;

    public AccountGetAuthorizationAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");
        this.authorizationService = authorizationService;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        Long accountId = (Long)joinPoint.getArgs()[1];
        if (accountId == null) {
            throw new IllegalArgumentException("empty account id");
        }
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String permissionName = typeName + "." + methodName;
        try {
            authorizationService.authorize(user, new AccountPartGetPermissionQuery(permissionName, accountId, user.getUsername()));
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException(e);
        }
    }

}
