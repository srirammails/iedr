package pl.nask.crs.payment.service;

import pl.nask.crs.payment.exceptions.PaymentException;

/**
 * @author: Marcin Tkaczyk
 */
public interface PaymentSender {

    String send(String commandXML) throws PaymentException;

}
