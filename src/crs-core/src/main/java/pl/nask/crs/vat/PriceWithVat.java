package pl.nask.crs.vat;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.Period;

import java.math.BigDecimal;

import static pl.nask.crs.commons.MoneyUtils.*;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceWithVat {

    private final Period period;
    private final String productCode;
    private final BigDecimal netAmount;
    private final Vat vat;
    private final BigDecimal vatAmount;
    private final BigDecimal total;

    public PriceWithVat(Period period, String productCode, BigDecimal netAmount, Vat vat) {
        this.period = period;
        this.productCode = productCode;
        this.netAmount = netAmount;
        this.netAmount.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_EVEN);
        this.vat = vat;
        this.vatAmount = MoneyUtils.multiply(netAmount, BigDecimal.valueOf(vat.getVatRate()));
        this.total = MoneyUtils.add(this.netAmount, this.vatAmount);
    }

    public PriceWithVat(Period period, String productCode, double netAmount, Vat vat) {
        this.period = period;
        this.productCode = productCode;
        this.netAmount = getRoudedAndScaledValue(netAmount);
        this.vat = vat;
        this.vatAmount = MoneyUtils.multiply(BigDecimal.valueOf(netAmount), BigDecimal.valueOf(vat.getVatRate()));
        this.total = MoneyUtils.add(this.netAmount, this.vatAmount);
    }

    public PriceWithVat(double netAmount, double vatAmount) {
        this.netAmount = getRoudedAndScaledValue(netAmount);
        this.vatAmount = getRoudedAndScaledValue(vatAmount);
        this.total = MoneyUtils.add(this.netAmount, this.vatAmount);
        this.period = null;
        this.productCode = null;
        this.vat = null;
    }

    public Period getPeriod() {
        return period;
    }

    public String getProductCode() {
        return productCode;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public Vat getVat() {
        return vat;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return String.format("PriceWithVat[productCode: %s, netAmount: %s, vat: %s, vatAmount: %s, periodInYears: %s]", productCode, netAmount, vat, vatAmount, period == null ? null : period.getYears());
    }
}
