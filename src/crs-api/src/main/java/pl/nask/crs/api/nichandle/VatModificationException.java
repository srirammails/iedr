package pl.nask.crs.api.nichandle;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class VatModificationException extends Exception {
    public VatModificationException() {
        super("Country modification is not allowed since it changes vat category.");
    }
}
