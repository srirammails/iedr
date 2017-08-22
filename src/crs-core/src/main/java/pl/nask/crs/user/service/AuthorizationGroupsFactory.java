package pl.nask.crs.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.nask.crs.user.Group;
import pl.nask.crs.user.permissions.Permission;

/**
 * @author Kasia Fulara
 */
public interface AuthorizationGroupsFactory {

    Set<Group> getGroups(int level);

    List<Group> getAllGroups();

    int getLevel(Set<Group> permissionGroups);

    Group getGroupByName(String group);
    
	Map<String, Permission> getPermissionsByName(Collection<String> permissionNames);

	List<String> getAllPermissionNames();
}
