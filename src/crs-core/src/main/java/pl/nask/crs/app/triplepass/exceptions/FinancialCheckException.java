package pl.nask.crs.app.triplepass.exceptions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class FinancialCheckException extends Exception {

    public FinancialCheckException() {
    }

    public FinancialCheckException(String s) {
        super(s);
    }

    public FinancialCheckException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FinancialCheckException(Throwable throwable) {
        super(throwable);
    }
}
