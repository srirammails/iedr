package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DuplicatedContactException extends ValidationException {

    private String nicHandleId;

    public DuplicatedContactException(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    @Override
    public String getMessage() {
        return String.format("Duplicated contac: %s", nicHandleId);
    }
}
