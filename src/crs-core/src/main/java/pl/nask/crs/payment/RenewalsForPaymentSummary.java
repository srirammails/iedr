package pl.nask.crs.payment;


public class RenewalsForPaymentSummary {
	private long totalDomains;
	private long totalAmount;
	private long totalVat;
	
	public long getTotalDomains() {
		return totalDomains;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public long getTotalVat() {
		return totalVat;
	}
	public void setTotalVat(long totalVat) {
		this.totalVat = totalVat;
	}
	public void setTotalDomains(long totalDomains) {
		this.totalDomains = totalDomains;
	}
	public long getTotalAmountWithVat() {		
		return getTotalAmount() + getTotalVat();
	}
	
}
