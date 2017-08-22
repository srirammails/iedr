package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk, Artur Gniadzik
 */
public class TooFewTechContactsException extends ValidationException {	
	private static final long serialVersionUID = 3001560727777134572L;

	private String msg;
    
	private int min;
	private int size;

	public TooFewTechContactsException(int min, int size) {
		this.min = min;
		this.size = size;
		this.msg = String.format("Too few tech contacts. Got %s, min is %s", size, min);
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
