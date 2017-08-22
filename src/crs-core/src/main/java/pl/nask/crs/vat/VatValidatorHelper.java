package pl.nask.crs.vat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pl.nask.crs.commons.Utils;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.utils.DateUtils;


public final class VatValidatorHelper {
	private VatValidatorHelper() {
		// hiding constructor of helper class
	}

	static public void validatePrice(List<Vat> vatRates, BigDecimal price, Date validFrom, Date validTo) throws ThirdDecimalPlaceException {
		List<Vat> rates = new LinkedList<Vat>(vatRates);
		while (!rates.isEmpty()) {
			Vat rate = rates.remove(0);
			if (valid(rate, validFrom, validTo, vatRates)) {
				if (Utils.hasThirdDecimalPlace(price, rate.getVatRate())) {
					String msg = String.format("Vat rate: %s, Price: %s valid from %s to %s", new Object[]{rate, price, validFrom, validTo});
					throw new ThirdDecimalPlaceException(msg);
				}
			}
		}
	}

	/**
	 * check if the vat rate given as the first argument is valid (may be used) in a period determined by from and to dates.  
	 * 
	 * @param rate
	 * @param from
	 * @param validTo
	 * @param otherVatRates
	 * @return
	 */
	static private boolean valid(Vat rate, Date from, Date validTo, List<Vat> otherVatRates) {
		if (DateUtils.isDateBetween(rate.getFromDate(), from, validTo, true, true)) {
			return true; // starts after the from, ends before it ends 
		} 

		if (DateUtils.isDateBetween(rate.getFromDate(), validTo, null, false, false)) {
			return false; // the vat rate starts 
		}

		// now: the vat.from is earlier, than 'from' date: have to check, if there is no vat rate which would invalidate this one before the 'from' date.

		for (Vat r:otherVatRates) {

			if (rate.getCategory().equals(r.getCategory()) 
					&&
					DateUtils.isDateBetween(r.getFromDate(), rate.getFromDate(), from, false, true)) 
			{
				return false; // there is a date, which would invalidate the one being checked
			}
		}

		return true;

	}

}
