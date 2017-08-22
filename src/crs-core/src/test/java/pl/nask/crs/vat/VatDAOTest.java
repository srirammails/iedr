package pl.nask.crs.vat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.vat.dao.VatDAO;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class VatDAOTest extends AbstractTest {

    @Resource
    VatDAO vatDAO;

    @Test
    public void getVatTest() {
        Vat vat = null;
        Date date1 = new Date(125, 0, 2);//2025-01-02
        vat = vatDAO.getCurrentVat("A", date1);
        AssertJUnit.assertEquals("A", vat.getCategory());
        AssertJUnit.assertTrue(date1.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.214, vat.getVatRate(), 0.0001);

        vat = vatDAO.getCurrentVat("B", date1);
        AssertJUnit.assertEquals("B", vat.getCategory());
        AssertJUnit.assertTrue(date1.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.10, vat.getVatRate(), 0.0001);

        vat = vatDAO.getCurrentVat("C", date1);
        AssertJUnit.assertEquals("C", vat.getCategory());
        AssertJUnit.assertTrue(date1.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.30, vat.getVatRate(), 0.0001);

        vat = vatDAO.getCurrentVat("S", date1);
        AssertJUnit.assertEquals("S", vat.getCategory());
        AssertJUnit.assertTrue(date1.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.95, vat.getVatRate(), 0.0001);

        Date date2 = new Date(110, 2, 2);//2010-03-01
        vat = vatDAO.getCurrentVat("A", date2);
        AssertJUnit.assertEquals("A", vat.getCategory());
        AssertJUnit.assertTrue(date2.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.19, vat.getVatRate(), 0.0001);

        vat = vatDAO.getCurrentVat("B", date2);
        AssertJUnit.assertEquals("B", vat.getCategory());
        AssertJUnit.assertTrue(date2.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.09, vat.getVatRate(), 0.0001);

        vat = vatDAO.getCurrentVat("C", date2);
        AssertJUnit.assertEquals("C", vat.getCategory());
        AssertJUnit.assertTrue(date2.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.29, vat.getVatRate(), 0.0001);

        vat = vatDAO.getCurrentVat("S", date2);
        AssertJUnit.assertNull(vat);
    }

    @Test
    public void fromDateMustBeTruncated() {
        Date date1 = DateUtils.endOfYear(new Date(124, 01, 01));//2024-12-31 23:59:59.999
        Vat vat = vatDAO.getCurrentVat("A", date1);
        AssertJUnit.assertEquals("A", vat.getCategory());
        AssertJUnit.assertTrue(date1.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.2, vat.getVatRate(), 0.0001);
    }

    @Test
    public void getVatListTest() {
        List<Vat> vats = null;
        Date date1 = new Date(125, 0, 2);//2012-01-02
        vats = vatDAO.getCurrentAndPreviousVatList(date1);
        AssertJUnit.assertEquals(9, vats.size());
        Date date2 = new Date(110, 2, 2);//2010-03-01
        vats = vatDAO.getCurrentAndPreviousVatList(date2);
        AssertJUnit.assertEquals(3, vats.size());
    }

    @Test
    public void createTest() {
        Date date = DateUtils.endOfDay(new Date());
        Vat newVat = new Vat(-1, "A", date, 0.5f);
        vatDAO.create(newVat);

        Vat fromDB = vatDAO.getCurrentVat("A", date);

        AssertJUnit.assertEquals(newVat.getVatRate(), fromDB.getVatRate(), 0.0001);

        fromDB = vatDAO.get(newVat.getId());
        AssertJUnit.assertEquals(org.apache.commons.lang.time.DateUtils.truncate(newVat.getFromDate(), Calendar.DATE), fromDB.getFromDate());
    }
// TODO: CRS-72
//    @Test
//    public void invalidateTest() {
//        Vat before = vatDAO.getCurrentVat("A", new Date());
//        AssertJUnit.assertEquals(2, before.getId());
//
//        vatDAO.invalidate(before.getId());
//
//        Vat after = vatDAO.getCurrentVat("A", new Date());
//        AssertJUnit.assertEquals(1, after.getId());
//    }

    @Test
    public void getVatForCategoryTest() {
        Vat vat = vatDAO.getVatForCategoryAndFrom("A", new Date());
        AssertJUnit.assertNull(vat);

        vat = vatDAO.getVatForCategoryAndFrom("A", DateUtils.endOfDay(new Date(110, 0, 1)));
        AssertJUnit.assertEquals(0.19f, vat.getVatRate(), 0.0001);
    }

    @Test
    public void getCurrentVatExceptVatIdTest() {
        Vat vat = vatDAO.getCurrentVatExceptVatId("A", new Date(), 3);
        AssertJUnit.assertEquals(2, vat.getId());

        vat = vatDAO.getCurrentVatExceptVatId("A", new Date(110, 1, 1), 1);
        AssertJUnit.assertNull(vat);

        int _ = 123; // id other than 1,2,3
        vat = vatDAO.getCurrentVatExceptVatId("A", DateUtils.endOfDay(new Date(110, 11, 31)), _);
        AssertJUnit.assertNotNull(vat);
        AssertJUnit.assertEquals(1, vat.getId());

        vat = vatDAO.getCurrentVatExceptVatId("A", DateUtils.startOfDay(new Date(111, 0, 1)), _);
        AssertJUnit.assertNotNull(vat);
        AssertJUnit.assertEquals(2, vat.getId());

        vat = vatDAO.getCurrentVatExceptVatId("A", DateUtils.startOfDay(new Date(111, 0, 1)), 2);
        AssertJUnit.assertNotNull(vat);
        AssertJUnit.assertEquals(1, vat.getId());
    }

    @Test
    public void getByIdTest() {
        Vat vat = vatDAO.get(1);
        AssertJUnit.assertEquals(0.19, vat.getVatRate(), 0.0001);
    }

    @Test
    public void getAllValidTest() {
        List<Vat> vatList = vatDAO.getAllValid();
        AssertJUnit.assertEquals(12, vatList.size());
    }

    @Test
    public void getCurrentAndFutureVatListTest() {
        Date date = new Date(111, 0, 1);//2011-01-01
        List<Vat> vatList = vatDAO.getCurrentAndFutureVatList(date);
        AssertJUnit.assertEquals(8, vatList.size());
    }

    @Test
    public void testGetCategories() throws Exception {
        List<String> categories = vatDAO.getCategories();
        AssertJUnit.assertEquals(3, categories.size());
    }
}
