package pl.nask.crs.payment;

import java.math.BigDecimal;
import static pl.nask.crs.commons.MoneyUtils.*;

public class ReservationTotals {

	private long totalResults;
	
	private BigDecimal totalAmount;
	
	private BigDecimal totalVat;

    private BigDecimal total;

    public ReservationTotals(long totalResults, double totalAmount, double totalVat) {
        this.totalResults = totalResults;
        this.totalAmount = getRoudedAndScaledValue(BigDecimal.valueOf(totalAmount), DEFAULT_SCALE);
        this.totalVat = getRoudedAndScaledValue(BigDecimal.valueOf(totalVat), DEFAULT_SCALE);
        this.total = getRoudedAndScaledValue(add(this.totalAmount, this.totalVat), DEFAULT_SCALE);
    }

    public long getTotalResults() {
		return totalResults;
	}

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalVat() {
        return totalVat;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return String.format("ReservationTotals[totalResults: %s, totalAmount: %s, totalVat: %s]", totalResults, totalAmount, totalVat);
    }
}
