package pl.nask.crs.domains.dsm.events;

import java.math.BigDecimal;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.TransactionDetails;
import pl.nask.crs.ticket.Ticket;

public class TransferToRegistrar extends AbstractTransferEvent {
	public TransferToRegistrar(Domain oldDomain, Ticket t,  PaymentMethod paymentMethod, TransactionDetails transactionDetails, BigDecimal transactionValue, String orderId) {
		super(DsmEventName.TransferToRegistrar, oldDomain, t);
		setParameter(PAY_METHOD, paymentMethod);
		setParameter(TRANSACTION_DETAIL, transactionDetails);
		setParameter(TRANSACTION_VALUE, MoneyUtils.getRoudedAndScaledValue(transactionValue));
		setParameter(ORDER_ID, orderId);
	}
	
	public TransferToRegistrar(Domain oldDomain, Ticket t) {
		super(DsmEventName.TransferToRegistrar, oldDomain, t);
		setParameter(PAY_METHOD, null);
	}
	
	public TransferToRegistrar(Contact oldBillingC, Contact newBillingC) {
		super(DsmEventName.TransferToRegistrar, oldBillingC, newBillingC);
	}
}
