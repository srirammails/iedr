package pl.nask.crs.api.techadminconsole;

import org.testng.annotations.Test;
import pl.nask.crs.app.tickets.exceptions.DomainModificationPendingException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;

public class AdminContactTest extends AbstractTestBase {

	@Override
	protected String getNic() {
		// A candidate for an Admin: AAI538-IEDR, AHC714-IEDR				
		return "AAI538-IEDR";
	}

	@Override
	protected String getOwnDomainName() {
		return "castlebargolfclub.ie";
	}
	
	// Access granted, but modification ticket is pending
	@Override
	@Test(expectedExceptions=DomainModificationPendingException.class)
	public void shouldModifyOwnDomainHolder() throws Exception {
		super.shouldModifyOwnDomainHolder();
	}
	
	// Access granted, but the Class/Category is not valid
	@Override
	@Test(expectedExceptions=HolderClassNotExistException.class)
	public void shouldModifyOwnDomainClassCategory() throws Exception {
		super.shouldModifyOwnDomainClassCategory();
	}
	
	@Override
	@Test(expectedExceptions=DomainModificationPendingException.class)
	public void shouldModifyOwnDomainAdminContacts() throws Exception {
		super.shouldModifyOwnDomainAdminContacts();
	}
	
	@Override
	@Test(expectedExceptions=DomainModificationPendingException.class)
	public void shouldModifyOwnDomainTechContacts() throws Exception {
		super.shouldModifyOwnDomainTechContacts();
	}
	
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldLeaveVnrpOwnDomain() throws Exception {
		super.shouldLeaveVnrpOwnDomain();
	}
}
