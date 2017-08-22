package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.Permission;

public class NicHandleCreatePermissionQuery extends NamedPermissionQuery {

	private final NewNicHandle nicHandle;
	private final AuthenticatedUser user;

	public NicHandleCreatePermissionQuery(String permissionName, NewNicHandle nicHandle, AuthenticatedUser user) {
		super(permissionName);
		this.nicHandle = nicHandle;
		this.user = user;
	}

	public AuthenticatedUser getUser() {
		return user;
	}
	
	public NewNicHandle getNicHandle() {
		return nicHandle;
	}

}
