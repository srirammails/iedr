package pl.nask.crs.payment.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class DepositNotFoundException extends Exception {

    public DepositNotFoundException() {
    }

    public DepositNotFoundException(String message) {
        super(message);
    }

    public DepositNotFoundException(Throwable cause) {
        super(cause);
    }

    public DepositNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
