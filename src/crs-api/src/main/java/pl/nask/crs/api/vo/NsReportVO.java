package pl.nask.crs.api.vo;

import pl.nask.crs.domains.nameservers.NsReport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NsReportVO {

    private String billingNH;
    private String domainName;
    private String holderName;
    private Date registrationDate;
    private Date renewalDate;
    private String dnsName;
    private Integer dnsOrder;
    private String dnsIpAddress;

    public NsReportVO() {}

    public NsReportVO(NsReport nsReport) {
        this.billingNH = nsReport.getBillingNH();
        this.domainName = nsReport.getDomainName();
        this.holderName = nsReport.getHolderName();
        this.registrationDate = nsReport.getRegistrationDate();
        this.renewalDate = nsReport.getRenewalDate();
        this.dnsName = nsReport.getDnsName();
        this.dnsOrder = nsReport.getDnsOrder();
        this.dnsIpAddress = nsReport.getDnsIpAddress();
    }

}
