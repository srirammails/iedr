package pl.nask.crs.payment;

import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class OfflineBatch {

    private String sessionId;
    private String nicHandleId;
    private Date transactionDate;
    private Float batchTotal;
    private int domainCount;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Float getBatchTotal() {
        return batchTotal;
    }

    public void setBatchTotal(Float batchTotal) {
        this.batchTotal = batchTotal;
    }

    public int getDomainCount() {
        return domainCount;
    }

    public void setDomainCount(int domainCount) {
        this.domainCount = domainCount;
    }
}
