package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 * 
 * This event carries no parameters so it can be a singleton.
 */
public final class GIBOAuthorisation extends AbstractEvent {
	private final static GIBOAuthorisation instance = new GIBOAuthorisation();
	
	public static GIBOAuthorisation getInstance() {
		return instance;
	}
	
    private GIBOAuthorisation() {
        super(DsmEventName.GIBOAuthorisation);
    }
}
