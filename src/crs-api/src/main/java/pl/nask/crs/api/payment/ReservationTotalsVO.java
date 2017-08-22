package pl.nask.crs.api.payment;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.ReservationTotals;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationTotalsVO {
    private long totalResults;

    private BigDecimal totalAmount;

	private BigDecimal totalVat;

	private BigDecimal totalWithVat;
    
    public ReservationTotalsVO() {
	}
    
    public ReservationTotalsVO(ReservationTotals totals) {
		this.totalResults = totals.getTotalResults();
		this.totalAmount = totals.getTotalAmount();
		this.totalVat = totals.getTotalVat();
		this.totalWithVat = totals.getTotal();
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
    
    public long getTotalResults() {
		return totalResults;
	}
    
    public BigDecimal getTotalVat() {
		return totalVat;
	}
    
    public BigDecimal getTotalWithVat() {
		return totalWithVat;
	}
}
