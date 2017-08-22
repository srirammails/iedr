package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

public final class GIBOAdminFailure extends AbstractEvent {
	private final static GIBOAdminFailure INSTANCE = new GIBOAdminFailure();
	
	public static GIBOAdminFailure getInstance() {
		return INSTANCE;
	}
	
	private GIBOAdminFailure() {
		super(DsmEventName.GIBOAdminFailure);
	}
}
