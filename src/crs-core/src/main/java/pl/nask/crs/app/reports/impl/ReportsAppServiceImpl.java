package pl.nask.crs.app.reports.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.app.NoAuthenticatedUserException;
import pl.nask.crs.reports.DomainsPerClass;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.TotalDomains;
import pl.nask.crs.reports.TotalDomainsPerDate;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.reports.search.TotalDomainsCriteria;
import pl.nask.crs.reports.search.TotalDomainsPerDateCriteria;
import pl.nask.crs.reports.service.ReportsService;
import pl.nask.crs.ticket.services.TicketReportsSearchService;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.service.DocumentReportsSearchService;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: Marcin Tkaczyk
 */
public class ReportsAppServiceImpl implements ReportsAppService {

    private TicketReportsSearchService ticketReportsSearchService;
    private DocumentReportsSearchService documentReportsSearchService;
    private ReportsService reportsService;

    public ReportsAppServiceImpl(TicketReportsSearchService ticketReportsSearchService, DocumentReportsSearchService documentReportsSearchService, ReportsService reportsService) {
        Validator.assertNotNull(ticketReportsSearchService, "ticket reports service");
        Validator.assertNotNull(documentReportsSearchService, "document reports service");
        this.ticketReportsSearchService = ticketReportsSearchService;
        this.documentReportsSearchService = documentReportsSearchService;
        this.reportsService = reportsService;
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<TicketReport> search(AuthenticatedUser user, ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
        validateLoggedIn(user);
        switch (criteria.getReportType()) {
            case TICKET_REVISIONS:
                return ticketReportsSearchService.find(criteria, offset, limit, orderBy);
            case DOCUMENTS_LOGGED:
                return documentReportsSearchService.find(criteria, offset, limit, orderBy);
            case ALL:
                // we can not sort it, that is why orderBy is null
                return findAllReports(criteria, offset, limit, null);
            default:
                throw new IllegalArgumentException("Invalid report type: " + criteria.getReportType().name());
        }
    }

    /**
     *
     * @param criteria
     * @param offset
     * @param limit
     * @param orderBy
     * @return All reports, first ticket reports then document reports
     */
    private LimitedSearchResult<TicketReport> findAllReports(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
        LimitedSearchResult<TicketReport> ticketReports = ticketReportsSearchService.find(criteria, offset, limit, orderBy);
        LimitedSearchResult<TicketReport> documentReports = documentReportsSearchService.find(criteria, 0, 1, orderBy);

        long totalTicketReports = ticketReports.getTotalResults();
        long totalDocumentReports = documentReports.getTotalResults();
        long totalDocumentsCount = totalTicketReports + totalDocumentReports;
        long actualTicketReportsCount = ticketReports.getResults().size();

        if (actualTicketReportsCount < limit) {
            long subOffset = 0;
            long subLimit = limit;
            if (actualTicketReportsCount > 0) {
                subLimit-=(totalTicketReports % limit);
                documentReports = documentReportsSearchService.find(criteria, subOffset, subLimit, orderBy);
                return joinResults(ticketReports, documentReports);
            } else {
                subOffset = offset - totalTicketReports;
                documentReports = documentReportsSearchService.find(criteria, subOffset, subLimit, orderBy);
                return updateTotalResults(documentReports, totalDocumentsCount);
            }
        }
        return updateTotalResults(ticketReports, totalDocumentsCount);
    }

    private LimitedSearchResult<TicketReport> joinResults(LimitedSearchResult<TicketReport> firstResult, LimitedSearchResult<TicketReport> secondResult) {
        List<TicketReport> retReslts = new ArrayList<TicketReport>();
        retReslts.addAll(firstResult.getResults());
        retReslts.addAll(secondResult.getResults());
        LimitedSearchResult<TicketReport> ret = new LimitedSearchResult<TicketReport>(
                firstResult.getCriteria(),
                null,
                firstResult.getLimit(),
                firstResult.getOffset(),
                retReslts,
                firstResult.getTotalResults() + secondResult.getTotalResults()
                );
        return ret;
    }

    private LimitedSearchResult<TicketReport> updateTotalResults(LimitedSearchResult<TicketReport> resultToUpdate, long newTotal) {
        return new LimitedSearchResult<TicketReport>(
                resultToUpdate.getCriteria(),
                null,
                resultToUpdate.getLimit(),
                resultToUpdate.getOffset(),
                resultToUpdate.getResults(),
                newTotal);
    }

    private void validateLoggedIn(AuthenticatedUser user)
            throws NoAuthenticatedUserException {
        if (user == null) {
            throw new NoAuthenticatedUserException();
        }
    }

    @Override
    public LimitedSearchResult<TotalDomains> findTotalDomains(AuthenticatedUser user, TotalDomainsCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        return reportsService.findTotalDomains(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<TotalDomainsPerDate> findTotalDomainsPerDate(AuthenticatedUser user, TotalDomainsPerDateCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        return reportsService.findTotalDomainsPerDate(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<DomainsPerClass> findTotalDomainsPerClass(AuthenticatedUser user, DomainsPerClassSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        validateLoggedIn(user);
        return reportsService.findTotalDomainsPerClass(criteria, offset, limit, sortBy);
    }
}
