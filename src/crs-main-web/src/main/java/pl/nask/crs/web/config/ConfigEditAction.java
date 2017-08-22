package pl.nask.crs.web.config;

import pl.nask.crs.app.config.ConfigAppService;
import pl.nask.crs.commons.config.ConfigEntry;
import pl.nask.crs.commons.config.MutableConfigEntry;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class ConfigEditAction extends AuthenticatedUserAwareAction {
	private final ConfigAppService service;

	private ConfigEntry entry;
	
	private String newValue;

	private MutableConfigEntry newEntry;

	private String key;
	
	public ConfigEditAction(ConfigAppService service) {
		this.service = service; 
	}
	
	boolean validateSave () {
		newEntry = new MutableConfigEntry();
		newEntry.setKey(getEntry().getKey());
		newEntry.setType(getEntry().getType());
		newEntry.setValue(newValue);
		if (!newEntry.validateValue()) {
			addFieldError("newValue", "Wrong value type, cannot parse to " + getEntry().getType());
			return false;
		} else {
			return true;
		}
	}
	
	public String save() {
		if (!validateSave()) {
			return ERROR;
		} else {
			service.updateEntry(getUser(), newEntry);
			return SUCCESS;
		}
	}
	
	public ConfigEntry getEntry() {
		if (entry == null) {
			entry = service.getEntry(getUser(), key);			
		}
		return entry;
	}
	
	public String getNewValue() {
		return newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
