package pl.nask.crs.api.vo;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.PaymentMethod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceVO {

    private int id;
    private String invoiceNumber;
    private String accountName;
    private long accountNumber;
    private String address1;
    private String address2;
    private String address3;
    private String billingNicHandle;
    private String country;
    private String county;
    private String crsVersion;
    private Date invoiceDate;
    private String MD5;
    private boolean completed;
    private BigDecimal totalCost;
    private BigDecimal totalNetAmount;
    private BigDecimal totalVatAmount;
    private Date settlementDate;
    private PaymentMethod paymentMethod;

    public InvoiceVO() {
    }

    public InvoiceVO(Invoice invoice) {
        this.id = invoice.getId();
        this.invoiceNumber = invoice.getInvoiceNumber();
        this.accountName = invoice.getAccountName();
        this.accountNumber = invoice.getAccountNumber();
        this.address1 = invoice.getAddress1();
        this.address2 = invoice.getAddress2();
        this.address3 = invoice.getAddress3();
        this.billingNicHandle = invoice.getBillingNicHandle();
        this.country = invoice.getCountry();
        this.county = invoice.getCounty();
        this.crsVersion = invoice.getCrsVersion();
        this.invoiceDate = invoice.getInvoiceDate();
        this.MD5 = invoice.getMD5();
        this.completed = invoice.isCompleted();
        this.totalCost = MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(invoice.getTotalCost());
        this.totalNetAmount = MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(invoice.getTotalNetAmount());
        this.totalVatAmount = MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(invoice.getTotalVatAmount());
        this.settlementDate = invoice.getSettlementDate();
        this.paymentMethod = invoice.getPaymentMethod();
    }

    public int getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getBillingNicHandle() {
        return billingNicHandle;
    }

    public String getCountry() {
        return country;
    }

    public String getCounty() {
        return county;
    }

    public String getCrsVersion() {
        return crsVersion;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public String getMD5() {
        return MD5;
    }

    public boolean isCompleted() {
        return completed;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public BigDecimal getTotalNetAmount() {
        return totalNetAmount;
    }

    public BigDecimal getTotalVatAmount() {
        return totalVatAmount;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
