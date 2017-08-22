package pl.nask.crs.payment.service;

import pl.nask.crs.payment.CardType;
import pl.nask.crs.payment.ExtendedPaymentRequest;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.service.impl.CardAuthDetails;

public interface CardPaymentService {
    public ExtendedPaymentRequest authorisePaymentTransaction(PaymentRequest request, CardType cardType) throws PaymentException;

	public void cancelRealexAuthorisation(String orderId, CardAuthDetails cardAuthDetails) throws PaymentException;

	public void settleRealexAuthorisation(String orderId, CardAuthDetails cardAuthDetails) throws PaymentException;

	public void settleRealexAuthorisation(ExtendedPaymentRequest extRequest) throws PaymentException;

	public void cancelRealexAuthorisation(ExtendedPaymentRequest extRequest) throws PaymentException;

}
