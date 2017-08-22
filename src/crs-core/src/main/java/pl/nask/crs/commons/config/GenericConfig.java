package pl.nask.crs.commons.config;

import java.util.List;

public interface GenericConfig {
	ConfigEntry getEntry(String key);

	void putEntry(ConfigEntry entry);

	List<ConfigEntry> getAllEntries();

	void updateEntry(ConfigEntry entry);

	ConfigEntry getEntryOrThrowNpe(String configKey);	
}
