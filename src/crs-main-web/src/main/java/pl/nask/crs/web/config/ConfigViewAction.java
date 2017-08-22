package pl.nask.crs.web.config;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.app.config.ConfigAppService;
import pl.nask.crs.commons.config.ConfigEntry;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class ConfigViewAction extends AuthenticatedUserAwareAction {
	
	private final ConfigAppService service;
	public List<ConfigEntry> entries;

	private ConfigEntry configEntry;
	
	public ConfigViewAction(ConfigAppService service) {
		this.service = service;
	}
	
	public List<ConfigEntry> getEntries() {
		if (entries == null) {
			entries = new ArrayList<ConfigEntry>();
			entries = service.getEntries(getUser());
		}
		return entries;
	}
	
	public ConfigEntry getConfigEntry() {
		return configEntry;
	}
	
	public void setConfigEntry(ConfigEntry configEntry) {
		this.configEntry = configEntry;
	}
}
