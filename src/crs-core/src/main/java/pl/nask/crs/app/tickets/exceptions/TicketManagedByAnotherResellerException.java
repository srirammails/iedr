package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class TicketManagedByAnotherResellerException extends ValidationException {

	private static final long serialVersionUID = -3801254926184781418L;
	private final String domainName;

	public TicketManagedByAnotherResellerException(String domainName) {
		this.domainName = domainName;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	@Override
	public String getMessage() {

		return String.format("Ticket for a domain %s managed by another reseller", domainName);
	}
}
