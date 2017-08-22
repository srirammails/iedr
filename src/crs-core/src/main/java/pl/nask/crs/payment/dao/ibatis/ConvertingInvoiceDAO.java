package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.dao.InvoiceDAO;
import pl.nask.crs.payment.dao.ibatis.converters.ExtendedInvoiceConverter;
import pl.nask.crs.payment.dao.ibatis.converters.InvoiceConverter;
import pl.nask.crs.payment.dao.ibatis.objects.InternalExtendedInvoice;
import pl.nask.crs.payment.dao.ibatis.objects.InternalInvoice;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ConvertingInvoiceDAO extends ConvertingGenericDAO<InternalInvoice, Invoice, Integer> implements InvoiceDAO {

    InternalInvoiceIbatisDAO internalDAO;
    InvoiceConverter invoiceConverter;

    public ConvertingInvoiceDAO(InternalInvoiceIbatisDAO internalDAO, InvoiceConverter invoiceConverter) {
        super(internalDAO, invoiceConverter);
        this.internalDAO = internalDAO;
        this.invoiceConverter = invoiceConverter;
    }

    @Override
    public List<Invoice> getAll() {
        return getInternalConverter().to(internalDAO.getAll());
    }

    @Override
    public int createInvoice(Invoice invoice) {
        return internalDAO.createInvoice(getInternalConverter().from(invoice));
    }

    @Override
    public List<DomainInfo> getInvoiceInfo(String invoiceNumber) {
        return internalDAO.getInvoiceInfo(invoiceNumber);
    }

    @Override
    public Invoice getByNumber(String invoiceNumber) {
        return getInternalConverter().to(internalDAO.getByNumber(invoiceNumber));
    }

    @Override
    public LimitedSearchResult<ExtendedInvoice> findExtended(ExtendedInvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalExtendedInvoice> result = internalDAO.findExtended((SearchCriteria)criteria, offset, limit, sortBy);
        List<ExtendedInvoice> list = new ExtendedInvoiceConverter().to(result.getResults());
        return new LimitedSearchResult<ExtendedInvoice>(criteria, sortBy, limit, offset, list, result.getTotalResults());
    }

    @Override
    public LimitedSearchResult<Invoice> findSimple(InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalInvoice> result = internalDAO.findSimple((SearchCriteria)criteria, offset, limit, sortBy);
        List<Invoice> list = invoiceConverter.to(result.getResults());
        return new LimitedSearchResult<Invoice>(criteria, sortBy, limit, offset, list, result.getTotalResults());
    }
}
