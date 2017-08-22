package pl.nask.crs.domains;

import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class TransferedDomain {

    private String domainName;
    private String domainHolder;
    private Date registrationDate;
    private Date renewalDate;
    private Date transferDate;
    private Date suspensionDate;
    private Date deletionDate;
    private DsmState dsmState;
    private boolean zonePublished;

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

    public Date getSuspensionDate() {
        return suspensionDate;
    }

    public void setSuspensionDate(Date suspensionDate) {
        this.suspensionDate = suspensionDate;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
    }

    public DsmState getDsmState() {
        return dsmState;
    }

    public void setDsmState(DsmState dsmState) {
        this.dsmState = dsmState;
    }

    public boolean isZonePublished() {
        return zonePublished;
    }

    public void setZonePublished(boolean zonePublished) {
        this.zonePublished = zonePublished;
    }

    @Override
    public String toString() {
        return String.format("TransferedDomain[domainName=%s, xferDate=%s]", domainName, transferDate);
    }
}
