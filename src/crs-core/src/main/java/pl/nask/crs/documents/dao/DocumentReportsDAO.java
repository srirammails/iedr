package pl.nask.crs.documents.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;

import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public interface DocumentReportsDAO extends GenericDAO<TicketReport, String> {

    LimitedSearchResult<TicketReport> findDocumentReports(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

}
