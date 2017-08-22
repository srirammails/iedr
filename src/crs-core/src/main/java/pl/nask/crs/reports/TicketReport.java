package pl.nask.crs.reports;

import java.util.Date;

/**
 * @author: Marcin Tkaczyk
 */
public class TicketReport {

    private String hostmasterName;
    private Date reportForDate;

    private Integer ticketRevisionsCount;
    private String ticketType;

    private Integer documentsCount;

    public TicketReport(String hostmasterName, Date reportDate, Integer ticketRevisionsCount, String ticketType) {
        this.hostmasterName = hostmasterName;
        this.reportForDate = reportDate;
        this.ticketRevisionsCount = ticketRevisionsCount;
        this.ticketType = ticketType;
    }

    public TicketReport(String hostmasterName, Date reportForDate, Integer documentsCount) {
        this.hostmasterName = hostmasterName;
        this.reportForDate = reportForDate;
        this.documentsCount = documentsCount;
    }

    public String getHostmasterName() {
        return hostmasterName;
    }

    public Integer getTicketRevisionsCount() {
        return ticketRevisionsCount;
    }

    public String getTicketType() {
        return ticketType;
    }

    public Date getReportForDate() {
        return reportForDate;
    }

    public Integer getDocumentsCount() {
        return documentsCount;
    }

}
