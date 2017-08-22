package pl.nask.crs.user.permissions;

/**
 * @author Kasia Fulara
 */
public class PackagePermission extends NamedPermission {

    public PackagePermission(String id, String name) {
        super(id, name);
    }

    public boolean implies(PermissionQuery permission) throws PermissionDeniedException {
        return permission.getName().contains(getName());
    }
    
    @Override
    public String toString() {    
    	return "Package[id:" + getId() + ", name:*" + getName() + "*]";
    }
    
    @Override
    public String getDescription() {    
    	if (getClass() != PackagePermission.class)
    		return null;
    	return "Non contextual, grants access to " + getDescriptionFromTheResourceBundle(getName());
    }
}
