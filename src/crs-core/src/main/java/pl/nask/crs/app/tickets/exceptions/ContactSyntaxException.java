package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class ContactSyntaxException extends ValidationException {

    private String nicHandleId;

    public ContactSyntaxException(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid syntax of contact: %s", nicHandleId);
    }
}
