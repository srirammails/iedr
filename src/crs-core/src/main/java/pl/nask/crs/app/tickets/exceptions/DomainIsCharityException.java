package pl.nask.crs.app.tickets.exceptions;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class DomainIsCharityException extends Exception {

    private final String domainName;

    public DomainIsCharityException(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }
}
