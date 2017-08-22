package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DeletedDomainRemoval extends AbstractEvent {
    public DeletedDomainRemoval() {
        super(DsmEventName.DeletedDomainRemoval);
    }
}
