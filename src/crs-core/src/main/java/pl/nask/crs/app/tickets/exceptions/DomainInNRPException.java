package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainInNRPException extends ValidationException {

	private static final long serialVersionUID = 7163664405643289936L;
	private final String domainName;

	public DomainInNRPException(String domainName) {
		this.domainName = domainName;	
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	@Override
	public String getMessage() {	
		return String.format("Domain %s in NRP", domainName);
	}
}
