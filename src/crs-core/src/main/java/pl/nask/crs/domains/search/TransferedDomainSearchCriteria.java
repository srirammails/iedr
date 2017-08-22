package pl.nask.crs.domains.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.Domain;

import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class TransferedDomainSearchCriteria implements SearchCriteria<Domain>{

    private TransferDirection transferDirection;
    private Date transferDateFrom;
    private Date transferDateTo;
    private String transferMonth;
    private String domainName;
    private String domainHolder;
    private Date registrationDate;
    private Date renewalDate;
    private Date transferDate;

    public TransferDirection getTransferDirection() {
        return transferDirection;
    }

    public void setTransferDirection(TransferDirection transferDirection) {
        this.transferDirection = transferDirection;
    }

    public Date getTransferDateFrom() {
        return transferDateFrom;
    }

    public void setTransferDateFrom(Date transferDateFrom) {
        this.transferDateFrom = transferDateFrom;
    }

    public Date getTransferDateTo() {
        return transferDateTo;
    }

    public void setTransferDateTo(Date transferDateTo) {
        this.transferDateTo = transferDateTo;
    }

    public String getTransferMonth() {
        return transferMonth;
    }

    public void setTransferMonth(String transferMonth) {
        this.transferMonth = transferMonth;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }
}
