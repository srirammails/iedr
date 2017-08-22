package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SetAutoRenew extends AbstractEvent {
    public SetAutoRenew() {
        super(DsmEventName.SetAutoRenew);
    }
}
