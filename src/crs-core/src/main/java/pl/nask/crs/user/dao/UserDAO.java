package pl.nask.crs.user.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.user.InternalLoginUser;
import pl.nask.crs.user.User;

import java.util.Date;
import java.util.List;

/**
 * @author Marianna Mysiorska
 *
 * This interface serves to retrieve user data.  
 */

public interface UserDAO extends GenericDAO<User, String> {

    void changePermissions(User user);
    
    void changePassword(User user);

    /**
     * Returns date of last access modification
     *
     * @param userName
     * @return
     */
    Date getLastChangeDate(String userName);

	void removeUserPermission(String nicHandleId, String permissionName);
	void addPasswordReset(String nicHandleId, String hash, Date now, String ip);

	void addUserPermission(String nicHandleId, String permissionName);

    List<InternalLoginUser> getInternalUsers();

	Date getLastPasswordChangeDate(String userName);

	void changeTfa(String nicHandleId, boolean useTfa);

	void changeSecret(String nicHandleId, String secret);
	
	PasswordResetToken getToken(String token);

	void invalidateToken(String token);

}
