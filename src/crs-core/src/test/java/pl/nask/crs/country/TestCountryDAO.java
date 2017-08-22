package pl.nask.crs.country;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import javax.annotation.Resource;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.country.dao.CountryDAO;


public class TestCountryDAO extends AbstractTest {
    @Resource
    CountryDAO countryDAO;

    @Test
    public void testFind() {        
        SearchResult<Country> r = countryDAO.find(null);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertTrue("No results found 3", r.getResults().size() > 0);
        AssertJUnit.assertNotNull("Name field not filled", r.getResults().get(0)
                .getName());
    }

}
