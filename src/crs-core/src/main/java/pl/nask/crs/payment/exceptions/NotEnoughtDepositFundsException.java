package pl.nask.crs.payment.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class NotEnoughtDepositFundsException extends Exception {

    public NotEnoughtDepositFundsException() {
    }

    public NotEnoughtDepositFundsException(Throwable cause) {
        super(cause);
    }

    public NotEnoughtDepositFundsException(String message) {
        super(message);
    }

    public NotEnoughtDepositFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
