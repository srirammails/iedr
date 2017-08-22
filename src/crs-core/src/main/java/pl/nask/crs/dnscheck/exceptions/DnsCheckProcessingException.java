package pl.nask.crs.dnscheck.exceptions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsCheckProcessingException extends Exception {

    public DnsCheckProcessingException(Throwable throwable) {
        super(throwable);
    }

    public DnsCheckProcessingException(String s) {
        super(s);
    }
}
