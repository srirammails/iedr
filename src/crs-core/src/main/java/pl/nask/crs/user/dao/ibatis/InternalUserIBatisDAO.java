package pl.nask.crs.user.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.user.InternalLoginUser;
import pl.nask.crs.user.dao.ibatis.objects.InternalUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marianna Mysiorska
 *         <p/>
 *         This class retrieves user data from DB using IBatis framework.
 *         It also hash a string using MySQL OLD_PASSWORD() function.
 */

public class InternalUserIBatisDAO extends GenericIBatisDAO<InternalUser, String> {

    public InternalUserIBatisDAO() {
        setGetQueryId("user.getUserForUsername");
        setFindQueryId("user.findUser");
        setCountFindQueryId("user.countTotalSearchResult");
        // setUpdateQueryId("user.updateUser");
        setCreateQueryId("user.createUser");
    }


    
    public void changePassword(InternalUser u) {
        int i = performUpdate("user.changePassword", u);
        if (i == 0) {
            // prevent from non existing entries
            performInsert("user.createPassword", u);
        }
    }

    public void changePermissions(InternalUser u) {       
        int i = performUpdate("user.changePermissions", u);
        if (i == 0) {
            performInsert("user.createPermissions", u);
        }
    }

    public Date getLastChangeDate(String userName) {
        return performQueryForObject("user.getLastChangeDate", userName);
    }
    
    public Date getLastPasswordChangeDate(String userName) {
        return performQueryForObject("user.getLastPasswordChangeDate", userName);
    }

	public void removeUserPermission(String nicHandleId, String permissionName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nicHandle", nicHandleId);
		param.put("permissionName", permissionName);
		performInsert("user.removeUserPermission", param);
	}
	
	public void addUserPermission(String nicHandleId, String permissionName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nicHandle", nicHandleId);
		param.put("permissionName", permissionName);
		performInsert("user.addUserPermission", param);
	}

	public void addPasswordReset(String nicHandleId, String hash, Date now,
			String ip) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nicHandleId", nicHandleId);
		map.put("hash", hash);
		map.put("date", now);
		map.put("ip", ip);
        performInsert("user.addPasswordReset", map);
	}

    public List<InternalLoginUser> getInternalUsers() {
        return performQueryForList("user.getInternalLoginUsers");
    }

	public void changeTfa(String nicHandleId, boolean useTfa) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("username", nicHandleId);
		param.put("useTfa", useTfa);
		performUpdate("user.changeTfa", param);
	}

    public void changeSecret(String nicHandleId, String secret) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", nicHandleId);
        param.put("secret", secret);
        performUpdate("user.changeSecret", param);
    }

	public void deleteToken(String token) {		
		performUpdate("user.invalidateToken", token);
	}

	public PasswordResetToken getToken(String token) {
		return performQueryForObject("user.getToken", token);
	}

}
