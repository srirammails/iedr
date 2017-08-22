package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalTransactionHistIbatisDAO extends GenericIBatisDAO<InternalTransaction, Long> {

    public InternalTransactionHistIbatisDAO() {
        setCreateQueryId("transaction-hist.createHistTransaction");
        setGetQueryId("transaction-hist.selectHistTransactionById");
        setCountFindQueryId("transaction-hist.countFindHistTransactions");
        setFindQueryId("transaction-hist.findHistTransactions");
    }

    public List<DomainInfo> getTransactionInfo(long transactionId) {
        return performQueryForList("transaction-hist.getTransactionInfo", transactionId);
    }

    public List<DomainInfo> getTransactionInfo(String orderId) {
        return performQueryForList("transaction-hist.getTransactionInfoByOrderId", orderId);
    }

	public InternalTransaction getByOrderId(String orderId) {
		return performQueryForObject("transaction-hist.selectHistTransactionByOrderId", orderId);
	}
}
