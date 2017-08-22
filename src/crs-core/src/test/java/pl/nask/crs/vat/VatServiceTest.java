package pl.nask.crs.vat;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.util.Date;

import javax.annotation.Resource;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.vat.dao.VatDAO;
import pl.nask.crs.vat.exceptions.NextValidVatNotFoundException;
import pl.nask.crs.vat.exceptions.VatFromDuplicationException;
import pl.nask.crs.vat.exceptions.VatNotFoundException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class VatServiceTest extends AbstractTest {

    @Resource
    VatDAO vatDAO;

    @Resource
    VatService vatService;

    @Test
    public void addTest() throws Exception {
        int newVatId = vatService.addVatRate("A", new Date(), 0.20, null);
        Vat vat = vatDAO.get(newVatId);
        AssertJUnit.assertNotNull(vat);
        AssertJUnit.assertEquals("A", vat.getCategory());
        AssertJUnit.assertEquals(0.2, vat.getVatRate());
    }

    @Test(expectedExceptions = VatFromDuplicationException.class)
    public void addWithDuplicationTest() throws Exception {
        vatService.addVatRate("A", new Date(125, 0, 1), 0.5, null);
    }

    @Test(expectedExceptions = ThirdDecimalPlaceException.class)
    public void addWithThirdDecimalPlaceTest() throws Exception {
        vatService.addVatRate("A", new Date(), 0.221, null);
    }

    @Test
    public void invalidateTest() throws Exception {
        Date currDate = new Date();
        Vat currVat = vatDAO.getCurrentVat("A", currDate);
        AssertJUnit.assertEquals(0.2, currVat.getVatRate());

        vatService.invalidate(currVat.getId());
        vatDAO.flushCache();

        currVat = vatDAO.getCurrentVat("A", currDate);
        AssertJUnit.assertEquals(0.19, currVat.getVatRate());
    }

    @Test(expectedExceptions = NextValidVatNotFoundException.class)
    public void invalidateWithoutValidNextVatTest() throws Exception {
        //add vat for future for date
        vatService.addVatRate("A", new Date(200, 0, 1), 0.99, null); //2100-01-01

        vatService.invalidate(1);
        vatService.invalidate(2);
        vatService.invalidate(3);
    }

    @Test(expectedExceptions = VatNotFoundException.class)
    public void invalidateNotExistingVatTest() throws Exception {
        vatService.invalidate(66);
    }
}
