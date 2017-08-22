package pl.nask.crs.payment;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceSearchCriteria implements SearchCriteria<Invoice> {

    private String billingNhId;

    private String accountName;

    private CustomerType customerType;

    private Date settledFrom;

    private Date settledTo;

    private PaymentMethod paymentMethod;

    private String invoiceNumber;

    private String invoiceNumberFrom;
    private String invoiceNumberTo;

    private Date invoiceDateFrom;
    private Date invoiceDateTo;

    private String settlementDateLike;
    private String invoiceDateLike;

    public String getBillingNhId() {
        return billingNhId;
    }

    public void setBillingNhId(String billingNhId) {
        this.billingNhId = billingNhId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Date getSettledFrom() {
        return settledFrom;
    }

    public void setSettledFrom(Date settledFrom) {
        this.settledFrom = settledFrom;
    }

    public Date getSettledTo() {
        return settledTo;
    }

    public void setSettledTo(Date settledTo) {
        this.settledTo = settledTo;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getType() {
        return customerType == null ? null : customerType.name();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumberFrom() {
        return invoiceNumberFrom;
    }

    public void setInvoiceNumberFrom(String invoiceNumberFrom) {
        this.invoiceNumberFrom = invoiceNumberFrom;
    }

    public String getInvoiceNumberTo() {
        return invoiceNumberTo;
    }

    public void setInvoiceNumberTo(String invoiceNumberTo) {
        this.invoiceNumberTo = invoiceNumberTo;
    }

    public Date getInvoiceDateFrom() {
        return invoiceDateFrom;
    }

    public void setInvoiceDateFrom(Date invoiceDateFrom) {
        this.invoiceDateFrom = DateUtils.startOfDay(invoiceDateFrom);
    }

    public Date getInvoiceDateTo() {
        return invoiceDateTo;
    }

    public void setInvoiceDateTo(Date invoiceDateTo) {
        this.invoiceDateTo = DateUtils.endOfDay(invoiceDateTo);
    }

    public String getSettlementDateLike() {
        return settlementDateLike;
    }

    public void setSettlementDateLike(String settlementDateLike) {
        this.settlementDateLike = settlementDateLike;
    }

    public String getInvoiceDateLike() {
        return invoiceDateLike;
    }

    public void setInvoiceDateLike(String invoiceDateLike) {
        this.invoiceDateLike = invoiceDateLike;
    }
}
