package pl.nask.crs.app.authorization.permissions;

import java.util.Set;

import pl.nask.crs.user.Group;

public interface ChangeLevelPermission {
    /**
     * returns set of disabled access levels
     * 
     * @return
     */
    Set<Group> getFrom();

    /**
     * returns set of enabled access levels
     * 
     * @return
     */
    Set<Group> getTo();      
}
