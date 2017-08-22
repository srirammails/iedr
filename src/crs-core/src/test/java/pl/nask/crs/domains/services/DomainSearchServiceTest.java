package pl.nask.crs.domains.services;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;
import javax.annotation.Resource;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.DomainSearchCriteria;

/**
 * @author Kasia Fulara
 */
public class DomainSearchServiceTest extends AbstractContextAwareTest {

    @Resource
    DomainSearchService domainSearchService;

    @Test
    public void testFindForNH() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();

        criteria.setNicHandle("AAI538-IEDR");
        LimitedSearchResult<Domain> result = domainSearchService.find(criteria, 0, 10, null);
        assertEquals(result.getResults().size(), 1);
    }
    
    @Test
    public void testFindActive() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setActive(true);

        LimitedSearchResult<Domain> result = domainSearchService.find(criteria, 0, 10, null);
        assertEquals(10, result.getResults().size());
        assertEquals(40, result.getTotalResults());
    }
}
