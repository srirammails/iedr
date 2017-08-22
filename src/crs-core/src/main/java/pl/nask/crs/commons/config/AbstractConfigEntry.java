package pl.nask.crs.commons.config;


public abstract class AbstractConfigEntry implements ConfigEntry {

	/* (non-Javadoc)
	 * @see pl.nask.crs.commons.config.ConfigEntry#getTypedValue()
	 */
	@Override
	public Object getTypedValue() {
		String value = getValue();
		ConfigValueType type = getType();
		if (value == null)
			return null;
		switch (type) {
		case LONG:
			return Long.parseLong(value);
		case INT:
			return Integer.parseInt(value);
		case STRING:
			return value;
		case BOOLEAN:
			return asBoolean(value);
		default:
			throw new IllegalStateException("Unknown value type: " + type);
		}
	}

	private boolean asBoolean(String v) {
		return ("t".equalsIgnoreCase(v) || "1".equals(v) || "y".equalsIgnoreCase(v));
	}

}
