package pl.nask.crs.api.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.ticket.FinancialStatusEnum;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationVO {

    private long id;
    private String nicHandleId;
    private String domainName;
    private int durationMonths;
    private Date creationDate;
    private String vatCategory;
    private BigDecimal netAmount;
    private BigDecimal vatAmount;
    private BigDecimal total;
    private boolean readyForSettlement;
    private boolean settled;
    private Date settledDate;
    private Long ticketId;
    private Long transactionId;
    private PaymentMethod paymentMethod;
    private OperationType operationType;
    private String invoiceNumber;
    private String orderId;
    private FinancialStatusEnum financialStatus;
    private boolean domainExists;

    public ReservationVO() {
    }

    public ReservationVO(Reservation reservation) {
        this.id = reservation.getId();
        this.nicHandleId = reservation.getNicHandleId();
        this.domainName = reservation.getDomainName();
        this.durationMonths = reservation.getDurationMonths();
        this.creationDate = reservation.getCreationDate();
        this.vatCategory = reservation.getVatCategory();
        this.netAmount = reservation.getNetAmount();
        this.vatAmount = reservation.getVatAmount();
        this.total = reservation.getTotal();
        this.readyForSettlement = reservation.isReadyForSettlement();
        this.settled = reservation.isSettled();
        this.settledDate = reservation.getSettledDate();
        this.ticketId = reservation.getTicketId();
        this.transactionId = reservation.getTransactionId();
        this.paymentMethod = reservation.getPaymentMethod();
        this.operationType = reservation.getOperationType();
        this.invoiceNumber = reservation.getInvoiceNumber();
        this.orderId = reservation.getOrderId();
        this.financialStatus = reservation.getTicketFinancialStatus() == null ? null : FinancialStatusEnum.valueForId(reservation.getTicketFinancialStatus());
        this.domainExists = reservation.getDomainExists();
    }
}
