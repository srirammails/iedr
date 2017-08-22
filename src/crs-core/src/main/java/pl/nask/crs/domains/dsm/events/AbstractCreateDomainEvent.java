package pl.nask.crs.domains.dsm.events;

import java.util.Date;

import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;

public abstract class AbstractCreateDomainEvent extends AbstractEvent {

	public AbstractCreateDomainEvent(DsmEventName eventName, Date renewalDate) {
		super(eventName);
		setParameter(DsmEvent.DOMAIN_RENEWAL_DATE, renewalDate);
	}

}
