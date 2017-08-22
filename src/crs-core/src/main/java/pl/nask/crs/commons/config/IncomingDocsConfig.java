package pl.nask.crs.commons.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IncomingDocsConfig {

    private Map<String, String> mappings = new HashMap<String, String>();

    public String getMappingFor(String documentType) {
        return mappings.get(documentType);
    }

    public void addMapping(String key, String path) {
        mappings.put(key, path);
    }

    public Set<String> getMappingKeys() {
        return mappings.keySet();
    }
}
