package pl.nask.crs.api.vo;

import pl.nask.crs.user.InternalLoginUser;

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
public class InternalLoginUserVO {

    private String nicHandleId;
    private String ipAddress;

    public InternalLoginUserVO() {}

    public InternalLoginUserVO(InternalLoginUser user) {
        this.nicHandleId = user.getNicHandleId();
        this.ipAddress = user.getIpAddress();
    }
}
