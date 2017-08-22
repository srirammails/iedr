package pl.nask.crs.payment;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DepositSearchCriteria implements SearchCriteria<Deposit> {

    private String nicHandleId;
    private Date transactionDateFrom;
    private Date transactionDateTo;
    private DepositTransactionType transactionType;
    private String correctorNH;
    private String remark;
    private String accountBillNH;

    public DepositSearchCriteria() {}

    public DepositSearchCriteria(String nicHandleId, String accountBillNH) {
        this.nicHandleId = nicHandleId;
        this.accountBillNH = accountBillNH;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public Date getTransactionDateFrom() {
        return DateUtils.startOfDay(transactionDateFrom);
    }

    public void setTransactionDateFrom(Date transactionDateFrom) {
        this.transactionDateFrom = transactionDateFrom;
    }

    public Date getTransactionDateTo() {
        return DateUtils.endOfDay(transactionDateTo);
    }

    public void setTransactionDateTo(Date transactionDateTo) {
        this.transactionDateTo = transactionDateTo;
    }

    public DepositTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(DepositTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getCorrectorNH() {
        return correctorNH;
    }

    public void setCorrectorNH(String correctorNH) {
        this.correctorNH = correctorNH;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccountBillNH() {
        return accountBillNH;
    }

    public void setAccountBillNH(String accountBillNH) {
        this.accountBillNH = accountBillNH;
    }
}
