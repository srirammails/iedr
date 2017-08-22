package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.app.authorization.queries.AccountSavePermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class AccountSaveAuthorizationAspect  implements AuthorizationAspect {
	 private AuthorizationService authorizationService;

	    public AccountSaveAuthorizationAspect(AuthorizationService authorizationService) {
	        Validator.assertNotNull(authorizationService, "authorization service");
	        this.authorizationService = authorizationService;
	    }

	    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
	        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
	        
	        Account account = (Account) joinPoint.getArgs()[1];	
	        
	        String typeName = joinPoint.getSignature().getDeclaringTypeName();
	        String methodName = joinPoint.getSignature().getName();
	        String permissionName = typeName + "." + methodName;
	        try {
	            authorizationService.authorize(user, new AccountSavePermissionQuery(permissionName, account));
	        } catch (PermissionDeniedException e) {
	            throw new AccessDeniedException(e);
	        }
	    }
}