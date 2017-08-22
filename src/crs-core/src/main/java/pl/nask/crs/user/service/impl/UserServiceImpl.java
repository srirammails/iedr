package pl.nask.crs.user.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.NRCLevel;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.HistoricalUserDAO;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.googleauthenticator.SecretGenerator;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

/**
 * @author Marianna Mysiorska
 */
public class UserServiceImpl implements UserService {

    private UserDAO dao;
    private UserSearchService userSearchService;
    private HistoricalUserDAO historicalUserDao;
    private HashAlgorithm hashAlgorithm;
    private AuthorizationGroupsFactory groupsFactory;
    private ApplicationConfig applicationConfig;

    public UserServiceImpl(UserDAO dao, UserSearchService userSearchService, HistoricalUserDAO historicalUserDao,
                           HashAlgorithm hashAlgorithm, AuthorizationGroupsFactory groupsFactory, ApplicationConfig applicationConfig) {
        Validator.assertNotNull(dao, "user dao");
        Validator.assertNotNull(userSearchService, "user search service");
        Validator.assertNotNull(historicalUserDao, "historical user service");
        Validator.assertNotNull(hashAlgorithm, "hashAlgorithm");
        Validator.assertNotNull(groupsFactory, "groups factory");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        this.dao = dao;
        this.userSearchService = userSearchService;
        this.historicalUserDao = historicalUserDao;
        this.hashAlgorithm = hashAlgorithm;
        this.groupsFactory = groupsFactory;
        this.applicationConfig = applicationConfig;
    }

    private void create(User user) {
        dao.create(user);
    }

    public void changePassword(String nicHandleId, String plainNewPassword, String hostmasterHandle)
            throws PasswordAlreadyExistsException {
        Validator.assertNotNull(nicHandleId, "nic handle id");
        Validator.assertNotNull(plainNewPassword, "new password");
        Validator.assertNotNull(hostmasterHandle, "hostmaster handle");
        nicHandleId = nicHandleId.trim().toUpperCase();
        User u = userSearchService.get(nicHandleId);
        String salt = hashAlgorithm.getSalt();
        String hashedNewPassword = hashAlgorithm.hashString(plainNewPassword, salt);
        if (u == null) {
            u = new User();
            u.setUsername(nicHandleId);
            u.setPassword(hashedNewPassword);
            u.setSalt(salt);
            create(u);
        } else {
            checkNotTheSame(u, plainNewPassword);
            u.setPassword(hashedNewPassword);
            u.setSalt(salt);
            dao.changePassword(u);
        }
        updateHistory(u, hostmasterHandle);
    }

    @Override
    public void changePassword(String nicHandleId, String oldPassword, String newPassword, String hostmasterHandle) throws PasswordAlreadyExistsException, InvalidOldPasswordException {
        Validator.assertNotNull(nicHandleId, "nic handle id");
        Validator.assertNotNull(oldPassword, "old password");
        validateOldPassword(nicHandleId, oldPassword);
        changePassword(nicHandleId, newPassword, hostmasterHandle);
    }

    private void validateOldPassword(String nicHandleId, String oldPassword) throws InvalidOldPasswordException {
        User user = userSearchService.get(nicHandleId);
        String encodedOldPassword = hashAlgorithm.hashString(oldPassword, user.getSalt());
        if (!encodedOldPassword.equals(user.getPassword())) {
            throw new InvalidOldPasswordException();
        }
    }

    public void changePermissionGroups(User user, Set<Group> remove, Set<Group> add, String hostmasterHandle) {
        Validator.assertNotNull(user, "user");
        Validator.assertNotNull(remove, "groups to remove");
        Validator.assertNotNull(add, "groups to add");
        Validator.assertNotNull(hostmasterHandle, "hostmaster handle");
        User u = userSearchService.get(user.getUsername());
        
        // create permissions
        Set<Group> permissions;
        if (u != null)
            permissions = u.getPermissionGroups();
        else
            permissions = new HashSet<Group>();
        
        permissions.removeAll(remove);
        permissions.addAll(add);
        user.setPermissionGroups(permissions);
        
        if (u == null)
            create(user);
        else
            dao.changePermissions(user);
        updateHistory(user, hostmasterHandle);
    }

