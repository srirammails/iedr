package pl.nask.crs.payment;

import pl.nask.crs.commons.utils.Validator;

import java.util.Date;

/**
 * @author: Marcin Tkaczyk
 */
public class Deposit {

    private final String nicHandleId;
    private final String nicHandleName;
    private final Date transactionDate;
    private final double openBal;
    private final double closeBal;
    private final double transactionAmount;
    private final DepositTransactionType transactionType;
    private final String orderId;
    private final String correctorNH;
    private final String remark;

    public static Deposit newInstance(String nicHandleId, Date transactionDate, double openBal, double closeBal,
                                      double transactionAmount, DepositTransactionType transactionType, String orderId,
                                      String correctorNH, String remark) {
        return new Deposit(nicHandleId, null, transactionDate, openBal, closeBal, transactionAmount, transactionType, orderId, correctorNH, remark);
    }

    public Deposit(String nicHandleId, String nicHandleName, Date transactionDate, double openBal, double closeBal,
                   double transactionAmount, DepositTransactionType transactionType, String orderId,
                   String correctorNH, String remark) {
		Validator.assertNotEmpty(nicHandleId, "nic Handle id");
        Validator.assertNotNull(transactionDate, "transaction date");
        this.nicHandleId = nicHandleId;
        this.nicHandleName = nicHandleName;
        this.transactionDate = transactionDate;
        this.openBal = openBal;
        this.closeBal = closeBal;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.orderId = orderId;
        this.correctorNH = correctorNH;
        this.remark = remark;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public String getNicHandleName() {
        return nicHandleName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getOpenBal() {
        return openBal;
    }

    public double getCloseBal() {
        return closeBal;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public DepositTransactionType getTransactionType() {
        return transactionType;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCorrectorNH() {
        return correctorNH;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return String.format("Deposit[nh: %s, closeBal: %s]", nicHandleId, closeBal);
    }
}
