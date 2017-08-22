package pl.nask.crs.ticket.dao;

import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public interface TicketReportsDAO extends GenericDAO<TicketReport, String> {

    LimitedSearchResult<TicketReport> findTicketReports(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);
}
