package pl.nask.crs.commons;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class MoneyUtils {

    public static final MathContext mc = new MathContext(9, RoundingMode.HALF_EVEN);
    public static final int DEFAULT_SCALE = 2;
    private static final int LOWEST_CURRENCY_MULTIPLIER = 100;

    /**
     * Returns BigDecimal with precision of 2 (or 0, if float represents a whole value)
     *
     * @param value
     * @return
     */
    public static BigDecimal getRoudedAndScaledValue(BigDecimal value) {
        if (value == null)
            return null;

        if (value.doubleValue() - value.intValue() != 0)
            return value.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_EVEN);
        else
            return value.setScale(0, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * Returns BigDecimal with precision of scale value
     *
     * @param value
     * @return
     */
    public static BigDecimal getRoudedAndScaledValue(BigDecimal value, int scale) {
        if (value == null)
            return null;
        return value.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
    }

    public static double getRoudedAndScaledDoubleValue(BigDecimal value) {
        if (value == null)
            return 0;

        if (value.doubleValue() - value.intValue() != 0)
            return value.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        else
            return value.setScale(0, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    /**
     * return BigDecimal with precision of 2 (or 0, if float represents a whole value)
     * @param money
     * @return
     */
    public static BigDecimal getRoudedAndScaledValue(double money) {
    	long v = (long) money;

    	if (money - v != 0)
    		return BigDecimal.valueOf(money).setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_EVEN);
    	else
    		return BigDecimal.valueOf(money).setScale(0);
    }
    
    /**
     * return BigDecimal with a given precision
     * @param money
     * @param scale
     * @return
     */
    public static BigDecimal getRoudedAndScaledValue(double money, int scale) {
  		return BigDecimal.valueOf(money).setScale(scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * Returns value in the lowest unit of the currency i.e. 100 euro would be entered as 10000.
     *
     * @param value
     * @return
     */
    public static int getValueInLowestCurrencyUnit(double value) {
        BigDecimal bAmount = BigDecimal.valueOf(value);
        return getValueInLowestCurrencyUnit(bAmount);
    }

    public static int getValueInLowestCurrencyUnit(BigDecimal value) {
        BigDecimal ret = value.multiply(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return ret.setScale(0, BigDecimal.ROUND_HALF_EVEN).intValue();
    }

    public static double getValueInStandardCurrencyUnit(int value) {
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoudedAndScaledDoubleValue(bAmount);
    }

    public static BigDecimal getBigDecimalValueInStandardCurrencyUnit(int value) {
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoudedAndScaledValue(bAmount);
    }
    
    public static BigDecimal getBigDecimalValueInStandardCurrencyUnit(Integer value) {
    	if (value == null)
    		return null;
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoudedAndScaledValue(bAmount);
    }

    public static BigDecimal getBigDecimalValueInStandardCurrencyUnit(int value, int scale) {
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoudedAndScaledValue(bAmount, scale);
    }
    
    public static BigDecimal getBigDecimalValueInStandardCurrencyUnit(Integer value, int scale) {
    	if (value == null)
    		return null;
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoudedAndScaledValue(bAmount, scale);
    }

    public static double substract(double minuend, double subreahend) {
        BigDecimal retVal = BigDecimal.valueOf(minuend);
        retVal = retVal.subtract(BigDecimal.valueOf(subreahend), mc);
        return getRoudedAndScaledDoubleValue(retVal);
    }

    public static BigDecimal substract(BigDecimal minuend, BigDecimal subreahend) {
        BigDecimal retVal = minuend;
        retVal = retVal.subtract(subreahend, mc);
        return getRoudedAndScaledValue(retVal);
    }

    public static double add(double value1, double value2) {
        BigDecimal retVal = BigDecimal.valueOf(value1);
        retVal = retVal.add(BigDecimal.valueOf(value2), mc);
        return getRoudedAndScaledDoubleValue(retVal);
    }

    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        BigDecimal retVal = value1;
        retVal = retVal.add(value2, mc);
        return getRoudedAndScaledValue(retVal);
    }

    public static double multiply(double value1, double value2) {
        BigDecimal retVal = BigDecimal.valueOf(value1);
        retVal = retVal.multiply(BigDecimal.valueOf(value2), mc);
        return getRoudedAndScaledDoubleValue(retVal);
    }

    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
        BigDecimal retVal = value1;
        retVal = retVal.multiply(value2, mc);
        return getRoudedAndScaledValue(retVal);
    }

    public static BigDecimal divide(double value1, double value2) {
        BigDecimal retVal = BigDecimal.valueOf(value1);
        retVal = retVal.divide(BigDecimal.valueOf(value2), mc);
        return getRoudedAndScaledValue(retVal);
    }
    
    public static BigDecimal divide(BigDecimal value1, double value2) {
        BigDecimal retVal = value1;
        retVal = retVal.divide(BigDecimal.valueOf(value2), mc);
        return getRoudedAndScaledValue(retVal);
    }
}
