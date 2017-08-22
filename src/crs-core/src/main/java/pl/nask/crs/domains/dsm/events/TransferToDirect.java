package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.ticket.Ticket;

public class TransferToDirect extends AbstractTransferEvent {
	public TransferToDirect(Domain oldDomain, Ticket t) {
		super(DsmEventName.TransferToDirect, oldDomain, t);
	}
	
	TransferToDirect(Contact oldBillC, Contact newBillC) {
		super(DsmEventName.TransferToDirect, oldBillC, newBillC);
	}
		
}
