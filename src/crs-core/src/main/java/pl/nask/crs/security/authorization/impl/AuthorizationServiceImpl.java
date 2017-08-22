package pl.nask.crs.security.authorization.impl;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * @author Patrycja Wegrzynowicz
 */
public class AuthorizationServiceImpl implements AuthorizationService {

    private UserDAO userDao;

    public AuthorizationServiceImpl(UserDAO userDAO) {
        Validator.assertNotNull(userDAO, "user dao");
        this.userDao = userDAO;
    }

    public void authorize(AuthenticatedUser authenticatedUser, PermissionQuery permission) throws PermissionDeniedException {
    	Validator.assertNotNull(authenticatedUser, "AuthenticatedUser");
    	Validator.assertNotNull(authenticatedUser.getUsername(), "AuthenticatedUser.username");
    	
        User user = userDao.get(authenticatedUser.getUsername());

        if (user == null)
            throw new PermissionDeniedException();
        
        user.checkPermission(permission);
    }

}
