package pl.nask.crs.commons.config;

import java.util.List;

import pl.nask.crs.commons.dao.ibatis.SqlMapClientLoggingDaoSupport;

public class IBatisDaoBasedConfig extends SqlMapClientLoggingDaoSupport implements GenericConfig {

    @Override
    public ConfigEntry getEntry(String key) {
        return performQueryForObject("config.getValue", key);
    }

    @Override
    public ConfigEntry getEntryOrThrowNpe(String configKey) {
        ConfigEntry c = getEntry(configKey);
        if (c == null)
            throw new NullPointerException("No config entry for key " + configKey);

        return c;
    }

    @Override
    public void putEntry(ConfigEntry entry) {
        performInsert("config.create", entry);
    }

    @Override
    public void updateEntry(ConfigEntry entry) {
        int i = performUpdate("config.update", entry);
        if (i == 0) {
            throw new IllegalArgumentException("No entry matching " + entry + " foung in the database");
        }
    }

    @Override
    public List<ConfigEntry> getAllEntries() {
        return performQueryForList("config.getAllEntries");
    }

}
