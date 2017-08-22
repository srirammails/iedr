package pl.nask.crs.app.authorization.permissions;

import java.security.acl.NotOwnerException;

import pl.nask.crs.app.InvalidAccountNumberException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermission;

public class OwnNhPermission extends DirectRegistrarPermission {

	public OwnNhPermission(String id, NamedPermission namedPermission,
			NicHandleDAO nicHandleDAO) {
		super(id, namedPermission, nicHandleDAO);
	}
	
	@Override
	protected boolean verifyAccessToTheNicHandle(AuthenticatedUser user,
			NicHandle nicHandle) throws InvalidAccountNumberException {
		return user.getUsername().equalsIgnoreCase(nicHandle.getNicHandleId());
	}

	@Override
	public String getDescription() {
		if (getClass() != OwnNhPermission.class)
			return null;
		return "Contextual permission, grants access to user's NicHandle object, combined with " + getMethodPermission().getId() + " (" + getMethodPermission().getDescription() + ")";
	}
}
