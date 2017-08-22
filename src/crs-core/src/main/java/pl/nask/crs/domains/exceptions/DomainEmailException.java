package pl.nask.crs.domains.exceptions;

/**
 * @author Kasia Fulara
 */
public class DomainEmailException extends Exception {
    public DomainEmailException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DomainEmailException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DomainEmailException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DomainEmailException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
