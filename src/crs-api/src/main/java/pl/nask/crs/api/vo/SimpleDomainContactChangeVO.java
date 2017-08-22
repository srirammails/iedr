package pl.nask.crs.api.vo;

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalStatus;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleDomainContactChangeVO {

    @XmlElement(required=true)
    private ContactVO newValue;
    private ContactVO oldValue;
    private Integer failureReasonId;
    private String failureReason;

    public SimpleDomainContactChangeVO() {
    }

    public SimpleDomainContactChangeVO(SimpleDomainFieldChange<Contact> c) {
        this.newValue = c.getNewValue() == null ? null : new ContactVO(c.getNewValue().getNicHandle(), c.getNewValue().getName());
        this.oldValue = c.getCurrentValue() == null ? null : new ContactVO(c.getCurrentValue().getNicHandle(), c.getCurrentValue().getName());
        this.failureReasonId = c.getFailureReason() == null ? null : c.getFailureReason().getId();
        this.failureReason = c.getFailureReason() == null ? null : c.getFailureReason().getDescription();
    }
}
