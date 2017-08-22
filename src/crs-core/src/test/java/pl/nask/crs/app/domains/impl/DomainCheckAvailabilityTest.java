package pl.nask.crs.app.domains.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.DomainAvailability;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;

@ContextConfiguration(locations = {"/application-services-config.xml"})
public class DomainCheckAvailabilityTest extends AbstractTransactionalTestNGSpringContextTests {
	@Resource
	DomainAppService domainAppService;
	
	@Resource
	AuthenticationService authenticationService;

	private AuthenticatedUser user;
	
	@BeforeMethod
	public void clear() throws Exception {
		user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
	}
	
	@Test
	public void testDomainRegistered() {
		DomainAvailability chk = domainAppService.checkAvailability(user, "suka.ie");
		AssertJUnit.assertTrue("Domain exists", chk.isDomainCreated());
		AssertJUnit.assertFalse("reg ticket exists", chk.isRegTicketCreated());
		AssertJUnit.assertFalse("Domain available", chk.isAvailable());		
	}
	
	@Test
	public void testRegistrationPending() {
		DomainAvailability chk = domainAppService.checkAvailability(user, "taga.ie");
		AssertJUnit.assertFalse("Domain exists", chk.isDomainCreated());
		AssertJUnit.assertTrue("Reg ticket exists", chk.isRegTicketCreated());
		AssertJUnit.assertFalse("Domain available", chk.isAvailable());
	}
	
	@Test
	public void testAvailable() {
		DomainAvailability chk = domainAppService.checkAvailability(user, "123asfsf211.ie");
		AssertJUnit.assertFalse("Domain exists", chk.isDomainCreated());
		AssertJUnit.assertFalse("reg ticket exists", chk.isRegTicketCreated());
		AssertJUnit.assertTrue("Domain available", chk.isAvailable());
	}

    @Test
    public void managedByRegistrarUserTest() {
        DomainAvailability chk = domainAppService.checkAvailability(user, "payDomain.ie");
        AssertJUnit.assertTrue("Domain exists", chk.isDomainCreated());
        AssertJUnit.assertTrue("Domain managed by user", chk.isManagedByUser());
        AssertJUnit.assertFalse("reg ticket exists", chk.isRegTicketCreated());
        AssertJUnit.assertFalse("Domain available", chk.isAvailable());
    }

    @Test
    public void managedByDirectUserTest() throws Exception {
        AuthenticatedUser u = authenticationService.authenticate("AAG061-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
        DomainAvailability chk = domainAppService.checkAvailability(u, "makingadifference.ie");
        AssertJUnit.assertTrue("Domain exists", chk.isDomainCreated());
        AssertJUnit.assertTrue("Domain managed by user", chk.isManagedByUser());
        AssertJUnit.assertFalse("reg ticket exists", chk.isRegTicketCreated());
        AssertJUnit.assertFalse("Domain available", chk.isAvailable());
    }

}
