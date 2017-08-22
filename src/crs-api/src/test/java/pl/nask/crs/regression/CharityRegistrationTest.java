package pl.nask.crs.regression;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.Helper;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.ExtendedDomainInfo;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.WsAuthenticationService;

/**
 * test for bug #6290 - promoteTicketToDomain: Charity registration
 * 
 * When promoting a charity ticket to a domain, the domains renew date is set to today. As there is no payment associated with this type of registration, the D_Ren_Dt should be set to +1 Year
 * 
 * Disclaimer: rolling a renew date is being made with a proper DSM action triggered by an event.
 * 
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 *
 * @author Artur Gniadzik
 *
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class CharityRegistrationTest extends AbstractTransactionalTestNGSpringContextTests {
	@Resource
	TriplePassAppService triplePassService;
	
	@Resource
	CommonAppService commonAppService;
	
	
	@Resource
	WsAuthenticationService authService;
	
	@Resource
	DomainAppService domainAppService;
	
	@Resource
	TicketAppService ticketAppService;

	AuthenticatedUser user;
    AuthenticatedUser adminUser;

	@BeforeMethod
	public void authenticate() throws Exception {
    	user = authService.authenticate("APITEST-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
    	adminUser = authService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
    }
	
	@Test
	public void testPromoteTicketToDomain() throws Exception {
		String domainName = "charityDomainForTest.ie";
		TicketRequest request = Helper.createCharityRegistrationRequest(domainName, "APITEST-IEDR");
		
		// make a registration request first
		long ticketId = commonAppService.registerDomain(user, request , null);
		TicketModel t = ticketAppService.view(user, ticketId);
		// verify registration ticket?
		
		ticketAppService.accept(adminUser, ticketId, "accept");
		
		boolean result = triplePassService.triplePass(adminUser, domainName);
		AssertJUnit.assertTrue("Result of triplepass", result);
		
		ExtendedDomainInfo d = domainAppService.view(user, domainName);
		Date renDate = d.getDomain().getRenewDate();
		Date crDate = d.getDomain().getRegistrationDate();
		
		AssertJUnit.assertEquals("Renewal date", renDate, DateUtils.addYears(crDate, 1));
		
	}
}
