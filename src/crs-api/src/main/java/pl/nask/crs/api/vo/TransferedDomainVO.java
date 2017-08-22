package pl.nask.crs.api.vo;

import pl.nask.crs.domains.TransferedDomain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@Deprecated
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferedDomainVO {

    private String domainName;
    private String domainHolder;
    private Date registrationDate;
    private Date renewalDate;
    private Date transferDate;

    public TransferedDomainVO() {
    }

    public TransferedDomainVO(TransferedDomain transferedDomain) {
        this.domainName = transferedDomain.getDomainName();
        this.domainHolder = transferedDomain.getDomainHolder();
        this.registrationDate = transferedDomain.getRegistrationDate();
        this.renewalDate = transferedDomain.getRenewalDate();
        this.transferDate = transferedDomain.getTransferDate();
    }

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

    public Date getTransferDate() {
        return transferDate;
    }
}
