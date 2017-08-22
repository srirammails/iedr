package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.app.authorization.queries.AccountPartGetPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.user.permissions.*;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class AccountPartGetPermission extends ContextualPermission {

    private NicHandleDAO nicHandleDAO;

    public AccountPartGetPermission(String id, String name, NicHandleDAO nicHandleDAO) {
        super(id, name);
        Validator.assertNotNull(nicHandleDAO, "nicHandleDAO");
        this.nicHandleDAO = nicHandleDAO;
    }

    @Override
    protected boolean verifyContext(PermissionQuery permission) throws PermissionDeniedException {
        if (permission instanceof AccountPartGetPermissionQuery) {
            long accountId = ((AccountPartGetPermissionQuery) permission).getAccountId();
            String nicHandleId = ((AccountPartGetPermissionQuery) permission).getNicHandleId();
            return accountMatch(accountId, nicHandleId);
        } else {
            return false;
        }
    }

    private boolean accountMatch(long accountId, String nicHandleId) {
        NicHandle nicHandle = nicHandleDAO.get(nicHandleId);
        return accountId == nicHandle.getAccount().getId();
    }
    
    @Override
    public String getDescription() {
    	if (getClass() != AccountPartGetPermission.class) {
    		return null;
    	}
    	return "Contextual, restincts access to the account with user's account number, in combination with: " + getDescriptionFromTheResourceBundle(getName());
    }
}
