package pl.nask.crs.domains.exceptions;

/**
 * @author Kasia Fulara
 */
public class DomainTransferException extends Exception {
    public DomainTransferException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DomainTransferException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DomainTransferException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DomainTransferException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
