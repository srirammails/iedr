package pl.nask.crs.user.service;

import java.util.Set;

import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

/**
 * @author Marianna Mysiorska
 * @author Artur Gniadzik
 */
public interface UserService {

    // public void update(User user);

    // public void create(User user);

    public void changePassword(String nicHandleId, String newPassword, String hostmasterHandle)
            throws PasswordAlreadyExistsException;

    public void changePassword(String nicHandleId, String oldPassword, String newPassword, String hostmasterHandle)
            throws PasswordAlreadyExistsException, InvalidOldPasswordException;

    /**
     * Changes users access levels.
     * 
     * @param username
     *            hostmaster performing this operation
     * @param user
     *            user, which access levels are to be changed
     * @param remove
     *            access levels to be removed
     * @param add
     *            access levels to be granted
     */
    public void changePermissionGroups(User user, Set<Group> remove, Set<Group> add, String username);

	public void addUserPermission(String nicHandleId, String permissionName);
	public void resetPassword(String nicHandleId, String token, String ipAddress, String hostmasterHandle);
	public void removeUserPermission(String nicHandleId, String permissionName);

    public int getUserLevel(String nicHandleId);

	void addUserToGroup(String nicHandleId, Level level, String hostmasterHandle);

    void setUserGroup(String nicHandleId, Level level, String hostmasterHandle);

    /**
     * changes TFA flag for user, if useTfa=true returns new secret otherwise null
     *
     * @param nicHandleId
     * @param useTfa
     * @return
     */
	public String changeTfa(String nicHandleId, boolean useTfa);

    public String generateSecret(String nicHandleId);

	public String useToken(String token, String nicHandleId) throws TokenExpiredException;
}
