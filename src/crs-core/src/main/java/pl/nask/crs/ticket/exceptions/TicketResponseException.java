package pl.nask.crs.ticket.exceptions;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketResponseException extends Exception {

    public TicketResponseException() {
    }

    public TicketResponseException(String message) {
        super(message);
    }

    public TicketResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketResponseException(Throwable cause) {
        super(cause);
    }

}
