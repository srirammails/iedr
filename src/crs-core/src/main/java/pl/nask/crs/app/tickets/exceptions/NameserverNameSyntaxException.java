package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class NameserverNameSyntaxException extends ValidationException {

    private String nsName;

    public NameserverNameSyntaxException(String nsName) {
        this.nsName = nsName;
    }

    public String getNsName() {
        return nsName;
    }
    
    public NameserverNameSyntaxException(Throwable e, String nsName) {
    	super(e);
    	this.nsName = nsName;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid nameserver name: %s", nsName);
    }
}
