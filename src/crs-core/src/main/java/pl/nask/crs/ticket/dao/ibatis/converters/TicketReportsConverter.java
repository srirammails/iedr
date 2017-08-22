package pl.nask.crs.ticket.dao.ibatis.converters;

import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicketReport;
import pl.nask.crs.commons.dao.AbstractConverter;

/**
 * @author: Marcin Tkaczyk
 */
public class TicketReportsConverter extends AbstractConverter<InternalTicketReport, TicketReport> {

    protected TicketReport _to(InternalTicketReport internalTicketReport) {
        return new TicketReport(internalTicketReport.getHostmasterName(),
                internalTicketReport.getReportForDate(),
                internalTicketReport.getTicketRevisionsCount(),
                internalTicketReport.getTicketType());
    }

    protected InternalTicketReport _from(TicketReport report) {
        throw new UnsupportedOperationException();
    }

}
