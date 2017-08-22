package pl.nask.crs.user.permissions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import pl.nask.crs.commons.utils.Validator;

/**
 * @author Patrycja Wegrzynowicz
 */
public class NamedPermission implements Permission {

    private String name;
	private String id;

    public NamedPermission(String id, String name) {
        Validator.assertNotNull(name, "name");
        Validator.assertNotNull(id, "id");
        this.name = name;
        this.id = id;
    }
    
    public NamedPermission(String name) {
        Validator.assertNotNull(name, "name");
        this.name = name;
        this.id = "query";
    }


    public String getName() {
        return name;
    }
    
    @Override
    public String getId() {
    	return id;
    }

    public boolean implies(PermissionQuery permission) throws PermissionDeniedException {
        return name.equals(permission.getName());
    }

    @Override
    public String toString() {     
        return "NamedPermission[id: " + id + ", name: " + name + "]";
    }
    
    @Override
    public String getDescription() {
    	if (getClass() != NamedPermission.class) {
    		return null;
    	}
    	return "Non contextual permission granting access to : " + getDescriptionFromTheResourceBundle(name);
    }

	protected String getDescriptionFromTheResourceBundle(String name) {
		ResourceBundle bundle = ResourceBundle.getBundle("permissionDescription");
		String key = name;
		if (bundle.containsKey(key)) {
			return bundle.getString(key) + " (" + name + ")";
		} else {
			return name;
		}
	}
	
	protected List<String> getDescriptionsFromTheResourceBundle(Collection<String> names) {
		List<String> descriptions = new LinkedList<String>();
		for (String name: names) {
			descriptions.add(getDescriptionFromTheResourceBundle(name));
		}
		return descriptions;
	}
}