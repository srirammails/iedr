package pl.nask.crs.app.authorization.permissions;

import java.util.HashSet;
import java.util.Set;

import pl.nask.crs.app.authorization.queries.NicHandleQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.user.permissions.ContextualPermission;
import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * @author Kasia Fulara
 *         <p/>
 *         User cannot edit anything else than address and vat
 */
public class NicHandlePartEditPermission extends ContextualPermission {

    private NicHandleDAO nicHandleDAO;
    static private Set<String> sectionPermissionKeywords = new HashSet<String>();

    static {
        sectionPermissionKeywords.add("editVat");
        sectionPermissionKeywords.add("editAddress");
    }

    public NicHandlePartEditPermission(String id, String name, NicHandleDAO nicHandleDAO) {
        super(id, name);
        Validator.assertNotNull(nicHandleDAO, "nichandle dao");
        this.nicHandleDAO = nicHandleDAO;
    }

    protected boolean isImplied(PermissionQuery permission) {
        if (!getName().equals(permission.getName())) {
            for (String s : sectionPermissionKeywords) {
                if (permission.getName().endsWith(s)) return true;
            }
            return false;
        }
        return true;
    }
    
    protected NicHandle getNhFromDb(String nh) {
    	return nicHandleDAO.get(nh);
    }

    protected boolean verifyContext(PermissionQuery permission) {
                
        if (!(permission instanceof NicHandleQuery)) {
            return false; //cast would cause an exception
        }
        
        NicHandle nicHandle = ((NicHandleQuery) permission).getNicHandle();
        NicHandle oldNicHandle = nicHandleDAO.get(nicHandle.getNicHandleId());
        
        return changeAllowed(oldNicHandle, nicHandle);
    }

	protected boolean changeAllowed(NicHandle oldNicHandle, NicHandle nicHandle) {
		 return (nicHandle.getAccount().getId() == oldNicHandle.getAccount().getId()) &&
	                nicHandle.getCompanyName().equals(oldNicHandle.getCompanyName()) &&
	                nicHandle.getName().equals(oldNicHandle.getName());
	}
}
