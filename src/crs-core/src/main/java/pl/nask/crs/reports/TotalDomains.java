package pl.nask.crs.reports;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TotalDomains implements Report{

    private String billingNH;
    private String accountName;
    private long domainCount;

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getDomainCount() {
        return domainCount;
    }

    public void setDomainCount(long domainCount) {
        this.domainCount = domainCount;
    }

    @Override
    public String toString() {
        return String.format("TotalDomains[billingNH=%s, domainCount=%s]", billingNH, domainCount);
    }
}
