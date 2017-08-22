package pl.nask.crs.api.vo;

import pl.nask.crs.payment.PaymentDomain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentDomainVO {

    private String domainName;
    private Date registrationDate;
    private Date renewalDate;
    private int periodInYears;
    private BigDecimal fee;
    private BigDecimal vat;
    private BigDecimal total;

    public PaymentDomainVO() {
    }

    public PaymentDomainVO(PaymentDomain paymentDomain) {
        this.domainName = paymentDomain.getDomainName();
        this.registrationDate = paymentDomain.getRegistrationDate();
        this.renewalDate = paymentDomain.getRenewalDate();
        this.periodInYears = paymentDomain.getPeriodInYears();
        this.fee = paymentDomain.getFee();
        this.vat = paymentDomain.getVat();
        this.total = paymentDomain.getTotal();
    }

}
