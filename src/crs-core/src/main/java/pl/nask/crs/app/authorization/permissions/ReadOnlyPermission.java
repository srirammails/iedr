package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Kasia Fulara
 */
public class ReadOnlyPermission extends NamedPermission {

    static private Set<String> readOnlyKeywords = new HashSet<String>();
    static private Set<String> exclusions = new HashSet<String>();

    static {
        readOnlyKeywords.add("search");
        readOnlyKeywords.add("view");
        readOnlyKeywords.add("history");
        readOnlyKeywords.add("accessLevel");
        readOnlyKeywords.add("get");
        readOnlyKeywords.add("current");
        readOnlyKeywords.add("getHistory");
        
        exclusions.add("ReportsAppService");
    }

    public ReadOnlyPermission(String id, String name) {
        super(id, name);
    }

    public boolean implies(PermissionQuery permission) throws PermissionDeniedException {

        if (!permission.getName().contains(getName())) return false;
        for (String s: exclusions) {
        	if (permission.getName().contains(s)) return false;
        }

        for (String s : readOnlyKeywords) {
            if (permission.getName().endsWith(s)) return true;
        }
        return false;
    }
    
    @Override
    public String getDescription() {
    	return "Non contextual, grants permission to read-only methods: *search, *view, *history, *accessLevel, *get, *current, *getHistory with the exclusion of ticket/domain reports";
    }
}
