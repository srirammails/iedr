package pl.nask.crs.reports.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.reports.DomainsPerClass;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainsPerClassSearchCriteria implements SearchCriteria<DomainsPerClass> {

    private String holderClass;
    private String holderCategory;
    private Date from;
    private Date to;
    private Long accountId;
    private String billingNH;

    public String getHolderClass() {
        return holderClass;
    }

    public void setHolderClass(String holderClass) {
        this.holderClass = holderClass;
    }

    public String getHolderCategory() {
        return holderCategory;
    }

    public void setHolderCategory(String holderCategory) {
        this.holderCategory = holderCategory;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }
}
