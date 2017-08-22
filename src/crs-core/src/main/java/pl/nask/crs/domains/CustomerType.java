package pl.nask.crs.domains;

public enum CustomerType implements DSMStatus {
	Registrar("R", "Registrar"), Direct("D", "Direct"), NA("N/A", "N/A");
	
	private final String code;
	private final String description;
	
	private CustomerType(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public static CustomerType forCode(String code) {
		if ("R".equals(code)) {
			return Registrar;
		} else if ("D".equals(code)) {
			return Direct;
		} else if (code == null || code.trim().length() == 0) {
			return null;
		} else {
			throw new IllegalArgumentException("Unsupported code for CustomerType: " + code);
		}
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
}
