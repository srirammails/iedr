package pl.nask.crs.payment.service;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface InvoiceNumberService {

    public int getNextInvoiceNumber();

    public int getNextInvoiceNumber(Date forYear);
}
