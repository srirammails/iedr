package pl.nask.crs.domains;

public enum RenewalMode implements DSMStatus {
	NoAutorenew("N", "No auto renew"),
	RenewOnce("R", "Renew Once"),
	Autorenew("A", "Autorenew"), NA("N/A", "N/A");
	
	private final String description;
	private final String code;

	private RenewalMode(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public static RenewalMode forCode(String code) {
		if ("N".equals(code)) {
			return NoAutorenew;
		} else if ("R".equals(code)) {
			return RenewOnce;
		} else if ("A".equals(code)) {
			return Autorenew;
		} else if (code == null || code.trim().length() == 0) {
			return null;
		} else {
			throw new IllegalArgumentException("Unsupported code for RenewalMode: " + code);
		}
	}

    public static RenewalMode forName(String n) {
        for (RenewalMode c : RenewalMode.values()) {
            if (c.name().equalsIgnoreCase(n))
                return c;
        }
        throw new IllegalArgumentException(n);
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
