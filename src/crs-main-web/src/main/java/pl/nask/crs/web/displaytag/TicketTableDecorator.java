package pl.nask.crs.web.displaytag;

import org.displaytag.decorator.TableDecorator;

import pl.nask.crs.ticket.Ticket;

public class TicketTableDecorator extends TableDecorator {
    public String getCheckedOut() {
        Ticket t = (Ticket) getCurrentRowObject();
        return t.isCheckedOut() ? "Y" : "N";
    }
    
    public String getHasDocuments() {
        Ticket t = (Ticket) getCurrentRowObject();
        return t.isHasDocuments() ? "Y" : "N";
    }
}
