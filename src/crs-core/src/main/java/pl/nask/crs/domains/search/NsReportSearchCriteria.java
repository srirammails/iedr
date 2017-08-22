package pl.nask.crs.domains.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.nameservers.NsReport;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class NsReportSearchCriteria implements SearchCriteria<NsReport> {

    private String domainName;
    private String holderName;
    private Date registrationFrom;
    private Date registrationTo;
    private Date renewalFrom;
    private Date renewalTo;
    private Date renewalDate;
    private Date registrationDate;
    private String dnsName;
    private Integer dnsOrder;
    private String dnsIpAddress;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Date getRegistrationFrom() {
        return registrationFrom;
    }

    public void setRegistrationFrom(Date registrationFrom) {
        this.registrationFrom = registrationFrom;
    }

    public Date getRegistrationTo() {
        return registrationTo;
    }

    public void setRegistrationTo(Date registrationTo) {
        this.registrationTo = registrationTo;
    }

    public Date getRenewalFrom() {
        return renewalFrom;
    }

    public void setRenewalFrom(Date renewalFrom) {
        this.renewalFrom = renewalFrom;
    }

    public Date getRenewalTo() {
        return renewalTo;
    }

    public void setRenewalTo(Date renewalTo) {
        this.renewalTo = renewalTo;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public Integer getDnsOrder() {
        return dnsOrder;
    }

    public void setDnsOrder(Integer dnsOrder) {
        this.dnsOrder = dnsOrder;
    }

    public String getDnsIpAddress() {
        return dnsIpAddress;
    }

    public void setDnsIpAddress(String dnsIpAddress) {
        this.dnsIpAddress = dnsIpAddress;
    }
}
