package pl.nask.crs.ticket;

public enum AdminStatusEnum implements AdminStatus {
	UNKNOWN_VALUE(-1, "Unknown value"),
	NEW(0, "New"),
	PASSED(1, "Passed"),
	HOLD_UPDATE(2, "Hold Update"),
	HOLD_PAPERWORK(3, "Hold Paperwork"),
	STALLED(4, "Stalled"),
	RENEW(5, "Renew"),
	FINANCE_HOLDUP(6, "Finance Holdup"),
	CANCELLED(9, "Cancelled"),
	HOLD_REGISTRAR_APPROVAL(10, "Hold Registrar Approval"),
	DOCUMENTS_SUBMITTED(11, "Documents Submitted");
	
	private int code;
	private String description;

	private AdminStatusEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	public int getCode() {
		return code;
	}
	

	public static AdminStatusEnum valueOf(AdminStatus adminStatus) {
		switch(adminStatus.getId()) {
		case 0: return NEW;
		case 1: return PASSED;
		case 2: return HOLD_UPDATE;
		case 3: return HOLD_PAPERWORK;
		case 4: return STALLED;
		case 5: return RENEW;
		case 6: return FINANCE_HOLDUP;
		case 9: return CANCELLED;
		case 10: return HOLD_REGISTRAR_APPROVAL;
		case 11: return DOCUMENTS_SUBMITTED;
		default: return UNKNOWN_VALUE;
		}
	}

	@Override
	public int getId() {		
		return code;
	}
}
