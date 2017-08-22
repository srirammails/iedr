package pl.nask.crs.user.permissions;


/**
 * @author Kasia Fulara
 */
public class FullAccessPermission extends NamedPermission {

    public FullAccessPermission(String id, String name) {
        super(id, name);
    }

    public boolean implies(PermissionQuery permission) throws PermissionDeniedException {
        return true;
    }
    
    @Override
    public String getDescription() {
    	return "Grants full access to CRS";
    }
}
