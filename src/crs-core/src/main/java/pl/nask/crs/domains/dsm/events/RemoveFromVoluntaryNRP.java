package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

public class RemoveFromVoluntaryNRP extends AbstractEvent {

	private static final RemoveFromVoluntaryNRP INSTANCE = new RemoveFromVoluntaryNRP();

	public RemoveFromVoluntaryNRP() {
		super (DsmEventName.RemoveFromVoluntaryNRP);
	}
	
	public static RemoveFromVoluntaryNRP getInstance() { 
		return INSTANCE;
	}

}
