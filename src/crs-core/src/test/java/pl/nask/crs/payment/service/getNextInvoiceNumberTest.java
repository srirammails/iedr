package pl.nask.crs.payment.service;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Date;

import javax.annotation.Resource;

import pl.nask.crs.payment.AbstractContextAwareTest;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class getNextInvoiceNumberTest extends AbstractContextAwareTest {

    @Resource
    InvoiceNumberService invoicingService;

    @Test
    public void getForExistingYearTest() {
        Date date = new Date(120, 6, 6);
        int nextInvoiceNumber = invoicingService.getNextInvoiceNumber(date);
        AssertJUnit.assertEquals(6, nextInvoiceNumber);
        invoicingService.getNextInvoiceNumber(date);
        nextInvoiceNumber = invoicingService.getNextInvoiceNumber(date);
        AssertJUnit.assertEquals(8, nextInvoiceNumber);
    }

    @Test
    public void getForNonExistingYear() {
        int nextInvoiceNumber = invoicingService.getNextInvoiceNumber();
        AssertJUnit.assertEquals(1, nextInvoiceNumber);
        invoicingService.getNextInvoiceNumber();
        nextInvoiceNumber = invoicingService.getNextInvoiceNumber();
        AssertJUnit.assertEquals(3, nextInvoiceNumber);
    }
}
