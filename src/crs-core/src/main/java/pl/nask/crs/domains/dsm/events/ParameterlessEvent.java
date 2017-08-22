package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ParameterlessEvent extends AbstractEvent {

    public ParameterlessEvent(DsmEventName eventName) {
        super(eventName);
    }

}
