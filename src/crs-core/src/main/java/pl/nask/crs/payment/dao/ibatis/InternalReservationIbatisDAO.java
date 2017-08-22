package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.SequentialNumberGenerator;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservation;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservationTotals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalReservationIbatisDAO extends GenericIBatisDAO<InternalReservation, Long> {
	private SequentialNumberGenerator idGenerator;

    private Map<String, String> transferredDomainSortMap = new HashMap<String, String>();
    {
        transferredDomainSortMap.put("domainName","domainName");
        transferredDomainSortMap.put("domainHolder","domainHolder");
        transferredDomainSortMap.put("registrationDate","registrationDate");
        transferredDomainSortMap.put("renewalDate","renewalDate");
        transferredDomainSortMap.put("transferDate","transferDate");
    }

    public InternalReservationIbatisDAO() {
        setUpdateQueryId("reservation.updateReservation");
        setDeleteQueryId("reservation.deleteReservationById");
        setLockQueryId("reservation.getLockedReservationById");
        setGetQueryId("reservation.getReservationById");
        setDeleteQueryId("reservation.deleteReservationById");
    }

    public long createReservation(InternalReservation internalReservation) {
    	long id = idGenerator.getNextId();
    	internalReservation.setId(id);
    	performInsert("reservation.insertReservation", internalReservation);
        return id;
    }

    public List<InternalReservation> getAllReservations(SearchCriteria<InternalReservation> criteria) {
        FindParameters findParameters = new FindParameters(criteria);
        return performQueryForList("reservation.findReservations", findParameters);
    }

    public LimitedSearchResult<InternalReservation> getReservations(SearchCriteria<InternalReservation> reservationSearchCriteria, long offset, long limit, List<SortCriterion> sortBy) {
        return performFind("reservation.findReservations", "reservation.countFindReservations", reservationSearchCriteria, offset, limit, sortBy);
    }

	public InternalReservationTotals getTotalsForDeposit(ReservationSearchCriteria criteria) {
		FindParameters params = new FindParameters(criteria);
		return performQueryForObject("reservation.getTotals", params);
	}
	
	public void setIdGenerator(SequentialNumberGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
}
