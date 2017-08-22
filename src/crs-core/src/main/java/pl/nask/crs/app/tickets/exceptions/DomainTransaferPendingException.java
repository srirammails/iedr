package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainTransaferPendingException extends ValidationException {

    private static final long serialVersionUID = 5288043055293803664L;
    private final String domainName;

    public DomainTransaferPendingException(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    @Override
   	public String getMessage() {
   		return String.format("Transfer ticket pending for domain: %s", domainName);
   	}
}
