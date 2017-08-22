package pl.nask.crs.domains.dsm.events;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;

public abstract class AbstractEvent implements DsmEvent {

	private final DsmEventName eventName;
	
	Map<String, Object> params = new HashMap<String, Object>(); 

	public AbstractEvent(DsmEventName eventName) {
		this.eventName = eventName;
	}

	@Override
	public DsmEventName getName() {
		return eventName;
	}
	
	@Override
	public Object getParameter(String name) {
		Object param = params.get(name);
		if (param == null) {
			throw new IllegalArgumentException("No parameter found for name " + name + " in this event");
		} else {
			return param;
		}
	}
	
	@Override
	public boolean hasParameter(String name) {	
		return params.containsKey(name);
	}

	@Override
	public Date getDateParameter(String name) {
		return getParameter(name, Date.class);
	}
	
	@Override
	public String getStringParameter(String name) {
		return getParameter(name, String.class);
	}
	
	@Override
	public int getIntParameter(String name) {	
		return getParameter(name, Integer.class);
	}
	
	protected void setParameter(String name, Object param) {
		if (param != null) {
			params.put(name, param);
		} else {
			params.remove(name);
		}
	}
	
	protected <T> T getParameter(String name, Class<T> clazz) {
		Object param = getParameter(name);
		if (clazz.isAssignableFrom(param.getClass())) {
			return (T) param;
		} else {
			throw new IllegalArgumentException("Parameter " + name + " is a type of " + param.getClass() + ". Expected: " + clazz);
		}
	}
	
}
