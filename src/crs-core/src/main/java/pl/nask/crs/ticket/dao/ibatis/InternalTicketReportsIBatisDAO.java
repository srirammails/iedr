package pl.nask.crs.ticket.dao.ibatis;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicketReport;

/**
 * @author: Marcin Tkaczyk
 */
public class InternalTicketReportsIBatisDAO extends GenericIBatisDAO<InternalTicketReport, String> {

    public LimitedSearchResult<InternalTicketReport> findTicketReports(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        String findQuery = null;
        String countFindQuery = null;

        switch (criteria.getReportTimeGranulation()) {
            case DAY:
                findQuery = "ticket-reports.getTicketReportsDayGranulation";
                countFindQuery = "ticket-reports.getTicketReportsCountDayGranulation";
                break;
            case MONTH:
                findQuery = "ticket-reports.getTicketReportsMonthGranulation";
                countFindQuery = "ticket-reports.getTicketReportsCountMonthGranulation";
                break;
            case YEAR:
                findQuery = "ticket-reports.getTicketReportsYearGranulation";
                countFindQuery = "ticket-reports.getTicketReportsCountYearGranulation";
                break;
            default:
                throw new IllegalArgumentException(criteria.getReportTimeGranulation().name());
        }

        FindParameters params = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy);

        Integer total = performQueryForObject(countFindQuery, params);
        List<InternalTicketReport> list;
        if (total == 0) {
        	list = new ArrayList<InternalTicketReport>();
        } else {
        	list = performQueryForList(findQuery, params);
        }

        return new LimitedSearchResult<InternalTicketReport>((SearchCriteria)criteria, sortBy, limit, offset,
                list, total);
    }
}

