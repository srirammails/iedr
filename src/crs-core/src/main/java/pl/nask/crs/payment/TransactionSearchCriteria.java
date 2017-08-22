package pl.nask.crs.payment;

import pl.nask.crs.commons.search.SearchCriteria;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransactionSearchCriteria implements SearchCriteria<Transaction> {

    private String billingNH;

    private Boolean cancelled;

    private Boolean readyForSettlement;

    private Boolean settlementStarted;

    private Boolean settlementEnded;

    private Boolean invoiceAssociated;

    private Boolean invalidated;

    private PaymentMethod paymentMethod;

    private Boolean reauthorised;

    private Boolean ticketExists;

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Boolean getReadyForSettlement() {
        return readyForSettlement;
    }

    public void setReadyForSettlement(Boolean readyForSettlement) {
        this.readyForSettlement = readyForSettlement;
    }

    public Boolean getSettlementStarted() {
        return settlementStarted;
    }

    public void setSettlementStarted(Boolean settlementStarted) {
        this.settlementStarted = settlementStarted;
    }

    public Boolean getSettlementEnded() {
        return settlementEnded;
    }

    public void setSettlementEnded(Boolean settlementEnded) {
        this.settlementEnded = settlementEnded;
    }

    public Boolean getInvoiceAssociated() {
        return invoiceAssociated;
    }

    public void setInvoiceAssociated(Boolean invoiceAssociated) {
        this.invoiceAssociated = invoiceAssociated;
    }

    public Boolean getInvalidated() {
        return invalidated;
    }

    public void setInvalidated(Boolean invalidated) {
        this.invalidated = invalidated;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getReauthorised() {
        return reauthorised;
    }

    public void setReauthorised(Boolean reauthorised) {
        this.reauthorised = reauthorised;
    }

    public Boolean getTicketExists() {
        return ticketExists;
    }

    public void setTicketExists(Boolean ticketExists) {
        this.ticketExists = ticketExists;
    }
}
