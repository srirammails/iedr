package pl.nask.crs.commons.email.service;

/**
 * @author Patrycja Wegrzynowicz
 */
public class EmailException extends Exception {

    public EmailException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public EmailException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public EmailException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
