package pl.nask.crs.domains.dsm;

import java.math.BigDecimal;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.domains.dsm.events.AbstractEvent;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.TransactionDetails;

public class PaymentInitiated extends AbstractEvent {
	public PaymentInitiated(PaymentMethod paymentMethod, TransactionDetails transactionDetails, BigDecimal transactionValue, String orderId) {
		super(DsmEventName.PaymentInitiated);
		setParameter(PAY_METHOD, paymentMethod);
		setParameter(TRANSACTION_DETAIL, transactionDetails);
		setParameter(TRANSACTION_VALUE, MoneyUtils.getRoudedAndScaledValue(transactionValue));
		setParameter(ORDER_ID, orderId);
	}
}
