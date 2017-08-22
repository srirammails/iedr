package pl.nask.crs.ticket.dao.ibatis.converters;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalHistoricalTicket;

/**
 * @author Patrycja Wegrzynowicz
 */
public class HistoricalTicketConverter extends AbstractConverter<InternalHistoricalTicket, HistoricalObject<Ticket>> {
    private static final Logger logger = Logger.getLogger(HistoricalTicketConverter.class);
    
    private TicketConverter ticketConverter;

    public HistoricalTicketConverter(TicketConverter ticketConverter) {
        Validator.assertNotNull(ticketConverter, "ticket converter");
        this.ticketConverter = ticketConverter;
    }

    protected HistoricalObject<Ticket> _to(InternalHistoricalTicket src) {
        try {
            return new HistoricalObject<Ticket>(
                src.getChangeId(),
                ticketConverter.to(src),
                src.getHistChangeDate(),
                src.getChangedByNicHandle());
        } catch (Exception e) {
            logger.error("Cannot convert Ticket History record with id=" + src.getId() + ". Error message was: " + e.getMessage());            
            return null;
        }
    }

    protected InternalHistoricalTicket _from(HistoricalObject<Ticket> ticketHistoricalObject) {
        throw new UnsupportedOperationException();
    }

}
