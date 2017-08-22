package pl.nask.crs.app.invoicing;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;
import pl.nask.crs.payment.dao.InvoiceDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.dao.TransactionHistDAO;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/application-services-config.xml", "/application-services-test-config.xml"})
public class InvoicingAppServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    InvoicingAppService invoicingAppService;

    @Resource
    InvoiceDAO invoiceDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TransactionHistDAO transactionHistDAO;

    @Test
    public void invoicingTest() {
        preAssert();

        invoicingAppService.runInvoicing(null);

        postAssert();
    }

    private void preAssert() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setSettlementEnded(true);
        List<Transaction> settledTransactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(0, settledTransactions.size());
        LimitedSearchResult<Transaction> historicalTransactions = transactionHistDAO.find(null, 0, 10, null);
        AssertJUnit.assertEquals(7, historicalTransactions.getTotalResults());

        List<Invoice> invoices = invoiceDAO.getAll();
        AssertJUnit.assertEquals(5, invoices.size());
    }

    private void postAssert() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setSettlementEnded(true);
        List<Transaction> settledTransactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(0, settledTransactions.size());
        LimitedSearchResult<Transaction> historicalTransactions = transactionHistDAO.find(null, 0, 20, null);
        AssertJUnit.assertEquals(11, historicalTransactions.getTotalResults());

        List<Invoice> invoices = invoiceDAO.getAll();
        AssertJUnit.assertEquals(7, invoices.size());

        Invoice invoice = invoices.get(5);
        assertInvoiceInProperState(invoice);
    }

    private void assertInvoiceInProperState(Invoice invoice) {
        int invoiceId = invoice.getId();
        AssertJUnit.assertEquals("INV0000001", invoice.getInvoiceNumber());
        AssertJUnit.assertEquals(666, invoice.getAccountNumber());
        AssertJUnit.assertTrue(invoice.isCompleted());
        AssertJUnit.assertEquals(20638, (int)invoice.getTotalCost());
        AssertJUnit.assertEquals(17000, (int)invoice.getTotalNetAmount());
        AssertJUnit.assertEquals(3638, (int)invoice.getTotalVatAmount());
        AssertJUnit.assertEquals(3, invoice.getTransactions().size());

        for (Transaction transaction: invoice.getTransactions()) {
            AssertJUnit.assertTrue(transaction.isFinanciallyPassed());
            AssertJUnit.assertTrue(transaction.isSettlementStarted());
            AssertJUnit.assertTrue(transaction.isSettlementEnded());
            AssertJUnit.assertFalse(transaction.isCancelled());
            AssertJUnit.assertEquals(invoiceId, (int)transaction.getInvoiceId());

            long transactionId = transaction.getId();
            for (Reservation reservation : transaction.getReservations()) {
                AssertJUnit.assertTrue(reservation.isReadyForSettlement());
                AssertJUnit.assertTrue(reservation.isSettled());
                AssertJUnit.assertEquals(transactionId, (long)reservation.getTransactionId());
            }
        }
    }

    @Test
    public void invalidateTransactionsTest() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setInvalidated(true);
        List<Transaction> transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(0, transactions.size());

        invoicingAppService.runTransactionInvalidation(null);

        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(3, transactions.size());
    }
}
