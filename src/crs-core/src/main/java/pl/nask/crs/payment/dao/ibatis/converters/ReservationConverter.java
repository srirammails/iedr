package pl.nask.crs.payment.dao.ibatis.converters;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservation;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.Vat;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReservationConverter extends AbstractConverter<InternalReservation, Reservation> {

	private GenericDAO<InternalDomain, String> domainDao;

	public ReservationConverter(GenericDAO<InternalDomain, String> domainDao) {
		this.domainDao = domainDao;
	}

    @Override
    protected Reservation _to(InternalReservation internalReservation) {
    	PriceWithVat price = new PriceWithVat(
                Period.fromMonths(internalReservation.getDurationMonths()),
                internalReservation.getProductCode(),
                internalReservation.getAmount(),
                new Vat(internalReservation.getVatId(),
                        internalReservation.getVatCategory(),
                        internalReservation.getVatFromDate(),
                        internalReservation.getVatRate()));
        InternalDomain domain = domainDao.get(internalReservation.getDomainName());
        Reservation res = new Reservation(
                    internalReservation.getId(),
                    internalReservation.getNicHandleId(),
                    internalReservation.getDomainName(),
                    internalReservation.getDurationMonths(),
                    internalReservation.getCreationDate(),
                    price,
                    internalReservation.isReadyForSettlement(),
                    internalReservation.isSettled(),
                    internalReservation.getSettledDate(),
                    internalReservation.getTicketId(),
                    internalReservation.getTransactionId(),
                    internalReservation.getOperationType(),
                    internalReservation.getPaymentMethod(),
                    internalReservation.getInvoiceNumber(),
                    internalReservation.getOrderId(),
                    internalReservation.getFinancialStatus(),
                    (domain != null)
            );
		
		res.setEndDate(internalReservation.getEndDate());
		res.setStartDate(internalReservation.getStartDate());
	
		return res;
    }

    @Override
    protected InternalReservation _from(Reservation reservation) {
        InternalReservation res = new InternalReservation(
                reservation.getId(),
                reservation.getNicHandleId(),
                reservation.getDomainName(),
                reservation.getDurationMonths(),
                reservation.getCreationDate(),
                reservation.getProductCode(),
                reservation.getNetAmount().doubleValue(),
                reservation.getVatId(),
                reservation.getVatAmount().doubleValue(),
                reservation.isReadyForSettlement(),
                reservation.isSettled(),
                reservation.getSettledDate(),
                reservation.getTicketId(),
                reservation.getTransactionId(),
                reservation.getOperationType(),
                reservation.getPaymentMethod(),
                reservation.getInvoiceNumber()
        );
        
        res.setStartDate(reservation.getStartDate());
        res.setEndDate(reservation.getEndDate());
        return res;
    }
}
