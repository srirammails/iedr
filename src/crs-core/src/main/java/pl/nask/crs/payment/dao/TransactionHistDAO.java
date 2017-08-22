package pl.nask.crs.payment.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.Transaction;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface TransactionHistDAO extends GenericDAO<Transaction, Long> {

    List<DomainInfo> getTransactionInfo(long transactionId);

    List<DomainInfo> getTransactionInfo(String orderId);

	Transaction getByOrderId(String orderId);
}
