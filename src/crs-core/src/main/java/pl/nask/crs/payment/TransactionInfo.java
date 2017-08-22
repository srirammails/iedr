package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransactionInfo {

    private final Date financiallyPassedDate;

    private final String orderId;

    private final OperationType operationType;

    private final BigDecimal totalCost;

    private final BigDecimal availableDepositBalance;

    public TransactionInfo(Date financiallyPassedDate, String orderId, OperationType operationType, BigDecimal totalCost, BigDecimal availableDepositBalance) {
        this.financiallyPassedDate = financiallyPassedDate;
        this.orderId = orderId;
        this.operationType = operationType;
        this.totalCost = totalCost;
        this.availableDepositBalance = availableDepositBalance;
    }

    public Date getFinanciallyPassedDate() {
        return financiallyPassedDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public BigDecimal getAvailableDepositBalance() {
        return availableDepositBalance;
    }

    @Override
    public String toString() {
        return String.format("TransactionInfo[passedDate: %s, orderId: %s, totalCost: %s, availableBalance: %s]",
                financiallyPassedDate, orderId, totalCost, availableDepositBalance);
    }
}
