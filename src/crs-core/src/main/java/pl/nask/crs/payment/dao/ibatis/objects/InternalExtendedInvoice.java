package pl.nask.crs.payment.dao.ibatis.objects;

import pl.nask.crs.payment.CustomerType;
import pl.nask.crs.payment.PaymentMethod;

import java.util.Date;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class InternalExtendedInvoice {

    private String domainName;
    private String invoiceNumber;
    private String billingNicHandle;
    private String billingNicHandleName;
    private PaymentMethod paymentMethod;
    private CustomerType customerType;
    private Date settledDate;
    private Date invoiceDate;
    private Date creationDate;
    private int durationMonths;
    private Date renewalDate;
    private Date startDate;
    private double netAmount;
    private double vatAmount;
    private String orderId;

    public InternalExtendedInvoice() {}

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getBillingNicHandle() {
        return billingNicHandle;
    }

    public void setBillingNicHandle(String billingNicHandle) {
        this.billingNicHandle = billingNicHandle;
    }

    public String getBillingNicHandleName() {
        return billingNicHandleName;
    }

    public void setBillingNicHandleName(String billingNicHandleName) {
        this.billingNicHandleName = billingNicHandleName;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Date getSettledDate() {
        return settledDate;
    }

    public void setSettledDate(Date settledDate) {
        this.settledDate = settledDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }
    
    public Date getStartDate() {
		return startDate;
	}
    
    public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
