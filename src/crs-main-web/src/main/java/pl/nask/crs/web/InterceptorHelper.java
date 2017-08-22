package pl.nask.crs.web;

import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.TextParseUtil;

public class InterceptorHelper {
    /**
     * Return a collection from the comma delimited String. (copied from the
     * MethodFilterInterceptor - it has a 'private' modifier there....)
     * 
     * @param commaDelim
     *            the comma delimited String.
     * @return A collection from the comma delimited String. Returns
     *         <tt>null</tt> if the string is empty.
     */
    public static Set<String> asCollection(String commaDelim) {
        if (commaDelim == null || commaDelim.trim().length() == 0) {
            return new HashSet<String>();
        }
        return TextParseUtil.commaDelimitedStringToSet(commaDelim);
    }

    /**
     * Creates a union of set given by the 'actualParams' parameter and the set
     * of parameters got from the DynamicInterceptorProperties.
     * 
     * @param actualParams
     *            actual set of parameters
     * @param invocation
     *            action invocation
     * @param interceptor
     *            interceptor, which dynamic parameters (given by the paramName
     *            parameter) are to be added to the actual set of parameters
     * @param paramName
     *            dynamic parameter name
     * @param interceptor
     * 
     * @return
     */
    public static Set<String> dynamic(Set actualParams, ActionInvocation invocation, Object interceptor,
            String paramName) {
        String dp = DynamicInterceptorProperties.getDynamicParameter(invocation, interceptor, paramName);
        Set<String> s = InterceptorHelper.asCollection(dp);

        s.addAll(actualParams);
        return s;
    }
}
