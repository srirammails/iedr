package pl.nask.crs.app.tickets.exceptions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainIsNotCharityException extends Exception {

    private final String domainName;

    public DomainIsNotCharityException(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }
}
