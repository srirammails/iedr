package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.PaymentPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class PaymentAuthorizationAspect implements AuthorizationAspect {

	 private AuthorizationService authorizationService;

	    public PaymentAuthorizationAspect(AuthorizationService authorizationService) {
	        Validator.assertNotNull(authorizationService, "authorization service");
	        this.authorizationService = authorizationService;
	    }
	    
	    public void checkPermissionWithNicHandle(JoinPoint joinPoint) throws AccessDeniedException {
	        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
	        String nicHandle = user.getUsername();
	        if (Validator.isEmpty(nicHandle)) {
	            throw new IllegalArgumentException("empty nicHandle value");
	        }
	        
	        try {
	            authorizationService.authorize(user, new PaymentPermissionQuery(permissionName(joinPoint), nicHandle, user));
	        } catch (PermissionDeniedException e) {
	            throw new AccessDeniedException(e);
	        }
	    }	    	   

		private String permissionName(JoinPoint joinPoint) {
			String typeName = joinPoint.getSignature().getDeclaringTypeName();
	        String methodName = joinPoint.getSignature().getName();
	        String permissionName = typeName + "." + methodName;
	        
	        return permissionName;
		}
}
