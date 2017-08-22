package pl.nask.crs.payment.exceptions;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class ReceiptAlreadyExistsException extends Exception {

    private String domainName;

    public ReceiptAlreadyExistsException(String domainName) {
        super();
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }
}
