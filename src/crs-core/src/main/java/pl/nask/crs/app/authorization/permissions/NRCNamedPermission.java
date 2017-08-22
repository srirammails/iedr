package pl.nask.crs.app.authorization.permissions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class NRCNamedPermission extends NamedPermission {

    private Set<String> permissionNames = new HashSet<String>();

    public NRCNamedPermission(String id, String name, List<String> operationNames) {
        super(id, name);
        for (String operationName : operationNames) {
            permissionNames.add(operationName);
        }
    }

    @Override
    public boolean implies(PermissionQuery permission) throws PermissionDeniedException {
        return permissionNames.contains(permission.getName());
    }

    public List<String> getPermissionNames() {
        return new ArrayList<String>(permissionNames);
    }
    
	@Override
	public String getDescription() {
		if (getClass() != NRCNamedPermission.class)
			return null;
		return "Non contextual, verifies customer role: "  
			+ (permissionNames.size() > 1 ? ": " : "") + 
				CollectionUtils.toString(getDescriptionsFromTheResourceBundle(permissionNames), ", ");
	}

}
