package pl.nask.crs.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.dao.DepositDAO;
import pl.nask.crs.payment.exceptions.DomainIncorrectStateForPaymentException;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;

@ContextConfiguration(locations = {"/application-services-config.xml"})
public class DomainAutorenewTest extends AbstractTransactionalTestNGSpringContextTests {
	
	@Resource
	ServicesRegistry services;
	
	@Resource 
	PaymentAppService paymentAppService;
	
	@Resource
	PaymentService paymentService;
	
	@Resource
	AuthenticationService authenticationService;	

	@Resource
	DomainDAO domainDAO;
	
	@Resource
	DepositDAO depositDAO;
		
	int renewOnceState = 49;
	int autorenewState = 81;
	int noAutoRenew = 17;
	
	final String domainName = "suka.ie";
	final String billingNh = "HIA1-IEDR";
	
	AuthenticatedUser user;
	Domain domain;
	
	@BeforeMethod
	public void clear() throws Exception {
		user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
		domain = null;
	}
	
	@Test
	public void renewOnceTest() throws Exception {
        initDomain(renewOnceState);
        assertRenewalMode(RenewalMode.RenewOnce);		
        assertNoReservation();
        topUpDeposit();
        checkRenewSuccessfull();
	}

	@Test
	public void renewAutoTest() throws Exception {
		initDomain(autorenewState);
		assertRenewalMode(RenewalMode.Autorenew);
		assertNoReservation();
		topUpDeposit();
		checkRenewSuccessfull();
	}
	
	private void topUpDeposit() {
		depositDAO.create(Deposit.newInstance(billingNh, new Date(), 0, 10000, 10000, DepositTransactionType.INIT, "a", null, null));
	}

	@Test(expectedExceptions=DomainIncorrectStateForPaymentException.class)
	public void renewNoAuto() throws Exception {
		initDomain(noAutoRenew);
		assertRenewalMode(RenewalMode.NoAutorenew);
		assertNoReservation();
		
		checkRenewUnSuccessfull();
	}
	
	// tests #12080
	@Test
	public void renewDomainWithHyphensInName() throws Exception {
		// having a domain with '-' in it's name		
		String newDomainName = "trans-o-flex.ie";
		createDomain(newDomainName);
		topUpDeposit();
		// the autorenewal for this domain should end with success
		checkRenewSuccessfull(newDomainName);
	}
	
	private void createDomain(String newDomainName) {
		Domain existingDomain = domainDAO.get(domainName);
		existingDomain.setName(newDomainName);
		existingDomain.setRenewDate(new Date());
		domainDAO.createDomain(existingDomain);
		// the domain needs to be active with Autorenew
		domainDAO.update(existingDomain, autorenewState);
		domain = domainDAO.get(newDomainName);
	}

	@Test
	public void domainWithHypensInNameIsTakenForAutorenewal() throws Exception {
		String newDomainName = "trans-o-flex.ie";
		createDomain(newDomainName);
		
		// when searching for the domain
		DomainSearchCriteria criteria = new DomainSearchCriteria();
		criteria.setRenewTo(new Date());
		criteria.setDomainRenewalModes(RenewalMode.Autorenew, RenewalMode.RenewOnce);
		int offset = 0;
		final int limit = 100;
		List<String> domains = services.getDomainSearchService().findDomainNames(criteria, offset, limit);
		// the domain should be on the list!
		Assert.assertTrue(domains.size() > 0);
		for (String domain: domains) {
			if (newDomainName.equalsIgnoreCase(domain)) {
				// success, end test
				return;
			}
		}
		
		Assert.fail("Domain " + newDomainName + " not found in the results: " + domains);
				
	}
	
	private void checkRenewUnSuccessfull() throws Exception {
		paymentAppService.autorenew(null, domainName);
		AssertJUnit.fail("autorenew should not succeed");		      	
	}

	private void checkRenewSuccessfull() throws Exception {
        checkRenewSuccessfull(domainName);
	}
	
	private void checkRenewSuccessfull(String domainName) throws Exception {
        PaymentSummary res = paymentAppService.autorenew(null, domainName);
        assertReservation(domainName);
        AssertJUnit.assertNotNull(res);
	}

	private void assertReservation(String domainName) {
		Reservation res = paymentService.getReadyReservation(billingNh, domainName);
		AssertJUnit.assertNotNull("existing reservation", res);
		// check, if it's a reservation for a renewal
	}

	private void assertNoReservation() {
		Reservation res = paymentService.getReadyReservation(billingNh, domainName);
		AssertJUnit.assertNull("existing reservation", res);
	}

	private void assertRenewalMode(RenewalMode mode) {
		AssertJUnit.assertEquals("renewal mode", mode, domain.getDsmState().getRenewalMode());
	}

	private void initDomain(int state) {
		domain = domainDAO.get(domainName);
		domainDAO.update(domain, state);
		domain = domainDAO.get(domainName);
	}
	
}
