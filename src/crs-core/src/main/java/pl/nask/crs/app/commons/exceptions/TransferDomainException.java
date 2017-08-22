package pl.nask.crs.app.commons.exceptions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransferDomainException extends Exception {

    private static final long serialVersionUID = -2420579049169718637L;

    public TransferDomainException(Throwable throwable) {
        super(throwable);
    }

    public Exception getExceptionCause() {
    	return (Exception) getCause();
    }
}
