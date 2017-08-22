package pl.nask.crs.app.config;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import pl.nask.crs.commons.config.ConfigEntry;
import pl.nask.crs.commons.config.GenericConfig;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class ConfigAppServiceImpl implements ConfigAppService {
	private GenericConfig config;

	public ConfigAppServiceImpl(GenericConfig config) {
		this.config = config;
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<ConfigEntry> getEntries(AuthenticatedUser user) {
		return config.getAllEntries();
	}

	@Override
    @Transactional
    public void updateEntry(AuthenticatedUser user, ConfigEntry entry) {
		config.updateEntry(entry);
	}

	@Override
    @Transactional
	public void createEntry(AuthenticatedUser user, ConfigEntry entry) {
		config.putEntry(entry);
	}

	@Override
    @Transactional(readOnly = true)
    public ConfigEntry getEntry(AuthenticatedUser user, String key) {
		return config.getEntry(key);
	}
}
