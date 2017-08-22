package pl.nask.crs.web.security;

import pl.nask.crs.security.authentication.*;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.authorization.PermissionAppService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * @author: Marcin Tkaczyk
 */
public class LogonWithPassChangeAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private String username;
    private String password;
    private Map session;
    private AuthenticationService authenticationService;
    private PermissionAppService permissionAppService;
    private String newPassword1;
    private String newPassword2;
    private NicHandleAppService nicHandleAppService;
    private HttpServletRequest request;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public PermissionAppService getPermissionAppService() {
        return permissionAppService;
    }

    public void setPermissionAppService(PermissionAppService permissionAppService) {
        this.permissionAppService = permissionAppService;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    public NicHandleAppService getNicHandleAppService() {
        return nicHandleAppService;
    }

    public void setNicHandleAppService(NicHandleAppService nicHandleAppService) {
        this.nicHandleAppService = nicHandleAppService;
    }

    public String logInAndSaveNewPassword() throws Exception {
        try {
            AuthenticatedUser u = authenticationService.authenticate(username, password, false, request.getRemoteAddr(), false, null, false, "crs");
            nicHandleAppService.saveNewPassword(u, newPassword1, newPassword2, username);
            session.put(SecurityConstants.USER_KEY, u);
            session.put(SecurityConstants.PERMISSION_KEY, permissionAppService);
        } catch (InvalidUsernameException ex) {
            addActionError("Invalid username: " + ex.getUsername());
            return ERROR;
        } catch (InvalidPasswordException ex) {
            addActionError("Invalid password");
            return ERROR;
        } catch (NoLoginPermissionException ex) {
            addActionError("No login permission for user " + ex.getUsername());
            return ERROR;
        } catch (AuthenticationException ex) {
            addActionError(ex.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    public void setSession(Map map) {
        this.session = map;
    }

    public String input() throws Exception {
        return super.input();
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
