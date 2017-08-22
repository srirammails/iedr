package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class HolderRemarkTooLongException extends ValidationException {
    @Override
    public String getMessage() {
        return "Holder remark is too long";
    }
}
