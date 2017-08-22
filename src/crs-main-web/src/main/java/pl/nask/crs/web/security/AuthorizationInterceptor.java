package pl.nask.crs.web.security;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.Map;

/**
 * @author Patrycja Wegrzynowicz
 */
public class AuthorizationInterceptor implements Interceptor {

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext ctx = actionInvocation.getInvocationContext();
        Map session = ctx.getSession();

        AuthenticatedUser user = (AuthenticatedUser) session.get(SecurityConstants.USER_KEY);
        String userName = user == null ? null : user.getUsername();
        boolean hasPermission = userName != null && SecurityWorker.getInstance().hasPermission(userName, ctx);
        return !hasPermission ? SecurityConstants.ACCESS_DENIED : actionInvocation.invoke();
    }
}
