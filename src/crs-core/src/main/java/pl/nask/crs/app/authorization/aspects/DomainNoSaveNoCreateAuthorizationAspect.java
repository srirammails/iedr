package pl.nask.crs.app.authorization.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.DomainSearchQuery;
import pl.nask.crs.app.authorization.queries.DomainWithoutSaveAndCreatePermissionQuery;
import pl.nask.crs.app.authorization.queries.QueriedDomains;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

public class DomainNoSaveNoCreateAuthorizationAspect implements AuthorizationAspect {
	private static final Logger log = Logger.getLogger(Permission.class);
	
    private AuthorizationService authorizationService;

    public DomainNoSaveNoCreateAuthorizationAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");
        this.authorizationService = authorizationService;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        Object domainObj = joinPoint.getArgs()[1];
        
        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);

        PermissionQuery query = null;
        if (domainObj instanceof DomainSearchCriteria) {
        	query = new DomainSearchQuery(user, permissionName, ((DomainSearchCriteria) domainObj).getBillingNH());
        } else if (domainObj instanceof DeletedDomainSearchCriteria) {
        	query = new DomainSearchQuery(user, permissionName, ((DeletedDomainSearchCriteria) domainObj).getBillingNH());
        } else if (domainObj instanceof SearchCriteria) {
        	query = new NamedPermissionQuery(permissionName);
        } else {
        	try {
        		query = new DomainWithoutSaveAndCreatePermissionQuery(permissionName, QueriedDomains.instanceFor(domainObj), user);
        	} catch (IllegalArgumentException e) {
        		log.error("Unknown method parameter for " + permissionName + " : " + domainObj);
            	throw new AccessDeniedException();	
        	}
        }
        
        try {
            authorizationService.authorize(user, query);
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException(e);
        }
    }
}
