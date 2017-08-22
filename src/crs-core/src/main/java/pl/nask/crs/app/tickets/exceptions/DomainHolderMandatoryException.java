package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainHolderMandatoryException extends ValidationException {

    @Override
    public String getMessage() {
        return "Domain holder is mandatory";
    }
}
