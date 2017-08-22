package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainAlreadyManagedByResellerException extends ValidationException {

	private static final long serialVersionUID = -4106977567448638388L;
	private final String domainName;

	public DomainAlreadyManagedByResellerException(String domainName) {
		this.domainName = domainName;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	@Override
	public String getMessage() {	
		return "Domain already managed by releller: " + domainName;
	}
}
