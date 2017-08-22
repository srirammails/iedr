package pl.nask.crs.app.tickets.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class NicHandleRecreateException extends Exception {
    public NicHandleRecreateException() {
    }

    public NicHandleRecreateException(String message) {
        super(message);
    }

    public NicHandleRecreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NicHandleRecreateException(Throwable cause) {
        super(cause);
    }
}
