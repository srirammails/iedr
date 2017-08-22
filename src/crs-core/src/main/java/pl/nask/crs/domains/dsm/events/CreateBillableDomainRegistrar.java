package pl.nask.crs.domains.dsm.events;

import java.util.Date;

import pl.nask.crs.domains.dsm.DsmEventName;

public class CreateBillableDomainRegistrar extends AbstractCreateDomainEvent{
	public CreateBillableDomainRegistrar(Date renewalDate) {
		super(DsmEventName.CreateBillableDomainRegistrar, renewalDate);
	}
}
