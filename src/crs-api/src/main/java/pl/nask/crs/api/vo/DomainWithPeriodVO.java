package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainWithPeriodVO {

    private String domainName;
    private Integer periodInYears;

    public DomainWithPeriodVO() {
    }

    public DomainWithPeriodVO(String domainName, Integer periodInYears) {
        this.domainName = domainName;
        this.periodInYears = periodInYears;
    }

    public String getDomainName() {
        return domainName;
    }

    public Integer getPeriodInYears() {
        return periodInYears;
    }
}
