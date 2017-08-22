package pl.nask.crs.payment.service.impl;

import pl.nask.crs.payment.dao.InvoiceNumberDAO;
import pl.nask.crs.payment.service.InvoiceNumberService;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceNumberServiceImpl implements InvoiceNumberService {

    private InvoiceNumberDAO invoicingDAO;

    public InvoiceNumberServiceImpl(InvoiceNumberDAO invoicingDAO) {
        this.invoicingDAO = invoicingDAO;
    }

    @Override
    public int getNextInvoiceNumber(Date forYear) {
        int lastInvoiceNumber = getLastInvoiceNumber(forYear);
        int nextInvoiceNumber = lastInvoiceNumber + 1;
        invoicingDAO.setNextInvoiceNumber(forYear, nextInvoiceNumber);
        return nextInvoiceNumber;
    }

    private int getLastInvoiceNumber(Date forYear) {
        Integer lastInvoiceNumber = invoicingDAO.getLastInvoiceNumber(forYear);
        if (lastInvoiceNumber == null) {
            Date minYear = invoicingDAO.getMinInvoiceYear();
            if (minYear == null || forYear.before(minYear)) {
                lastInvoiceNumber = 0;
            } else {
                lastInvoiceNumber = getLastInvoiceNumber(DateUtils.addYears(forYear, -1));
            }
            invoicingDAO.initLastInvoiceNumber(forYear, lastInvoiceNumber);
        }
        return lastInvoiceNumber;
    }

    @Override
    public int getNextInvoiceNumber() {
        Date currentDate = new Date();
        return getNextInvoiceNumber(currentDate);
    }

}
