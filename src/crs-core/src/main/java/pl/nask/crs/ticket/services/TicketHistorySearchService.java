package pl.nask.crs.ticket.services;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;

public interface TicketHistorySearchService {

    List<HistoricalObject<Ticket>> getTicketHistory(long id);

    LimitedSearchResult<HistoricalObject<Ticket>> findTicketHistory(HistoricTicketSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy);

}
