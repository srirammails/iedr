package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SetNoAutoRenew extends AbstractEvent {
    public SetNoAutoRenew() {
        super(DsmEventName.SetNoAutoRenew);
    }
}
