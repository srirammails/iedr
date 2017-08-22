package pl.nask.crs.commons.config;


public interface ConfigEntry {
	
	public enum ConfigValueType {
		LONG, INT, STRING, BOOLEAN
	}

	public String getKey();

	public String getValue();

	public ConfigValueType getType();

	public Object getTypedValue();

}