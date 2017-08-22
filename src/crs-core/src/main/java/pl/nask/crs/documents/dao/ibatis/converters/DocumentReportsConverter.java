package pl.nask.crs.documents.dao.ibatis.converters;

import pl.nask.crs.documents.dao.ibatis.objects.InternalDocumentReport;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.commons.dao.AbstractConverter;

/**
 * @author: Marcin Tkaczyk
 */
public class DocumentReportsConverter extends AbstractConverter<InternalDocumentReport, TicketReport> {

    protected TicketReport _to(InternalDocumentReport internalDocumentReport) {
        return new TicketReport(internalDocumentReport.getHostmasterName(),
                internalDocumentReport.getReportForDate(),
                internalDocumentReport.getDocumentsCount());
    }

    protected InternalDocumentReport _from(TicketReport report) {
        throw new UnsupportedOperationException();
    }
}
