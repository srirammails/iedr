package pl.nask.crs.payment.testhelp;

import org.apache.commons.lang.time.DateUtils;
import pl.nask.crs.payment.*;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * @author: Marcin Tkaczyk
 */
public class PaymentTestHelp {

    public static void comapareDeposit(Deposit expected, Deposit actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.getNicHandleId(), actual.getNicHandleId());
        assertEquals(expected.getOpenBal(), actual.getOpenBal(), 0.0001);
        assertEquals(expected.getCloseBal(), actual.getCloseBal(), 0.0001);
        assertEquals(expected.getOrderId(), actual.getOrderId());
        assertEquals(expected.getTransactionAmount(), actual.getTransactionAmount(), 0.0001);
        assertEquals(expected.getTransactionType(), actual.getTransactionType());
        assertEquals(expected.getCorrectorNH(), actual.getCorrectorNH());
        assertEquals(expected.getRemark(), actual.getRemark());
        compareDate(expected.getTransactionDate(), actual.getTransactionDate());
    }

    public static void compareReservation(Reservation expected, Reservation actual) {
        assertNotNull(actual);
        assertNotNull(expected);
        assertEquals(expected.getNicHandleId(), actual.getNicHandleId());
        assertEquals(expected.getDomainName(), actual.getDomainName());
        assertEquals(expected.getDurationMonths(), actual.getDurationMonths());
        compareDate(expected.getCreationDate(), actual.getCreationDate());
        assertEquals(expected.getProductCode(), actual.getProductCode());
        assertEquals(0, expected.getNetAmount().compareTo(actual.getNetAmount()));
        assertEquals(expected.getVatCategory(), actual.getVatCategory());
        assertEquals(0, Double.compare(expected.getVatRate(), actual.getVatRate()));
        assertEquals(0, expected.getVatAmount().compareTo(actual.getVatAmount()));
        assertEquals(expected.getTicketId(), actual.getTicketId());
        assertEquals(expected.isReadyForSettlement(), actual.isReadyForSettlement());
        assertEquals(expected.getPaymentMethod(), actual.getPaymentMethod());
        assertEquals(expected.getTransactionId(), actual.getTransactionId());
        assertEquals(expected.getOperationType(), actual.getOperationType());
        assertEquals(expected.getVatId(), actual.getVatId());
        assertEquals(expected.getVatRate(), actual.getVatRate(), 0.0001);
        assertEquals(expected.getVatAmount(), actual.getVatAmount());
        assertEquals(expected.getVatCategory(), actual.getVatCategory());

    }

    public static void compareNewReservation(Reservation actual, Reservation expected) {
        compareReservation(expected, actual);
        assertFalse(actual.isSettled());
        assertNull(actual.getSettledDate());
    }

    public static void compareSettledReservation(Reservation actual, Reservation expected) {
        compareReservation(expected, actual);
        assertTrue(actual.isSettled());
        assertNotNull(actual.getSettledDate());
        compareDate(expected.getSettledDate(), actual.getSettledDate());
    }

    public static void compareNewReadyForSettlement(Reservation actual, Reservation expected) {
        compareNewReservation(actual, expected);
        assertTrue(actual.isReadyForSettlement());
    }

    //MYSQL missing miliseconds
    private static void compareDate(Date expected, Date actual) {
        if (expected == null) {
            assertNull(actual);
        } else {
            if (actual == null)
                assert false;

            assertEquals(DateUtils.truncate(expected, Calendar.SECOND), actual);
        }
    }

    public static void compareHistTransactions(Transaction expected, Transaction actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getInvoiceId(), actual.getInvoiceId());
        assertEquals(expected.getTotalCost(), actual.getTotalCost());
        assertEquals(expected.getTotalNetAmount(), actual.getTotalNetAmount());
        assertEquals(expected.getTotalVatAmount(), actual.getTotalVatAmount());
        assertEquals(expected.isSettlementStarted(), actual.isSettlementStarted());
        assertEquals(expected.isSettlementEnded(), actual.isSettlementEnded());
        assertEquals(expected.isCancelled(), actual.isCancelled());
        compareDate(expected.getCancelledDate(), actual.getCancelledDate());
        assertEquals(expected.getOrderId(), actual.getOrderId());
        assertEquals(expected.isInvalidated(), actual.isInvalidated());
        compareDate(expected.getInvalidatedDate(), actual.getInvalidatedDate());
        assertEquals(expected.getReauthorisedId(), actual.getReauthorisedId());
    }


    public static void compareTransactions(Transaction expected, Transaction actual) {
        compareHistTransactions(expected, actual);
        if (expected.getReservations() != null) {
            assertEquals(expected.getReservations().size(), actual.getReservations().size());
        }
    }

    public static void compareInvoices(Invoice expected, Invoice actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getInvoiceNumber(), actual.getInvoiceNumber());
        assertEquals(expected.getAccountName(), actual.getAccountName());
        assertEquals(expected.getAccountNumber(), actual.getAccountNumber());
        assertEquals(expected.getAddress1(), actual.getAddress1());
        assertEquals(expected.getAddress2(), actual.getAddress2());
        assertEquals(expected.getAddress3(), actual.getAddress3());
        assertEquals(expected.getBillingNicHandle(), actual.getBillingNicHandle());
        assertEquals(expected.getCountry(), actual.getCountry());
        assertEquals(expected.getCounty(), actual.getCounty());
        assertEquals(expected.getCrsVersion(), actual.getCrsVersion());
        assertEquals(expected.getMD5(), actual.getMD5());
        compareDate(expected.getInvoiceDate(), actual.getInvoiceDate());
        if (expected.getTransactions() != null) {
            assertEquals(expected.getTransactions().size(), actual.getTransactions().size());
        }
        assertEquals(expected.getTotalCost(), actual.getTotalCost());
        assertEquals(expected.getTotalNetAmount(), actual.getTotalNetAmount());
        assertEquals(expected.getTotalVatAmount(), actual.getTotalVatAmount());
    }
}
