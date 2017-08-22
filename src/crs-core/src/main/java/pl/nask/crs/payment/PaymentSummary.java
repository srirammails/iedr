package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.vat.PriceWithVat;

/**
 * @author: Marcin Tkaczyk
 */
public class PaymentSummary {
    private final Payment totalPayment;
    private final String orderId;
    private final List<PaymentDomain> paymentDomains;
    
    private final Comparator<PaymentDomain> paymentDomainComparator = new PaymentDomainComparator();

    public PaymentSummary(Map<Domain, PriceWithVat> pricePerDomain, Payment totalPayment, String orderId) {
        this.totalPayment = totalPayment;
        this.orderId = orderId;
        this.paymentDomains = preparePaymentDomains(pricePerDomain);
    }

    public PaymentSummary(String domainName, Date registrationDate, Date renewalDate, int periodInYears, BigDecimal totalFee, BigDecimal totalVat, BigDecimal total, String orderId) {
        this.totalPayment = new Payment(totalFee, totalVat, total);
        this.orderId = orderId;
        this.paymentDomains = Arrays.asList(new PaymentDomain(domainName, registrationDate, renewalDate, periodInYears, totalFee, totalVat, total));
    }

    private List<PaymentDomain> preparePaymentDomains(Map<Domain, PriceWithVat> pricePerDomain) {
        List<PaymentDomain> ret = new ArrayList<PaymentDomain>(pricePerDomain.size());
        for (Map.Entry<Domain, PriceWithVat> entry : pricePerDomain.entrySet()) {
            Domain domain = entry.getKey();
            PriceWithVat priceWithVat = entry.getValue();
            ret.add(new PaymentDomain(
                    domain.getName(),
                    domain.getRegistrationDate(),
                    domain.getRenewDate(),
                    priceWithVat.getPeriod().getYears(),
                    priceWithVat.getNetAmount(),
                    priceWithVat.getVatAmount(),
                    priceWithVat.getTotal()));
        }
        Collections.sort(ret, paymentDomainComparator);
        
        return ret;
    }

    public BigDecimal getFee() {
        return totalPayment.getFee();
    }

    public BigDecimal getVat() {
        return totalPayment.getVat();
    }

    public BigDecimal getTotal() {
        return totalPayment.getTotal();
    }

    public String getOrderId() {
        return orderId;
    }

    public List<PaymentDomain> getPaymentDomains() {
        return paymentDomains;
    }

    class PaymentDomainComparator implements Comparator<PaymentDomain> {
		@Override
		public int compare(PaymentDomain o1, PaymentDomain o2) {
			return o1.getDomainName().compareToIgnoreCase(o2.getDomainName());
		}
	}

}
