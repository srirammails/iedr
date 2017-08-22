package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainManagedByAnotherResellerException extends ValidationException {

	private static final long serialVersionUID = 6518934161901064217L;
	private final String domainName;

	public DomainManagedByAnotherResellerException(String domainName) {
		this.domainName = domainName;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	@Override
	public String getMessage() {
		return String.format("Domain %s managed by another reseller", domainName);
	}
}
