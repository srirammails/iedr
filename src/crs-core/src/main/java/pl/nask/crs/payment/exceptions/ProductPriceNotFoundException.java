package pl.nask.crs.payment.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class ProductPriceNotFoundException extends Exception {

    public ProductPriceNotFoundException() {
    }

    public ProductPriceNotFoundException(String message) {
        super(message);
    }

    public ProductPriceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProductPriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
