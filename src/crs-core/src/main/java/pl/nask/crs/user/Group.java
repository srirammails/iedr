package pl.nask.crs.user;

import java.util.Set;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * @author Patrycja Wegrzynowicz
 */
public class Group implements Permission {
    private static final Logger log = Logger.getLogger(Group.class);
    
    private String label;
    
    private Set<Permission> permissions;

	private Level level;

    public Group(String name, Set<Permission> permissions) {
        this(name, name, permissions);     
    }
    
    public Group(String name, String label, Set<Permission> permissions) {
        Validator.assertNotNull(name, "name");
        Validator.assertNotNull(permissions, "permissions");
        this.level = Level.levelForName(name);
        this.permissions = permissions;        
        this.label = label;
    }
    
    public Group(Level level, String label, Set<Permission> permissions) {
        Validator.assertNotNull(level, "level");
        Validator.assertNotNull(permissions, "permissions");
        this.level = level;
        this.permissions = permissions;        
        this.label = label;
    }

    public String getName() {
        return level.getName();
    }
    
    @Override
    public String getId() {
    	return level.getName();
    }
    
    public Level getLevel() {
		return level;
	}

    @Override
    public boolean implies(PermissionQuery permission) throws PermissionDeniedException {    
    	return hasPermission(permission);
    }
    
    /**
     * Checks, if the group is allowed to use queried permission.
     * 
     * @param permission the queried permission
     * 
     * @return true, if the group has the permission, false if it doesn't 
     * 
     * @throws PermissionDeniedException if this permission is explicitly removed from the group  
     */
    public boolean hasPermission(PermissionQuery permission) throws PermissionDeniedException {
    	
        PermissionDeniedException holdException = null;
		for (Permission hold : permissions) {
            try {
                if (hold.implies(permission))
                    return true;
            } catch (PermissionDeniedException e) {
            	/*
            	 * a PermissionDeniedException is thrown if the group has a 'negative' permission, which means, the permission explicitly disallows the group to perform operation.
				 * in this case, if there are no 'positive' permissions, an exception should be thrown  
            	 */
            	holdException  = e;
            	logException(permission, hold, e);            	 
            } catch (Exception e) {
            	logException(permission, hold, e);
            }
        }
        if (holdException == null)
        	return false;
        else 
        	throw holdException;
    }

    private void logException(PermissionQuery permission, Permission hold, Exception e) {
    	log.warn("Exception while checking permission " + permission + " (" + permission.getName() + ") with " + hold + " (" + hold.getName() + ")");
        if (log.isDebugEnabled())
            log.debug("Exception while checking permission", e);		
	}

	public Set<Permission> getPermissions() {
        return permissions;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Group) {
            return level == (((Group) obj).getLevel());
        }
        
        return false;
    }
    
    public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }
    
    @Override
    public String toString() {
        return label;
    }

    @Override
    public String getDescription() {
    	return "A group of permissions allowed for " + label + " users, NH_Level==" + level.getLevel();
    }
}
