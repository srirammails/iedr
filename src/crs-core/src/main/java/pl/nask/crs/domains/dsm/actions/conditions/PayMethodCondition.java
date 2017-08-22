package pl.nask.crs.domains.dsm.actions.conditions;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.payment.PaymentMethod;

public enum PayMethodCondition implements ActionCondition {
	ADP(PaymentMethod.ADP), CC(PaymentMethod.CC), DC(PaymentMethod.DEB);
	
	private PaymentMethod paymentMethod;

	private PayMethodCondition(PaymentMethod pm) {
		this.paymentMethod = pm;
	}

	@Override
	public boolean accept(Domain domain, DsmEvent event) {
		if (event.hasParameter(DsmEvent.PAY_METHOD)) {
			PaymentMethod m = (PaymentMethod) event.getParameter(DsmEvent.PAY_METHOD);		
			return paymentMethod == m;
		} else {
			return false;
		}
	}
}