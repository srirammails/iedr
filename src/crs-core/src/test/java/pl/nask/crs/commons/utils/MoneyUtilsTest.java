package pl.nask.crs.commons.utils;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.math.BigDecimal;

import static pl.nask.crs.commons.MoneyUtils.*;

public class MoneyUtilsTest {
    @Test
    public void valueInLowestCurrencyUnitTest() {
        AssertJUnit.assertEquals(1810, getValueInLowestCurrencyUnit(18.10));
        AssertJUnit.assertEquals(1811, getValueInLowestCurrencyUnit(18.11));
        AssertJUnit.assertEquals(1812, getValueInLowestCurrencyUnit(18.12));
        AssertJUnit.assertEquals(1813, getValueInLowestCurrencyUnit(18.13));
        AssertJUnit.assertEquals(1814, getValueInLowestCurrencyUnit(18.14));
        AssertJUnit.assertEquals(1815, getValueInLowestCurrencyUnit(18.15));
        AssertJUnit.assertEquals(1816, getValueInLowestCurrencyUnit(18.16));
        AssertJUnit.assertEquals(1817, getValueInLowestCurrencyUnit(18.17));
        AssertJUnit.assertEquals(1818, getValueInLowestCurrencyUnit(18.18));
        AssertJUnit.assertEquals(1819, getValueInLowestCurrencyUnit(18.19));
        AssertJUnit.assertEquals(1820, getValueInLowestCurrencyUnit(18.2));
        AssertJUnit.assertEquals(1820, getValueInLowestCurrencyUnit(18.201));
        AssertJUnit.assertEquals(1820, getValueInLowestCurrencyUnit(18.205));
        AssertJUnit.assertEquals(1821, getValueInLowestCurrencyUnit(18.206));

        AssertJUnit.assertEquals(1810, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.10)));
        AssertJUnit.assertEquals(1811, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.11)));
        AssertJUnit.assertEquals(1812, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.12)));
        AssertJUnit.assertEquals(1813, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.13)));
        AssertJUnit.assertEquals(1814, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.14)));
        AssertJUnit.assertEquals(1815, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.15)));
        AssertJUnit.assertEquals(1816, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.16)));
        AssertJUnit.assertEquals(1817, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.17)));
        AssertJUnit.assertEquals(1818, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.18)));
        AssertJUnit.assertEquals(1819, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.19)));
        AssertJUnit.assertEquals(1820, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.2)));
        AssertJUnit.assertEquals(1820, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.201)));
        AssertJUnit.assertEquals(1820, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.205)));
        AssertJUnit.assertEquals(1821, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.206)));
        AssertJUnit.assertEquals(-1821, getValueInLowestCurrencyUnit(BigDecimal.valueOf(-18.206)));
    }

    @Test
    public void valueInStandardCurrencyUnit() {
        AssertJUnit.assertEquals(18.14, getValueInStandardCurrencyUnit(1814));
        AssertJUnit.assertEquals(18.15, getValueInStandardCurrencyUnit(1815));
        AssertJUnit.assertEquals(18.16, getValueInStandardCurrencyUnit(1816));
        AssertJUnit.assertEquals(18.17, getValueInStandardCurrencyUnit(1817));
        AssertJUnit.assertEquals(18.18, getValueInStandardCurrencyUnit(1818));
        AssertJUnit.assertEquals(18.19, getValueInStandardCurrencyUnit(1819));
        AssertJUnit.assertEquals(18.20, getValueInStandardCurrencyUnit(1820));
        AssertJUnit.assertEquals(18.21, getValueInStandardCurrencyUnit(1821));
        AssertJUnit.assertEquals(182.15, getValueInStandardCurrencyUnit(18215));
        AssertJUnit.assertEquals(182.16, getValueInStandardCurrencyUnit(18216));
        AssertJUnit.assertEquals(-182.16, getValueInStandardCurrencyUnit(-18216));
    }

    @Test
    public void substractionTest() {
        AssertJUnit.assertEquals(65635.01, substract(65651.95, 16.94));
        AssertJUnit.assertEquals(65616.86, substract(65635.01, 18.15));
        AssertJUnit.assertEquals(65549.15, substract(65566.09, 16.94));
        AssertJUnit.assertEquals(65549.15, substract(65566.09, 16.94));
        AssertJUnit.assertEquals(9999999.08, substract(10000000.03, 0.95));
        AssertJUnit.assertEquals(9999999.08, substract(10000000.03, 0.95f));

        AssertJUnit.assertEquals(BigDecimal.valueOf(65635.01), substract(BigDecimal.valueOf(65651.95), BigDecimal.valueOf(16.94)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(65616.86), substract(BigDecimal.valueOf(65635.01), BigDecimal.valueOf(18.15)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(65549.15), substract(BigDecimal.valueOf(65566.09), BigDecimal.valueOf(16.94)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(65549.15), substract(BigDecimal.valueOf(65566.09), BigDecimal.valueOf(16.94)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(9999999.08), substract(BigDecimal.valueOf(10000000.03), BigDecimal.valueOf(0.95)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(9999999.08), substract(BigDecimal.valueOf(10000000.03), BigDecimal.valueOf(0.95f)));

        AssertJUnit.assertEquals(BigDecimal.valueOf(-9999999.08), substract(BigDecimal.valueOf(-10000000.03), BigDecimal.valueOf(-0.95f)));
    }

    @Test
    public void additionTest() {
        AssertJUnit.assertEquals(9000000.98, add(9000000.03, 0.95));
        AssertJUnit.assertEquals(9000001.09, add(9000000.14, 0.95));

        AssertJUnit.assertEquals(-9000001.09, add(-9000000.14, -0.95));
    }

    @Test
    public void multiplicationTest() {
        AssertJUnit.assertEquals(13.65, multiply(65.00, 0.21));
        AssertJUnit.assertEquals(20.91, multiply(99.55, 0.21));

        AssertJUnit.assertEquals(-20.91, multiply(-99.55, 0.21));
    }

}
