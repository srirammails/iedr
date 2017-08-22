package pl.nask.crs.payment.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.payment.ReservationTotals;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservationTotals;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class ReservationTotalConverter extends AbstractConverter<InternalReservationTotals, ReservationTotals> {
    @Override
    protected ReservationTotals _to(InternalReservationTotals internalReservationTotals) {
        return new ReservationTotals(internalReservationTotals.getTotalResults(),
                internalReservationTotals.getTotalAmount(),
                internalReservationTotals.getTotalVat());
    }

    @Override
    protected InternalReservationTotals _from(ReservationTotals reservationTotals) {
        return null;
    }
}
