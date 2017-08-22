package pl.nask.crs.domains.dsm.events;

import java.util.Date;

import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;

public class CreateGIBODomain extends AbstractEvent {
	public CreateGIBODomain(Date renewalDate) {
		super(DsmEventName.CreateGIBODomain);
		setParameter(DsmEvent.DOMAIN_RENEWAL_DATE, renewalDate);
	}
}
