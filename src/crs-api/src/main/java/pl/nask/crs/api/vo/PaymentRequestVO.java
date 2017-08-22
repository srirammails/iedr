package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.*;
import pl.nask.crs.payment.PaymentRequest;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentRequestVO {
    @XmlElement(required = true, nillable = false)
    private String currency;
    private double amount;
    @XmlElement(required = true, nillable = false)
    private String cardNumber;
    @XmlElement(required = true, nillable = false)
    private String cardExpDate;
    @XmlElement(required = true, nillable = false)
    private String cardType;
    @XmlElement(required = true, nillable = false)
    private String cardHolderName;
    private Integer cvnNumber;
    private Integer cvnPresenceIndicator;

    public PaymentRequestVO() {}

    public PaymentRequest toPaymentRequest() {
        return new PaymentRequest(
                this.currency,
                this.amount,
                this.cardNumber,
                this.cardExpDate,
                this.cardType,
                this.cardHolderName,
                this.cvnNumber,
                this.cvnPresenceIndicator
                );
    }

    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpDate() {
        return cardExpDate;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public Integer getCvnNumber() {
        return cvnNumber;
    }

    public Integer getCvnPresenceIndicator() {
        return cvnPresenceIndicator;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpDate(String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCvnNumber(Integer cvnNumber) {
        this.cvnNumber = cvnNumber;
    }

    public void setCvnPresenceIndicator(Integer cvnPresenceIndicator) {
        this.cvnPresenceIndicator = cvnPresenceIndicator;
    }
}
