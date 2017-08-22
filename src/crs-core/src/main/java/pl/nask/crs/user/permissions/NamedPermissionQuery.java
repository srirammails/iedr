package pl.nask.crs.user.permissions;

public class NamedPermissionQuery extends NamedPermission implements PermissionQuery {

	public NamedPermissionQuery(String name) {
		super("query", name);
	}
}
