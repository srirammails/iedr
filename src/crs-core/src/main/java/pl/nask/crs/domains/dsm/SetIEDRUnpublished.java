package pl.nask.crs.domains.dsm;

import pl.nask.crs.domains.dsm.events.AbstractEvent;

public class SetIEDRUnpublished extends AbstractEvent {
	public SetIEDRUnpublished() {
		super(DsmEventName.SetIEDRUnpublished);
	}
}
