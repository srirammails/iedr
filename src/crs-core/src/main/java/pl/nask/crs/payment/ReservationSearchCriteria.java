package pl.nask.crs.payment;

import pl.nask.crs.commons.search.SearchCriteria;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReservationSearchCriteria implements SearchCriteria<Reservation> {

    private String billingNH;

    private Boolean readyForSettlement;

	private PaymentMethod paymentMethod;

    private Integer ticketId;

    private String domainName;

    private Boolean settled;

    private Integer transactionId;

    private OperationType operationType;

    private Boolean cancelled;

    public static ReservationSearchCriteria newInstance() {
        return new ReservationSearchCriteria();
    }

    public static ReservationSearchCriteria newSettledInstance(boolean settled) {
        return new ReservationSearchCriteria(settled);
    }

    public static ReservationSearchCriteria newReadyForSettlementInstance(boolean readyForSettlement) {
        return new ReservationSearchCriteria(readyForSettlement, false);
    }

    public ReservationSearchCriteria() {}

    private ReservationSearchCriteria(Boolean settled) {
        this.settled = settled;
    }

    private ReservationSearchCriteria(Boolean readyForSettlement, Boolean settled) {
        this.readyForSettlement = readyForSettlement;
        this.settled = settled;
    }

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

    public Boolean isReadyForSettlement() {
        return readyForSettlement;
    }

    public void setPaymentMethod(PaymentMethod method) {
		this.paymentMethod = method;
    }

    public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Boolean isSettled() {
        return settled;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }
}
