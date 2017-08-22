package pl.nask.crs.app.triplepass.exceptions;

import pl.nask.crs.ticket.Ticket;

public class TicketIllegalStateException extends Exception {

	private final Ticket t;

	public TicketIllegalStateException(String msg, Ticket t) {
		super(msg);
		this.t = t;
	}

	public Ticket getTicket() {
		return t;
	}

}
