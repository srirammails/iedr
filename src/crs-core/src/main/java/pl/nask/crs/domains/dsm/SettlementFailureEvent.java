package pl.nask.crs.domains.dsm;

import pl.nask.crs.domains.dsm.events.AbstractEvent;
import pl.nask.crs.payment.PaymentMethod;

public class SettlementFailureEvent extends AbstractEvent {
	public SettlementFailureEvent(PaymentMethod paymentMethod) {
		super(DsmEventName.SettlementFailure);
		setParameter(DsmEvent.PAY_METHOD, paymentMethod);
	}
}
