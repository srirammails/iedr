package pl.nask.crs.web.security;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.Map;

/**
 * @author Patrycja Wegrzynowicz, Artur Gniadzik
 */
public class AuthenticationInterceptor implements Interceptor {

    public void destroy() {
    }

    public void init() {
    }

    /**
     * Check, if the user is logged in.
     *
     * @returns "login" if the user is NOT logged in, or result from the next interceptor from the stack, if he/she is.
     */
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map session = actionInvocation.getInvocationContext().getSession();
        AuthenticatedUser user = (AuthenticatedUser) session.get(SecurityConstants.USER_KEY);
        boolean isAuthenticated = user != null;
        return !isAuthenticated ? Action.LOGIN : actionInvocation.invoke();
    }
}
