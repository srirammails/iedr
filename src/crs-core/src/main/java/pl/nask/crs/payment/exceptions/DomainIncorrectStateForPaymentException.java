package pl.nask.crs.payment.exceptions;


/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainIncorrectStateForPaymentException extends Exception {

    private String domainName;
    private String causeStr;

    public DomainIncorrectStateForPaymentException() {
    }

    public DomainIncorrectStateForPaymentException(String domainName, String cause) {
        super("Payment for " + domainName + " is not allowed (" + cause + ")");
        this.domainName = domainName;
        this.causeStr = cause;
    }

    public String getDomainName() {
        return domainName;
    }
}
