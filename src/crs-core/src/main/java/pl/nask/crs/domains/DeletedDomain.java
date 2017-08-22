package pl.nask.crs.domains;

import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DeletedDomain {

    private String domainName;
    private String domainHolder;
    private String billingName;
    private String billingNic;
    private Date registrationDate;
    private Date renewalDate;
    private Date deletionDate;
    private String holderClass;
    private String holderCategory;
    private String country;
    private String county;

    public String getDomainName() {
        return domainName;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingNic() {
        return billingNic;
    }

    public void setBillingNic(String billingNic) {
        this.billingNic = billingNic;
    }

    @Override
    public String toString() {
        return String.format("DeletedDomain[name=%s]", domainName);
    }

}
