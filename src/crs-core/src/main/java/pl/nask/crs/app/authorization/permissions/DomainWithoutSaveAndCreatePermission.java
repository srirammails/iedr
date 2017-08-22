package pl.nask.crs.app.authorization.permissions;

import java.util.Arrays;

import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.user.permissions.MultiNamedPermission;

/**
 * @author Kasia Fulara
 */
public class DomainWithoutSaveAndCreatePermission extends MultiNamedPermission {

	public DomainWithoutSaveAndCreatePermission(String id, String name) {
        super(id, name, Arrays.asList("view", "viewPlain", "search", "findTransferedDomains", "checkDomainExists", "isEventValid", "checkAvailability", "checkTransferAvailable", "checkPayAvailable"));
    }
	
	@Override
	public String getDescription() {
		if (getClass() != DomainWithoutSaveAndCreatePermission.class)
			return null;
		return "Non contextual, granting access to " 
			+ (permissionNames.size() > 1 ? ": " : "") + 
				CollectionUtils.toString(getDescriptionsFromTheResourceBundle(permissionNames), ", ");
	}

}
