package pl.nask.crs.ticket.services.impl;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;

/**
 * @author Kasia Fulara
 */
public class TicketSearchServiceImpl implements TicketSearchService {
    private TicketDAO ticketDao;

    public TicketSearchServiceImpl(TicketDAO ticketDao) {
        Validator.assertNotNull(ticketDao, "ticket dao");
        this.ticketDao = ticketDao;
    }

    public Ticket getTicket(long id) throws TicketNotFoundException {
        Ticket ret = ticketDao.get(id);
        if (ret == null)
            throw new TicketNotFoundException(id);
        return ret;
    }

    @Override
    public Ticket lockTicket(long id) throws TicketNotFoundException {
        if (ticketDao.lock(id)) {
            return ticketDao.get(id);
        } else {
            throw new TicketNotFoundException(id);
        }
    }

    /**
     * @param sortBy
     *            list of sort criteria. Only valid criteria will be used.
     */
    public LimitedSearchResult<Ticket> find(
            TicketSearchCriteria searchCriteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        
        return ticketDao.find(searchCriteria, offset, limit, TicketSortCriteria.filterValid(sortBy));
    }

    public List<String> findDomainNames(SearchCriteria<Ticket> cr, int offset, int limit) {
        return ticketDao.findDomainNames(cr, offset, limit);
    }

    @Override
    public List<Ticket> findAll(TicketSearchCriteria criteria, List<SortCriterion> sortBy) {
        return ticketDao.find(criteria, sortBy).getResults();
    }
}
