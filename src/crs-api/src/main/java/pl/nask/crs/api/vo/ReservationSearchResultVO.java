package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.Reservation;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationSearchResultVO {

    private long total;
    private List<ReservationVO> results = new ArrayList<ReservationVO>();


    public ReservationSearchResultVO() {
    }

    public ReservationSearchResultVO(LimitedSearchResult<Reservation> results) {
        this.total = results.getTotalResults();
        for (Reservation r : results.getResults()) {
            this.results.add(new ReservationVO(r));
        }
    }

    public List<ReservationVO> getResults() {
        return results;
    }
}
