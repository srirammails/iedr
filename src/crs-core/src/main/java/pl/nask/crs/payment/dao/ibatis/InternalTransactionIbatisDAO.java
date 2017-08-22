package pl.nask.crs.payment.dao.ibatis;

import java.util.List;

import pl.nask.crs.commons.SequentialNumberGenerator;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.TransactionSearchCriteria;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalTransactionIbatisDAO extends GenericIBatisDAO<InternalTransaction, Long> {
	private SequentialNumberGenerator idGenerator;

    public InternalTransactionIbatisDAO() {
        setGetQueryId("transaction.selectTransactionById");
        setLockQueryId("transaction.selectTransactionLockedById");
        setUpdateQueryId("transaction.updateTransaction");
        setDeleteQueryId("transaction.deleteTransactionById");
        setCountFindQueryId("transaction.countFindTransactions");
        setFindQueryId("transaction.findTransactions");
    }

    public long createTransaction(InternalTransaction internalTransaction) {
    	long id = idGenerator.getNextId();
    	internalTransaction.setId(id);
        performInsert("transaction.insertTransaction", internalTransaction);
        return id;
    }

    public List<InternalTransaction> getAll() {
        return performQueryForList("transaction.selectAll");
    }

    public List<InternalTransaction> getTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy) {
        FindParameters findParameters = new FindParameters(criteria).setOrderBy(sortBy);
        return performQueryForList("transaction.getTransactions", findParameters);
    }

    public void setIdGenerator(SequentialNumberGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
}
