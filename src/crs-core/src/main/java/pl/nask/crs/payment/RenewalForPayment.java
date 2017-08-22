package pl.nask.crs.payment;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.vat.PriceWithVat;

public class RenewalForPayment {
	private Domain domain;
	private PriceWithVat price;
	
	public RenewalForPayment(Domain domain, PriceWithVat priceWithVat) {
		this.domain = domain;
		price = priceWithVat;
	}
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public PriceWithVat getPrice() {
		return price;
	}
	public void setPrice(PriceWithVat price) {
		this.price = price;
	}
}
