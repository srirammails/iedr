package pl.nask.crs.domains.dsm.events;

import java.util.Date;

import pl.nask.crs.domains.dsm.DsmEventName;

public class CreateCharityDomainDirect extends AbstractCreateDomainEvent {
	public CreateCharityDomainDirect(Date renewalDate) {
		super(DsmEventName.CreateCharityDomainDirect, renewalDate);
	}
}
