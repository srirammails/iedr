package pl.nask.crs.documents.dao;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;
import java.util.Date;

import javax.annotation.Resource;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.documents.AbstractContextAwareTest;
import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
/**
 * @author: Marcin Tkaczyk
 */

public class DocumentReportsDAOTest extends AbstractContextAwareTest {

    @Resource
    DocumentReportsDAO documentReportsDAO;

    @Test
    public void findAllReports() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        LimitedSearchResult<TicketReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for day", 7, result.getResults().size());
        assertEquals("total reports for day", 7, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for month", 5, result.getResults().size());
        assertEquals("total reports for month", 5, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for year", 3, result.getResults().size());
        assertEquals("total reports for year", 3, result.getTotalResults());

    }

    @Test
    public void findAllUserReports() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setHostmasterName("CIARA-IEDR");
        LimitedSearchResult<TicketReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result =  documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for day", 5, result.getResults().size());
        assertEquals("total reports for day", 5, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        result =  documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for month", 3, result.getResults().size());
        assertEquals("total reports for month", 3, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        result =  documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for year", 2, result.getResults().size());
        assertEquals("total reports for year", 2, result.getTotalResults());
    }

    @Test
    public void findUserReportsWithDateLimit() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setHostmasterName("CIARA-IEDR");
        criteria.setFrom(new Date(1224672872796L));
        criteria.setTo(new Date());
        LimitedSearchResult<TicketReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result =  documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for day", 3, result.getResults().size());
        assertEquals("total reports for day", 3, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        result =  documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for month", 3, result.getResults().size());
        assertEquals("total reports for month", 3, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        result =  documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for year", 2, result.getResults().size());
        assertEquals("total reports for year", 2, result.getTotalResults());
    }

    @Test
    public void findUserReportsWithOffset() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setHostmasterName("CIARA-IEDR");
        LimitedSearchResult<TicketReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result =  documentReportsDAO.findDocumentReports(criteria, 2, 5, null);
        assertEquals("reports for day with offset, limit", 3, result.getResults().size());
        assertEquals("total report for day", 5, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result =  documentReportsDAO.findDocumentReports(criteria, 0, 3, null);
        assertEquals("reports for day with limit", 3, result.getResults().size());
        assertEquals("total report for day", 5, result.getTotalResults());
    }

}
