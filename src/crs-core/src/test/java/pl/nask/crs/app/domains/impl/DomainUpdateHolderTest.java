package pl.nask.crs.app.domains.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;


/**
 * https://drotest4.nask.net.pl:3000/issues/11847
 * 
 * 
 * Available deposit is deducted when Registrar makes payment for domain which is updated to Charity before settlement
 *
 * The operation changing the HolderType must be validated and the change must be blocked if there are any unsettled reservations.
 * 
 * @author Artur Gniadzik
 *
 */
@ContextConfiguration(locations = {"/application-services-config.xml"})
public class DomainUpdateHolderTest extends AbstractTransactionalTestNGSpringContextTests {
	
	@Autowired
	DomainAppService domainAppService;
	
	@Autowired
	DomainSearchService domainSearchService;
		
	@Autowired
	AuthenticationService authenticationService;
		

	AuthenticatedUser user;
	
	@BeforeMethod
	public void hostmasterLogIn() throws Exception {
		user = authenticationService.authenticate("AAA906-IEDR", "Passw0rd!", false, "127.0.0.1", false, null, false, "crs");
	}

	@Test
	public void changeShouldBeBlockIfThereArePendingReservations() throws Exception {
		// having domain with reservations pending
		Domain domain= findDomainWithPendingReservation(true);
		// when
		try {
			performHolderChange(domain);
			// an exception will be thrown
			Assert.fail("this operation should not be allowed!");
		} catch (DomainIllegalStateException e) {
			// good
		}
		
	}

	private Domain findDomainWithPendingReservation(boolean b) {
		DomainSearchCriteria criteria = new DomainSearchCriteria();		
		criteria.setAttachReservationInfo(true);		
		LimitedSearchResult<Domain> res = domainSearchService.find(criteria , 0, 1000, null);
		for (Domain d: res.getResults()) {
			if (d.getDsmState().getDomainHolderType() == DomainHolderType.Billable && domainAppService.isEventValid(user, d.getName(), DsmEventName.SetCharity)) {
				if (b && (d.hasPendingADPReservations() || d.hasPendingCCReservations())) {
					return d;
				} else if (! (b || d.hasPendingADPReservations() || d.hasPendingCCReservations())) {
					return d;
				}
			}
		}
		
		throw new IllegalStateException("Couldn't find a right domain!");
	}

	@Test
	public void changeShouldBePermittedIfThereAreNoPendingReservations() throws Exception {
		// having domain with reservations pending
		Domain domain= findDomainWithPendingReservation(false);
		// when
		performHolderChange(domain);
		// no exception will not be thrown, state will be changed
		domain = domainSearchService.getDomain(domain.getName());
		Assert.assertEquals(domain.getDsmState().getDomainHolderType(), DomainHolderType.Charity, "Holder type should be changed");
	}

	private void performHolderChange(Domain domain) throws Exception {
		domainAppService.updateHolderType(user, domain.getName(), DomainHolderType.Charity, "test");
	}
}
