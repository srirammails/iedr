package pl.nask.crs.payment.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface TransactionDAO extends GenericDAO<Transaction, Long>{

    long createTransaction(Transaction transaction);

    List<Transaction> getAll();

    List<Transaction> findAllTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy);

}
