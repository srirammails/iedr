package pl.nask.crs.commons.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.config.ConfigEntry.ConfigValueType;

public class HardcodedGenericConfig implements GenericConfig {

    private Map<String, ConfigEntry> entries = new HashMap<String, ConfigEntry>();

    public HardcodedGenericConfig() {
        addEntry("iedr_account_id", 2L);
        addEntry("nrp_mailed_period", 14);
        addEntry("nrp_suspended_period", 14);
        addEntry("iedr_vat_no", null);
        addEntry("deletion_dsm_state", 387);
        addEntry("account_update_xml_export_path", "/tmp/accountUpdateExport/");
        addEntry("doa_xml_export_path", "/tmp/doaExport/");
        addEntry("gibo_retry_timeout_hours", 24);
        addEntry("incoming_docs_path_fax_new", "/test_incoming_docs/new");
        addEntry("incoming_docs_path_fax_assigned", "/test_incoming_docs/assigned");
        addEntry("nameserver_min_count", 2);
        addEntry("nameserver_max_count", 7);
        addEntry("document_allowed_types", "tif,tiff");
    }

    private void addEntry(String key, long l) {
        entries.put(key, new ImmutableConfigEntry(key, "" + l, ConfigValueType.LONG));
    }

    private void addEntry(String key, int i) {
        entries.put(key, new ImmutableConfigEntry(key, "" + i, ConfigValueType.INT));
    }

    private void addEntry(String key, String s) {
        entries.put(key, new ImmutableConfigEntry(key, s, ConfigValueType.STRING));
    }

    @Override
    public ConfigEntry getEntry(String key) {
        if (!entries.containsKey(key)) {
            return null;
        }
        return entries.get(key);
    }

    @Override
    public void putEntry(ConfigEntry entry) {
        entries.put(entry.getKey(), entry);
    }

    @Override
    public List<ConfigEntry> getAllEntries() {
        return new ArrayList<ConfigEntry>(entries.values());
    }

    @Override
    public void updateEntry(ConfigEntry entry) {
        putEntry(entry);
    }

    @Override
    public ConfigEntry getEntryOrThrowNpe(String configKey) {
        ConfigEntry c = getEntry(configKey);
        if (c == null)
            throw new NullPointerException("No config entry for key " + configKey);

        return c;
    }
}

