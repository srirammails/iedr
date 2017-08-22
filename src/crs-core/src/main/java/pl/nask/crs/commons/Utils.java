package pl.nask.crs.commons;

import java.math.BigDecimal;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public final class Utils {

    public static boolean hasThirdDecimalPlace(BigDecimal price, double vatRate) {
        BigDecimal v = BigDecimal.valueOf(vatRate);
        BigDecimal vatValue = price.multiply(v);
        if (BigDecimal.ZERO.compareTo(vatValue) == 0) {
            return false;
        }
        //if you wont recreate there will be problem with scale!!!
        BigDecimal stripped = new BigDecimal(vatValue.stripTrailingZeros().toPlainString());
        String vatValueAsString = stripped.toPlainString();
        int decimalPointIndex = vatValueAsString.indexOf(".");
        int decimalPlaces = decimalPointIndex < 0 ? 0 : vatValueAsString.length() - decimalPointIndex - 1;
        return decimalPlaces >= 3;
    }

}
