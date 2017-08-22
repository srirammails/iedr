package pl.nask.crs.ticket.exceptions;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketNotFoundException extends TicketException {

    private long ticketId;

    public TicketNotFoundException(long ticketId) {
        this.ticketId = ticketId;
    }
    
    @Override
    public String getMessage() {
        return "Ticket with id=" + ticketId + " not found";
    }
}
