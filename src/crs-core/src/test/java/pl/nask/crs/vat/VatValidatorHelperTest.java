package pl.nask.crs.vat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.utils.DateUtils;

public class VatValidatorHelperTest {
	private Vat rate0 = Vat.newInstance("A", DateUtils.getCurrDate(-100), 1);
	private Vat rate1 = Vat.newInstance("A", DateUtils.getCurrDate(-10), 0.20);
	private Vat rate2 = Vat.newInstance("A", DateUtils.getCurrDate(0), 0.22);
	private Vat rate3 = Vat.newInstance("A", DateUtils.getCurrDate(10), 0.21);
	private Vat rate4 = Vat.newInstance("A", DateUtils.getCurrDate(20), 1);
	
	private List<Vat> vatList = new ArrayList<Vat>(Arrays.asList(rate0, rate1, rate2, rate3, rate4));
	
	private BigDecimal price = BigDecimal.valueOf(1.5);
	
	@Test
	public void testPriceValidRate1() throws ThirdDecimalPlaceException {
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(-20), DateUtils.getCurrDate(-9));
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(-10), DateUtils.getCurrDate(-9));
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(-9), DateUtils.getCurrDate(-8));
	}
	
	@Test
	public void testPriceValidRate2() throws ThirdDecimalPlaceException {
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(-20), DateUtils.getCurrDate(1));
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(-10), DateUtils.getCurrDate(1));
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(0), DateUtils.getCurrDate(1));
	}
	
	@Test(expectedExceptions=ThirdDecimalPlaceException.class)
	public void testPriceInvalidRate3InsideCheckedPeriod() throws ThirdDecimalPlaceException {
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(-20), DateUtils.getCurrDate(100));
	}
	
	@Test(expectedExceptions=ThirdDecimalPlaceException.class)
	public void testPriceInvalidRate3AllCheckedPeriodInside() throws ThirdDecimalPlaceException {
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(11), DateUtils.getCurrDate(12));
	}
	
	@Test(expectedExceptions=ThirdDecimalPlaceException.class)
	public void testPriceInvalidRate3PeriodEnd() throws ThirdDecimalPlaceException {
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(9), DateUtils.getCurrDate(10));
	}
	
	@Test(expectedExceptions=ThirdDecimalPlaceException.class)
	public void testPriceInvalidRate3PeriodStart() throws ThirdDecimalPlaceException {
		VatValidatorHelper.validatePrice(vatList, price, DateUtils.getCurrDate(19), DateUtils.getCurrDate(20));
	}
}
