package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.payment.dao.InvoiceNumberDAO;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceNumberDAOImpl implements InvoiceNumberDAO {

    InternalInvoiceNumberIbatisDAO internalDAO;

    public InvoiceNumberDAOImpl(InternalInvoiceNumberIbatisDAO internalDAO) {
        this.internalDAO = internalDAO;
    }

    @Override
    public Date getMinInvoiceYear() {
        return internalDAO.getMinInvoiceYear();
    }

    @Override
    public Integer getLastInvoiceNumber(Date forYear) {
        return internalDAO.getLastInvoiceNumber(forYear);
    }

    @Override
    public void initLastInvoiceNumber(Date forYear, int lastInvoiceNumber) {
        internalDAO.initLastInvoiceNumber(forYear, lastInvoiceNumber);
    }

    @Override
    public void setNextInvoiceNumber(Date forYear, int nextInvoiceNumber) {
        internalDAO.setNextInvoiceNumber(forYear, nextInvoiceNumber);
    }
}
