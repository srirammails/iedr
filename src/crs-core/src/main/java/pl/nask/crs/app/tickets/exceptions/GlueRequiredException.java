package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class GlueRequiredException extends ValidationException {

    private String nsName;

    public GlueRequiredException(String nsName) {
        this.nsName = nsName;
    }

    public String getNsName() {
        return nsName;
    }

    @Override
    public String getMessage() {
        return String.format("Glue is required for namesever: %s", nsName);
    }
}
