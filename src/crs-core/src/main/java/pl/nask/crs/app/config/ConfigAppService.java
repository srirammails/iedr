package pl.nask.crs.app.config;

import java.util.List;

import pl.nask.crs.commons.config.ConfigEntry;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface ConfigAppService {
	public List<ConfigEntry> getEntries(AuthenticatedUser user);
	public void updateEntry(AuthenticatedUser user, ConfigEntry entry);
	public void createEntry(AuthenticatedUser user, ConfigEntry entry);
	public ConfigEntry getEntry(AuthenticatedUser user, String key);
}

