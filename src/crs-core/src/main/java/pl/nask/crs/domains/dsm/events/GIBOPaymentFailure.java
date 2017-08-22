package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

public final class GIBOPaymentFailure extends AbstractEvent {	
	private final static GIBOPaymentFailure instance = new GIBOPaymentFailure();
	
	public static GIBOPaymentFailure getInstance() {
		return instance;
	}
	
	private GIBOPaymentFailure() {
		super(DsmEventName.GIBOPaymentFailure);
	}
}
