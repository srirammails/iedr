package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.dao.ibatis.objects.InternalExtendedInvoice;
import pl.nask.crs.payment.dao.ibatis.objects.InternalInvoice;

import java.util.*;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalInvoiceIbatisDAO extends GenericIBatisDAO<InternalInvoice, Integer> {

    public InternalInvoiceIbatisDAO() {
        setGetQueryId("invoice.selectInvoiceById");
        setLockQueryId("invoice.selectInvoiceLockedById");
        setCreateQueryId("invoice.insertInvoice");
        setLimitedFindQueryId("invoice.findInvoices");
        setCountFindQueryId("invoice.countFindInvoices");
        setUpdateQueryId("invoice.updateInvoice");
    }

    public List<InternalInvoice> getAll() {
        return performQueryForList("invoice.selectAll");
    }

    public int createInvoice(InternalInvoice internalInvoice) {
        performInsert("invoice.insertInvoice", internalInvoice);
        return internalInvoice.getId();
    }

    public List<DomainInfo> getInvoiceInfo(String invoiceNumber) {
        return performQueryForList("invoice.getInvoiceInfo", invoiceNumber);
    }

    public InternalInvoice getByNumber(String invoiceNumber) {
        return (InternalInvoice) performQueryForObject("invoice.selectInvoiceByNumber", invoiceNumber);
    }

    public LimitedSearchResult<InternalExtendedInvoice> findExtended(SearchCriteria<InternalExtendedInvoice> criteria, long offset, long limit, List<SortCriterion> sortBy) {
        sortBy = initDefaultSort(sortBy);
        FindParameters parameters = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy);
        List<InternalExtendedInvoice> list = null;
        Integer total = performQueryForObject("invoice.countFindExtendedReservations", parameters);
        if (total == 0) {
            list = Collections.emptyList();
        } else {
            list = performQueryForList("invoice.findExtendedReservations", parameters);
        }
        return new LimitedSearchResult<InternalExtendedInvoice>(criteria, sortBy, limit, offset, list, total);
    }

    private List<SortCriterion> initDefaultSort(List<SortCriterion> sortBy) {
        if (Validator.isEmpty(sortBy)) {
            return Arrays.asList(new SortCriterion("invoiceNumber", true));
        } else {
            return sortBy;
        }
    }
    public LimitedSearchResult<InternalInvoice> findSimple(SearchCriteria<InternalInvoice> criteria, long offset, long limit, List<SortCriterion> sortBy) {
        sortBy = initDefaultSort(sortBy);
        FindParameters parameters = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy);
        List<InternalInvoice> list = null;
        Integer total = performQueryForObject("invoice.countFindInvoices", parameters);
        if (total == 0) {
            list = Collections.emptyList();
        } else {
            list = performQueryForList("invoice.findSimpleInvoices", parameters);
        }
        return new LimitedSearchResult<InternalInvoice>(criteria, sortBy, limit, offset, list, total);
    }


}
