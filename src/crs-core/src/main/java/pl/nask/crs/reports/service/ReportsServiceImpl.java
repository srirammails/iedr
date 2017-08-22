package pl.nask.crs.reports.service;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.DomainsPerClass;
import pl.nask.crs.reports.TotalDomains;
import pl.nask.crs.reports.TotalDomainsPerDate;
import pl.nask.crs.reports.dao.ReportsDAO;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.reports.search.TotalDomainsCriteria;
import pl.nask.crs.reports.search.TotalDomainsPerDateCriteria;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReportsServiceImpl implements ReportsService {

    private ReportsDAO dao;

    public ReportsServiceImpl(ReportsDAO dao) {
        this.dao = dao;
    }

    @Override
    public LimitedSearchResult<TotalDomains> findTotalDomains(TotalDomainsCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return dao.findTotalDomains(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<TotalDomainsPerDate> findTotalDomainsPerDate(TotalDomainsPerDateCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return dao.findTotalDomainsPerDate(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<DomainsPerClass> findTotalDomainsPerClass(DomainsPerClassSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return dao.findTotalDomainsPerClass(criteria, offset, limit, sortBy);
    }
}
