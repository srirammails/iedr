package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainModificationPendingException extends ValidationException {

	private static final long serialVersionUID = 4843837238334621182L;
	private final String domainName;

	public DomainModificationPendingException(String domainName) {
		this.domainName = domainName;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	@Override
	public String getMessage() {
		return String.format("Modification ticket pending for domain: %s", domainName);
	}
}
