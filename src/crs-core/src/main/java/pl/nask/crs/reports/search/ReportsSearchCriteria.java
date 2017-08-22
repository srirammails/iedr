package pl.nask.crs.reports.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.ReportType;

import java.util.Date;

/**
 * @author: Marcin Tkaczyk
 */
public class ReportsSearchCriteria implements SearchCriteria<TicketReport> {

    private ReportType reportType;
    private ReportTimeGranulation reportTimeGranulation;

    private String hostmasterName;
    private Date from;
    private Date to;

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public ReportTimeGranulation getReportTimeGranulation() {
        return reportTimeGranulation;
    }

    public void setReportTimeGranulation(ReportTimeGranulation reportTimeGranulation) {
        this.reportTimeGranulation = reportTimeGranulation;
    }

    public String getHostmasterName() {
        return hostmasterName;
    }

    public void setHostmasterName(String hostmasterName) {
        this.hostmasterName = hostmasterName;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
