package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class DomainSearchQuery extends NamedPermissionQuery {

	private String billingNH;
	private AuthenticatedUser user;

	public DomainSearchQuery(AuthenticatedUser user, String permissionName, String billingNH) {
		super(permissionName);
		this.user = user;
		this.billingNH = billingNH;
	}

	public String getBillingNH() {
		return billingNH;
	}
	
	public AuthenticatedUser getUser() {
		return user;
	}
}
