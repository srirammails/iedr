package pl.nask.crs.domains.dsm.events;

import java.util.Date;

import pl.nask.crs.domains.dsm.DsmEventName;

public class CreateBillableDomainDirect extends AbstractCreateDomainEvent {

	public CreateBillableDomainDirect(Date renewalDate) {
		super(DsmEventName.CreateBillableDomainDirect, renewalDate);
	}

}
