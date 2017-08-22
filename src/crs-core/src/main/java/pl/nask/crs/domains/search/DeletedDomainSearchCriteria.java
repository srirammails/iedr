package pl.nask.crs.domains.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;

import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DeletedDomainSearchCriteria implements SearchCriteria<DeletedDomain>{

    private String billingNH;
    private String domainName;
    private Date registrationFrom;
    private Date registrationTo;
    private Date renewalFrom;
    private Date renewalTo;
    private Date deletionFrom;
    private Date deletionTo;
    private CustomerType type;
    private Long accountId;

    public DeletedDomainSearchCriteria() {}

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setRegistrationFrom(Date registrationFrom) {
        this.registrationFrom = registrationFrom;
    }

    public void setRegistrationTo(Date registrationTo) {
        this.registrationTo = registrationTo;
    }

    public void setRenewalFrom(Date renewalFrom) {
        this.renewalFrom = renewalFrom;
    }

    public void setRenewalTo(Date renewalTo) {
        this.renewalTo = renewalTo;
    }

    public String getDomainName() {
        return domainName;
    }

    public Date getRegistrationFrom() {
        return registrationFrom;
    }

    public Date getRegistrationTo() {
        return registrationTo;
    }

    public Date getRenewalFrom() {
        return renewalFrom;
    }

    public Date getRenewalTo() {
        return renewalTo;
    }

    public Date getDeletionFrom() {
        return deletionFrom;
    }

    public void setDeletionFrom(Date deletionFrom) {
        this.deletionFrom = deletionFrom;
    }

    public Date getDeletionTo() {
        return deletionTo;
    }

    public void setDeletionTo(Date deletionTo) {
        this.deletionTo = deletionTo;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
