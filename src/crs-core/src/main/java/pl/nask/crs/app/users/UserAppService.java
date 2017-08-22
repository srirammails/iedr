package pl.nask.crs.app.users;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.*;
import pl.nask.crs.user.service.UserService;

import java.util.List;
import java.util.Set;

/**
 * @author Marianna Mysiorska
 * @author Artur Gniadzik
 */
public interface UserAppService {

    public User getUser(String nicHandleId);

    /**
     * Changes users access levels.
     *
     * @param hostmaster logged-in hostmaster performing this operation
     * @param user       user, which access levels are to be changed
     * @param remove     access levels to be removed
     * @param add        access levels to be granted
     * @throws AccessDeniedException if the hostmaster is no allowed to perform this operation
     * @see {@link UserService#changePermissionGroups(User, Set, Set, String)}
     */
    public void changePermissionGroups(AuthenticatedUser hostmaster, User user, Set<Group> remove, Set<Group> add) throws AccessDeniedException;

    public LimitedSearchResult<HistoricalObject<User>> getHistory(AuthenticatedUser hostmaster, String nicHandleId, int offset, int limit) throws AccessDeniedException;

    public boolean isUserInGroup(AuthenticatedUser user, Level level) throws AccessDeniedException;

//    public boolean hasPermission(AuthenticatedUser user, String permissionName) throws AccessDeniedException;

//    public List<String> getNRCPermissionsNames(AuthenticatedUser user) throws AccessDeniedException;

    public int getUserLevel(AuthenticatedUser user) throws AccessDeniedException;

    public List<InternalLoginUser> getInternalUsers();

	public String changeTfa(AuthenticatedUser user, String nicHandleId, boolean useTfa);

    public String generateSecret(String nicHandleId);
}
