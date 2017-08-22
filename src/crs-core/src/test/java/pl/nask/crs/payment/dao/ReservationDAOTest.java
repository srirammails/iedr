package pl.nask.crs.payment.dao;

import org.apache.commons.lang.time.DateUtils;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareNewReadyForSettlement;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareNewReservation;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareSettledReservation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.ReservationTotals;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.Vat;
/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReservationDAOTest extends AbstractContextAwareTest {

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    TransactionDAO transactionDAO;

    private long transactionId;

    @BeforeMethod
	public void initTransaction() {
        transactionId = transactionDAO.createTransaction(Transaction.newInstance(null, null, true, false, 50, 40, 10, "order id", null));
    }

    @Test
    public void instertReservationTest() {
        final Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 65, new Vat(1, "A", aDate, 0.19f)), 1234L, transactionId, OperationType.REGISTRATION, PaymentMethod.ADP);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 45.5, new Vat(4, "B", aDate, 0.09f)), 1234L, transactionId, OperationType.REGISTRATION, PaymentMethod.ADP);
        Reservation reservation3 = Reservation.newInstanceForTicket("Test2-IEDR", "testDomain3.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 35.5, new Vat(9, "C", aDate, 0.316f)), 1234L, transactionId, OperationType.REGISTRATION, PaymentMethod.ADP);
        Reservation reservation4 = Reservation.newInstanceForGIBORegistration("Test2-IEDR", "testDomain4.ie", new PriceWithVat(Period.fromYears(1), "Std1Year", 35.5, new Vat(9, "C", aDate, 0.316f)), transactionId, PaymentMethod.ADP);
        long r1 = reservationDAO.createReservation(reservation1);
        long r2 = reservationDAO.createReservation(reservation2);
        long r3 = reservationDAO.createReservation(reservation3);
        long r4 = reservationDAO.createReservation(reservation4);
        AssertJUnit.assertTrue(r1 < r2);
        AssertJUnit.assertTrue(r2 < r3);
        AssertJUnit.assertTrue(r3 < r4);
        List<Reservation> reservationsFromDB1 = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB1.size());
        compareNewReservation(reservationsFromDB1.get(0), reservation1);
        compareNewReservation(reservationsFromDB1.get(1), reservation2);

        List<Reservation> reservationsFromDB2 = getAllReservationsForBillingNH("Test2-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB2.size());
        compareNewReservation(reservationsFromDB2.get(0), reservation3);
        compareNewReservation(reservationsFromDB2.get(1), reservation4);
    }

    private List<Reservation> getAllReservationsForBillingNH(String billingNH) {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newInstance();
        criteria.setBillingNH(billingNH);
        return reservationDAO.getAllReservations(criteria);
    }

    @Test
    public void insertReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 65, new Vat(1, "A", new Date(), 0.19f)), 1234L,  transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 45.5, new Vat(4, "B", new Date(), 0.09f)), 1234L,  transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation3 = Reservation.newInstanceForTicket("Test2-IEDR", "testDomain3.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 35.5, new Vat(9, "C", new Date(), 0.316f)), 1234L,  transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation4 = Reservation.newInstanceForGIBORegistration("Test2-IEDR", "testDomain4.ie", new PriceWithVat(Period.fromYears(1), "Std1Year", 35.5, new Vat(9, "C", new Date(), 0.316f)),  transactionId, PaymentMethod.CC);
        reservationDAO.createReservation(reservation1);
        reservationDAO.createReservation(reservation2);
        reservationDAO.createReservation(reservation3);
        reservationDAO.createReservation(reservation4);

        List<Reservation> reservationsFromDB1 = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB1.size());
        compareNewReservation(reservationsFromDB1.get(0), reservation1);
        compareNewReservation(reservationsFromDB1.get(1), reservation2);

        List<Reservation> reservationsFromDB2 = getAllReservationsForBillingNH("Test2-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB2.size());
        compareNewReservation(reservationsFromDB2.get(0), reservation3);
        compareNewReservation(reservationsFromDB2.get(1), reservation4);
    }

    @Test
    public void settleReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 55.5, new Vat(1, "A", new Date(), 0.19f)), 1234L,  transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 45.5, new Vat(4, "B", new Date(), 0.09f)), 1234L,  transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
        reservationDAO.createReservation(reservation1);
        reservationDAO.createReservation(reservation2);

        List<Reservation> reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());
        compareNewReservation(reservationsFromDB.get(0), reservation1);
        compareNewReservation(reservationsFromDB.get(1), reservation2);

        Date settledDate = DateUtils.setMilliseconds(new Date(), 999);
        reservation1 = reservationsFromDB.get(0);
        reservation2 = reservationsFromDB.get(1);
        reservation1.markSettled(settledDate);
        reservation2.markSettled(settledDate);
        reservationDAO.update(reservation1);
        reservationDAO.update(reservation2);

        reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());
        compareSettledReservation(reservationsFromDB.get(0), reservation1);
        compareSettledReservation(reservationsFromDB.get(1), reservation2);
    }

    @Test
    public void setReadyForSettlementTest() {
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 55.5, new Vat(1, "A", new Date(), 0.19f)), 1234L, transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 45.5, new Vat(4, "B", new Date(), 0.09f)), 1234L, transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
        reservationDAO.createReservation(reservation1);
        reservationDAO.createReservation(reservation2);

        List<Reservation> reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());
        compareNewReservation(reservationsFromDB.get(0), reservation1);
        compareNewReservation(reservationsFromDB.get(1), reservation2);

        reservation1 = (Reservation)reservationsFromDB.get(0);
        reservation2 = (Reservation)reservationsFromDB.get(1);
        reservation1.setReadyForSettlement(true);
        reservation2.setReadyForSettlement(true);
        reservationDAO.update(reservation1);
        reservationDAO.update(reservation2);

        reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());
        compareNewReadyForSettlement(reservationsFromDB.get(0), reservation1);
        compareNewReadyForSettlement(reservationsFromDB.get(1), reservation2);
    }

    @Test
    public void deleteReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 55.5, new Vat(1, "A", new Date(), 0.19f)), 1234L,  transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1, new PriceWithVat(Period.fromYears(1), "Std1Year", 45.5, new Vat(4, "B", new Date(), 0.09f)), 1234L,  transactionId, OperationType.REGISTRATION, PaymentMethod.CC);
        reservationDAO.createReservation(reservation1);
        reservationDAO.createReservation(reservation2);

        List<Reservation> reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());

        reservationDAO.deleteById(reservationsFromDB.get(0).getId());
        reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(1, reservationsFromDB.size());
    }

    @Test
    public void getReservationTest() {
        List<Reservation> allReservations = getAllReservationsForBillingNH("APITEST-IEDR");
        AssertJUnit.assertEquals(9, allReservations.size());

        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(true);
        criteria.setBillingNH("APITEST-IEDR");
        List<Reservation> readyReservations = reservationDAO.getReservations(criteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(4, readyReservations.size());

        criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        criteria.setBillingNH("APITEST-IEDR");
        List<Reservation> notReadyReservations = reservationDAO.getReservations(criteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(5, notReadyReservations.size());
    }

    @Test
    public void lockTest() {
        AssertJUnit.assertTrue(reservationDAO.lock(1L));
        Reservation reservation = reservationDAO.get(1L);
        AssertJUnit.assertNotNull(reservation);
        AssertJUnit.assertEquals("createDomainRegistrarBasic.ie", reservation.getDomainName());
    }

    @Test
    public void insertRenewalReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForRenewal("Test1-IEDR", "testDomain1.ie", 12, new PriceWithVat(Period.fromYears(1), "Std1Year", 65, new Vat(1, "A", new Date(), 0.19f)), 1, PaymentMethod.ADP);
        long r1 = reservationDAO.createReservation(reservation1);
        List<Reservation> reservationsFromDB1 = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(1, reservationsFromDB1.size());
        compareNewReservation(reservationsFromDB1.get(0), reservation1);
    }
//TODO: CRS-72
//    @Test(expectedExceptions = Exception.class)
//    public void insertRenewalReservationWithWrongTransactionTest() {
//        Reservation reservation1 = Reservation.newInstanceForRenewal("Test1-IEDR", "testDomain1.ie", 12, new PriceWithVat(Period.fromYears(1), "Std1Year", 65, new Vat(1, "A", new Date(), 0.19f)), 666, PaymentMethod.ADP);
//        long r1 = reservationDAO.createReservation(reservation1);
//    }

    @Test
    public void insertCCRenewalReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForRenewal("Test1-IEDR", "testDomain1.ie", 12, new PriceWithVat(Period.fromYears(1), "Std1Year", 65, new Vat(1, "A", new Date(), 0.19f)), 1, PaymentMethod.CC);
        long r1 = reservationDAO.createReservation(reservation1);
        List<Reservation> reservationsFromDB1 = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(1, reservationsFromDB1.size());
        compareNewReservation(reservationsFromDB1.get(0), reservation1);
    }
