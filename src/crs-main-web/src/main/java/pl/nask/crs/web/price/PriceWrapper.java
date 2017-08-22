package pl.nask.crs.web.price;

import java.math.BigDecimal;
import java.util.Date;

import pl.nask.crs.price.DomainPrice;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceWrapper {

    private String id;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Date validFrom;
    private Date validTo;
    private boolean forRegistration;
    private boolean forRenewal;
    private boolean direct;

    public PriceWrapper() {
    }

    public PriceWrapper(DomainPrice domainPrice) {
        this.id = domainPrice.getId();
        this.description = domainPrice.getDescription();
        this.price = domainPrice.getPrice();
        this.duration = domainPrice.getDuration();
        this.validFrom = domainPrice.getValidFrom();
        this.validTo = domainPrice.getValidTo();
        this.forRegistration = domainPrice.isForRegistration();
        this.forRenewal = domainPrice.isForRenewal();
        this.direct = domainPrice.isDirect();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public boolean isForRegistration() {
        return forRegistration;
    }

    public void setForRegistration(boolean forRegistration) {
        this.forRegistration = forRegistration;
    }

    public boolean isForRenewal() {
        return forRenewal;
    }

    public void setForRenewal(boolean forRenewal) {
        this.forRenewal = forRenewal;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }
}
