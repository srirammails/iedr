package pl.nask.crs.security.authentication.impl;

import pl.nask.crs.user.User;
import pl.nask.crs.user.permissions.LoginPermission;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * @author Marianna Mysiorska
 */
public class LoginPermissionChecker {
    /**
     * CHeck if the user has a permission to log in into the system identified by the discrimator
     * @param user
     * @param discriminator identifies the system
     * @return
     */
    public static boolean checkLoginPermission(User user, String discriminator) {
    	PermissionQuery query = new NamedPermissionQuery(new LoginPermission(discriminator).getName());

    	try {
    		user.checkPermission(query);
    		return true;
    	} catch (PermissionDeniedException e) { 
    		return false;
    	}    	
    }
}
