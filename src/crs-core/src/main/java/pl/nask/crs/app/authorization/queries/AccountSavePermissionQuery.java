package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class AccountSavePermissionQuery extends NamedPermissionQuery {

	private Account account;
	
	public AccountSavePermissionQuery(String name) {
		super(name);
	}

	public AccountSavePermissionQuery(String name, Account account) {
		super(name);
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
}
