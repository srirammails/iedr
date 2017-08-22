package pl.nask.crs.country;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.util.List;

import javax.annotation.Resource;

import pl.nask.crs.commons.AbstractTest;

public class TestCountryFactory extends AbstractTest {
    @Resource
    CountryFactory countryFactory;

    @Test
    public void testFind() {
        List<Country> r = countryFactory.getEntries();        
        AssertJUnit.assertNotNull("No results found 1", r);        
        AssertJUnit.assertTrue("No results found 3", r.size() > 0);
        AssertJUnit.assertNotNull("Name field not filled", r.get(0).getName());
        AssertJUnit.assertNotNull("Counties field not filled", r.get(0).getCounties());
    }
    
    @Test
    public void testGetUSA() {
        Country r = countryFactory.getEntry(254l);
        AssertJUnit.assertNotNull("No results found 1", r);        
        AssertJUnit.assertNotNull("Name field not filled", r.getName());
        AssertJUnit.assertNotNull("Counties field not filled", r.getCounties());
        AssertJUnit.assertNotNull("No counties found", r.getCounties().size() > 0);
    }
    
    @Test
    public void validateUSA() throws InvalidCountryException{
    	countryFactory.validateCountry("USA");
    }

    @Test(expectedExceptions=InvalidCountryException.class)
    public void validateJUESA() throws InvalidCountryException {
    	countryFactory.validateCountry("JUESA");
    }
    
    @Test
    public void validateCountyOk() throws InvalidCountryException, InvalidCountyException {
    	countryFactory.validate("United Kingdom", "Avon", true);
    }
    
    @Test(expectedExceptions=InvalidCountyException.class)
    public void validateCountyFail() throws InvalidCountryException, InvalidCountyException {
    	countryFactory.validate("United Kingdom", "Pcim", true);
    }
    
    @Test
    public void validateCountyAny() throws InvalidCountryException, InvalidCountyException {
    	countryFactory.validate("Poland", "", true);
    }

    @Test(expectedExceptions = CountyNotExistsException.class)
    public void validateCountyStrict() throws InvalidCountryException, InvalidCountyException {
        countryFactory.validate("Poland", "Mazowieckie", true);
    }

    @Test
    public void validateCountyNonStrict() throws InvalidCountryException, InvalidCountyException {
        countryFactory.validate("Poland", "Mazowieckie", false);
    }

    @Test(expectedExceptions = InvalidCountyException.class)
    public void validateCountyFailNonStrict() throws InvalidCountryException, InvalidCountyException {
        countryFactory.validate("United Kingdom", "Pcim", false);
    }

}
