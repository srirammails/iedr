package pl.nask.crs.web.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;

/**
 * (C) Copyright 2008 NASK Software Research & Development Department
 *
 * @author Artur Gniadzik
 */
public class ExceptionInterceptor extends ExceptionMappingInterceptor {	

    @Override
    public String intercept(ActionInvocation invocation) {
        String result;
        
        try {
            result = invocation.invoke();
        } catch (Exception e) {
            if (isLogEnabled()) {
                handleLogging(e);
            }
            List<ExceptionMappingConfig> exceptionMappings = invocation
                    .getProxy().getConfig().getExceptionMappings();
            ExceptionMappingConfig mappedResult = this
                    .findMappingFromExceptions(exceptionMappings, e);
            if (mappedResult != null) {
                result = mappedResult.getName();
                publishException(invocation, mappedResult, e);
            } else {
                result = "error";
                publishException(invocation, new ExceptionMappingConfig.Builder("error", e.getClass().getCanonicalName(), result).build(), e);
            }
        }

        return result;
    }

    private void publishException(ActionInvocation invocation,
                                  ExceptionMappingConfig mappedResult, Exception e) {
        LOG.error("Exception intercepted", e);
        LOG.error("Using mapping for: " + mappedResult.getExceptionClassName());
        if (invocation.getAction() instanceof ActionSupport) {
            ActionSupport a = (ActionSupport) invocation.getAction();
            String errorMessage = "Internal error occurred. See logs for details.";
            String msg = mappedResult.getParams().get("message");
            String useExceptionMsg = mappedResult.getParams().get("useExceptionMsg");
            if (msg != null) {
            	errorMessage = msg;
                if (useExceptionMsg != null && !"false".equalsIgnoreCase(useExceptionMsg))
                	errorMessage += " Exception message is: " + e.getMessage();
            } else {
            	errorMessage = e.getMessage();
            }

            a.addActionError(errorMessage);
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.close();            
            invocation.getInvocationContext().getSession().put("ACTION_ERROR_MESSSAGE", errorMessage);
            invocation.getInvocationContext().getSession().put("EXCEPTION_OBJECT", e);
            invocation.getInvocationContext().getSession().put("EXCEPTION_TRACE", sw.toString());
        }

    }

    private ExceptionMappingConfig findMappingFromExceptions(
            List<ExceptionMappingConfig> exceptionMappings, Throwable t) {
        ExceptionMappingConfig result = null;

        // Check for specific exception mappings.
        if (exceptionMappings != null) {
            int deepest = Integer.MAX_VALUE;
            for (ExceptionMappingConfig exceptionMappingConfig : exceptionMappings) {
                int depth = getDepth(exceptionMappingConfig
                        .getExceptionClassName(), t);
                if (depth >= 0 && depth < deepest) {
                    deepest = depth;
                    result = exceptionMappingConfig;
                }
            }
        }

        return result;
    }

    /**
     * Copied from {@link ExceptionMappingInterceptor}
     * <p/>
     * Return the depth to the superclass matching. 0 means ex matches exactly.
     * Returns -1 if there's no match. Otherwise, returns depth. Lowest depth
     * wins.
     *
     * @param exceptionMapping the mapping classname
     * @param t                the cause
     * @return the depth, if not found -1 is returned.
     */
    public int getDepth(String exceptionMapping, Throwable t) {
        return getDepth(exceptionMapping, t.getClass(), 0);
    }

    /**
     * Copied from {@link ExceptionMappingInterceptor}
     */
    private int getDepth(String exceptionMapping, Class exceptionClass,
                         int depth) {
        if (exceptionClass.getName().indexOf(exceptionMapping) != -1) {
            // Found it!
            return depth;
        }
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass.equals(Throwable.class)) {
            return -1;
        }
        return getDepth(exceptionMapping, exceptionClass.getSuperclass(),
                depth + 1);
    }

}
