package pl.nask.crs.web.validators;

import java.math.BigDecimal;

import com.opensymphony.xwork2.validator.validators.AbstractRangeValidator;

public class BigDecimalValidator extends AbstractRangeValidator {
	private BigDecimal max;
	private BigDecimal min;	
	
	
	
	
	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	@Override
	protected Comparable getMaxComparatorValue() {
		return max;
	}

	@Override
	protected Comparable getMinComparatorValue() {
		return min;
	}

}

