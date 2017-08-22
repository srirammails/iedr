package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ConvertingTransactionDAO extends ConvertingGenericDAO<InternalTransaction, Transaction, Long> implements TransactionDAO{

    InternalTransactionIbatisDAO internalDAO;

    public ConvertingTransactionDAO(InternalTransactionIbatisDAO internalDAO, Converter<InternalTransaction, Transaction> internalConverter) {
        super(internalDAO, internalConverter);
        this.internalDAO = internalDAO;
    }

    @Override
    public long createTransaction(Transaction transaction) {
        return internalDAO.createTransaction(getInternalConverter().from(transaction));
    }

    @Override
    public List<Transaction> getAll() {
        return getInternalConverter().to(internalDAO.getAll());
    }

    @Override
    public List<Transaction> findAllTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy) {
        return getInternalConverter().to(internalDAO.getTransactions(criteria, sortBy));
    }
}
