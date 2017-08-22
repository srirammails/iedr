package pl.nask.crs.app.authorization.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.ViewTransactionPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.dao.TransactionHistDAO;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class ViewTransactionAspect implements AuthorizationAspect {
	 private static final Logger log = Logger.getLogger(Permission.class);
	    private AuthorizationService authorizationService;
		private TransactionHistDAO transactionDao;

	    public ViewTransactionAspect(AuthorizationService authorizationService, TransactionHistDAO transactionDao) {
	        this.transactionDao = transactionDao;
			Validator.assertNotNull(authorizationService, "authorizationService");
	        this.authorizationService = authorizationService;
	    }

	    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
	        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
	        Object transactionRef = joinPoint.getArgs()[1];
	        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);

	        ViewTransactionPermissionQuery query;
			if (transactionRef instanceof String) {
				query = new ViewTransactionPermissionQuery(permissionName, transactionDao.getByOrderId((String) transactionRef), user);
	        } else {
	        	query = new ViewTransactionPermissionQuery(permissionName, transactionDao.get((Long) transactionRef), user); 
	        }
	        
	        try {
	            authorizationService.authorize(user, query);
	        } catch (PermissionDeniedException e) {
	            throw new AccessDeniedException(e);
	        }
	    }

}
