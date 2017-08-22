package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.ticket.Ticket;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransferRequest extends AbstractEvent {

    public TransferRequest(Ticket t) {
        super(DsmEventName.TransferRequest);
        setParameter(DsmEvent.TICKET, t);
    }
}
