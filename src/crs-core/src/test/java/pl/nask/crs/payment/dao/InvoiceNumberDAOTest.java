package pl.nask.crs.payment.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.payment.AbstractContextAwareTest;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceNumberDAOTest extends AbstractContextAwareTest {

    @Resource
    InvoiceNumberDAO invoiceNumberDAO;

    @Test
    public void lastInvoiceNumberTest() {
        Date date = new Date(120, 0, 1);
        Integer lastInvoiceNumber = invoiceNumberDAO.getLastInvoiceNumber(date);
        Assert.assertNotNull(lastInvoiceNumber);
        AssertJUnit.assertEquals(5, (int)lastInvoiceNumber);

        Date currentDate = DateUtils.endOfYear(new Date());
        lastInvoiceNumber = invoiceNumberDAO.getLastInvoiceNumber(currentDate);
        Assert.assertNull(lastInvoiceNumber);
        invoiceNumberDAO.initLastInvoiceNumber(currentDate, 0);
        lastInvoiceNumber = invoiceNumberDAO.getLastInvoiceNumber(currentDate);
        AssertJUnit.assertEquals(0, (int)lastInvoiceNumber);
        invoiceNumberDAO.setNextInvoiceNumber(currentDate, 1);
        lastInvoiceNumber = invoiceNumberDAO.getLastInvoiceNumber(currentDate);
        AssertJUnit.assertEquals(1, (int)lastInvoiceNumber);
    }
}
