package pl.nask.crs.price;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.price.dao.DomainPricingDAO;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainPricingDictionaryTest extends AbstractTest {

    private Dictionary<String, DomainPrice> domainPricingDictionary;

    @Resource
    private DomainPricingDAO domainPricingDAO;
    
    @BeforeMethod
	public void prepareDomainPricingDictionary() {
    	domainPricingDictionary = new DomainPricingDictionary(domainPricingDAO);
    }

    @Test
    public void getAllTest() {
        List<DomainPrice> prices = domainPricingDictionary.getEntries();
        AssertJUnit.assertEquals("total prices", 4, prices.size());
    }

    @Test
    public void getByCodeTest() {
        DomainPrice domainPrice = domainPricingDictionary.getEntry("Std1Year");
        AssertJUnit.assertEquals("description", null, domainPrice.getDescription());
        AssertJUnit.assertEquals("price", BigDecimal.valueOf(65).setScale(2), domainPrice.getPrice());
    }

    @Test
    public void getAllWithDateTest() {
        Date forDate = new Date(100, 0, 1); //2000-01-01
        Dictionary<String, DomainPrice> dictionary = new DomainPricingDictionary(domainPricingDAO, forDate);
        List<DomainPrice> prices = dictionary.getEntries();
        AssertJUnit.assertEquals("total prices", 3, prices.size());
    }

    @Test
    public void getByCodeWithDateTest() {
        Date forDate = new Date(100, 0, 1); //2000-01-01
        Dictionary<String, DomainPrice> dictionary = new DomainPricingDictionary(domainPricingDAO, forDate);
        DomainPrice domainPrice = dictionary.getEntry("RM6Yr");
        AssertJUnit.assertEquals("code", "RM6Yr", domainPrice.getId());
        AssertJUnit.assertEquals("description", "Registration for 6 Year", domainPrice.getDescription());
        AssertJUnit.assertEquals("price", BigDecimal.valueOf(120).setScale(2), domainPrice.getPrice());
    }
}
