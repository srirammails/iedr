package pl.nask.crs.api.vo;

import pl.nask.crs.commons.Period;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.OperationType;

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
public class DomainInfoVO {

    private String domainName;

    private String orderId;

    private Date regDate;

    private Date renDate;

    private Date transferDate;

    private OperationType operationType;

    private int durationYears;

    private double netAmount;

    private double vatAmount;

    private double total;

    public DomainInfoVO() {
    }

    public DomainInfoVO(DomainInfo domainInfo) {
        this.domainName = domainInfo.getDomainName();
        this.orderId = domainInfo.getOrderId();
        this.regDate = domainInfo.getRegDate();
        this.renDate = domainInfo.getRenDate();
        this.transferDate = domainInfo.getTransferDate();
        this.operationType = domainInfo.getOperationType();
        this.durationYears = Period.fromMonths(domainInfo.getDurationMonths()).getYears();
        this.netAmount = domainInfo.getNetAmount();
        this.vatAmount = domainInfo.getVatAmount();
        this.total = domainInfo.getTotal();
    }

}
