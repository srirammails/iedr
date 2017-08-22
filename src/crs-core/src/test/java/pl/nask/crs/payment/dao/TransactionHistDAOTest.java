package pl.nask.crs.payment.dao;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareHistTransactions;

import javax.annotation.Resource;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransactionHistDAOTest extends AbstractContextAwareTest {

    @Resource
    TransactionHistDAO transactionHistDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    ReservationHistDAO reservationHistDAO;

    @Test
    public void findTest() {
    	String billNh = "APITEST-IEDR";
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH(billNh);
        LimitedSearchResult<Transaction> result = transactionHistDAO.find(criteria, 0, 10, null);

        // should get only transactions with the right BillingNH
        for (Transaction t: result.getResults()) {
        	AssertJUnit.assertEquals(billNh, t.getBillNicHandleId());
        }
        
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals(5, result.getResults().size());
    }

    @Test
    public void createTest() {
        LimitedSearchResult<Transaction> result = transactionHistDAO.find(null, 0, 10, null);
        long histTransactionsCount = result.getTotalResults();

        Transaction transaction = transactionDAO.get(20L);
        transactionHistDAO.create(transaction);

        Transaction historicalTransaction = transactionHistDAO.get(20L);
        compareHistTransactions(transaction, historicalTransaction);

        result = transactionHistDAO.find(null, 0, 10, null);
        AssertJUnit.assertEquals(histTransactionsCount + 1, result.getTotalResults());
        AssertJUnit.assertEquals(histTransactionsCount + 1, (long) result.getResults().size());
    }

    @Test
    public void testGetTransactionInfo() throws Exception {
        List<DomainInfo> domainInfoList = transactionHistDAO.getTransactionInfo(64);
        AssertJUnit.assertEquals(3, domainInfoList.size());
    }

    @Test
    public void testGetTransactionInfoByOrderId() throws Exception {
        List<DomainInfo> domainInfoList = transactionHistDAO.getTransactionInfo("20120621142531-D-1161975");
        AssertJUnit.assertEquals(3, domainInfoList.size());

        domainInfoList = transactionHistDAO.getTransactionInfo("20120621142533-D-8730425");
        AssertJUnit.assertEquals(1, domainInfoList.size());

        domainInfoList = transactionHistDAO.getTransactionInfo("not existing orderId");
        AssertJUnit.assertEquals(0, domainInfoList.size());
    }
    
    @Test
    public void testGetTransactionByOrderId() throws Exception {
    	SearchResult<Transaction> res = transactionHistDAO.find(null);
    	for (Transaction t: res.getResults()) {
    		Transaction transByOrderId = transactionHistDAO.getByOrderId(t.getOrderId());
    		AssertJUnit.assertEquals("TransactionId should be the same", t.getId(), transByOrderId.getId());
    	}
    }
}
