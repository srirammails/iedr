package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.app.authorization.queries.ViewInvoicePermissionQuery;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.dao.InvoiceDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.ContextualPermission;
import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ViewInvoicePermission extends ContextualPermission {

    InvoiceDAO invoiceDAO;

    public ViewInvoicePermission(String id, String name, InvoiceDAO invoiceDAO) {
        super(id, name);
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    protected boolean verifyContext(PermissionQuery permission) throws PermissionDeniedException {
        if (permission instanceof ViewInvoicePermissionQuery) {
            AuthenticatedUser user = ((ViewInvoicePermissionQuery) permission).getUser();
            String invoiceNumber = ((ViewInvoicePermissionQuery) permission).getInvoiceNumber();
            Invoice invoice = invoiceDAO.getByNumber(invoiceNumber);
            return ownInvoice(user, invoice);
        } else if (permission instanceof NamedPermission) {
            return true;
        } else {
            return false;
        }
    }

    private boolean ownInvoice(AuthenticatedUser user, Invoice invoice) {
         return user != null && invoice != null && invoice.getBillingNicHandle().equalsIgnoreCase(user.getUsername());
    }
    
    @Override
	public String getDescription() {
		if (getClass() != ViewInvoicePermission.class)
			return null;
		return "Contextual, allows access to invoice issued to a user, combined with " + getDescriptionFromTheResourceBundle(getName());
	}
}
