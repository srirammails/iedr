package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class GlueNotAllowedException extends ValidationException {

    private String nsName;

    public GlueNotAllowedException(String nsName) {
        this.nsName = nsName;
    }

    public String getNsName() {
        return nsName;
    }

    @Override
    public String getMessage() {
        return String.format("Glue not allowed for nameserver: %s", nsName);
    }
}
