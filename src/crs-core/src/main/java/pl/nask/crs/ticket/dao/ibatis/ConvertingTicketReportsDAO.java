package pl.nask.crs.ticket.dao.ibatis;

import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicketReport;
import pl.nask.crs.ticket.dao.TicketReportsDAO;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public class ConvertingTicketReportsDAO extends ConvertingGenericDAO<InternalTicketReport, TicketReport, String> implements TicketReportsDAO {

    protected InternalTicketReportsIBatisDAO internalDao;

    public ConvertingTicketReportsDAO(InternalTicketReportsIBatisDAO internalDao, Converter<InternalTicketReport, TicketReport> internalConverter) {
        super(internalDao, internalConverter);
        this.internalDao = internalDao;
    }

    public LimitedSearchResult<TicketReport> findTicketReports(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalTicketReport> res = internalDao.findTicketReports(criteria, offset, limit, sortBy);
        List<TicketReport> ret = getInternalConverter().to(res.getResults());
        return new LimitedSearchResult<TicketReport>(criteria, sortBy, res.getLimit(),
                res.getOffset(), ret, res.getTotalResults());
    }

}
