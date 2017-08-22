package pl.nask.crs.commons.config;

public class ImmutableConfigEntry extends AbstractConfigEntry implements ConfigEntry {

	private final String key;
	private final String value;
	private final ConfigValueType type;

	public ImmutableConfigEntry(String key, String value, ConfigValueType type) {
		this.key = key;
		this.value = value;
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see pl.nask.crs.commons.config.ConfigEntry#getKey()
	 */
	@Override
	public String getKey() {
		return key;
	}
	/* (non-Javadoc)
	 * @see pl.nask.crs.commons.config.ConfigEntry#getValue()
	 */
	@Override
	public String getValue() {
		return value;
	}
	/* (non-Javadoc)
	 * @see pl.nask.crs.commons.config.ConfigEntry#getType()
	 */
	@Override
	public ConfigValueType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "ImmutableConfigEntry [key=" + key + ", value=" + value
				+ ", type=" + type + "]";
	}
	
	
}
