package pl.nask.crs.domains;

public enum DomainHolderType implements DSMStatus {
	Billable("B", "Billable"),
	Charity("C", "Charity"),
	IEDRUnpublished("IU", "IEDR-Unpublished"),
	IEDRPublished("IP", "IEDR-Published"),
	NonBillable("N", "Non-Billable"), NA("N/A", "N/A");
	
	private final String code;
	private final String description;

	private DomainHolderType(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	public static DomainHolderType forCode(String code) {
		if ("B".equals(code)) {
			return Billable;
		} else if ("C".equals(code)) {
			return Charity;
		} else if ("IU".equals(code)) {
			return IEDRUnpublished;
		} else if ("IP".equals(code)) {
			return IEDRPublished;
		} else if ("N".equals(code)) {
			return NonBillable;
		} else if (code == null || code.trim().length() == 0) {
			return null;
		} else {
			throw new IllegalArgumentException("Unsupported code for HolderType: " + code);
		}
	}
	
}
