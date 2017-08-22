package pl.nask.crs.app.commons.register;

import pl.nask.crs.app.ValidationException;

public class CharityRegistrationNotPossibleException extends
		ValidationException {

	private static final long serialVersionUID = -8831607942267220851L;
	private final String domainName;

	public CharityRegistrationNotPossibleException(String domainName) {
		this.domainName = domainName;
	}
	
	@Override
	public String getMessage() {
		return "Charity reservation not possible for domain: " + domainName;
	}

	public String getDomainName() {
		return domainName;
	}
}
