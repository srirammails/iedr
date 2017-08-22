package pl.nask.crs.api.vo;

import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.ReauthoriseTransaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReauthoriseTransactionVO {

    private long id;
    private OperationType operationType;
    private String domainName;
    private BigDecimal oldTotalCost;
    private BigDecimal oldNetAmount;
    private BigDecimal oldVatAmount;
    private String orderId;
    private BigDecimal newTotalCost;
    private BigDecimal newNetAmount;
    private BigDecimal newVatAmount;
    private int periodInYears;

    public ReauthoriseTransactionVO() {}

    public ReauthoriseTransactionVO(ReauthoriseTransaction reauthoriseTransaction) {
        this.id = reauthoriseTransaction.getId();
        this.operationType = reauthoriseTransaction.getOperationType();
        this.domainName = reauthoriseTransaction.getDomainName();
        this.oldTotalCost = reauthoriseTransaction.getOldTotalCost();
        this.oldNetAmount = reauthoriseTransaction.getOldNetAmount();
        this.oldVatAmount = reauthoriseTransaction.getOldVatAmount();
        this.orderId = reauthoriseTransaction.getOrderId();
        this.newTotalCost = reauthoriseTransaction.getNewTotalCost();
        this.newNetAmount = reauthoriseTransaction.getNewNetAmount();
        this.newVatAmount = reauthoriseTransaction.getNewVatAmount();
        this.periodInYears = reauthoriseTransaction.getPeriodInYears();
    }
}
