package pl.nask.crs.price;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

import org.apache.commons.lang.time.DateUtils;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.price.dao.DomainPricingDAO;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceDAOTest extends AbstractTest {

    @Resource
    DomainPricingDAO pricingDAO;

    @Test
    public void getByIdTest() {
        DomainPrice price = pricingDAO.get("RM2Yr");
        AssertJUnit.assertNotNull(price);
        AssertJUnit.assertEquals("RM2Yr", price.getId());
        AssertJUnit.assertEquals("Registration for 2 Year", price.getDescription());
        AssertJUnit.assertEquals(BigDecimal.valueOf(40).setScale(2), price.getPrice());
        AssertJUnit.assertEquals(2, price.getDuration());
        assertNotNull(price.getValidFrom());
        assertNotNull(price.getValidTo());
        AssertJUnit.assertTrue(price.isForRegistration());
        AssertJUnit.assertTrue(price.isForRenewal());
        AssertJUnit.assertFalse(price.isDefaultPrice());
        AssertJUnit.assertFalse(price.isDirect());
    }

    @Test
    public void createTest() {
        Date validFromDate = pl.nask.crs.commons.utils.DateUtils.endOfYear(new Date());
        validFromDate = DateUtils.addYears(validFromDate, -1);
        Date validToDate = DateUtils.addYears(validFromDate, 5);
        DomainPrice price = DomainPrice.newInstance("newPrice", "description", BigDecimal.valueOf(66).setScale(2), 2, validFromDate, validToDate, true, true, true);
        pricingDAO.create(price);
        DomainPrice fromDB = pricingDAO.get(price.getId());
        AssertJUnit.assertEquals(price.getId(), fromDB.getId());
        AssertJUnit.assertEquals(price.getDescription(), fromDB.getDescription());
        AssertJUnit.assertEquals(price.getPrice(), fromDB.getPrice());
        AssertJUnit.assertEquals(price.getDuration(), fromDB.getDuration());
        AssertJUnit.assertEquals(DateUtils.truncate(validFromDate, Calendar.DATE), fromDB.getValidFrom());
        AssertJUnit.assertEquals(DateUtils.truncate(validToDate, Calendar.DATE), fromDB.getValidTo());
        AssertJUnit.assertEquals(price.isForRegistration(), fromDB.isForRegistration());
        AssertJUnit.assertEquals(price.isForRenewal(), fromDB.isForRenewal());
        AssertJUnit.assertEquals(price.isDefaultPrice(), fromDB.isDefaultPrice());
        AssertJUnit.assertEquals(price.isDirect(), fromDB.isDirect());
    }

    @Test
    public void getAllTest() {
        List<DomainPrice> domainPrices = pricingDAO.getAll();
        AssertJUnit.assertEquals(13, domainPrices.size());
    }

    @Test
    public void updateTest() {
        String id = "RM2Yr";
        DomainPrice price = pricingDAO.get(id);

        price.setDescription("new description");
        price.setPrice(BigDecimal.valueOf(55).setScale(2));
        price.setDuration(5);
        price.setValidFrom(null);
        price.setValidTo(null);
        price.setForRegistration(false);
        price.setForRenewal(false);
        price.setDirect(true);

        pricingDAO.update(price);

        DomainPrice updated = pricingDAO.get(id);

        AssertJUnit.assertEquals(id, updated.getId());
        AssertJUnit.assertEquals(price.getDescription(), updated.getDescription());
        AssertJUnit.assertEquals(price.getPrice(), updated.getPrice());
        AssertJUnit.assertEquals(price.getDuration(), updated.getDuration());
        assertNull(updated.getValidFrom());
        assertNull(updated.getValidTo());
        AssertJUnit.assertFalse(updated.isForRegistration());
        AssertJUnit.assertFalse(updated.isForRenewal());
        AssertJUnit.assertFalse(updated.isDefaultPrice());
        AssertJUnit.assertTrue(updated.isDirect());
    }

    @Test
    public void findAllTest() {
        LimitedSearchResult<DomainPrice> result = pricingDAO.findAll(0, 10, null);
        AssertJUnit.assertEquals(13, result.getTotalResults());
        AssertJUnit.assertEquals(10, result.getResults().size());
    }

    @Test
    public void getDomainPriceListTest() throws Exception {
        List<DomainPrice> prices = pricingDAO.getDomainPriceList(new Date());
        AssertJUnit.assertEquals(4, prices.size());
    }

    @Test
    public void getDomainPriceByCodeTest() throws Exception {
        String id = "Std2Year";
        DomainPrice price = pricingDAO.getDomainPriceByCode("Std2Year", new Date());
        AssertJUnit.assertNotNull(price);
        AssertJUnit.assertEquals(id, price.getId());
        AssertJUnit.assertEquals("Registration of your domain for 2 years", price.getDescription());
        AssertJUnit.assertEquals(BigDecimal.valueOf(184).setScale(2), price.getPrice());
        AssertJUnit.assertEquals(2, price.getDuration());
        assertNotNull(price.getValidFrom());
        assertNotNull(price.getValidTo());
        AssertJUnit.assertTrue(price.isForRegistration());
        AssertJUnit.assertTrue(price.isForRenewal());
        AssertJUnit.assertTrue(price.isDefaultPrice());
        AssertJUnit.assertTrue(price.isDirect());
    }
}
