package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk, Artur Gniadzik
 */
public class TooFewAdminContactsException extends ValidationException {    
	private static final long serialVersionUID = 4360626486079935651L;
	
	private String msg;
	private int min;
	private int size;

	public TooFewAdminContactsException(int min, int size) {
		this.min = min;
		this.size = size;
		this.msg = String.format("Too few admin contacts. Got %s, min is %s", size, min);
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getSize() {
		return size;
	}
}
