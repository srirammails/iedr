package pl.nask.crs.api.techadminconsole;

import org.testng.annotations.Test;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class TechContactTest extends AbstractTestBase {

	@Override
	protected String getNic() {
		// A candidate for a Tech: AAB069-IEDR, TDI2-IEDR
		return "TDI2-IEDR";
	}

	@Override
	protected String getOwnDomainName() {
		return "shannonpipeline.ie";
	}

	// TECH users can't put domains into VNRP
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldEnterVnrpOwnDomain() throws Exception {
		super.shouldEnterVnrpOwnDomain();
	}

	// TECH users can't modify domain data other than nameservers
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldModifyOwnDomainAdminContacts() throws Exception {
		super.shouldModifyOwnDomainAdminContacts();
	}
	
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldModifyOwnDomainHolder() throws Exception {
		super.shouldModifyOwnDomainHolder();
	}
	
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldModifyOwnDomainClassCategory() throws Exception {
		super.shouldModifyOwnDomainClassCategory();
	}
	
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldModifyOwnDomainRenewalMode() throws Exception {
		super.shouldModifyOwnDomainRenewalMode();
	}
	
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldModifyOwnDomainRenewalMode2way() throws Exception {
		super.shouldModifyOwnDomainRenewalMode2way();
	}
	
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldModifyOwnDomainTechContacts() throws Exception {
		super.shouldModifyOwnDomainTechContacts();
	}
	
	// it's a part of the domain.modify, so the tech will not have an access to it
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldModifyOwnDomainNameservers() throws Exception {
		super.shouldModifyOwnDomainNameservers();
	}
	
	// tech has an access to it, but in this particular case the NH is not active.
	@Override
	@Test(expectedExceptions=NicHandleNotActiveException.class)
	public void shouldModifyOwnDNS() throws Exception {
		super.shouldModifyOwnDNS();
	}
	
	@Override
	@Test(expectedExceptions=AccessDeniedException.class)
	public void shouldLeaveVnrpOwnDomain() throws Exception {
		super.shouldLeaveVnrpOwnDomain();
	}
	
}
