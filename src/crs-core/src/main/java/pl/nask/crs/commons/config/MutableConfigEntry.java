package pl.nask.crs.commons.config;

public class MutableConfigEntry extends AbstractConfigEntry implements ConfigEntry {
	private String key;
	private String value;
	private ConfigValueType type;
	
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public ConfigValueType getType() {
		return type;
	}

	@Override
	public Object getTypedValue() {
		return super.getTypedValue();
	}

	@Override
	public String toString() {
		return "MutableConfigEntry [key=" + key + ", value=" + value
				+ ", type=" + type + "]";
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setType(ConfigValueType type) {
		this.type = type;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public boolean validateValue() {
		try {
			getTypedValue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
