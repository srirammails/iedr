package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ViewInvoicePermissionQuery extends NamedPermissionQuery {

    private String invoiceNumber;
    private AuthenticatedUser user;

    public ViewInvoicePermissionQuery(String name, String invoiceNumber, AuthenticatedUser user) {
        super(name);
        this.invoiceNumber = invoiceNumber;
        this.user = user;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public AuthenticatedUser getUser() {
        return user;
    }
}
