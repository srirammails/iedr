package pl.nask.crs.api.triplepass;

import static pl.nask.crs.api.Helper.createBasicCreateRequest;

import java.util.Collections;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.NameserverVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.triplepass.TriplePassAppServiceImpl;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.events.GIBOAdminFailure;
import pl.nask.crs.domains.dsm.events.GIBOAuthorisation;
import pl.nask.crs.domains.dsm.events.GIBOPaymentFailure;
import pl.nask.crs.domains.dsm.events.GIBOPaymentRetryTimeout;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.DepositDAO;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidPasswordException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.TechStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.services.impl.TicketServiceImpl;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TriplePassRegisterTest extends TriplePassTest {
	private static final Logger LOG = Logger.getLogger(TriplePassAppServiceImpl.class);

    @Resource
    DepositDAO depositDao;
    
    @Resource
    DomainDAO domainDao;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    DnsNotificationService dnsNotificationService;
    
    @Resource
    CommonAppService commonAppService;
    
    
    String domainName = "registerDomainForTripplePass.ie";
    String giboDomainName = "registerGIBODomainForTripplePass.ie";
    
    AuthenticatedUserVO user;
    
    OpInfo opInfo = new OpInfo("test");
    
    
    @BeforeMethod
	public void authenticate() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException {
    	user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
    }

    @Test
    public void checkAdminPassedRequired() throws Exception {
    	long ticketId = registerDomainWithAdpPayment(user, domainName);
        boolean res = triplePassAppService.triplePass(user, domainName);
        AssertJUnit.assertFalse(res);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(0, t.getAdminStatus().getId());
        AssertJUnit.assertEquals(0, t.getTechStatus().getId());
        AssertJUnit.assertEquals(0, t.getFinancialStatus().getId());
    }

    /**
	 * Ticket with ADP payment.
	 * admin: passed
	 * triplePass: successfull
	 */
    @Test
    public void uc02sc01Test() throws Exception {
    	long ticketId = registerDomainWithAdpPayment(user, domainName);
    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.PASSED, "test");

        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(passed);
        assertTicketClosed(ticketId);
        assertDomainCreated(domainName);
    }

    /**
	 * Ticket with CC payment.
	 * admin: passed
	 * triplePass: successfull
	 */
    @Test
    public void uc02sc02Test() throws Exception {
    	long ticketId = 259930L;
    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.PASSED, "test");

        boolean passed = triplePassAppService.triplePass(user, "registerCCDomainForTripplePass.ie");

        AssertJUnit.assertTrue(passed);
        assertTicketClosed(ticketId);
        assertDomainCreated("registerCCDomainForTripplePass.ie");
    }

    /**
	 * Ticket with charity domain.
	 * admin: passed
	 * triplePass: successfull
	 */
    @Test
    public void uc02sc04Test() throws Exception {
    	long ticketId = registerCharityDomain();
    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.PASSED, "test");

        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(passed);
        assertTicketClosed(ticketId);
        assertDomainCreated(domainName);
    }

    /**
	 * Ticket with ADP payment.
	 * admin: passed
	 * techCheck: failed
	 */
    @Test
    public void uc02sc05Test() throws Exception {
    	long ticketId = registerAdpDomainWrongNameserver();
    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.PASSED, "test");

        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatusEnum.STALLED.getId(), t.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.NEW.getId(), t.getFinancialStatus().getId());
    }

    /**
	 * Ticket with ADP payment.
	 * admin: passed
	 * techCheck: successful
     * financialCheck : failed
	 */
    @Test
    public void uc02sc06Test() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("SWD2-IEDR", "Passw0rd!", "1.1.1.1", null);
        long ticketId = registerDomainWithAdpPayment(user, domainName);
    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.PASSED, "test");
    	ticketService.updateFinanacialStatus(ticketId, FinancialStatusEnum.NEW, "test");

    	expectInsufficientDepositNotificationWillBeSent(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_NREG, 1);

    	boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), t.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.STALLED.getId(), t.getFinancialStatus().getId());
        Deposit d = depositService.viewDeposit("SWD2-IEDR");
    }

	/**
	 * Ticket with ADP payment.
	 * adminCheck: failed
	 */
    @Test
    public void uc02sc07Test() throws Exception {
        long ticketId = registerDomainWithAdpPayment(user, domainName);
    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.STALLED, "test");

        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatusEnum.NEW.getId(), t.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.NEW.getId(), t.getFinancialStatus().getId());
    }

    /**
	 * Ticket with CC payment.
	 * admin: passed
	 * triplePass: failed (invalidated transaction)
	 */
    @Test
    public void uc02sc10Test() throws Exception {
        long ticketId = 259930L;
        String domainName = "registerCCDomainForTripplePass.ie";

        ticketService.updateAdminStatus(ticketId, AdminStatusEnum.PASSED, "test");

        ReservationSearchCriteria rCriteria = new ReservationSearchCriteria();
        rCriteria.setDomainName(domainName);
        Reservation reservation = reservationDAO.getReservations(rCriteria, 0, 1, null).getResults().get(0);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        transaction.markInvalidated();
        transactionDAO.update(transaction);

        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), t.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.STALLED.getId(), t.getFinancialStatus().getId());
    }

    /**
	 * Ticket with ADP payment.
	 * admin: passed
	 * tech: stalled
	 * triplePass: successfull
	 */
	@Test
	public void testUc030sc01() throws Exception {		
		long ticketId = registerDomainWithAdpPayment(user, domainName);
		((TicketServiceImpl) ticketService).updateStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.STALLED, null, "test", null);
		// do the work
		triplePassAppService.triplePass(user, domainName);
		
		// check the results
		assertTicketClosed(ticketId);
		assertDomainCreated(domainName);
	}
	

	/**
	 * Ticket with ADP payment
	 * admin: passed
	 * tech: stalled
	 * techCheck: failed
	 */
	@Test
	public void testUc030sc02() throws Exception {
		long ticketId = registerAdpDomainWrongNameserver();
		((TicketServiceImpl) ticketService).updateStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.STALLED, null, "test", null);

		triplePassAppService.triplePass(user, domainName);
		
		Ticket t = ticketSearchService.getTicket(ticketId);
		AssertJUnit.assertEquals(TechStatusEnum.STALLED.getId(), t.getTechStatus().getId());
	}
	

	/**
	 * Ticket for a charity domain
	 * admin:passed
	 * tech: stalled
	 * 
	 * triplePass: successful
	 */
	@Test
	public void testUc030sc05() throws Exception {
		long ticketId = registerCharityDomain();
		((TicketServiceImpl) ticketService).updateStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.STALLED, null, "test", null);
		
		triplePassAppService.triplePass(user, domainName);
		
		assertTicketClosed(ticketId);
		assertDomainCreated(domainName);
	}
	
	// successful retry 
	@Test
	public void uc026sc01() throws Exception {
		user = crsAuthenticationService.authenticate("APITS1-IEDR", "Passw0rd!", "1.1.1.1", null);
		registerGiboDomainAdp();
		// make sure the domain is in the proper state/ after GiboPaymentFailure
		dsm.handleEvent(null, giboDomainName, GIBOPaymentFailure.getInstance(), opInfo);
		// top-up deposit to make the financial check pass
		depositTopUp("APITS1-IEDR");		
		
		triplePassAppService.triplePass(user, giboDomainName);
		assertDomainCreated(giboDomainName);
	}
	
	private void depositTopUp(String nicHandle) {
		Deposit d = Deposit.newInstance(nicHandle, new Date(), 0, 10000, 10000, DepositTransactionType.TOPUP, "orderId", null, null);
		depositDao.update(d);		
	}
	
	private void depositClear(String nicHandle) {
		Deposit d = Deposit.newInstance(nicHandle, new Date(), 0, 0, 0, DepositTransactionType.MANUAL, "orderId", "", "");
		depositDao.update(d);		
	}

	// period expired, domain marked for deletion
	@Test
	public void uc026sc03() throws Exception {
		user = crsAuthenticationService.authenticate("APITS1-IEDR", "Passw0rd!", "1.1.1.1", null);
		registerGiboDomainAdp();
		
		// make sure the domain is in the proper state/ after GiboPaymentFailure
		dsm.handleEvent(null, giboDomainName, GIBOPaymentFailure.getInstance(), opInfo);
		
		// change gibo retry timeout to make sure it expires when the check is performed		
		Domain d = domainSearchService.getDomain(giboDomainName);
		d.setGiboRetryTimeout(DateUtils.addHours(new Date(), -25));
		domainDao.update(d);
		
		triplePassAppService.triplePass(user, giboDomainName);
		
		// the domain should be flagged for deletion
		d = domainSearchService.getDomain(giboDomainName);
		AssertJUnit.assertNotNull(d);
		AssertJUnit.assertEquals("Domain NRP status", NRPStatus.Deleted, d.getDsmState().getNRPStatus());
	}
	
	// financial check retry fails
	@Test
	public void uc026sc04() throws Exception {
		user = crsAuthenticationService.authenticate("APITS1-IEDR", "Passw0rd!", "1.1.1.1", null);
		registerGiboDomainAdp();
		// make sure the domain is in the proper state/ after GiboPaymentFailure
		dsm.handleEvent(null, giboDomainName, GIBOPaymentFailure.getInstance(), opInfo);
		// make sure that there will be no deposit funds
		depositClear("APITS1-IEDR");
		
		// save the domain state for future check
		Domain domain = domainSearchService.getDomain(giboDomainName);
		HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
		criteria.setDomainName(giboDomainName);
		LimitedSearchResult<HistoricalObject<Domain>> domainHist = historicalDomainService.findHistory(criteria , 0, 10, null);
		triplePassAppService.triplePass(user, giboDomainName);
		
		// nothing should change, no event should be handled!		
		Domain changedDomain = domainSearchService.getDomain(giboDomainName);
		LimitedSearchResult<HistoricalObject<Domain>> changedHist = historicalDomainService.findHistory(criteria , 0, 10, null);
		
		AssertJUnit.assertEquals("Domain state should not change", domain.getDsmState().getId(), changedDomain.getDsmState().getId());
		AssertJUnit.assertEquals("No domain change should be logged in the history", domainHist.getTotalResults(), changedHist.getTotalResults());		
	}
	
	
	// WIPO disputed - no change on the domain should be performed/financial re-check does not change anything
	@Test
	public void uc026sc05() throws Exception {
		user = crsAuthenticationService.authenticate("APITS1-IEDR", "Passw0rd!", "1.1.1.1", null);
		registerGiboDomainAdp();
		// make sure the domain is in the proper state/ after GiboPaymentFailure, WIPO arbitration entered
		dsm.handleEvent(null, giboDomainName, GIBOPaymentFailure.getInstance(), opInfo);
				
		// have to set it manually
//		dsm.handleEvent(giboDomainName, new EnterWIPOArbitration());
		Domain domain = domainSearchService.getDomain(giboDomainName);
		domainDao.update(domain, 40);
		
		// save the domain state for future check
		domain = domainSearchService.getDomain(giboDomainName);
		Assert.assertTrue(domain.getDsmState().getWipoDispute());
		
		HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
		criteria.setDomainName(giboDomainName);
		LimitedSearchResult<HistoricalObject<Domain>> domainHist = historicalDomainService.findHistory(criteria , 0, 10, null);
		
		triplePassAppService.triplePass(user, giboDomainName);
		
		// nothing should change		
		Domain changedDomain = domainSearchService.getDomain(giboDomainName);
		LimitedSearchResult<HistoricalObject<Domain>> changedHist = historicalDomainService.findHistory(criteria , 0, 10, null);
		
		AssertJUnit.assertEquals("Domain state should not change", domain.getDsmState().getId(), changedDomain.getDsmState().getId());
		AssertJUnit.assertEquals("No domain change should be logged in the history", domainHist.getTotalResults(), changedHist.getTotalResults());		
	}
	
	// happy path
	@Test
	public void uc003sc01Test() throws Exception {
		user = crsAuthenticationService.authenticate("APITS1-IEDR", "Passw0rd!", "1.1.1.1", null);
		registerGiboDomainAdp();
		
		// financial check
		triplePassAppService.triplePass(user, giboDomainName);
		
		// a domain should be active now
		assertDomainCreated(giboDomainName);
	}
	
	// financial check failed
	@Test
	public void uc003sc02Test() throws Exception {
		user = crsAuthenticationService.authenticate("APITS1-IEDR", "Passw0rd!", "1.1.1.1", null);
		registerGiboDomainAdp();
		// clear the deposit to make sure the financial check will fail
		depositClear("APITS1-IEDR");
		
		// financial check
		triplePassAppService.triplePass(user, giboDomainName);
		
		// a domain should be in a 'transaction failed' state
		Domain domain = domainSearchService.getDomain(giboDomainName);
		NRPStatus nrpStatus = domain.getDsmState().getNRPStatus();
		AssertJUnit.assertEquals("NRP Status", NRPStatus.TransactionFailed, nrpStatus);
	}
	
	// admin check is failed - admin moves the domain into 'deleted' state
	@Test
	public void uc003sc03Test() throws Exception {
		user = crsAuthenticationService.authenticate("APITS1-IEDR", "Passw0rd!", "1.1.1.1", null);
		registerGiboDomainAdp();

		dsm.handleEvent(null, giboDomainName, GIBOAdminFailure.getInstance(), opInfo);

		// a domain should be in a 'transaction failed' state
		Domain domain = domainSearchService.getDomain(giboDomainName);
		NRPStatus nrpStatus = domain.getDsmState().getNRPStatus();
		AssertJUnit.assertEquals("NRP Status", NRPStatus.Deleted, nrpStatus);
	}
	
	// wipo disputed
	// no operations should be possible for such domain, triplePass should not change the domain state.
	@Test
	public void uc003sc04() throws Exception {
		user = crsAuthenticationService.authenticate("APITS1-IEDR", "Passw0rd!", "1.1.1.1", null);
		registerGiboDomainAdp();		
				
//		dsm.handleEvent(giboDomainName, new EnterWIPOArbitration());
		Domain domain = domainSearchService.getDomain(giboDomainName);
		domainDao.update(domain, 40);
		
		// save the domain state for future check
		domain = domainSearchService.getDomain(giboDomainName);
		Assert.assertTrue(domain.getDsmState().getWipoDispute());
		
		
		boolean valid = dsm.validateEvent(giboDomainName, GIBOAdminFailure.getInstance(),
				GIBOPaymentFailure.getInstance(),
				GIBOPaymentRetryTimeout.getInstance(),
				GIBOAuthorisation.getInstance());
		
		AssertJUnit.assertFalse("WIPO allows to perform triplePass", valid);
	}

    @Test
    public void testChg0039() throws Exception {
        String domainName1 = "1domena39.ie";
        String domainName2 = "2domena39.ie";

        long ticketId1 = registerDomainWithAdpPayment(user, domainName1);
        long ticketId2 = registerDomainWithAdpPayment(user, domainName2);
        ((TicketServiceImpl) ticketService).updateStatuses(ticketId1, AdminStatusEnum.PASSED, TechStatusEnum.STALLED, null, "test", null);
        ((TicketServiceImpl) ticketService).updateStatuses(ticketId2, AdminStatusEnum.PASSED, TechStatusEnum.STALLED, null, "test", null);

        // do the work
        triplePassAppService.triplePass();
        
        // check the results
        Domain domain1 = domainSearchService.getDomain(domainName1);
        Domain domain2 = domainSearchService.getDomain(domainName2);

        assertTicketClosed(ticketId1);
        assertDomainCreated(domain1);

        assertTicketClosed(ticketId2);
        assertDomainCreated(domain2);

//        Assert.assertTrue("Domain1 create after domain2", domain1.getc);
    }

    private long registerAdpDomainWrongNameserver() throws Exception {
		RegistrationRequestVO registrationRequestVO = createBasicCreateRequest(domainName, "APITEST-IEDR");
		registrationRequestVO.updateNameservers(Collections.singletonList(new NameserverVO("bad." + domainName, "127.1.1.1")));
		crsCommonAppService.registerDomain(user, registrationRequestVO, null);
		return ticketAppService.internalGetTicketForDomain(domainName).getId();
	}
	
	private long registerCharityDomain() throws Exception {
		RegistrationRequestVO registrationRequestVO = createBasicCreateRequest(domainName, "APITEST-IEDR");
		registrationRequestVO.setCharityCode("chy123");
		crsCommonAppService.registerDomain(user, registrationRequestVO, null);
		return ticketAppService.internalGetTicketForDomain(domainName).getId();
	}

    private long registerDomainWithAdpPayment(AuthenticatedUserVO user, String domainName) throws Exception {
        RegistrationRequestVO registrationRequestVO = createBasicCreateRequest(domainName, user.getUsername());
        crsCommonAppService.registerDomain(user, registrationRequestVO, null);
        return ticketAppService.internalGetTicketForDomain(domainName).getId();
    }
    
    @Deprecated
	private void registerGiboDomainAdp() throws Exception{
		RegistrationRequestVO registrationRequestVO = createBasicCreateRequest(giboDomainName, user.getUsername());
		commonAppService.registerGIBODomain(user, registrationRequestVO);
	}

	private void assertDomainCreated(String domainName) throws DomainNotFoundException {
		Domain d = domainSearchService.getDomain(domainName);
        assertDomainCreated(d);
	}

    private void assertDomainCreated(Domain domain) throws DomainNotFoundException {
        AssertJUnit.assertNotNull(domain);
        AssertJUnit.assertEquals("Domain is active", NRPStatus.Active, domain.getDsmState().getNRPStatus());
    }

}
