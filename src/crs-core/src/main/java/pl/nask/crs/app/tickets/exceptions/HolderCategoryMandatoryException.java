package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class HolderCategoryMandatoryException extends ValidationException {

    @Override
    public String getMessage() {
        return "Holder category is mandatory";
    }
}
