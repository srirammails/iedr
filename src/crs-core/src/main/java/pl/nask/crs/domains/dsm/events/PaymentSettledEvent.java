package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PaymentSettledEvent extends AbstractEvent {
	/**
	 * @param period renewal period in years, will be used for rolling the renewalDate forward
	 */
    public PaymentSettledEvent(int period) {
    	super(DsmEventName.PaymentSettled);
    	setParameter(RENEWAL_PERIOD, period);
    }
}
