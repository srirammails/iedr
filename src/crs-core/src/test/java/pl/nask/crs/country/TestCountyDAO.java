package pl.nask.crs.country;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import javax.annotation.Resource;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.country.dao.CountyDAO;
import pl.nask.crs.country.search.CountySearchCriteria;

public class TestCountyDAO extends AbstractTest {
    @Resource
    CountyDAO countyDAO;

    @Test
    public void testFindNone() {
        CountySearchCriteria c = new CountySearchCriteria(1L); // Afghanistan
        SearchResult<String> r = countyDAO.find(c);
                
        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 0, r.getResults().size());        
    }
    
    @Test
    public void testFindAll() {        
        SearchResult<String> r = countyDAO.find(null);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        
        AssertJUnit.assertTrue("More than zero results expected", r.getResults().size() > 0);
    }

    @Test
    public void testFindIreland() {
        CountySearchCriteria c = new CountySearchCriteria(121L);
        SearchResult<String> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 26, r.getResults().size());
    }
    
    @Test
    public void testFindNorthernIreland() {
        CountySearchCriteria c = new CountySearchCriteria(119L);
        SearchResult<String> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 6, r.getResults().size());
    }
    
    @Test
    public void testFindUK() {
        CountySearchCriteria c = new CountySearchCriteria(254L); // UK
        SearchResult<String> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 75, r.getResults().size());
    }
    @Test
    public void testFindUSA() {
        CountySearchCriteria c = new CountySearchCriteria(265L); // USA
        SearchResult<String> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 51, r.getResults().size());
    }
}
