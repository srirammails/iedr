package pl.nask.crs.domains.dsm;

import pl.nask.crs.domains.dsm.events.AbstractEvent;

public class SetIEDRPublishedEvent extends AbstractEvent {
	public SetIEDRPublishedEvent() {
		super(DsmEventName.SetIEDRPublished);
	}
}
