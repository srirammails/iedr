package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class HolderClassMandatoryException extends ValidationException {
    @Override
    public String getMessage() {
        return "Holder class is mandatory";
    }
}
