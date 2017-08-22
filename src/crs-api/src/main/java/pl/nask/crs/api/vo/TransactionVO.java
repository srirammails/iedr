package pl.nask.crs.api.vo;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Transaction;

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
public class TransactionVO {

    private Date settlementDate;

    private long id;

    private OperationType operationType;

    private BigDecimal totalCost;

    private BigDecimal totalNetAmount;

    private BigDecimal totalVatAmount;

    private String orderId;

    private Integer invoiceId;
    
    private String invoiceNumber;

    private PaymentMethod paymentMethod;

    public TransactionVO() {}

    public TransactionVO(Transaction transaction) {
        this.settlementDate = transaction.getSettlementDate();
        this.id = transaction.getId();
        this.operationType = transaction.getOperationType();
        this.totalCost = MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(transaction.getTotalCost());
        this.totalNetAmount = MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(transaction.getTotalNetAmount());
        this.totalVatAmount = MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(transaction.getTotalVatAmount());
        this.orderId = transaction.getOrderId();
        this.invoiceId = transaction.getInvoiceId();
        this.paymentMethod = transaction.getPaymentMethod();
        this.invoiceNumber = transaction.getInvoiceNumber();
    }
}