//TODO: CRS-72
//    @Test(expectedExceptions = Exception.class)
//    public void inserCCtRenewalReservationWithWrongTransactionTest() {
//        Reservation reservation1 = Reservation.newInstanceForRenewal("Test1-IEDR", "testDomain1.ie", 12, new PriceWithVat(Period.fromYears(1), "Std1Year", 65, new Vat(1, "A", new Date(), 0.19f)), 666, PaymentMethod.CC);
//        long r1 = reservationDAO.createReservation(reservation1);
//    }

    @Test
    public void getReservationsWithCriteriaTest() {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        LimitedSearchResult<Reservation> result = null;

        result = reservationDAO.getReservations(null, 0, 10, null);
        AssertJUnit.assertEquals(9, result.getTotalResults());
        AssertJUnit.assertEquals(9, result.getResults().size());

        criteria.setBillingNH("APITEST-IEDR");
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals(5, result.getResults().size());
        for (Reservation reservation : result.getResults()) {
            AssertJUnit.assertFalse(reservation.isReadyForSettlement());
        }

        criteria = ReservationSearchCriteria.newReadyForSettlementInstance(true);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(4, result.getTotalResults());
        AssertJUnit.assertEquals(4, result.getResults().size());
        for (Reservation reservation : result.getResults()) {
            AssertJUnit.assertTrue(reservation.isReadyForSettlement());
        }

        criteria = ReservationSearchCriteria.newInstance();
        criteria.setPaymentMethod(PaymentMethod.ADP);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(5, result.getTotalResults());

        criteria = ReservationSearchCriteria.newInstance();
        criteria.setOperationType(OperationType.REGISTRATION);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(7, result.getTotalResults());

        criteria = ReservationSearchCriteria.newInstance();
        criteria.setCancelled(true);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(0, result.getTotalResults());

        criteria = ReservationSearchCriteria.newInstance();
        criteria.setCancelled(false);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(9, result.getTotalResults());
    }

    @Test
    public void getReservationsWithLimitTest() {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        LimitedSearchResult<Reservation> result = null;

        criteria.setBillingNH("APITEST-IEDR");
        result = reservationDAO.getReservations(criteria, 0, 2, null);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals(2, result.getResults().size());

        criteria.setBillingNH("APITEST-IEDR");
        result = reservationDAO.getReservations(criteria, 2, 10, null);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals(3, result.getResults().size());
    }
    
    @Test 
    public void getReservationTotalsTest() {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(true);
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setPaymentMethod(PaymentMethod.ADP);

        ReservationTotals result = reservationDAO.getTotals(criteria);
        AssertJUnit.assertNotNull(result);
        AssertJUnit.assertEquals(3, result.getTotalResults());
        AssertJUnit.assertEquals(0, result.getTotalAmount().compareTo(BigDecimal.valueOf(170)));
        AssertJUnit.assertEquals(0, result.getTotalVat().compareTo(BigDecimal.valueOf(36.38)));

        criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setPaymentMethod(PaymentMethod.ADP);

        result = reservationDAO.getTotals(criteria);
        AssertJUnit.assertNotNull(result);
        AssertJUnit.assertEquals(2, result.getTotalResults());
        AssertJUnit.assertEquals(0, result.getTotalAmount().compareTo(BigDecimal.valueOf(130)));
        AssertJUnit.assertEquals(0, result.getTotalVat().compareTo(BigDecimal.valueOf(27.82)));

        criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        criteria.setBillingNH("X");
        result = reservationDAO.getTotals(criteria);
        AssertJUnit.assertNotNull(result);
        AssertJUnit.assertEquals(0, result.getTotalResults());
        AssertJUnit.assertEquals(0, result.getTotalAmount().compareTo(BigDecimal.ZERO));
        AssertJUnit.assertEquals(0, result.getTotalVat().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void getReservationsWithSortTest() {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        LimitedSearchResult<Reservation> result = null;
        criteria.setBillingNH("APITEST-IEDR");
        List<SortCriterion> sortBy = null;

        sortBy = Arrays.asList(new SortCriterion("domainName", true));
        result = reservationDAO.getReservations(criteria, 0, 10, sortBy);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals("1registerDomainCC.ie", result.getResults().get(0).getDomainName());
        AssertJUnit.assertEquals("createCCDomainTechPassed.ie", result.getResults().get(1).getDomainName());
        AssertJUnit.assertEquals("createDomainRegistrarBasic2.ie", result.getResults().get(2).getDomainName());

        sortBy = Arrays.asList(new SortCriterion("domainName", false));
        result = reservationDAO.getReservations(criteria, 0, 10, sortBy);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals("registerCCDomainForTripplePass.ie", result.getResults().get(0).getDomainName());
        AssertJUnit.assertEquals("createDomainRegistrarBasic3.ie", result.getResults().get(1).getDomainName());
        AssertJUnit.assertEquals("createDomainRegistrarBasic2.ie", result.getResults().get(2).getDomainName());
    }

    @Test
    public void getReadyReservationsByTransactionIdTest() {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(true);
        criteria.setTransactionId(1);
        List<Reservation> res = reservationDAO.getReservations(criteria, 0, 10 ,null).getResults();
        AssertJUnit.assertEquals(1, res.size());
    }

    @Test
    public void deleteTest() {
        LimitedSearchResult<Reservation> result = reservationDAO.getReservations(null, 0, 20, null);
        AssertJUnit.assertEquals(9, result.getTotalResults());
        AssertJUnit.assertEquals(9, result.getResults().size());

        Reservation reservation = reservationDAO.get(1L);
        AssertJUnit.assertNotNull(reservation);

        reservationDAO.deleteById(1L);

        result = reservationDAO.getReservations(null, 0, 20, null);
        AssertJUnit.assertEquals(8, result.getTotalResults());
        AssertJUnit.assertEquals(8, result.getResults().size());

        reservation = reservationDAO.get(1L);
        AssertJUnit.assertNull(reservation);
    }
}
