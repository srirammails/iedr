package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservation;

import java.util.HashMap;
import java.util.Map;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalReservationHistIbatisDAO extends GenericIBatisDAO<InternalReservation, Long> {

    private Map<String, String> reservationSortMap = new HashMap<String, String>();

    {
        reservationSortMap.put("id", "id");
        reservationSortMap.put("nicHandleId", "nicHandleId");
        reservationSortMap.put("domainName", "domainName");
        reservationSortMap.put("durationMonths", "durationMonths");
        reservationSortMap.put("creationDate", "creationDate");
        reservationSortMap.put("productCode", "productCode");
        reservationSortMap.put("amount", "amount");
        reservationSortMap.put("netAmount", "amount");
        reservationSortMap.put("vatId", "vatId");
        reservationSortMap.put("vatCategory", "vatCategory");
        reservationSortMap.put("vatFromDate", "vatFromDate");
        reservationSortMap.put("vatRate", "vatRate");
        reservationSortMap.put("vatAmount", "vatAmount");
        reservationSortMap.put("readyForSettlement", "readyForSettlement");
        reservationSortMap.put("settled", "settled");
        reservationSortMap.put("settledDate", "settledDate");
        reservationSortMap.put("ticketId", "ticketId");
        reservationSortMap.put("transactionId", "transactionId");
        reservationSortMap.put("operationType", "operationType");
        reservationSortMap.put("startDate", "startDate");
        reservationSortMap.put("endDate", "endDate");
        reservationSortMap.put("paymentMethod", "paymentMethod");
        reservationSortMap.put("invoiceNumber", "invoiceNumber");
        reservationSortMap.put("orderId", "orderId");
        reservationSortMap.put("total", "total");
    }

    public InternalReservationHistIbatisDAO() {
        setCountFindQueryId("reservation-hist.countFindHistReservations");
        setFindQueryId("reservation-hist.findHistReservations");
        setCreateQueryId("reservation-hist.createHistReservation");
        setGetQueryId("reservation-hist.getById");
        setSortMapping(reservationSortMap);
    }
}
