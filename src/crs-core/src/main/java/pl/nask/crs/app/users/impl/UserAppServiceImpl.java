package pl.nask.crs.app.users.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.app.NoAuthenticatedUserException;
import pl.nask.crs.app.authorization.permissions.NRCNamedPermission;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.InternalLoginUser;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.search.HistoricalUserSearchCriteria;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;
import pl.nask.crs.user.service.HistoricalUserSearchService;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

/**
 * @author Marianna Mysiorska
 */
public class UserAppServiceImpl implements UserAppService {

    private UserSearchService userSearchService;
    private HistoricalUserSearchService historicalUserSearchService;
    private UserService userService;
    private AuthorizationGroupsFactory groupsFactory;
    private AuthorizationService authorizationService;
    
    private void validateLoggedIn(AuthenticatedUser user)
            throws NoAuthenticatedUserException {
        if (user == null) {
            throw new NoAuthenticatedUserException();
        }
    }

    public UserAppServiceImpl(UserSearchService userSearchService, UserService userService, HistoricalUserSearchService historicalUserSearchService, AuthorizationGroupsFactory groupsFactory, AuthorizationService authorizationService) {
		Validator.assertNotNull(userSearchService, "user search service");
        Validator.assertNotNull(userService, "user service");
        Validator.assertNotNull(historicalUserSearchService, "historical user search service");
        Validator.assertNotNull(groupsFactory, "groupsFactory");
        Validator.assertNotNull(authorizationService, "authorizationService");
        this.userSearchService = userSearchService;
        this.userService = userService;
        this.historicalUserSearchService = historicalUserSearchService;
        this.groupsFactory = groupsFactory;
        this.authorizationService = authorizationService;
    }

    public User getUser(String nicHandleId) {
        User user = userSearchService.get(nicHandleId);
//        if (user == null)
//            user = new User(nicHandleId)
        return user;
    }
  
    public void changePermissionGroups(AuthenticatedUser hostmaster, User user, Set<Group> remove, Set<Group> add) throws AccessDeniedException {
        validateLoggedIn(hostmaster);
        userService.changePermissionGroups(user, remove, add, prepareHostmasterHandle(hostmaster));
    }

    private String prepareHostmasterHandle(AuthenticatedUser user) {
        return user.getSuperUserName() == null ? user.getUsername() : user.getSuperUserName();
    }

//    @Override
    public void changePermissions(AuthenticatedUser hostmaster, String userNicHandle, Set<String> remove, Set<String> add) throws AccessDeniedException {
    	validateLoggedIn(hostmaster);
    	User user = userSearchService.get(userNicHandle);
    	Set<Group> addedGroups = new HashSet<Group>();
        if (add != null) {
            for (String g: add) {
                addedGroups.add(groupsFactory.getGroupByName(g));
            }
        }

    	Set<Group> removedGroups = new HashSet<Group>();
        if (remove != null) {
            for (String g: remove) {
                removedGroups.add(groupsFactory.getGroupByName(g));
            }
        }

    	changePermissionGroups(hostmaster, user, removedGroups, addedGroups);
    }

    @Override
    public LimitedSearchResult<HistoricalObject<User>> getHistory(AuthenticatedUser hostmaster, String nicHandleId, int offset, int limit) {
        return historicalUserSearchService.find(new HistoricalUserSearchCriteria(nicHandleId), offset, limit);
    }

    @Override
    public boolean isUserInGroup(AuthenticatedUser user, Level level) throws AccessDeniedException {
        validateLoggedIn(user);
        return userSearchService.get(user.getUsername()).hasGroup(level);

    }

//    @Override
    public boolean hasPermission(AuthenticatedUser user, String permissionName) throws AccessDeniedException {
        validateLoggedIn(user);
        try {
            authorizationService.authorize(user, new NamedPermissionQuery(permissionName));
        } catch (PermissionDeniedException e) {
            return false;
        }
        return true;
    }

    // removed from the API
    @Deprecated
    public List<String> getNRCPermissionsNames(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        User u = userSearchService.get(user.getUsername());
        for (Group group : u.getPermissionGroups()) {
            for (Permission permission : group.getPermissions()) {
                if (permission instanceof NRCNamedPermission) {
                    return ((NRCNamedPermission)permission).getPermissionNames();
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public int getUserLevel(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return userService.getUserLevel(user.getUsername());
    }

    @Override
    public List<InternalLoginUser> getInternalUsers() {
        return userSearchService.getInternalUsers();
    }
    
    @Override
    public String changeTfa(AuthenticatedUser user, String nicHandleId, boolean useTfa) {
    	return userService.changeTfa(nicHandleId, useTfa);
    }

    @Override
    public String generateSecret(String nicHandleId) {
        return userService.generateSecret(nicHandleId);
    }
}
