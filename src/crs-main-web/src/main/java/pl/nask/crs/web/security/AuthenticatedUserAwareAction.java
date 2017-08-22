package pl.nask.crs.web.security;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import pl.nask.crs.app.authorization.PermissionAppService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Patrycja Wegrzynowicz
 */
public class AuthenticatedUserAwareAction extends ActionSupport implements SessionAware {

    private static final String CANCEL = "cancel";
    private Map<String, Object> session;

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }
    
    public Map<String, Object> getSession() {
        return session;
    }

    protected AuthenticatedUser getUser() {
        return (AuthenticatedUser) session.get(SecurityConstants.USER_KEY);
    }

    protected String getUsername() {
        AuthenticatedUser user = getUser();
        return user == null ? null : user.getUsername();
    }
    
    public String cancel() {
        return CANCEL;
    }
    
    public boolean hasPermission(String permissionName) {
    	return getPermissionAppService().hasPermission(getUser(), permissionName);
    }
    
    protected PermissionAppService getPermissionAppService() {
    	return (PermissionAppService) session.get(SecurityConstants.PERMISSION_KEY);
    }

}
