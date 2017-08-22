package pl.nask.crs.app.tickets.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class TooManyTicketsException extends RuntimeException {

    public TooManyTicketsException() {
    }

    public TooManyTicketsException(String message) {
        super(message);
    }
}
