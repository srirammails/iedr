package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

public final class GIBOPaymentRetryTimeout extends AbstractEvent {
	private static final GIBOPaymentRetryTimeout INSTANCE = new GIBOPaymentRetryTimeout(); 
	
	public static GIBOPaymentRetryTimeout getInstance() {
		return INSTANCE;
	}
	private GIBOPaymentRetryTimeout() {
		super(DsmEventName.GIBOPaymentRetryTimeout);
	}
}
