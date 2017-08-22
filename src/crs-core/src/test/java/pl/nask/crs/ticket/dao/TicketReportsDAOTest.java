package pl.nask.crs.ticket.dao;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.ticket.AbstractContextAwareTest;

/**
 * @author: Marcin Tkaczyk
 */
public class TicketReportsDAOTest extends AbstractContextAwareTest {

    @Autowired
    TicketReportsDAO ticketReportsDAO;

    @Test
    public void findAllUserReports() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setHostmasterName("CIARA-IEDR");
        LimitedSearchResult<TicketReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result =  ticketReportsDAO.findTicketReports(criteria, 0, 10, null);
        assertEquals("reports for day", 8, result.getResults().size());
        assertEquals("total reports for day", 8, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        result =  ticketReportsDAO.findTicketReports(criteria, 0, 10, null);
        assertEquals("reports for month", 6, result.getResults().size());
        assertEquals("total reports for month", 6, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        result =  ticketReportsDAO.findTicketReports(criteria, 0, 10, null);
        assertEquals("reports for year", 4, result.getResults().size());
        assertEquals("total reports for year", 4, result.getTotalResults());
    }

    @Test
    public void findUserReportsWithDateLimit() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setHostmasterName("CIARA-IEDR");
        criteria.setFrom(new Date(1170406987179L));
        criteria.setTo(new Date());
        LimitedSearchResult<TicketReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result =  ticketReportsDAO.findTicketReports(criteria, 0, 10, null);
        assertEquals("reports for day", 4, result.getResults().size());
        assertEquals("total reports for day", 4, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        result =  ticketReportsDAO.findTicketReports(criteria, 0, 10, null);
        assertEquals("reports for month", 3, result.getResults().size());
        assertEquals("total reports for month", 3, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        result =  ticketReportsDAO.findTicketReports(criteria, 0, 10, null);
        assertEquals("reports for year", 2, result.getResults().size());
        assertEquals("total reports for year", 2, result.getTotalResults());
    }

    @Test
    public void findUserReportsWithOffset() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setHostmasterName("CIARA-IEDR");
        LimitedSearchResult<TicketReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result =  ticketReportsDAO.findTicketReports(criteria, 5, 10, null);
        assertEquals("reports for day with offset, limit", 3, result.getResults().size());
        assertEquals("total report for day", 8, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result =  ticketReportsDAO.findTicketReports(criteria, 0, 5, null);
        assertEquals("reports for day with limit", 5, result.getResults().size());
        assertEquals("total report for day", 8, result.getTotalResults());        
    }

}
