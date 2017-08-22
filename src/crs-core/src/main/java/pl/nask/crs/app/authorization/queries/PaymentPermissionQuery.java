package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class PaymentPermissionQuery extends NamedPermissionQuery {

	private final String nicHandle;
	private final AuthenticatedUser user;

	public PaymentPermissionQuery(String permissionName, String nicHandle, AuthenticatedUser user) {
		super(permissionName);
		this.nicHandle = nicHandle;
		this.user = user;
	}

    public String getNicHandle() {
        return nicHandle;
    }

    public AuthenticatedUser getUser() {
        return user;
    }
}
