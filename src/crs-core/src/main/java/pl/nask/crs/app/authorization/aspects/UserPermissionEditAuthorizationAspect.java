package pl.nask.crs.app.authorization.aspects;

import java.util.Set;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.UserChangeLevelPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class UserPermissionEditAuthorizationAspect implements AuthorizationAspect {
    private AuthorizationService authorizationService;
    private final UserDAO userDao;

    public UserPermissionEditAuthorizationAspect(AuthorizationService authorizationService, UserDAO userDao) {
        Validator.assertNotNull(authorizationService, "authorization service");
        Validator.assertNotNull(userDao, "user dao");
        this.authorizationService = authorizationService;
        this.userDao = userDao;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        User u = (User) joinPoint.getArgs()[1];
        if (user == null) {
            throw new IllegalArgumentException("empty user");
        }
        Set<Group> remove = (Set<Group>) joinPoint.getArgs()[2];
        Set<Group> add = (Set<Group>) joinPoint.getArgs()[3];
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String permissionName = typeName + "." + methodName;
        UserChangeLevelPermissionQuery perm = new UserChangeLevelPermissionQuery("query", permissionName, u, remove, add);
        try {
            authorizationService.authorize(user, perm);
        } catch (PermissionDeniedException e) {
            throw new AccessDeniedException("user==" + u.getUsername() + " permission==" + perm, e);
        }
    }
}
