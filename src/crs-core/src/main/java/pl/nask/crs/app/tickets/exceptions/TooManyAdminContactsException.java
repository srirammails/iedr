package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk, Artur Gniadzik
 */
public class TooManyAdminContactsException extends ValidationException {
	private static final long serialVersionUID = -9207567834234104105L;

	private String msg;
	private int max;
	private int size;

	public TooManyAdminContactsException(int max, int size) {
		this.max = max;
		this.size = size;
		this.msg = String.format("Too many admin contacts. Got %s, max is %s", size, max);
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
