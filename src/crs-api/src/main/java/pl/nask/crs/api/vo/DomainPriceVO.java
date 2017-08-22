package pl.nask.crs.api.vo;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.price.DomainPrice;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainPriceVO {

    private String id;
    private String description;
    private BigDecimal price;
    private int duration;
    private boolean defaultPrice;
    private boolean forRegistration;
    private boolean forRenewal;

    public DomainPriceVO() {
    }

    public DomainPriceVO(DomainPrice domainPrice) {
        this.id = domainPrice.getId();
        this.description = domainPrice.getDescription();
        this.price = domainPrice.getPrice();
        this.duration = domainPrice.getDuration();
        this.defaultPrice = domainPrice.isDefaultPrice();
        this.forRegistration = domainPrice.isForRegistration();
        this.forRenewal = domainPrice.isForRenewal();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isDefaultPrice() {
        return defaultPrice;
    }

    public boolean isForRegistration() {
        return forRegistration;
    }

    public boolean isForRenewal() {
        return forRenewal;
    }
}
