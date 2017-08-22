package pl.nask.crs.api.vo;

import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationCriteriaVO {

    private PaymentMethod paymentMethod;
    private String domainName;
    private OperationType operationType;

    public String getDomainName() {
        return domainName;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
