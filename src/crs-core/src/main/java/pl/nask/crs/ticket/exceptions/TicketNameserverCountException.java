package pl.nask.crs.ticket.exceptions;

/**
 * Tickets are allowed predefined min and max nameserver count. This exception is thrown
 * if ticket has wrong nameserver count on update.
 */
public class TicketNameserverCountException extends TicketNameserverException {
    public TicketNameserverCountException(String message) {
        super(message);
    }
}
