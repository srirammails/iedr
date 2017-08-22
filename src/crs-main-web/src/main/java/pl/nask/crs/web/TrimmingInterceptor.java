package pl.nask.crs.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class TrimmingInterceptor extends MethodFilterInterceptor {
	private Set<String> excludeTrim = new HashSet<String>();

	public void setExcludeTrim(String params) {		
		excludeTrim = InterceptorHelper.asCollection(params);
	}

	protected void trim(Map<String, Object> map, Set<String> excludeTrim) {
		for (Map.Entry<String, Object> e : map.entrySet()) {
            if ((e.getValue() instanceof String[]) && !excludeTrim.contains(e.getKey())) {			    
				String[] val = (String[]) e.getValue();
				if (val != null) {
					for (int i = 0; i < val.length; i++) {
						val[i] = val[i] != null ? val[i].trim() : null;
					}
				}
			}
		}
	}

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {	    
		Map params = invocation.getInvocationContext().getParameters();
		try {
			trim(params, InterceptorHelper.dynamic(excludeTrim, invocation, this, "excludeTrim"));
		} finally {
			log.debug("trimmed");
		}
		return invocation.invoke();
	}

    
}
