package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.dao.ReservationHistDAO;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservation;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ConvertingReservationHistDAO extends ConvertingGenericDAO<InternalReservation, Reservation, Long> implements ReservationHistDAO {

    InternalReservationHistIbatisDAO internalDAO;

    public ConvertingReservationHistDAO(InternalReservationHistIbatisDAO internalDAO, Converter<InternalReservation, Reservation> internalConverter) {
        super(internalDAO, internalConverter);
        this.internalDAO = internalDAO;
    }
}
