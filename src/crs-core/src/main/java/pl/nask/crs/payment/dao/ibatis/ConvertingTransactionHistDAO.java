package pl.nask.crs.payment.dao.ibatis;

import java.util.List;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.TransactionHistDAO;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ConvertingTransactionHistDAO extends ConvertingGenericDAO<InternalTransaction, Transaction, Long> implements TransactionHistDAO {

    InternalTransactionHistIbatisDAO internalDAO;

    public ConvertingTransactionHistDAO(InternalTransactionHistIbatisDAO internalDAO, Converter<InternalTransaction, Transaction> internalConverter) {
        super(internalDAO, internalConverter);
        this.internalDAO = internalDAO;
    }

    @Override
    public List<DomainInfo> getTransactionInfo(long transactionId) {
        return internalDAO.getTransactionInfo(transactionId);
    }

    @Override
    public List<DomainInfo> getTransactionInfo(String orderId) {
        return internalDAO.getTransactionInfo(orderId);
    }
    
    @Override
    public Transaction getByOrderId(String orderId) {    
    	return getInternalConverter().to(internalDAO.getByOrderId(orderId));
    }
}
