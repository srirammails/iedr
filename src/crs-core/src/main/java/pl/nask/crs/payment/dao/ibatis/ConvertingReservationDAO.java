package pl.nask.crs.payment.dao.ibatis;

import java.util.List;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.ReservationTotals;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.ibatis.converters.ReservationTotalConverter;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservation;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservationTotals;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ConvertingReservationDAO extends ConvertingGenericDAO<InternalReservation, Reservation, Long> implements ReservationDAO {

    InternalReservationIbatisDAO internalDAO;

    public ConvertingReservationDAO(InternalReservationIbatisDAO internalDAO, Converter<InternalReservation, Reservation> internalConverter) {
        super(internalDAO, internalConverter);
        Validator.assertNotNull(internalDAO, "internal dao");
        Validator.assertNotNull(internalConverter, "internal converter");
        this.internalDAO = internalDAO;
    }

    @Override
    public long createReservation(Reservation reservation) {
        return internalDAO.createReservation(getInternalConverter().from(reservation));
    }

    @Override
    public List<Reservation> getAllReservations(SearchCriteria<Reservation> criteria) {
        return getInternalConverter().to(internalDAO.getAllReservations((SearchCriteria)criteria));
    }

    @Override
    public LimitedSearchResult<Reservation> getReservations(SearchCriteria<Reservation> criteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalReservation> res = internalDAO.getReservations((SearchCriteria)criteria, offset, limit, sortBy);
        List<Reservation> reservations = getInternalConverter().to(res.getResults());
        return new LimitedSearchResult<Reservation>(criteria, sortBy, res.getLimit(), res.getOffset(), reservations, res.getTotalResults());
    }
    
    @Override
    public ReservationTotals getTotals(ReservationSearchCriteria criteria) {
        Converter<InternalReservationTotals, ReservationTotals> converter = new ReservationTotalConverter();
    	return converter.to(internalDAO.getTotalsForDeposit(criteria));
    }
}
