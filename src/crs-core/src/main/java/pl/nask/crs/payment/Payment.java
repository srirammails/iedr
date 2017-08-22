package pl.nask.crs.payment;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.vat.PriceWithVat;

import java.math.BigDecimal;
import java.util.Map;

import static pl.nask.crs.commons.MoneyUtils.*;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class Payment {

    private final BigDecimal fee;
    private final BigDecimal vat;
    private final BigDecimal total;

    public Payment(BigDecimal fee, BigDecimal vat, BigDecimal total) {
        this.fee = fee;
        this.vat = vat;
        this.total = total;
    }

    public Payment(Map<Domain, PriceWithVat> pricePerDomain) {
        BigDecimal tmpFee = BigDecimal.ZERO;
        BigDecimal tmpVat = BigDecimal.ZERO;
        for (PriceWithVat priceWithVat : pricePerDomain.values()) {
            tmpFee = add(tmpFee, priceWithVat.getNetAmount());
            tmpVat = add(tmpVat, priceWithVat.getVatAmount());
        }
        this.fee = tmpFee;
        this.vat = tmpVat;
        this.total = add(this.fee, this.vat);
    }

    public BigDecimal getFee() {
        return getRoudedAndScaledValue(fee);
    }

    public BigDecimal getVat() {
        return getRoudedAndScaledValue(vat);
    }

    public BigDecimal getTotal() {
        return getRoudedAndScaledValue(total);
    }
}
