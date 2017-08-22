package pl.nask.crs.payment.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.*;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface InvoiceDAO extends GenericDAO<Invoice, Integer> {

	/**
	 * This is for testing purposes only! Do not use this method in the production code!
	 */
    public List<Invoice> getAll();

    public int createInvoice(Invoice invoice);

    public List<DomainInfo> getInvoiceInfo(String invoiceNumber);

    public Invoice getByNumber(String invoiceNumber);

    public LimitedSearchResult<ExtendedInvoice> findExtended(ExtendedInvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);
    public LimitedSearchResult<Invoice> findSimple(InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);
}
