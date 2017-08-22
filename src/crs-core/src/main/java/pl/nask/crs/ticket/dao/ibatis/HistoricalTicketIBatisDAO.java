package pl.nask.crs.ticket.dao.ibatis;

import java.util.Date;
import java.util.List;
import java.util.Arrays;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.HistoricTicketDAO;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalHistoricalTicket;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;

/**
 * todo: we cannot determine correctly the modification fields of the historical tickets - no information when the contact and nameservers was added
 *
 * @author Kasia Fulara
 * @author Patrycja Wegrzynowicz
 */
public class HistoricalTicketIBatisDAO extends ConvertingGenericDAO<InternalHistoricalTicket, HistoricalObject<Ticket>, Long> implements HistoricTicketDAO {
    //fix for bug #1417
    private final static List<SortCriterion> DESCENDING_ORDER = Arrays.asList(new SortCriterion[] {new SortCriterion("Chng_ID", false)});

    public HistoricalTicketIBatisDAO(GenericDAO<InternalHistoricalTicket, Long> internalDao, Converter<InternalHistoricalTicket, HistoricalObject<Ticket>> internalConverter) {
        super(internalDao, internalConverter);
    }

    public void create(long id, Date changeDate, String changedBy) {
        InternalHistoricalTicket ticket = new InternalHistoricalTicket();
        ticket.setId(id);
        ticket.setHistChangeDate(changeDate);
        ticket.setChangedByNicHandle(changedBy);
        getInternalDao().create(ticket);
    }

    public List<HistoricalObject<Ticket>> get(long id) {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setTicketId(id);
        return find(criteria, DESCENDING_ORDER).getResults();
    }

}
