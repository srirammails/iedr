package pl.nask.crs.payment.exceptions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DuplicatedDomainException extends Exception {

    private String domainName;

    public DuplicatedDomainException(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }
}
