package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

public class EnterWIPOArbitration extends AbstractEvent {
	public EnterWIPOArbitration() {
		super (DsmEventName.EnterWIPOArbitration);
	}
}
