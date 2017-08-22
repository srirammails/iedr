package pl.nask.crs.web.reports;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.TotalDomains;
import pl.nask.crs.reports.search.TotalDomainsCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TotalDomainsReportAction extends GenericSearchAction<TotalDomains, TotalDomainsCriteria> {

    private final int PAGE_SIZE = Integer.MAX_VALUE;

    private final static List<String> excludedRegistrarsNic = Arrays.asList("IH4-IEDR", "WAIVED-IEDR", "DUMMY-IEDR");

    public TotalDomainsReportAction(final ReportsAppService reportsAppService) {
        super(new AppSearchService<TotalDomains, TotalDomainsCriteria>() {
            public LimitedSearchResult<TotalDomains> search(AuthenticatedUser user, TotalDomainsCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return reportsAppService.findTotalDomains(user, criteria, offset, limit, orderBy);
            }
        });
    }

    @Override
    protected TotalDomainsCriteria createSearchCriteria() {
        return new TotalDomainsCriteria(excludedRegistrarsNic);
    }

    @Override
    protected int getPageSize() {
        return PAGE_SIZE;
    }
}
