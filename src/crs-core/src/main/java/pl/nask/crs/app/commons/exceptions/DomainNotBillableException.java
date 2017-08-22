package pl.nask.crs.app.commons.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainNotBillableException extends ValidationException {

    private static final long serialVersionUID = -2302360736344942227L;

    private final String domainName;

	private String domainBillingStatus;

    public DomainNotBillableException(String domainName) {
        this.domainName = domainName;
    }
    
    public DomainNotBillableException(String domainName, String domainBillingStatus) {
        this.domainName = domainName;
        this.domainBillingStatus = domainBillingStatus;
    }

    public String getDomainName() {
        return domainName;
    }

    @Override
    public String getMessage() {
        return String.format("Domain: %s is not billable, status is %s", domainName, domainBillingStatus);
    }
}
