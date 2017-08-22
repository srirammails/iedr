package pl.nask.crs.payment.exceptions;

/**
 * Indicates an error with payment processing by an external system
 * 
 * @author: Marcin Tkaczyk
 */
public class PaymentException extends Exception {

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
