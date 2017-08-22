package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.ticket.Ticket;

public abstract class AbstractTransferEvent extends AbstractEvent {

	public AbstractTransferEvent(DsmEventName eventName, Contact oldBillingC, Contact newBillingC) {
		super(eventName);
		Validator.assertNotNull(oldBillingC, "oldBillingC");
		Validator.assertNotNull(newBillingC, "newBillingC");
		Validator.assertNotEmpty(oldBillingC.getNicHandle(), "oldBillingC.nicHandle");
		Validator.assertNotNull(newBillingC.getNicHandle(), "newBillingC.nicHandle");
		setParameter(NEW_BILL_C, newBillingC.getNicHandle());
		setParameter(OLD_BILL_C, oldBillingC.getNicHandle());
	}

	public AbstractTransferEvent(DsmEventName eventName, Domain oldDomain, Ticket t) {
		this(eventName, t.getOperation().getBillingContactsField().get(0).getCurrentValue(), t.getOperation().getBillingContactsField().get(0).getNewValue());
		setParameter(TICKET, t);
		setParameter(OLD_DOMAIN, oldDomain);
	}
}
