package pl.nask.crs.payment.dao;

import java.util.Date;
/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface InvoiceNumberDAO {

    public Date getMinInvoiceYear();

    public Integer getLastInvoiceNumber(Date forYear);

    public void initLastInvoiceNumber(Date forYear, int lastInvoiceNumber);

    public void setNextInvoiceNumber(Date forYear, int nextInvoiceNumber);

}
