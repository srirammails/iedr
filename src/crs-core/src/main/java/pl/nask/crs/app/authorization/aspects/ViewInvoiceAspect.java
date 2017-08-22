package pl.nask.crs.app.authorization.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import pl.nask.crs.app.authorization.queries.ViewInvoicePermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ViewInvoiceAspect implements AuthorizationAspect {

    private static final Logger log = Logger.getLogger(Permission.class);
    private AuthorizationService authorizationService;

    public ViewInvoiceAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorizationService");
        this.authorizationService = authorizationService;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        Object invoiceNumber = joinPoint.getArgs()[1];

        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String permissionName = typeName + "." + methodName;

        try {
            authorizationService.authorize(user, new ViewInvoicePermissionQuery(permissionName, (String) invoiceNumber, user));
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException(e);
        }
    }
}
