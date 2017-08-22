package pl.nask.crs.payment;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DepositTopUp {

    private Date operationDate;
    private double topUpAmount;
    private String orderId;
    private double closingBalance;

    public DepositTopUp() {
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public void setTopUpAmount(double topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public double getTopUpAmount() {
        return topUpAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    @Override
    public String toString() {
        return String.format("DepositTopUp[orderId=%s, topUpAmount=%s, operationDate=%s]", orderId, topUpAmount, operationDate);
    }
}
