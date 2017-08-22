package pl.nask.crs.ticket.dao.ibatis.objects;

import java.util.Date;

/**
 * @author: Marcin Tkaczyk
 */
public class InternalTicketReport {
    private String hostmasterName;
    private Integer ticketRevisionsCount;
    private String ticketType;
    private Date reportForDate;

    public String getHostmasterName() {
        return hostmasterName;
    }

    public void setHostmasterName(String hostmasterName) {
        this.hostmasterName = hostmasterName;
    }

    public Integer getTicketRevisionsCount() {
        return ticketRevisionsCount;
    }

    public void setTicketRevisionsCount(Integer ticketRevisionsCount) {
        this.ticketRevisionsCount = ticketRevisionsCount;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Date getReportForDate() {
        return reportForDate;
    }

    public void setReportForDate(Date reportDate) {
        this.reportForDate = reportDate;
    }
}
