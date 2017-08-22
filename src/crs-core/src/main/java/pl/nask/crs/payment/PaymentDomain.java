package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class PaymentDomain {

    private final String domainName;
    private final Date registrationDate;
    private final Date renewalDate;
    private final int periodInYears;
    private final BigDecimal fee;
    private final BigDecimal vat;
    private final BigDecimal total;

    public PaymentDomain(String domainName, Date registrationDate, Date renewalDate, int periodInYears, BigDecimal fee, BigDecimal vat, BigDecimal total) {
        this.domainName = domainName;
        this.registrationDate = registrationDate;
        this.renewalDate = renewalDate;
        this.periodInYears = periodInYears;
        this.fee = fee;
        this.vat = vat;
        this.total = total;
    }

    public String getDomainName() {
        return domainName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public int getPeriodInYears() {
        return periodInYears;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
