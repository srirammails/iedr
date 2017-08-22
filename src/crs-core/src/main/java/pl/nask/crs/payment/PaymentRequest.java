package pl.nask.crs.payment;

import pl.nask.crs.commons.MoneyUtils;

import java.math.BigDecimal;

import static pl.nask.crs.commons.MoneyUtils.getValueInLowestCurrencyUnit;

public class PaymentRequest {

    private final String currency;
    private int amountInLowestCurrencyUnit;
    private final String cardNumber;
    private final String cardExpDate;
    private final String cardType;
    private final String cardHolderName;
    private final Integer cvnNumber;
    private final Integer cvnPresenceIndicator;

    public PaymentRequest(String currency, double amountInStandardCurrencyUnit, String cardNumber,
			String cardExpDate, String cardType, String cardHolderName, Integer cvnNumber, Integer cvnPresenceIndicator) {
		this.currency = currency;
		this.amountInLowestCurrencyUnit = getValueInLowestCurrencyUnit(amountInStandardCurrencyUnit);
		this.cardNumber = cardNumber;
		this.cardExpDate = cardExpDate;
		this.cardType = cardType;
		this.cardHolderName = cardHolderName;
        this.cvnNumber = cvnNumber;
        this.cvnPresenceIndicator = cvnPresenceIndicator;
	}

    public PaymentRequest(String currency, String cardNumber,
			String cardExpDate, String cardType, String cardHolderName, Integer cvnNumber, Integer cvnPresenceIndicator) {
		this.currency = currency;
		this.cardNumber = cardNumber;
		this.cardExpDate = cardExpDate;
		this.cardType = cardType;
		this.cardHolderName = cardHolderName;
        this.cvnNumber = cvnNumber;
        this.cvnPresenceIndicator = cvnPresenceIndicator;
	}

	public String getCurrency() {
        return currency;
    }

    public int getAmountInLowestCurrencyUnit() {
        return amountInLowestCurrencyUnit;
    }

    public double getAmountInStandardCurrencyUnit() {
        return MoneyUtils.getValueInStandardCurrencyUnit(amountInLowestCurrencyUnit);
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

    public void setAmount(double amountInStandardCurrencyUnit) {
        this.amountInLowestCurrencyUnit = MoneyUtils.getValueInLowestCurrencyUnit(amountInStandardCurrencyUnit);
    }

    public void setAmount(BigDecimal amountInStandardCurrencyUnit) {
        this.amountInLowestCurrencyUnit = MoneyUtils.getValueInLowestCurrencyUnit(amountInStandardCurrencyUnit.doubleValue());
    }
}
