package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.payment.Transaction;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class ViewTransactionPermissionQuery extends NamedPermissionQuery {

	private Transaction transaction;
	private AuthenticatedUser user;

	public ViewTransactionPermissionQuery(String permissionName, Transaction transaction, AuthenticatedUser user) {
		super(permissionName);
		this.transaction = transaction;
		this.user = user;
	}

	public Transaction getTransaction() {
		return transaction;
	}
	
	public AuthenticatedUser getUser() {
		return user;
	}
}
