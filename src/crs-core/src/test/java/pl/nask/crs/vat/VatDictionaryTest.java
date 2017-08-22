package pl.nask.crs.vat;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.vat.dao.VatDAO;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class VatDictionaryTest extends AbstractTest {

    @Resource
    VatDAO vatDAO;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void vatDictionaryWithNullDateTest() {
        Vat vat = null;
        Dictionary<String, Vat> vatDictionary = new VatDictionary(vatDAO, null);
    }

    @Test
    public void getEntryTest() {
        Vat vat = null;
        Dictionary<String, Vat> vatDictionary = null;

        Date date1 = new Date(112, 0, 2);//2012-01-02
        vatDictionary = new VatDictionary(vatDAO, date1);
        vat = vatDictionary.getEntry("A");
        AssertJUnit.assertEquals("A", vat.getCategory());
        AssertJUnit.assertTrue(date1.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.2, vat.getVatRate(), 0.0001);

        vat = vatDictionary.getEntry("B");
        AssertJUnit.assertEquals("B", vat.getCategory());
        AssertJUnit.assertTrue(date1.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.10, vat.getVatRate(), 0.0001);

        vat = vatDictionary.getEntry("C");
        AssertJUnit.assertEquals("C", vat.getCategory());
        AssertJUnit.assertTrue(date1.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.30, vat.getVatRate(), 0.0001);

        vat = vatDictionary.getEntry("S");
        AssertJUnit.assertEquals("S", vat.getCategory());
        AssertJUnit.assertTrue(date1.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.95, vat.getVatRate(), 0.0001);

        Date date2 = new Date(110, 2, 2);//2010-03-01
        vatDictionary = new VatDictionary(vatDAO, date2);
        vat = vatDictionary.getEntry("A");
        AssertJUnit.assertEquals("A", vat.getCategory());
        AssertJUnit.assertTrue(date2.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.19, vat.getVatRate(), 0.0001);

        vat = vatDictionary.getEntry("B");
        AssertJUnit.assertEquals("B", vat.getCategory());
        AssertJUnit.assertTrue(date2.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.09, vat.getVatRate(), 0.0001);

        vat = vatDictionary.getEntry("C");
        AssertJUnit.assertEquals("C", vat.getCategory());
        AssertJUnit.assertTrue(date2.after(vat.getFromDate()));
        AssertJUnit.assertEquals(0.29, vat.getVatRate(), 0.0001);

        vat = vatDictionary.getEntry("S");
        AssertJUnit.assertNull(vat);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getEntryWithNullCategoryTest() {
        Vat vat = null;
        Date date1 = new Date(112, 0, 2);//2012-01-02
        Dictionary<String, Vat> vatDictionary = new VatDictionary(vatDAO, date1);
        vat = vatDictionary.getEntry(null);
    }

    @Test
    public void getEntriesTest() {
        List<Vat> vatList = null;
        Vat vat = null;
        Dictionary<String, Vat> vatDictionary = null;

        Date date1 = new Date(112, 0, 2);//2012-01-02
        vatDictionary = new VatDictionary(vatDAO, date1);
        vatList = vatDictionary.getEntries();
        AssertJUnit.assertEquals(4, vatList.size());
        vat = new Vat(3, "A", new Date(125, 0, 1), 0.214);
        AssertJUnit.assertFalse("vat list does not contain vat: " + vat, vatList.contains(vat));
        vat = new Vat(5, "B", new Date(111, 1, 1), 0.1);
        AssertJUnit.assertTrue("vat list does not contain vat: " + vat, vatList.contains(vat));
        vat = new Vat(8, "C", new Date(111, 2, 1), 0.3);
        AssertJUnit.assertTrue("vat list does not contain vat: " + vat , vatList.contains(vat));
        vat = new Vat(11, "S", new Date(111, 3, 1), 0.95);
        AssertJUnit.assertTrue("vat list does not contain vat: " + vat , vatList.contains(vat));


        Date date2 = new Date(110, 2, 2);//2010-03-01
        vatDictionary = new VatDictionary(vatDAO, date2);
        vatList = vatDictionary.getEntries();
        AssertJUnit.assertEquals(3, vatList.size());
        vat = new Vat(1, "A", new Date(110, 0, 1), 0.19);
        AssertJUnit.assertTrue("vat list does not contain vat: " + vat , vatList.contains(vat));
        vat = new Vat(4, "B", new Date(110, 1, 1), 0.09);
        AssertJUnit.assertTrue("vat list does not contain vat: " + vat , vatList.contains(vat));
        vat = new Vat(7, "C", new Date(110, 2, 1), 0.29);
        AssertJUnit.assertTrue("vat list does not contain vat: " + vat , vatList.contains(vat));
    }

}
