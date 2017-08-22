package pl.nask.crs.user.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionQuery;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

/**
 * @author Kasia Fulara
 */
public class AuthorizationGroupsFactoryImpl implements AuthorizationGroupsFactory {

    private Map<String, Group> groupsMap = new HashMap<String, Group>();
    private List<Group> groups;
	private Map<String, Permission> permissions = new HashMap<String, Permission>();

    public AuthorizationGroupsFactoryImpl(List<Group> groups, List <Permission> permissions) { 
        Validator.assertNotNull(groups, "groups' set");
        Validator.assertNotNull(permissions,  "permissions");
        this.groups = groups;
        for (Group g : groups) {
            groupsMap.put(g.getName(), g);
        }
        List<String> conflicts = new LinkedList<String>();
        for (Permission p: permissions) {
        	if(! (p instanceof PermissionQuery || p instanceof Group)) {
        		String id = p.getId();
        		if (this.permissions.containsKey(id)) {      
        			conflicts.add(String.format("Duplicated permission id:%s. Existing permission:%s, new permission %s", id, this.permissions.get(id), p));
        		} else {
        			this.permissions.put(id, p);
        		}
        	}
        }
        if (!conflicts.isEmpty()) {
        	throw new IllegalArgumentException(conflicts.toString());
        }
    }

    public Set<Group> getGroups(int level) {
        Set<Group> userGroups = new HashSet<Group>();
        for (Level l : Level.values()) {
            if (l.isContained(level)) {
                if (groupsMap.get(l.getName()) != null) {
                    userGroups.add(groupsMap.get(l.getName()));
                }
            }
        }
        return userGroups;
    }

    public List<Group> getAllGroups() {
        return new ArrayList<Group>(groups);
    }

    public int getLevel(Set<Group> permissionGroups) {
        int level = 0;
        for (Group group : permissionGroups) {
            int l = Level.valueOf(group.getName()).getLevel();
            if (Level.valueOf(group.getName()).isContained(l)) {
                level += l;
            }
        }
        return level;
    }

    /**
     * @param group
     * @return Group or null if there is no group for given name
     */
    public Group getGroupByName(String group) {
        return groupsMap.get(group);
    }
    
    @Override
    public List<String> getAllPermissionNames() {    	
    	return new ArrayList<String>(permissions.keySet());
    }
    
    @Override
    public Map<String, Permission> getPermissionsByName(Collection<String> permissionNames) {
    	Map<String, Permission> p = new HashMap<String, Permission>();
    	if (permissionNames != null) {
    		for (String s: permissionNames) {
    			Permission perm = permissions.get(s);
    			if (perm != null) {
    				p.put(s, perm);
    			}
    		}
    	}
    	
    	return p;
    }
}
