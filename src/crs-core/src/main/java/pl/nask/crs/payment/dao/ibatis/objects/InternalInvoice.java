package pl.nask.crs.payment.dao.ibatis.objects;

import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalInvoice {
    private int id;
    private String invoiceNumber;
    private String accountName;
    private long accountNumber;
    private String address1;
    private String address2;
    private String address3;
    private String billingNicHandle;
    private String billingNicHandleName;
    private boolean completed;
    private String country;
    private String county;
    private String crsVersion;
    private Date invoiceDate;
    private String MD5;
    private List<InternalTransaction> transactions;
    private Integer totalCost;
    private Integer totalNetAmount;
    private Integer totalVatAmount;

    private Date settlementDate;
    private PaymentMethod paymentMethod;

    public InternalInvoice() {
    }

    public InternalInvoice(int id, String invoiceNumber, String accountName, long accountNumber, String address1, String address2, String address3, String billingNicHandle, String billingNicHandleName, boolean completed, String country, String county, String crsVersion, Date invoiceDate, String MD5, List<InternalTransaction> transactions, Integer totalCost, Integer totalNetAmount, Integer totalVatAmount) {
        // generated by db
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.billingNicHandle = billingNicHandle;
        this.billingNicHandleName = billingNicHandleName;
        this.completed = completed;
        this.country = country;
        this.county = county;
        this.crsVersion = crsVersion;
        this.invoiceDate = invoiceDate;
        this.MD5 = MD5;
        this.transactions = transactions;
        this.totalCost = totalCost;
        this.totalNetAmount = totalNetAmount;
        this.totalVatAmount = totalVatAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCrsVersion() {
        return crsVersion;
    }

    public void setCrsVersion(String crsVersion) {
        this.crsVersion = crsVersion;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public List<InternalTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<InternalTransaction> transactions) {
        this.transactions = transactions;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getTotalNetAmount() {
        return totalNetAmount;
    }

    public void setTotalNetAmount(Integer totalNetAmount) {
        this.totalNetAmount = totalNetAmount;
    }

    public Integer getTotalVatAmount() {
        return totalVatAmount;
    }

    public void setTotalVatAmount(Integer totalVatAmount) {
        this.totalVatAmount = totalVatAmount;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
