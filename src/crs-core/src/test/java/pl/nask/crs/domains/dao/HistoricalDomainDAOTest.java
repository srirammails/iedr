package pl.nask.crs.domains.dao;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Date;

import javax.annotation.Resource;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.history.HistoricalObject;


public class HistoricalDomainDAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalDomainDAO historicalDomainDAO;
    @Resource
    DomainDAO domainDAO;

    @Test
    public void testGetDomainHistoryForDomain() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName("castlebargolfclub.ie");
        SearchResult<HistoricalObject<Domain>> result = historicalDomainDAO.find(criteria);
        AssertJUnit.assertEquals(result.getResults().size(), 23);
    }

    @Test
    public void testCreate() {
        final String domainName = "castlebargolfclub.ie";
        historicalDomainDAO.create(domainName, new Date(), "aa");
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName(domainName);
        SearchResult<HistoricalObject<Domain>> result = historicalDomainDAO.find(criteria);
        AssertJUnit.assertEquals(24, result.getResults().size());
    }

    @Test
    public void testLimitedFindByDomainName() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName("theweb");
        criteria.setDomainHolder("");
        criteria.setNicHandle("");
        LimitedSearchResult<HistoricalObject<Domain>> found = historicalDomainDAO.find(criteria, 0, 10);
        AssertJUnit.assertEquals(10, found.getResults().size());
        AssertJUnit.assertEquals(14, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByAccount() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setAccountId(122L);
        LimitedSearchResult<HistoricalObject<Domain>> found = historicalDomainDAO.find(criteria, 0, 10);
        AssertJUnit.assertEquals(10, found.getResults().size());
        AssertJUnit.assertEquals(27, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByDomainHolder() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainHolder("astleba");
        LimitedSearchResult<HistoricalObject<Domain>> found = historicalDomainDAO.find(criteria, 0, 10);
        AssertJUnit.assertEquals(10, found.getResults().size());
        AssertJUnit.assertEquals(23, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByNicHandle() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setNicHandle("AAH014-IEDR");
        LimitedSearchResult<HistoricalObject<Domain>> found = historicalDomainDAO.find(criteria, 0, 1);
        AssertJUnit.assertEquals(1, found.getResults().size());
        AssertJUnit.assertEquals(28, found.getTotalResults());
    }
}

