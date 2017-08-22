package pl.nask.crs.payment.exceptions;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainManagedByAnotherResellerException extends Exception {

    private String domainName;

    public DomainManagedByAnotherResellerException() {
    }

    public DomainManagedByAnotherResellerException(String domainName) {
        super();
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }
}
