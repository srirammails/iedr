package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;


public final class EnterVoluntaryNRP extends AbstractEvent {
	private static final DsmEvent INSTANCE = new EnterVoluntaryNRP();

	private EnterVoluntaryNRP() {
		super (DsmEventName.EnterVoluntaryNRP);
	}

	public static DsmEvent getInstance() {	
		return INSTANCE;
	}
}
