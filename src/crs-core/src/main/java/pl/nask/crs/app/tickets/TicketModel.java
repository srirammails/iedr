package pl.nask.crs.app.tickets;

import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;

import java.util.List;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketModel {

    private Ticket ticket;

    private TicketInfo additionalInfo;

    private List<HistoricalObject<Ticket>> history;

    public TicketModel(Ticket ticket, TicketInfo additionalInfo, List<HistoricalObject<Ticket>> history) {
        this.ticket = ticket;
        this.additionalInfo = additionalInfo;
        this.history = history;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public TicketInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(TicketInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<HistoricalObject<Ticket>> getHistory() {
        return history;
    }

    public void setHistory(List<HistoricalObject<Ticket>> history) {
        this.history = history;
    }

}


