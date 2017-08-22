package pl.nask.crs.payment.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.ReservationTotals;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface ReservationDAO extends GenericDAO<Reservation, Long> {

    long createReservation(Reservation reservation);

    List<Reservation> getAllReservations(SearchCriteria<Reservation> criteria);

    LimitedSearchResult<Reservation> getReservations(SearchCriteria<Reservation> criteria, long offset, long limit, List<SortCriterion> sortBy);

	ReservationTotals getTotals(ReservationSearchCriteria criteria);

}
