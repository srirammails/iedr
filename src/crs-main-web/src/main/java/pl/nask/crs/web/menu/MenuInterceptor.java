package pl.nask.crs.web.menu;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.security.SecurityConstants;
import pl.nask.crs.app.authorization.PermissionAppService;

import java.util.Map;

/**
 * @author Patrycja Wegrzynowicz
 */
public class MenuInterceptor implements Interceptor {

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext ctx = actionInvocation.getInvocationContext();
        Map session = ctx.getSession();
        AuthenticatedUser user = (AuthenticatedUser) session.get(SecurityConstants.USER_KEY);
//		 session.put(MenuAdapter.MENU_KEY, new MenuAdapter(user == null ? null : user.getUsername()));
		 PermissionAppService permissionAppService = (PermissionAppService) session.get(SecurityConstants.PERMISSION_KEY);
		 session.put(MenuAdapter.MENU_KEY, new MenuAdapter(user == null ? null : user, permissionAppService));
		 return actionInvocation.invoke();
    }
}
