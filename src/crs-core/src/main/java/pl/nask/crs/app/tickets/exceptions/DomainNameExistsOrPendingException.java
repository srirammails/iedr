package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainNameExistsOrPendingException extends ValidationException {
	private static final long serialVersionUID = 1276620297251528324L;

	private final String domainName;

	public DomainNameExistsOrPendingException(String domainName) {
		this.domainName = domainName;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	@Override
	public String getMessage() {	
		return "Domain name cannot be used: " + domainName;
	}
}
