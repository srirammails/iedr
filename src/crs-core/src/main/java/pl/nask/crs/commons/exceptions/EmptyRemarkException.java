package pl.nask.crs.commons.exceptions;

/**
 * @author Patrycja Wegrzynowicz
 */
public class EmptyRemarkException extends Exception {

	public EmptyRemarkException() {
	}
	
	public EmptyRemarkException(EmptyRemarkException e) {
		super(e);
	}
}
