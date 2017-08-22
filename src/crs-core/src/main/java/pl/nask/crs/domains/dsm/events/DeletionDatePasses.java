package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

public class DeletionDatePasses extends AbstractEvent {
	public DeletionDatePasses() {
		super(DsmEventName.DeletionDatePasses);
	}
}
