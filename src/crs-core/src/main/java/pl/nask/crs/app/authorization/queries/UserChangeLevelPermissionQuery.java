package pl.nask.crs.app.authorization.queries;

import java.util.Set;

import pl.nask.crs.app.authorization.permissions.ChangeLevelPermission;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PermissionQuery;

public class UserChangeLevelPermissionQuery extends NamedPermission implements ChangeLevelPermission, PermissionQuery {
    private Set<Group> from;
    private Set<Group> to;
    private final String text;
        
    public UserChangeLevelPermissionQuery(String id, String name, User changedUser, Set<Group> remove, Set<Group> add) {
        super(id, name);
        from = remove;
        to = add;               
        this.text = name + " username: " + changedUser.getName() + " remove:" + remove + " add:" + add;
    }
    
    public Set<Group> getFrom() {     
        return from;
    }

    public Set<Group> getTo() {
        return to;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
