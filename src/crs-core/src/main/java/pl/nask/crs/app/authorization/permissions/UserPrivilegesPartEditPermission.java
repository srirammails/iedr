package pl.nask.crs.app.authorization.permissions;

import java.util.Set;

import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.permissions.ContextualPermission;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * @author Kasia Fulara
 */
public class UserPrivilegesPartEditPermission extends ContextualPermission {


    private Group from;
    private Set<Group> to;;
    
    public UserPrivilegesPartEditPermission(String id, String name, Group from, Set<Group> to) {        
        super(id, name);
        Validator.assertNotNull(from, "change from");
        Validator.assertNotNull(to, "change to");

        this.from = from;
        this.to = to;
    }

    protected boolean verifyContext(PermissionQuery permission) {
        if (!(permission instanceof ChangeLevelPermission)) {
            return false; //cast would cause an exception
        }
        
        ChangeLevelPermission perm = (ChangeLevelPermission) permission;
        
        Set<Group> remove = perm.getFrom();
        Set<Group> add = perm.getTo();
        
        if (remove.isEmpty() && add.isEmpty()) {
            // general query - check if changing permissions is enabled (don't
            // change anything)
            return true; 
        }

        // rule 1: user can change one group only
        if (remove.size() != 1 || add.size() != 1) {
            return false;
        }
        
        // check, if user is allowed to remove a group
        if (!remove.contains(from)) {
            return false;
        }
                     
        // last check: if user is allowed to add a group        
        return (to.containsAll(add));                                    
    }
    
    @Override
    public String getDescription() {
    	if (getClass() != UserPrivilegesPartEditPermission.class) {
    		return null;
    	}
    	return "Non contextual, allows to change the group of the user from " + from + " to one of (" + CollectionUtils.toString(to, ", ") + ")"; 
    }
}
