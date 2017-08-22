package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

public class SuspensionDatePasses extends AbstractEvent {

	public SuspensionDatePasses() {
		super(DsmEventName.SuspensionDatePasses);
	}
}
