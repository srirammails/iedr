package pl.nask.crs.user.permissions;


/**
 * Note: this class is single-threaded
 *
 * @author Patrycja Wegrzynowicz
 */
public abstract class ContextualPermission extends NamedPermission {

    protected ContextualPermission(String id, String name) {
        super(id, name);
    }

    public final boolean implies(PermissionQuery permission) throws PermissionDeniedException {
        if (isImplied(permission)) {
            return verifyContext(permission);
        }
        return false;
    }

    protected boolean isImplied(PermissionQuery permission) throws PermissionDeniedException {
        return super.implies(permission);
    }

    protected abstract boolean verifyContext(PermissionQuery permission) throws PermissionDeniedException;

}
