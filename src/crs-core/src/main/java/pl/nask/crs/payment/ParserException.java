package pl.nask.crs.payment;

/**
 * @author: Marcin Tkaczyk
 */
public class ParserException extends Exception {

    public ParserException() {
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }
}
