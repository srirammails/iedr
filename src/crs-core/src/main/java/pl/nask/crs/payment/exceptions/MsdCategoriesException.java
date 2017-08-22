package pl.nask.crs.payment.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class MsdCategoriesException extends Exception {
    public MsdCategoriesException() {
    }

    public MsdCategoriesException(String message) {
        super(message);
    }

    public MsdCategoriesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MsdCategoriesException(Throwable cause) {
        super(cause);
    }
}
