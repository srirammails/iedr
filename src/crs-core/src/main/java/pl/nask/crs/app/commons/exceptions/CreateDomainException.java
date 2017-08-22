package pl.nask.crs.app.commons.exceptions;

public class CreateDomainException extends Exception {

	private static final long serialVersionUID = 356971180195901150L;

	public CreateDomainException(Exception cause) {
		super(cause);
	}
	
	public Exception getExceptionCause() {
		return (Exception) getCause();
	}

}
