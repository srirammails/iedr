package pl.nask.crs.ticket.services.impl;

import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.ticket.services.TicketReportsSearchService;
import pl.nask.crs.ticket.dao.TicketReportsDAO;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public class TicketReportsSearchServiceImpl implements TicketReportsSearchService {

    private TicketReportsDAO ticketReportsDAO;

    public TicketReportsSearchServiceImpl(TicketReportsDAO ticketReportsDAO) {
        Validator.assertNotNull(ticketReportsDAO, "ticket reports dao");
        this.ticketReportsDAO = ticketReportsDAO;
    }

    public LimitedSearchResult<TicketReport> find(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
        return ticketReportsDAO.findTicketReports(criteria, offset, limit, orderBy);
    }
}