    private void updateHistory(User user, String hostmasterHandle) {
        HistoricalObject<User> hist = new HistoricalObject<User>(user, new Date(), hostmasterHandle);
        historicalUserDao.create(hist);
    }

    private void checkNotTheSame(User user, String plainNewPass)
            throws PasswordAlreadyExistsException {
    	String currentSalt = user.getSalt();
    	if (currentSalt == null) 
    		return; // no check is possible
        String hashedNewPassWithOldSalt = hashAlgorithm.hashString(plainNewPass, user.getSalt());
        if (hashedNewPassWithOldSalt.equals(user.getPassword()))
            throw new PasswordAlreadyExistsException();
    }

    @Override
    public void addUserPermission(String nicHandleId, String permissionName) {
    	dao.addUserPermission(nicHandleId, permissionName);
    }
    
    @Override
    public void removeUserPermission(String nicHandleId, String permissionName) {
    	dao.removeUserPermission(nicHandleId, permissionName);
    }

    @Override
    public void resetPassword(String nicHandleId, String token, String ipAddress, String hostmasterHandle) {
        Date now = new Date();
        Date expires = DateUtils.addMinutes(now, applicationConfig.getPasswordResetTokenExpiry());

        String hash = hashToken(token);
        dao.addPasswordReset(nicHandleId, hash, expires, ipAddress);

        User user = userSearchService.get(nicHandleId);

        if (user == null) {
            user = new User();
            user.setName(nicHandleId);
        }
        updateHistory(user, hostmasterHandle);
    }

    private String hashToken(String token) {
        return hashAlgorithm.hashString(token, "jSe9VrjJRosMn/hKdT6MPu");
    }
    @Override
    public int getUserLevel(String nicHandleId) {
        User u = userSearchService.get(nicHandleId);
        if (u.hasGroup(Level.SuperRegistrar)) {
            return NRCLevel.SUPER_REGISTRAR.getLevel();
        } else if (u.hasGroup(Level.Registrar)) {
            return NRCLevel.REGISTRAR.getLevel();
        } else if (u.hasGroup(Level.Direct)) {
            return NRCLevel.DIRECT.getLevel();
        } else {
            return NRCLevel.TAC.getLevel();
        }
    }
    
    @Override
    public void addUserToGroup(String nicHandleId, Level level, String hostmasterHandle) {
    	User u = new User();
    	u.setUsername(nicHandleId);
    	Set<Group> remove = Collections.emptySet();
		changePermissionGroups(u, remove , groupsFactory.getGroups(level.getLevel()), hostmasterHandle);
    }

    @Override
    public void setUserGroup(String nicHandleId, Level level, String hostmasterHandle) {
        User u = userSearchService.get(nicHandleId);
        if (u == null) {
        	u = new User();
        	u.setUsername(nicHandleId);
        }
        Set<Group> remove = u.getPermissionGroups();
        changePermissionGroups(u, remove , groupsFactory.getGroups(level.getLevel()), hostmasterHandle);
    }

    @Override
    public String changeTfa(String nicHandleId, boolean useTfa) {
    	dao.changeTfa(nicHandleId, useTfa);
        if (useTfa) {
            return generateSecret(nicHandleId);
        }
        return null;
    }

    @Override
    public String generateSecret(String nicHandleId) {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        String newSecret = SecretGenerator.generateSecret();
        dao.changeSecret(nicHandleId, newSecret);
        return newSecret;
    }
    
    @Override
    public String useToken(String token, String nicHandleId) throws TokenExpiredException {
    	String hashedToken = hashToken(token);
    	PasswordResetToken t = dao.getToken(hashedToken);
    	dao.invalidateToken(hashedToken);
    	if (t == null)
    		throw new TokenExpiredException();
    	if (!t.isValid(nicHandleId))
    		throw new TokenExpiredException();    	
    	return t.getNicHandle();
    }
}
