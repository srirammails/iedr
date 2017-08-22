package pl.nask.crs.commons;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import pl.nask.crs.commons.Period;
import pl.nask.crs.vat.PriceWithVat;
import java.math.BigDecimal;
import java.util.Date;

import pl.nask.crs.vat.Vat;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceWithVatTest {

    @Test
    public void vatTest() {
        Vat vat = new Vat(1, "A" , new Date(), 0.23);
        PriceWithVat priceWithVat = new PriceWithVat(Period.fromYears(1), "Std1Year", BigDecimal.valueOf(55), vat);
        AssertJUnit.assertEquals(BigDecimal.valueOf(55), priceWithVat.getNetAmount());
        AssertJUnit.assertEquals(0.23, priceWithVat.getVat().getVatRate());
        AssertJUnit.assertEquals(BigDecimal.valueOf(12.65), priceWithVat.getVatAmount());
        AssertJUnit.assertEquals(BigDecimal.valueOf(67.65), priceWithVat.getTotal());
        AssertJUnit.assertEquals("Std1Year", priceWithVat.getProductCode());

        vat = new Vat(1, "A" , new Date(), 0.233);
        priceWithVat = new PriceWithVat(Period.fromYears(1), "Std1Year", BigDecimal.valueOf(55.33), vat);
        AssertJUnit.assertEquals(BigDecimal.valueOf(55.33), priceWithVat.getNetAmount());
        AssertJUnit.assertEquals(0.233, priceWithVat.getVat().getVatRate());
        AssertJUnit.assertEquals(BigDecimal.valueOf(12.89), priceWithVat.getVatAmount());
        AssertJUnit.assertEquals(BigDecimal.valueOf(68.22), priceWithVat.getTotal());
        AssertJUnit.assertEquals("Std1Year", priceWithVat.getProductCode());
    }
}
