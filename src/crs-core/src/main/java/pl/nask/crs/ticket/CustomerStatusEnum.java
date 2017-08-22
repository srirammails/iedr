package pl.nask.crs.ticket;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public enum CustomerStatusEnum implements CustomerStatus {
    NEW(0, "New"),
    CANCELLED(9, "Cancelled");

    private int code;
   	private String description;

    private CustomerStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getId() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static CustomerStatus valueForId(int id) {
   		switch (id) {
   		case 0: return NEW;
   		case 9: return CANCELLED;
   		default:
   			throw new IllegalArgumentException("Unexpected status id: " + id);
   		}
   	}
}
