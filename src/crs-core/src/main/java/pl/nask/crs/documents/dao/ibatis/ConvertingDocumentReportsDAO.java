package pl.nask.crs.documents.dao.ibatis;

import pl.nask.crs.documents.dao.ibatis.objects.InternalDocumentReport;
import pl.nask.crs.documents.dao.DocumentReportsDAO;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public class ConvertingDocumentReportsDAO extends ConvertingGenericDAO<InternalDocumentReport, TicketReport, String> implements DocumentReportsDAO {

    protected InternalDocumentReportsIBatisDAO internalDAO;

    public ConvertingDocumentReportsDAO(InternalDocumentReportsIBatisDAO internalDao, Converter<InternalDocumentReport, TicketReport> internalConverter) {
        super(internalDao, internalConverter);
        this.internalDAO = internalDao;
    }

    public LimitedSearchResult<TicketReport> findDocumentReports(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalDocumentReport> res = internalDAO.findDocumentReports(criteria, offset, limit, sortBy);
        List<TicketReport> ret = getInternalConverter().to(res.getResults());
        return new LimitedSearchResult<TicketReport>(criteria, sortBy, res.getLimit(),
                res.getOffset(), ret, res.getTotalResults());
    }
}
