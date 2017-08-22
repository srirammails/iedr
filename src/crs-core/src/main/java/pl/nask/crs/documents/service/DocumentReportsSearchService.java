package pl.nask.crs.documents.service;

import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public interface DocumentReportsSearchService {

    public LimitedSearchResult<TicketReport> find(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy);

}
