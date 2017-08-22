package pl.nask.crs.user.permissions;

/**
 * Indicates, that the permission is used as a query (not as an actual permission) to check, if it's implied by the permissions hold by the user.  
 *
 */
public interface PermissionQuery {

	String getName();
}
