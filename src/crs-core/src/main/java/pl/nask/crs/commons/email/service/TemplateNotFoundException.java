package pl.nask.crs.commons.email.service;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TemplateNotFoundException extends EmailException {

    public TemplateNotFoundException(String message) {
        super(message);
    }
}
