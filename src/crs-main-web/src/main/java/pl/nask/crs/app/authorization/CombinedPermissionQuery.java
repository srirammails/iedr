package pl.nask.crs.app.authorization;

import pl.nask.crs.security.authentication.AuthenticatedUser;

public class CombinedPermissionQuery {

	private String[] permissionNames;

	public boolean isMetFor(PermissionAppService service, AuthenticatedUser user) {
		for (String name: permissionNames) {
			if (service.hasPermission(user, name)) {
				return true;
			}
		}
		return false;
	}

	public static CombinedPermissionQuery alternativeOf(String... name) {
		CombinedPermissionQuery q = new CombinedPermissionQuery();
		q.setPermissionNames(name);
		return q;
	}

	private void setPermissionNames(String[] name) {
		this.permissionNames = name;
	}

}
