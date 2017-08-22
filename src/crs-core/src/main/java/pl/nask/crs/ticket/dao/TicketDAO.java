package pl.nask.crs.ticket.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

/**
 * @author Kasia Fulara
 * @author Patrycja Wegrzynowicz
 */
public interface TicketDAO extends GenericDAO<Ticket, Long> {

    List<String> findDomainNames(SearchCriteria<Ticket> cr, int offset, int limit);

	long createTicket(Ticket ticket);

}
