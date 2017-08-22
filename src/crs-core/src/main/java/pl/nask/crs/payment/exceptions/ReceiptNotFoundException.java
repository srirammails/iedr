package pl.nask.crs.payment.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class ReceiptNotFoundException extends Exception {

    public ReceiptNotFoundException() {
    }

    public ReceiptNotFoundException(String message) {
        super(message);
    }

    public ReceiptNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReceiptNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
