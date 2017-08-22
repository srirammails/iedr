package pl.nask.crs.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class DynamicInterceptorProperties extends MethodFilterInterceptor {      
    private static final String DYNAMIC_PARAMS = "nask_dynamic_params";
    private static final Logger log = Logger.getLogger(DynamicInterceptorProperties.class);
    
    private Map<String, Map<String, String>> dynamicParams = new HashMap<String, Map<String, String>>();   
    
    // comma delimited array of params in the following format:
    // interceptorName:paramName:value1:value2:value3....
    public void setDynamicParams(String params) {
        this.dynamicParams = buildParamsMap(params);
    }
    
    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        Map<String, InterceptorMapping> interceptorsMap = buildInterceptorsMap(invocation);         
        Map<String, Map<String, String>> dynamicParamsMap = buildDynamicParams(interceptorsMap, dynamicParams);
        invocation.getInvocationContext().put(DYNAMIC_PARAMS, dynamicParamsMap);
        return invocation.invoke();
    }   

    private Map<String, Map<String, String>> buildDynamicParams(Map<String, InterceptorMapping> interceptorsMap,
            Map<String, Map<String, String>> dynamicParams) {
        HashMap<String, Map<String, String>> dynamicParamsMap = new HashMap<String, Map<String, String>>();
        for (Map.Entry<String, Map<String, String>> param : dynamicParams.entrySet()) {
            InterceptorMapping i = interceptorsMap.get(param.getKey());
            if (i != null)
                dynamicParamsMap.put(i.getInterceptor().getClass().getName(), param.getValue());
            else
                log.warn("Interceptor " + param.getKey() + " not found in the interceptor stack");
        }
        return dynamicParamsMap;
    }

    

    private Map buildParamsMap(String dynamicParams) {
        Set<String> params = InterceptorHelper.asCollection(dynamicParams);
        if (params == null)
            return new HashMap();
        
        Map<String, Map<String, String>> paramsMap = new HashMap<String, Map<String, String>>();
        for (String param : params) {
            String[] parama = param.split(":", 3);
            if (parama.length < 2) // skip (bad entry)
                continue;
            
            addEntry(paramsMap, parama[0], parama[1], parama.length == 2 ? null : parama[2].replaceAll(":", ","));                
        }
        
        return paramsMap;
        
    }

    private void addEntry(Map<String, Map<String, String>> paramsMap, String key, String subkey, String value) {        
        Map<String, String> m = paramsMap.get(key);
        if(m==null) {
            m = new HashMap<String, String>();
            paramsMap.put(key, m);
        }
        m.put(subkey, value);                
    }

    private Map<String, InterceptorMapping> buildInterceptorsMap(ActionInvocation invocation) {
        Map<String, InterceptorMapping> map = new HashMap<String, InterceptorMapping>();
        
        for (InterceptorMapping m : invocation.getProxy().getConfig().getInterceptors()) {
            map.put(m.getName(), m);
        }
        return map;
    }

    public static String getDynamicParameter(ActionInvocation invocation, Object owner, String paramName) {
        Object m = invocation.getInvocationContext().get(DYNAMIC_PARAMS);        
        if (m == null)
            return null;
        
        if (!(m instanceof Map)) {
            log.warn("Map expected to be found under " + DYNAMIC_PARAMS + " in ActionContext (not " + m + ")");
            return null;
        }
                    
        Object mm = ((Map) m).get(owner.getClass().getName());
        if (mm == null)
            return null;
        
        if (!(mm instanceof Map)) {
            log.warn("Map expected to be found under " + DYNAMIC_PARAMS + "[" + owner.getClass().getName()
                    + "] in ActionContext (not " + m + ")");
            return null;
        }
        
        Object v = ((Map) mm).get(paramName);
        if(v==null)
            return null;
        
        if(!(v instanceof String)) {
            log.warn("String expected to be found under " + DYNAMIC_PARAMS + "[" + owner.getClass().getName()
                    + "]["+paramName+"] in ActionContext (not " + m + ")");
            return null;
        }
        
        return (String) v;         
    }    
}
