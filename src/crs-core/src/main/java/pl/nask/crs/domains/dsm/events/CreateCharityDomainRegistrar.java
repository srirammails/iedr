package pl.nask.crs.domains.dsm.events;

import java.util.Date;

import pl.nask.crs.domains.dsm.DsmEventName;

public class CreateCharityDomainRegistrar extends AbstractCreateDomainEvent {

	public CreateCharityDomainRegistrar(Date renewalDate) {
		super(DsmEventName.CreateCharityDomainRegistrar, renewalDate);
	}
}
