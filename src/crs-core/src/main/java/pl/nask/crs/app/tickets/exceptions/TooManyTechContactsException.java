package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk, Artur Gnaidzik
 */
public class TooManyTechContactsException extends ValidationException {
	private static final long serialVersionUID = -8637732397706077415L;

	private String msg;
	private int max;
	private int size;

	public TooManyTechContactsException(int max, int size) {
		this.max = max;
		this.size = size;
		this.msg = String.format("Too many tech contacts. Got %s, max is %s", size, max);
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
	
	public int getMax() {
		return max;
	}
	
	public int getSize() {
		return size;
	}
}
