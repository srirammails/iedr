package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.NicHandleCreatePermissionQuery;
import pl.nask.crs.app.authorization.queries.NicHandleQuery;
import pl.nask.crs.app.authorization.queries.NicHandleSearchPermissionQuery;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

/**
 * @author Kasia Fulara
 */
public class NicHandleEditAuthorizationAspect implements AuthorizationAspect {

    private AuthorizationService authorizationService;

    public NicHandleEditAuthorizationAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");
        this.authorizationService = authorizationService;
    }

    /**
     * expects, that NicHandle will be given as a second parameter
     * @param joinPoint
     * @throws AccessDeniedException
     */
    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        NicHandle nicHandle = (NicHandle) joinPoint.getArgs()[1];
        if (nicHandle == null) {
            throw new IllegalArgumentException("empty nicHandle");
        }                
        
        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);
        
        try {
            authorizationService.authorize(user, new NicHandleQuery(permissionName, nicHandle, user));
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException(e);
        }
    }
    
    /**
     * expects, that a nic handle ID is given as a second parameter
     * @param joinPoint
     * @throws AccessDeniedException
     */
    public void checkPermission2(JoinPoint joinPoint) throws AccessDeniedException {
    	checkWithID(joinPoint, 1);
    }
    
    /**
     * expects, that a nic handle ID is given as a fifth parameter
     * @param joinPoint
     * @throws AccessDeniedException
     */
    public void checkPermission3(JoinPoint joinPoint) throws AccessDeniedException {
    	checkWithID(joinPoint, 4);
    }
    
    public void checkPermissionSearch(JoinPoint joinPoint) throws AccessDeniedException {
    	AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
    	NicHandleSearchCriteria crit = (NicHandleSearchCriteria) joinPoint.getArgs()[1];
    	Long accountNumber = crit != null ? crit.getAccountNumber() : null;
    	
    	String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);
    	
    	 try {
             authorizationService.authorize(user, new NicHandleSearchPermissionQuery(permissionName, accountNumber, user));
         } catch (PermissionDeniedException e) {
             throw new AccessDeniedException(e);
         }	
    }
    
    /**
     * expects, that a nic handle ID is given as a fourth parameter
     * @param joinPoint
     * @throws AccessDeniedException
     */
    public void checkPermissionSavePass(JoinPoint joinPoint) throws AccessDeniedException {
    	checkWithID(joinPoint, 3);    	
    }
    
    public void checkPermissionCreate (JoinPoint joinPoint) throws AccessDeniedException {
    	AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        NewNicHandle nicHandle = (NewNicHandle) joinPoint.getArgs()[1];
        if (nicHandle == null) {
            throw new IllegalArgumentException("empty nicHandle");
        }
        
        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);
        
        try {
            authorizationService.authorize(user, new NicHandleCreatePermissionQuery(permissionName, nicHandle, user));
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException(e);
        }		
    }
    

	private void checkWithID(JoinPoint joinPoint, int paramIndex) throws AccessDeniedException {
		AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        String nicHandle = (String) joinPoint.getArgs()[paramIndex];
        if (nicHandle == null) {
            throw new IllegalArgumentException("empty nicHandle");
        }
        
        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);
        
        try {
            authorizationService.authorize(user, new NicHandleQuery(permissionName, nicHandle, user));
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException(e);
        }		
	}
}
