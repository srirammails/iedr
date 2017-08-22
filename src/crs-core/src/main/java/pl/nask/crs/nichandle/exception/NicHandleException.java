package pl.nask.crs.nichandle.exception;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleException  extends Exception {

    public NicHandleException() {
        super();
    }

    public NicHandleException(String message) {
        super(message);
    }

    public NicHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NicHandleException(Throwable cause) {
        super(cause);
    }
}
