package pl.nask.crs.user.service;

import pl.nask.crs.user.permissions.Permission;

import java.util.List;

/**
 * @author Kasia Fulara
 */
public interface PermissionsFactory {

    List<Permission> getPermissions();

}
