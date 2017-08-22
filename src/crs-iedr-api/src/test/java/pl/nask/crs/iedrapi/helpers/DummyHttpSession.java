package pl.nask.crs.iedrapi.helpers;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import pl.nask.crs.security.authentication.AuthenticatedUser;

public class DummyHttpSession implements HttpSession {
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public DummyHttpSession(final String username) {
		setAttribute("user", new AuthenticatedUser() {
			@Override
			public String getUsername() {
				return username;
			}
			
			@Override
			public String getSuperUserName() {
				return null;
			}
			
			@Override
			public String getAuthenticationToken() {
				return "token";
			}
		});
	}

	@Override
	public long getCreationTime() {
		return 0;
	}

	@Override
	public String getId() {
		return "sessionId";
	}

	@Override
	public long getLastAccessedTime() {
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
	}

	@Override
	public int getMaxInactiveInterval() {
		return 100;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}

	@Override
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public Object getValue(String name) {
		return null;
	}

	@Override
	public Enumeration getAttributeNames() {
		return Collections.enumeration(attributes.keySet());
	}

	@Override
	public String[] getValueNames() {
		return null;
	}

	@Override
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	@Override
	public void putValue(String name, Object value) {
	}

	@Override
	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	@Override
	public void removeValue(String name) {
	}

	@Override
	public void invalidate() {
		attributes.clear();
	}

	@Override
	public boolean isNew() {
		return false;
	}

}
