package pl.nask.crs.payment;

import java.math.BigDecimal;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReauthoriseTransaction {

    private final long id;
    private final OperationType operationType;
    private final String domainName;
    private final BigDecimal oldTotalCost;
    private final BigDecimal oldNetAmount;
    private final BigDecimal oldVatAmount;
    private final String orderId;
    private final BigDecimal newTotalCost;
    private final BigDecimal newNetAmount;
    private final BigDecimal newVatAmount;
    private final int periodInYears;

    public ReauthoriseTransaction(long id, OperationType operationType, String domainName, BigDecimal oldTotalCost, BigDecimal oldNetAmount, BigDecimal oldVatAmount, String orderId, BigDecimal newTotalCost, BigDecimal newNetAmount, BigDecimal newVatAmount, int periodInYears) {
        this.id = id;
        this.operationType = operationType;
        this.domainName = domainName;
        this.oldTotalCost = oldTotalCost;
        this.oldNetAmount = oldNetAmount;
        this.oldVatAmount = oldVatAmount;
        this.orderId = orderId;
        this.newTotalCost = newTotalCost;
        this.newNetAmount = newNetAmount;
        this.newVatAmount = newVatAmount;
        this.periodInYears = periodInYears;
    }

    public long getId() {
        return id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getDomainName() {
        return domainName;
    }

    public BigDecimal getOldTotalCost() {
        return oldTotalCost;
    }

    public BigDecimal getOldNetAmount() {
        return oldNetAmount;
    }

    public BigDecimal getOldVatAmount() {
        return oldVatAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getNewTotalCost() {
        return newTotalCost;
    }

    public BigDecimal getNewNetAmount() {
        return newNetAmount;
    }

    public BigDecimal getNewVatAmount() {
        return newVatAmount;
    }

    public int getPeriodInYears() {
        return periodInYears;
    }
}
