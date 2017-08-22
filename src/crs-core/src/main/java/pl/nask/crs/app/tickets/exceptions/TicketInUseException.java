package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class TicketInUseException extends ValidationException {

	private static final long serialVersionUID = -2036841434738841477L;
	private final String domainName;

	public TicketInUseException(String domainName) {
		this.domainName = domainName;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	@Override
	public String getMessage() {
		return "Ticket in use for domain: " + domainName;
	}
}
