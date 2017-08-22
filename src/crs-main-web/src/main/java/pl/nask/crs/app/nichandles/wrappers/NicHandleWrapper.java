package pl.nask.crs.app.nichandles.wrappers;

import java.util.HashSet;
import java.util.Set;

import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;


/**
 * @author Marianna Mysiorska
 */
public class NicHandleWrapper {
    NicHandle nichandle;
    private PhoneWrapper phonesWrapper;
    private PhoneWrapper faxesWrapper;
    private PermissionGroupsWrapper permissionGroupsWrapper;
    private User user;

    public NicHandleWrapper(NicHandle nichandle) {
        this.nichandle = nichandle;
        if(nichandle.getPhones()==null)
            nichandle.setPhones(new HashSet<String>());
        if(nichandle.getFaxes()==null)
            nichandle.setFaxes(new HashSet<String>());
        this.phonesWrapper = new PhoneWrapper(nichandle.getPhones());
        this.faxesWrapper = new PhoneWrapper(nichandle.getFaxes());
    }


    public void createPermissionGroupsWrapper(AuthorizationGroupsFactory authorizationGroupsFactory){
        this.permissionGroupsWrapper = new PermissionGroupsWrapper(authorizationGroupsFactory);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NicHandle getNichandle() {
        return nichandle;
    }

    public PhoneWrapper getPhonesWrapper() {
        return phonesWrapper;
    }

    public PhoneWrapper getFaxesWrapper() {
        return faxesWrapper;
    }

    public PermissionGroupsWrapper getPermissionGroupsWrapper() {
        return permissionGroupsWrapper;
    }

    public void setPermissionGroupsWrapper(PermissionGroupsWrapper permissionGroupsWrapper) {
        this.permissionGroupsWrapper = permissionGroupsWrapper;
    }

    public void setGroupsFromUser(){
        if (user != null){
            for (Group g : user.getPermissionGroups()) {
                permissionGroupsWrapper.setGroup(g, true);
            }            
        }
    }

    public void setGroupsToUser(){
        if (user == null) {
            user = new User();
            user.setUsername(nichandle.getNicHandleId());
        }
        
        user.setPermissionGroups(permissionGroupsWrapper.getPermissionGroups());
    }


	public Set<String> getUserPermissions() {
		return user.getPermissions().keySet();
	}
	
	public boolean hasUserPermission(String name) {
		return user.getPermissions().containsKey(name);
	}
}
