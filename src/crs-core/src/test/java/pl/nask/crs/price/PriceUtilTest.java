package pl.nask.crs.price;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.Utils;

import java.math.BigDecimal;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class PriceUtilTest {

    @Test
    public void testHasThirdDecimalPlace() throws Exception {
        BigDecimal price;
        price = MoneyUtils.getRoudedAndScaledValue(BigDecimal.valueOf(100.1), 2);
        AssertJUnit.assertFalse(Utils.hasThirdDecimalPlace(price, 0.10));
        price = MoneyUtils.getRoudedAndScaledValue(BigDecimal.valueOf(100.1), 5);
        AssertJUnit.assertFalse(Utils.hasThirdDecimalPlace(price, 0.1));
        price = MoneyUtils.getRoudedAndScaledValue(BigDecimal.valueOf(100.00), 2);
        AssertJUnit.assertFalse(Utils.hasThirdDecimalPlace(price, 0.0));
        price = MoneyUtils.getRoudedAndScaledValue(BigDecimal.valueOf(100.00), 5);
        AssertJUnit.assertFalse(Utils.hasThirdDecimalPlace(price, 0.0));
        price = MoneyUtils.getRoudedAndScaledValue(BigDecimal.valueOf(100.00));
        AssertJUnit.assertFalse(Utils.hasThirdDecimalPlace(price, 0.0));
        price = MoneyUtils.getRoudedAndScaledValue(BigDecimal.valueOf(0));
        AssertJUnit.assertFalse(Utils.hasThirdDecimalPlace(price, 0));
        price = MoneyUtils.getRoudedAndScaledValue(BigDecimal.valueOf(100.2), 2);
        AssertJUnit.assertFalse(Utils.hasThirdDecimalPlace(price, 0.2));


        price = MoneyUtils.getRoudedAndScaledValue(BigDecimal.valueOf(100.54));
        AssertJUnit.assertTrue(Utils.hasThirdDecimalPlace(price, 0.32));
    }
}
