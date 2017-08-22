package pl.nask.crs.payment.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.DepositInfo;
import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionInfo;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.Vat;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PaymentServiceTest extends AbstractContextAwareTest {

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    PaymentService paymentService;

    @Resource
    TransactionDAO transactionDAO;
    
    @Resource
    DepositService depositService;
    
    OpInfo opInfo = new OpInfo("test");

    @Test
    public void getReservationForTicketIdTest() {
        long transactionId = transactionDAO.createTransaction(Transaction.newInstance(50, 40, 10, "order id", null));
    	Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 45.5, new Vat(4, "B", new Date(), 0.09f)), 1234L, transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
    	reservationDAO.createReservation(reservation1);
    	Reservation res = paymentService.getReservationForTicket(1234L);
        AssertJUnit.assertNotNull("reservation", res);
        AssertJUnit.assertEquals("PaymentMethod", PaymentMethod.CC, res.getPaymentMethod());
        AssertJUnit.assertEquals(transactionId, (long)res.getTransactionId());
    }

    @Test
    public void getReadyReservationTest() {
        Reservation res = paymentService.getReadyReservation("APITEST-IEDR", "createCCDomain.ie");
        AssertJUnit.assertNotNull(res);
        AssertJUnit.assertEquals("createCCDomain.ie", res.getDomainName());
    }

    @Test
    public void getNotReadyReservationTest() {
        Reservation res = paymentService.getNotReadyReservation("APITEST-IEDR", "createDomainRegistrarBasic3.ie");
        AssertJUnit.assertNotNull(res);
        AssertJUnit.assertEquals("createDomainRegistrarBasic3.ie", res.getDomainName());
    }

    @Test(expectedExceptions = TransactionInvalidStateForSettlement.class)
    public void settleTransactionInWrongStateTest() throws Exception {
        paymentService.settleTransaction(null, opInfo, 6);
    }

    @Test
    public void settleTransactionTest() throws Exception {
        long transactionId = 6;
        DepositInfo depositBeforeSettlement = depositService.viewDeposit("APITEST-IEDR");

        paymentService.setTransactionStartedSettlement(transactionId);
        paymentService.settleTransaction(null, opInfo, transactionId);

        Transaction transaction = transactionDAO.get(transactionId);
        AssertJUnit.assertTrue(transaction.isSettlementEnded());

        DepositInfo depositAfterSettlement = depositService.viewDeposit("APITEST-IEDR");

        AssertJUnit.assertEquals(BigDecimal.valueOf(206.38), depositBeforeSettlement.getReservedFunds());
        AssertJUnit.assertEquals(2594, depositBeforeSettlement.getCloseBal(), 0.0001);
        AssertJUnit.assertEquals(BigDecimal.valueOf(127.47), depositAfterSettlement.getReservedFunds());
        AssertJUnit.assertEquals(2515.09, depositAfterSettlement.getCloseBal(), 0.0001);
        AssertJUnit.assertEquals(depositBeforeSettlement.getCloseBal() - MoneyUtils.substract(depositBeforeSettlement.getReservedFunds(), depositAfterSettlement.getReservedFunds()).doubleValue(), depositAfterSettlement.getCloseBal(), 0.0001);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void invalidateTransactionInvalidStateTest() throws Exception {
        int transactionId = 1;
        paymentService.invalidateTransactionsIfNeeded(null, transactionId);
    }
//TODO: CRS-72
//    @Test
//    public void invalidateTransactionTest() throws Exception {
//        long transactionId = 3;
//
//        Transaction transactionBefore = transactionDAO.get(transactionId);
//        AssertJUnit.assertFalse(transactionBefore.isInvalidated());
//        Assert.assertNull(transactionBefore.getInvalidatedDate());
//
//        paymentService.invalidateTransactionsIfNeeded(transactionId);
//
//        Transaction transactionAfter = transactionDAO.get(transactionId);
//        AssertJUnit.assertTrue(transactionAfter.isInvalidated());
//        Assert.assertNotNull(transactionAfter.getInvalidatedDate());
//    }

    @Test
    public void getReadyTransactionsReportTest() throws Exception {
        List<TransactionInfo> reports = paymentService.getReadyADPTransactionsReport("APITEST-IEDR");
        AssertJUnit.assertEquals(3, reports.size());
        AssertJUnit.assertEquals(2545.44, reports.get(0).getAvailableDepositBalance().doubleValue());
        AssertJUnit.assertEquals(2466.53, reports.get(1).getAvailableDepositBalance().doubleValue());
    }

    @Test
    public void findDepositsTest() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("APITEST-IEDR");
        LimitedSearchResult<DepositInfo> searchResult = depositService.findDeposits(criteria, 0, 10, null);
        AssertJUnit.assertEquals(1, searchResult.getTotalResults());
        AssertJUnit.assertEquals(1, searchResult.getResults().size());
        AssertJUnit.assertEquals(BigDecimal.valueOf(206.38), searchResult.getResults().get(0).getReservedFunds());
    }
}
