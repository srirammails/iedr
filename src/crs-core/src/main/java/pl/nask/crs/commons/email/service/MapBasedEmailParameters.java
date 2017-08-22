package pl.nask.crs.commons.email.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.utils.HtmlPrintable;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class MapBasedEmailParameters implements EmailParameters { 
	private Map<ParameterNameEnum, Object> values = new HashMap<ParameterNameEnum, Object>();

    public String getLoggedInNicHandle() {
        return null; // Implemented in subclasses
    }

    public String getAccountRelatedNicHandle() {
        return null; // Implemented in subclasses
    }

    public String getDomainName() {
        return null; // Implemented in subclasses
    }

	public void set(ParameterNameEnum name, Object value) {
		values.put(name, value);		
	}

	@Override
	public List<? extends ParameterName> getParameterNames() {		
		return new ArrayList<ParameterName>(values.keySet());				
	}

	@Override
	public String getParameterValue(String name, boolean html) {
		ParameterNameEnum key = ParameterNameEnum.forName(name);
		if (values.containsKey(key)) {
			Object res = values.get(key);
			if (res == null) {
				return "";
			} else {
				if (html && res instanceof HtmlPrintable) {
					return (((HtmlPrintable) res).toHtmlString());
				} else {
					return res.toString();
				}
			}
		} else {
			return name;
		}
	}
}
