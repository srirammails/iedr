package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainDeletionPendingException extends ValidationException {

	private static final long serialVersionUID = -7204348378650558958L;
	private final String domainName;

	public DomainDeletionPendingException(String domainName) {
		this.domainName = domainName;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	@Override
	public String getMessage() {
		return String.format("Domain %s waiting for deletion", domainName);
	}
}
