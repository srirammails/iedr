package pl.nask.crs.user.permissions;

public class PackagePermissionQuery extends PackagePermission implements PermissionQuery {
	
	public PackagePermissionQuery(String packageName) {
		super ("query", packageName);
	}
}
