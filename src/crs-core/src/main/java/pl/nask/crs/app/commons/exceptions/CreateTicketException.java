package pl.nask.crs.app.commons.exceptions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class CreateTicketException extends Exception {

	private static final long serialVersionUID = -7815284897641795595L;

	public CreateTicketException(String s, Exception throwable) {
        super(s, throwable);
    }

    public CreateTicketException(Exception throwable) {
        super("Exeption when creating ticket: " + throwable.getClass() + " ("  + throwable.getMessage() + ")", throwable);
    }
    
    public Exception getExceptionCause() { 
    	return (Exception) getCause();
    }
}
