package pl.nask.crs.app.invoicing;

import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface InvoicingAppService {

    public void runInvoicing(AuthenticatedUser user);

    public void runTransactionInvalidation(AuthenticatedUser user);
}
