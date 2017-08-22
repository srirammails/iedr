package pl.nask.crs.app.authorization.queries;

import java.util.HashSet;
import java.util.Set;

import pl.nask.crs.app.authorization.permissions.ChangeLevelPermission;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class SimpleChangeLevelPermissionQuery extends NamedPermissionQuery implements ChangeLevelPermission {

    private final Set<Group> from = new HashSet<Group>(1);
    private final Set<Group> to = new HashSet<Group>(1);

    public SimpleChangeLevelPermissionQuery(String name, Group from, Group to) {
        super(name);
        if (from != null)
            this.from.add(from);
        if (to != null)
            this.to.add(to);       
    }

    public Set<Group> getFrom() {
        return from;
    }

    public Set<Group> getTo() {
        return to;
    }           
}
