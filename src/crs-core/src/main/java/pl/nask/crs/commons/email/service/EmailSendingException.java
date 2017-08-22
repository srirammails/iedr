package pl.nask.crs.commons.email.service;

/**
 * @author Patrycja Wegrzynowicz
 */
public class EmailSendingException extends EmailException {

    public EmailSendingException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public EmailSendingException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public EmailSendingException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
