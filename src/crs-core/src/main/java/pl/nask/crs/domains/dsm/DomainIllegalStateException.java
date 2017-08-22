package pl.nask.crs.domains.dsm;

import pl.nask.crs.domains.Domain;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainIllegalStateException extends RuntimeException {

    private final Domain domain;

    public DomainIllegalStateException(String msg, Domain domain) {
        super(msg);
        this.domain = domain;
    }

    public Domain getDomain() {
        return domain;
    }
}
