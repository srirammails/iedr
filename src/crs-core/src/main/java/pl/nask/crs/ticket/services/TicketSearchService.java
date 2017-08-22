package pl.nask.crs.ticket.services;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

public interface TicketSearchService {

    public Ticket getTicket(long id) throws TicketNotFoundException;

    public Ticket lockTicket(long id) throws TicketNotFoundException;

    public LimitedSearchResult<Ticket> find(TicketSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy);

    public List<String> findDomainNames(SearchCriteria<Ticket> cr, int offset, int limit);

    public List<Ticket> findAll(TicketSearchCriteria criteria, List<SortCriterion> sortBy);

}
