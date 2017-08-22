package pl.nask.crs.ticket.exceptions;

/**
 * @author Kasia Fulara
 */
public class TicketEmailException extends Exception {
    public TicketEmailException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TicketEmailException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TicketEmailException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TicketEmailException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
