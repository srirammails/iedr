package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DuplicatedNameserverException extends ValidationException {

    private String nsName;

    public DuplicatedNameserverException(String nsName) {
        this.nsName = nsName;
    }

    public String getNsName() {
        return nsName;
    }

    @Override
    public String getMessage() {
        return String.format("Duplicated nameserver: %s", nsName);
    }
}
