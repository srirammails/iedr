package pl.nask.crs.app.commons.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainIncorrectStateException extends ValidationException {
    private static final long serialVersionUID = 1866017080867208927L;

    private String domainName;
    private String additionalMessage;

    public DomainIncorrectStateException(String message, String domainName) {
        super(message);
        this.domainName = domainName;
        this.additionalMessage = message;
    }

    public String getDomainName() {
        return domainName;
    }

    @Override
    public String getMessage() {
        return String.format("Incorrect state of domain: %s. %s", domainName, additionalMessage);
    }
}
