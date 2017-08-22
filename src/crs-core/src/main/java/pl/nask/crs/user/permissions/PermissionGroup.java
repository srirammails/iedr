package pl.nask.crs.user.permissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import pl.nask.crs.commons.utils.Validator;

/**
 * A group of permissions is used to check if the queried permissions is implied by one of the permissions from the groups list. 
 * The permissions are being processed as they are ordered on the list.
 * 
 * @author Artur Gniadzik
 *
 */
public class PermissionGroup implements Permission {

	private final String id;
	private final List<Permission> permissions;
	
	public PermissionGroup(String id, List<Permission> permissions) {
		Validator.assertNotEmpty(id, "id");
		this.id = id;
		if (permissions != null) {
			this.permissions = permissions;
		} else {
			this.permissions = Collections.emptyList();
		}
	}
	
	@Override
	public String getName() {
		return PermissionGroup.class.getCanonicalName() + "$$" + id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean implies(PermissionQuery permission) throws PermissionDeniedException {
		for (Permission p: permissions) {
			if (p.implies(permission))
				return true;
		}
		return false;
	}
	
	public Collection<Permission> getEmbeddedPermissions() {
		return new ArrayList<Permission>(permissions);
	}
	
	@Override
	public String toString() {
		return "Permission Group: " + id + " with permissions: " + permissions;
	}
	
	@Override
	public String getDescription() {		
		return "A group of permissions, check getEmbeddedPermissions() for details";
	}
}
