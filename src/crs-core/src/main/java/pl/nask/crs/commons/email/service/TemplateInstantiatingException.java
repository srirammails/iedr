package pl.nask.crs.commons.email.service;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TemplateInstantiatingException extends EmailException {
    public TemplateInstantiatingException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TemplateInstantiatingException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TemplateInstantiatingException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TemplateInstantiatingException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
