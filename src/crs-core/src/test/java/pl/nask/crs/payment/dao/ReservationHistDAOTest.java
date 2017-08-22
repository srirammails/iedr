package pl.nask.crs.payment.dao;

import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareReservation;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReservationHistDAOTest extends AbstractContextAwareTest {

    @Resource
    ReservationHistDAO reservationHistDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TransactionHistDAO transactionHistDAO;

    @Test
    public void createTest() {
        Transaction transaction = transactionDAO.get(20L);
        transactionHistDAO.create(transaction);

        LimitedSearchResult<Reservation> result = reservationHistDAO.find(null, 0, 20, null);
        long reservationHistCount = result.getTotalResults(); 

        Reservation reservation = reservationDAO.get(8L);      
        reservationHistDAO.create(reservation);

        Reservation historicalReservation = reservationHistDAO.get(8L);
        compareReservation(reservation, historicalReservation);

        result = reservationHistDAO.find(null, 0, 20, null);
        AssertJUnit.assertEquals(reservationHistCount + 1, result.getTotalResults());
        AssertJUnit.assertEquals(reservationHistCount + 1, (long) result.getResults().size());
    }
    
}
