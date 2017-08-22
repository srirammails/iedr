package pl.nask.crs.payment.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservation;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransactionConverter extends AbstractConverter<InternalTransaction, Transaction> {

    private ReservationConverter reservationConverter;

    public TransactionConverter(ReservationConverter reservationConverter) {
		this.reservationConverter = reservationConverter;
	}

    @Override
    protected Transaction _to(InternalTransaction internalTransaction) {
        List<Reservation> reservations = reservationConverter.to(internalTransaction.getReservations());
        return new Transaction(internalTransaction.getId(),
                internalTransaction.getNicHandleId(),
                internalTransaction.getInvoiceId(),
                internalTransaction.getInvoiceNumber(),
                internalTransaction.getOrderId(),
                internalTransaction.isSettlementStarted(),
                internalTransaction.isSettlementEnded(),
                internalTransaction.getTotalCost(),
                internalTransaction.getTotalNetAmount(),
                internalTransaction.getTotalVatAmount(),
                internalTransaction.isCancelled(),
                internalTransaction.getCancelledDate(),
                internalTransaction.getFinanciallyPassedDate(),
                reservations,
                internalTransaction.isInvalidated(),
                internalTransaction.getInvalidatedDate(),
                internalTransaction.getReauthorisedId(),
                internalTransaction.getSettlementDate(),
                internalTransaction.getPaymentMethod(),
                internalTransaction.getOperationType(),
                internalTransaction.getRealexAuthcode(),
                internalTransaction.getRealexPassref());
    }

    @Override
    protected InternalTransaction _from(Transaction transaction) {
        List<InternalReservation> reservations = reservationConverter.from(transaction.getReservations());
        return new InternalTransaction(
                transaction.getId(),
                transaction.getBillNicHandleId(),
                transaction.getInvoiceId(),
                transaction.getOrderId(),
                transaction.isSettlementStarted(),
                transaction.isSettlementEnded(),
                transaction.getTotalCost(),
                transaction.getTotalNetAmount(),
                transaction.getTotalVatAmount(),
                transaction.isCancelled(),
                transaction.getCancelledDate(),
                transaction.getFinanciallyPassedDate(),
                reservations,
                transaction.isInvalidated(),
                transaction.getInvalidatedDate(),
                transaction.getReauthorisedId(),
                transaction.getSettlementDate(),
                transaction.getPaymentMethod(),
                transaction.getOperationType(),
                transaction.getRealexAuthCode(),
                transaction.getRealexPassRef());
    }
}
