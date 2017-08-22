package pl.nask.crs.reports.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
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
public class InternalReportsDAO extends GenericIBatisDAO implements ReportsDAO {

    @Override
    public LimitedSearchResult<TotalDomains> findTotalDomains(TotalDomainsCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        FindParameters parameters = getParameters(criteria, offset, limit, sortBy);
        return performFind("reports.getTotalDomains", "reports.getTotalDomainsCount", parameters);
    }

    private <T> FindParameters getParameters(SearchCriteria<T> criteria, long offset, long limit, List<SortCriterion> sortBy) {
        FindParameters parameters = new FindParameters(criteria);
        parameters.setLimit(offset, limit);
        parameters.setOrderBy(sortBy);
        return parameters;
    }

    @Override
    public LimitedSearchResult<TotalDomainsPerDate> findTotalDomainsPerDate(TotalDomainsPerDateCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        FindParameters parameters = getParameters(criteria, offset, limit, sortBy);
        switch (criteria.getReportTypeGranulation()) {
            case REGISTRAR:
                switch (criteria.getReportTimeGranulation()) {
                    case MONTH:
                        return performFind("reports.getDomainsPerMonth", "reports.getDomainsPerMonthCount", parameters);
                    case YEAR:
                        return performFind("reports.getDomainsPerYear", "reports.getDomainsPerYearCount", parameters);
                    default:
                        throw new IllegalArgumentException("Illegal time granulation: " + criteria.getReportTimeGranulation());
                }
            case ALLREGISTRARS:
                switch (criteria.getReportTimeGranulation()) {
                    case MONTH:
                        return performFind("reports.getTotalDomainsPerMonth", "reports.getTotalDomainsPerMonthCount", parameters);
                    case YEAR:
                        return performFind("reports.getTotalDomainsPerYear", "reports.getTotalDomainsPerYearCount", parameters);
                    default:
                        throw new IllegalArgumentException("Illegal time granulation: " + criteria.getReportTimeGranulation());
                }
            default:
                throw new IllegalArgumentException("Illegal type granulation: " + criteria.getReportTimeGranulation());
        }
    }

    @Override
    public LimitedSearchResult<DomainsPerClass> findTotalDomainsPerClass(DomainsPerClassSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        FindParameters parameters = getParameters(criteria, offset, limit, sortBy);
        return performFind("reports.getDomainsPerClass", "reports.getDomainsPerClassCount", parameters);
    }
}
