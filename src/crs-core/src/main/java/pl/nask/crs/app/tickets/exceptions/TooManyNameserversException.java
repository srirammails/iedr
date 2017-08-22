package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class TooManyNameserversException extends ValidationException {
    @Override
    public String getMessage() {
        return "Too many nameservers";
    }
}
