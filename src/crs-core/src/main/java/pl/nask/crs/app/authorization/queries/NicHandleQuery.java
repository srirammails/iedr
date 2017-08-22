package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class NicHandleQuery extends NamedPermissionQuery {

	private AuthenticatedUser user;
	private NicHandle nicHandle;
	private String nicHandleId;
	
	public NicHandleQuery(String name, NicHandle nicHandle, AuthenticatedUser user) {		
		super(name);
		this.nicHandle = nicHandle;
		this.user = user;
	}
	
	public NicHandleQuery(String name, String nicHandleId, AuthenticatedUser user) {		
		super(name);
		this.nicHandleId = nicHandleId;
		this.user = user;
	}

	public AuthenticatedUser getUser() {
		return user;
	}

	public NicHandle getNicHandle() {
		return nicHandle;
	}

	public String getNicHandleId() {
		if (nicHandleId != null)
			return nicHandleId;
		
		if (nicHandle != null)
			return nicHandle.getNicHandleId();
		
		return null;
	}

}
