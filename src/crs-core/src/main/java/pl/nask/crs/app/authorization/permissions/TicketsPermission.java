package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * @author Kasia Fulara
 */
public class TicketsPermission extends NamedPermission {

    public TicketsPermission(String id, String name) {
        super(id, name);
    }

    public boolean implies(PermissionQuery permission) throws PermissionDeniedException {
        return (permission.getName().contains(getName()));
    }
}
