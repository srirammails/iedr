package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.app.authorization.queries.ViewTransactionPermissionQuery;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.ContextualPermission;
import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PermissionQuery;

public class ViewTransactionPermission extends ContextualPermission {
	public ViewTransactionPermission(String id, String name) {
		super(id, name);
	}

	@Override
	protected boolean verifyContext(PermissionQuery permission) {
    if (permission instanceof ViewTransactionPermissionQuery) {
        AuthenticatedUser user = ((ViewTransactionPermissionQuery) permission).getUser();
        Transaction transaction = ((ViewTransactionPermissionQuery) permission).getTransaction();
        return ownTransaction(user, transaction);
    } else if (permission instanceof NamedPermission) {
        return true;
    } else {
        return false;
    }
}

private boolean ownTransaction(AuthenticatedUser user, Transaction transaction) {
     return user != null && transaction != null && transaction.getBillNicHandleId().equalsIgnoreCase(user.getUsername());
}

@Override
public String getDescription() {
	if (getClass() != ViewTransactionPermission.class)
		return null;
	return "Contextual, allows access to transactions performed by a user, combined with " + getDescriptionFromTheResourceBundle(getName());
}

}
