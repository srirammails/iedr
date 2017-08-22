package pl.nask.crs.reports;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainsPerClass implements Report {

    private String domainClass;
    private String domainCategory;
    private long domainCount;
    private String accountName;
    private String billNHId;

    public String getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(String domainClass) {
        this.domainClass = domainClass;
    }

    public String getDomainCategory() {
        return domainCategory;
    }

    public void setDomainCategory(String domainCategory) {
        this.domainCategory = domainCategory;
    }

    public long getDomainCount() {
        return domainCount;
    }

    public void setDomainCount(long domainCount) {
        this.domainCount = domainCount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBillNHId() {
        return billNHId;
    }

    public void setBillNHId(String billNHId) {
        this.billNHId = billNHId;
    }
}
