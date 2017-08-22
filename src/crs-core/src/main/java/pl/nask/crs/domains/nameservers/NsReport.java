package pl.nask.crs.domains.nameservers;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class NsReport {

    private String billingNH;
    private String domainName;
    private String holderName;
    private Date registrationDate;
    private Date renewalDate;
    private String dnsName;
    private Integer dnsOrder;
    private String dnsIpAddress;

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

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

    @Override
    public String toString() {
        return String.format("NsReport[dnsName=%s, domainName=%s, billingNH=%s]", dnsName, domainName, billingNH);
    }
}
