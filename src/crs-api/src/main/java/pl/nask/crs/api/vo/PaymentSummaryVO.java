package pl.nask.crs.api.vo;

import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.payment.PaymentDomain;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentSummaryVO {
    private List<PaymentDomainVO> paymentDomains;
    private BigDecimal amount;
    private BigDecimal vat;
    private BigDecimal total;
    private String orderId;


    public PaymentSummaryVO() {
    }

    public PaymentSummaryVO(PaymentSummary paymentData) {
        this.paymentDomains = toVOList(paymentData.getPaymentDomains());
        this.amount = paymentData.getFee();
        this.vat = paymentData.getVat();
        this.total = paymentData.getTotal();
        this.orderId = paymentData.getOrderId();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getOrderId() {
        return orderId;
    }

    private List<PaymentDomainVO> toVOList(List<PaymentDomain> paymentDomains) {
        if (paymentDomains == null ||  paymentDomains.isEmpty()) {
            return null;
        } else {
            List<PaymentDomainVO> ret = new ArrayList<PaymentDomainVO>();
            for (PaymentDomain pd : paymentDomains) {
                ret.add(new PaymentDomainVO(pd));
            }
            return ret;
        }
    }
}
