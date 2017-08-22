package pl.nask.crs.payment;

import pl.nask.crs.commons.search.SearchCriteria;

import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class OfflineBatchesSearchCriteria implements SearchCriteria<OfflineBatch> {

    private String sessionId;
    private String transactionDate;
    private Float batchTotal;
    private Integer domainCount;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setBatchTotal(Float batchTotal) {
        this.batchTotal = batchTotal;
    }

    public void setDomainCount(Integer domainCount) {
        this.domainCount = domainCount;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public Float getBatchTotal() {
        return batchTotal;
    }

    public Integer getDomainCount() {
        return domainCount;
    }
}
