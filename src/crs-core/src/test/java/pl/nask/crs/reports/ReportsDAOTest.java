package pl.nask.crs.reports;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.util.Date;

import javax.annotation.Resource;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.reports.dao.ReportsDAO;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.reports.search.TotalDomainsCriteria;
import pl.nask.crs.reports.search.TotalDomainsPerDateCriteria;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReportsDAOTest extends AbstractTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    ReportsDAO reportsDAO;

    @Test
    public void findTotalDomainsTest() {
        LimitedSearchResult<TotalDomains> result = reportsDAO.findTotalDomains(null, 0, 50, null);
        AssertJUnit.assertEquals(110, result.getTotalResults());
        AssertJUnit.assertEquals(51, result.getResults().size());

        TotalDomainsCriteria criteria = new TotalDomainsCriteria();
        criteria.setExcludedRegistrarsNic("WAIVED-IEDR");
        result = reportsDAO.findTotalDomains(criteria, 0, 50, null);
        AssertJUnit.assertEquals(109, result.getTotalResults());
        AssertJUnit.assertEquals(51, result.getResults().size());

        criteria = new TotalDomainsCriteria();
        criteria.setExcludedRegistrarsNic("IH4-IEDR");
        result = reportsDAO.findTotalDomains(criteria, 0, 50, null);
        AssertJUnit.assertEquals(106, result.getTotalResults());
        AssertJUnit.assertEquals(51, result.getResults().size());

        criteria = new TotalDomainsCriteria();
        criteria.setExcludedRegistrarsNic("WAIVED-IEDR", "IH4-IEDR");
        result = reportsDAO.findTotalDomains(criteria, 0, 50, null);
        AssertJUnit.assertEquals(105, result.getTotalResults());
        AssertJUnit.assertEquals(51, result.getResults().size());
    }

    @Test
    public void findDomainsPerMonthPerRegistrar() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        AssertJUnit.assertEquals(31, result.getTotalResults());
        AssertJUnit.assertEquals(31, result.getResults().size());
    }

    @Test
    public void findDomainsPerYearPerRegistrar() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        AssertJUnit.assertEquals(30, result.getTotalResults());
        AssertJUnit.assertEquals(30, result.getResults().size());
    }

    @Test
    public void findDomainsPerMonth() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.ALLREGISTRARS);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        AssertJUnit.assertEquals(17, result.getTotalResults());
        AssertJUnit.assertEquals(17, result.getResults().size());
    }

    @Test
    public void findDomainsPerYear() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.ALLREGISTRARS);
        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        AssertJUnit.assertEquals(7, result.getTotalResults());
        AssertJUnit.assertEquals(7, result.getResults().size());
    }

    @Test
    public void findDomainsPerMonthPerDirectRegistrar() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        criteria.setCustomerType(TotalDomainsPerDateCriteria.CustomerType.DIRECT);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        AssertJUnit.assertEquals(10, result.getTotalResults());
        AssertJUnit.assertEquals(10, result.getResults().size());
    }

    @Test
    public void findDomainsPerMonthPerRegistrarWithClassCriteria() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        criteria.setHolderClass("Natural Person");
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        AssertJUnit.assertEquals(2, result.getTotalResults());
        AssertJUnit.assertEquals(2, result.getResults().size());
    }

    @Test
    public void findDomainsPerClassTest() {
        LimitedSearchResult<DomainsPerClass> result = reportsDAO.findTotalDomainsPerClass(null, 0, 10, null);
        AssertJUnit.assertEquals(10, result.getResults().size());
        AssertJUnit.assertEquals(26, result.getTotalResults());

        DomainsPerClassSearchCriteria criteria = new DomainsPerClassSearchCriteria();
        criteria.setHolderClass("Sole Trader");
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, null);
        AssertJUnit.assertEquals(7, result.getResults().size());
        AssertJUnit.assertEquals(7, result.getTotalResults());


        criteria = new DomainsPerClassSearchCriteria();
        criteria.setHolderCategory("Registered Business Name");
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, null);
        AssertJUnit.assertEquals(10, result.getResults().size());
        AssertJUnit.assertEquals(10, result.getTotalResults());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setFrom(new Date(107, 5, 1));
        criteria.setTo(new Date(108, 7, 31));
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, null);
        AssertJUnit.assertEquals(10, result.getResults().size());
        AssertJUnit.assertEquals(16, result.getTotalResults());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setAccountId(666L);
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, null);
        AssertJUnit.assertEquals(1, result.getResults().size());
        AssertJUnit.assertEquals(1, result.getTotalResults());
        AssertJUnit.assertEquals(41, result.getResults().get(0).getDomainCount());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, null);
        AssertJUnit.assertEquals(1, result.getResults().size());
        AssertJUnit.assertEquals(1, result.getTotalResults());
        AssertJUnit.assertEquals(41, result.getResults().get(0).getDomainCount());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setTo(new Date(108, 3, 15));
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, null);
        AssertJUnit.assertEquals(1, result.getResults().size());
        AssertJUnit.assertEquals(1, result.getTotalResults());
        AssertJUnit.assertEquals(2, result.getResults().get(0).getDomainCount());
    }
}
