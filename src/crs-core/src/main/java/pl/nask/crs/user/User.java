package pl.nask.crs.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * DTO for user.
 *
 * @author Kasia Fulara
 * @author: Marianna Mysiorska
 */
public class User {
	private Logger log = Logger.getLogger(User.class);

	private String username;
    private String password;
    private String salt;
    private String secret;
    private String name;
    private Set<Group> permissionGroups;   
    private Map<String, Permission> permissions;
    private boolean useTwoFactorAuthentication;

    public User() {
    }

    public User(String username, String password, String salt, String secret, String name, Set<Group> permissionGroups, Map<String, Permission> userPermissions, boolean useTwoFactorAuthentication) {
        Validator.assertNotEmpty(username, "username");
        Validator.assertNotNull(permissionGroups, "permission groups");
        Validator.assertNotNull(userPermissions, "user permissions");
        this.username = username;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.secret = secret;
        this.permissionGroups = permissionGroups;
        this.permissions = userPermissions;
        this.useTwoFactorAuthentication = useTwoFactorAuthentication;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Group> getPermissionGroups() {
    	if (permissionGroups == null) {
    		permissionGroups = new HashSet<Group>();
    	}
        return permissionGroups;
    }

    public void setPermissionGroups(Set<Group> permissionGroups) {
        this.permissionGroups = permissionGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasGroup(String groupName){
    	Group g = new Group(groupName, Collections.EMPTY_SET);
    	return permissionGroups.contains(g);        
    }
    
    public boolean hasGroup(Level accessLevel){
    	return hasGroup(accessLevel.getName());
    }
    
    public void setPermissions(Map<String, Permission> permissions) {
		this.permissions = permissions;
	}
    
    public Map<String, Permission> getPermissions() {
    	if (permissions == null) {
    		permissions = new HashMap<String, Permission>();
    	}
		return permissions;
	}
    
	public void checkPermission(PermissionQuery permission) throws PermissionDeniedException {		
		PermissionDeniedException holdException = null;

		Set<Permission> permissionsToCheck = new HashSet<Permission>();
		permissionsToCheck.addAll(getPermissionGroups());
		permissionsToCheck.addAll(getPermissions().values());
		
		for (Permission perm: permissionsToCheck) {
        	try {
        		if (perm.implies(permission)) {
        			return;
        		}
        	} catch (PermissionDeniedException e) {
        		log.debug(username + " : PermissionDeniedException for " + permission);
        		holdException = e;
        	} catch (Exception e) {
        		log.debug(username + " : Exception while checking permission " + permission, e);
        	}
        }
		
		log.debug(username + " : Not enough privileges for " + permission);

		if (holdException != null) {
        	throw holdException;
		} else { 
        	throw new PermissionDeniedException();
		}
	}

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public boolean isUseTwoFactorAuthentication() {
		return useTwoFactorAuthentication;
	}
    
    public void setUseTwoFactorAuthentication(boolean useTwoFactorAuthentication) {
		this.useTwoFactorAuthentication = useTwoFactorAuthentication;
	}

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

	public boolean hasDefaultAccessOnly() {
		return (permissionGroups.size() == 1 && hasGroup(Level.Default));
	}
}

