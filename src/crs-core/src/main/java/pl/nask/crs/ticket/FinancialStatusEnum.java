package pl.nask.crs.ticket;

public enum FinancialStatusEnum implements FinancialStatus {
	NEW(0, "New"),
	PASSED(1, "Passed"),
	STALLED(2, "Stalled");

	private final String desc;
	private final int id;

	private FinancialStatusEnum(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	public static FinancialStatusEnum valueForId(int id) {
		switch (id) {
		case 0: return NEW;
		case 1: return PASSED;
		case 2: return STALLED;
		default:
			throw new IllegalArgumentException("Unexpected status id: " + id);
		}
	}
}
