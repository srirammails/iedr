package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * @author Kasia Fulara
 */
public class NicHandleWithoutEditPermission extends NamedPermission implements PermissionQuery {

    public NicHandleWithoutEditPermission(String id, String name) {
        super(id, name);
    }

    public boolean implies(PermissionQuery permission) throws PermissionDeniedException {
        return (permission.getName().contains(getName()) 
                && !permission.getName().endsWith("save")
                && !permission.getName().endsWith("editVat")
                && !permission.getName().endsWith("editAddress")
                && !permission.getName().endsWith("editMain"));
    }
    
    @Override
    public String getDescription() {
    	if (getClass() != NicHandleWithoutEditPermission.class) 
    		return null;
    	return "Non contextual, allows to perform all operations on the NicHandle but changing it's data (status change is allowed)";
    
    }
}
