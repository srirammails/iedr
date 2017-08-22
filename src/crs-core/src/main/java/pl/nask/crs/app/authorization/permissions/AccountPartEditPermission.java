package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.app.authorization.queries.AccountSavePermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.permissions.ContextualPermission;
import pl.nask.crs.user.permissions.PermissionQuery;


/**
 * Allows to perform save operation, but denies to change account flags (arrangementSigned and ticketEdit)  
 * 
 * @author Artur Gniadzik
 *
 */
public class AccountPartEditPermission extends ContextualPermission {
	private AccountDAO accountDao;
	
	
	public AccountPartEditPermission(String id, String name, AccountDAO accountDao) {
		super(id, name);		
		Validator.assertNotNull(accountDao, "accountDao");
		this.accountDao = accountDao;
	}

	@Override
	protected boolean verifyContext(PermissionQuery permission) {
		if (!(permission instanceof AccountSavePermissionQuery)) {
            return false; // deny by default (cast would cause an exception)
		}
		
		AccountSavePermissionQuery perm = (AccountSavePermissionQuery) permission;
		Account newAcc = perm.getAccount();
		
        if (newAcc == null)
        	return true; // allow to save null value.
        
        Account oldAcc = accountDao.get(newAcc.getId());
		// compare flags - if not changed, return true
		return (newAcc.isAgreementSigned() == oldAcc.isAgreementSigned())
				&&
			   (newAcc.isTicketEdit() == oldAcc.isTicketEdit());        	
	}
	
	@Override
	public String getDescription() {	
		if (getClass() != AccountPartEditPermission.class)
			return null;
		return "Non contextual, allows to modify and save registrar's account data not allowing to change 'agreement signed' and 'ticket edit' flags";
	}
}
