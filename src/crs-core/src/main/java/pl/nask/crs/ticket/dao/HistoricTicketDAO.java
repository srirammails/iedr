package pl.nask.crs.ticket.dao;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;

/**
 * @author Kasia Fulara
 */
public interface HistoricTicketDAO {

    void create(long id, Date changeDate, String changedBy);

    List<HistoricalObject<Ticket>> get(long id);

    SearchResult<HistoricalObject<Ticket>> find(SearchCriteria<HistoricalObject<Ticket>> criteria);

    LimitedSearchResult<HistoricalObject<Ticket>> find(SearchCriteria<HistoricalObject<Ticket>> criteria, long offset, long limit, List<SortCriterion> sortBy);

}
