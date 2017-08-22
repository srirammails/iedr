package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class NicHandleSearchPermissionQuery extends NamedPermissionQuery {

	private final AuthenticatedUser user;
	private final Long accountNumber;

	public NicHandleSearchPermissionQuery(String permissionName,
			Long accountNumber, AuthenticatedUser user) {
		super(permissionName);
		this.accountNumber = accountNumber;
		this.user = user;		
	}
	
	public AuthenticatedUser getUser() {
		return user;
	}
	
	public Long getAccountNumber() {
		return accountNumber;
	}

}
