package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainNameSyntaxException extends ValidationException {

    private String domainName;

    public DomainNameSyntaxException(String domainName) {
        this.domainName = domainName;
    }
    
    public DomainNameSyntaxException(Throwable cause, String domainName) {
    	super(cause);
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid syntax of a domain: %s", domainName);
    }
}
