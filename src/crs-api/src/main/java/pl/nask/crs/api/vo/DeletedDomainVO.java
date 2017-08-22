package pl.nask.crs.api.vo;

import pl.nask.crs.domains.DeletedDomain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DeletedDomainVO {

    private String domainName;
    private String domainHolder;
    private Date registrationDate;
    private Date renewalDate;
    private Date deletionDate;
    private String holderClass;
    private String holderCategory;

    public DeletedDomainVO() {
    }

    public DeletedDomainVO(DeletedDomain deletedDomain) {
        this.domainName = deletedDomain.getDomainName();
        this.domainHolder = deletedDomain.getDomainHolder();
        this.registrationDate = deletedDomain.getRegistrationDate();
        this.renewalDate = deletedDomain.getRenewalDate();
        this.deletionDate = deletedDomain.getDeletionDate();
        this.holderClass = deletedDomain.getHolderClass();
        this.holderCategory = deletedDomain.getHolderCategory();
    }

}
