package pl.nask.crs.user.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.user.InternalLoginUser;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.dao.ibatis.objects.InternalUser;

import java.util.Date;
import java.util.List;

/**
 * @author Kasia Fulara
 */
public class ConvertingUserDAO extends ConvertingGenericDAO<InternalUser, User, String> implements UserDAO {

    private InternalUserIBatisDAO dao;
    private Converter<InternalUser, User> converter;

    public ConvertingUserDAO(InternalUserIBatisDAO internalDao, Converter<InternalUser, User> internalConverter) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        this.dao = internalDao;
        this.converter = internalConverter;
    }

    public void changePassword(User user) {
        dao.changePassword(converter.from(user));
    }

    public void changePermissions(User user) {
        dao.changePermissions(converter.from(user));
    }

    public Date getLastChangeDate(String userName) {
        return dao.getLastChangeDate(userName);
    }    
    
    @Override
    public Date getLastPasswordChangeDate(String userName) {
        return dao.getLastPasswordChangeDate(userName);
    }
    
    @Override
    public void addUserPermission(String nicHandleId, String permissionName) {
    	dao.addUserPermission(nicHandleId, permissionName);
    }

	@Override
	public void addPasswordReset(String nicHandleId, String hash, Date now,
			String ip) {
		dao.addPasswordReset(nicHandleId, hash, now, ip);
	}
    @Override
    public void removeUserPermission(String nicHandleId, String permissionName) {
    	dao.removeUserPermission(nicHandleId, permissionName);
    }

    @Override
    public List<InternalLoginUser> getInternalUsers() {
        return dao.getInternalUsers();
    }
    
    @Override
    public void changeTfa(String nicHandleId, boolean useTfa) {    
    	dao.changeTfa(nicHandleId, useTfa);
    }
    
    @Override
    public void changeSecret(String nicHandleId, String secret) {
        dao.changeSecret(nicHandleId, secret);
    }
    
    @Override
    public void invalidateToken(String token) {
    	dao.deleteToken(token);
    }
    
    @Override
    public PasswordResetToken getToken(String token) {
    	return dao.getToken(token);
    }
}
