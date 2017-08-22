package pl.nask.crs.app;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.AbstractCrsTest;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.security.authentication.InvalidPasswordException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
import pl.nask.crs.ticket.exceptions.TicketEditFlagException;

/*
 * I've been asked to raise an urgent new request - we need to modify CRS so that it can modify and display 2 new flags that correspond to 2 agreements resellers may have made with us.

1) Signed Agreement.
2) Edit Ticket.

The status of these flags should be displayed on the Account+NicHandle+Ticket Views screens. In Green if the reseller has, and red if not.

Only the hostmaster lead / technical/ technical lead must be able to modify these states.

when viewing historical records it would only be appropriate to display the state those flags were at that time, if the display was just to show what state they're at now it would mean that the user may think that historically the reseller had flags set which they wouldn't have done at the time.

if the Edit Ticket field has not been set for that account, then the Hostmaster should not be able to edit the contents of a ticket for that account.
The 'Edit' option in the 'Ticket Revise' screen should not appear/be disabled for the user.

On modification, the 'Edit Ticket' state can only be set if the 'Signed Agreement' state has already been set.
 */
public class TestForFeature2373  extends AbstractCrsTest {
	// by default GUEST ACCOUNT has both flags ON
	@Autowired
	TicketAppService ticketAppService;
	
    @Autowired
    AuthenticationService authenticationService;
	
    @Autowired
    AccountAppService accountAppService;
    
    @Autowired
    NicHandleAppService nicHandleAppService;

	private AuthenticatedUser registrar;

	private AuthenticatedUser technical;

	private AuthenticatedUser hostmasterLead;

	private AuthenticatedUser hostmaster;

	private AuthenticatedUser technicalLead;
    
    /*
     * test modifications
     * 
     */
    @Test
    public void foo(){
    	// does nothing, but lets the application context load before all other tests are run
    }
    
	@Test	
	public void testFlagsEditHostmasterLead() throws Exception {
		// don't expect any exception to be thrown
		modifyFlags(hostmasterLead());		
	}
	
	@Test	
	public void testFlagsEditTechnicalLead() throws Exception {
		// don't expect any exception to be thrown
		modifyFlags(technicalLead());			
	}
	
	@Test	
	public void testFlagsEditTechnical() throws Exception {
		// don't expect any exception to be thrown
		modifyFlags(technical());			
	}
	
	private void modifyFlags(AuthenticatedUser user) throws Exception {
		// since history for the account uses datetime instead of timestamp, we have to wait every time we save some data...
		Account guestAccount = accountAppService.get(user, 1);
		AssertJUnit.assertTrue(guestAccount.isAgreementSigned());
		guestAccount.setTicketEdit(false);
		accountAppService.save(user, guestAccount, new AccountWrapper(guestAccount), "asdfs");
	}
		
	@Test
	public void testFlagsEditFail() throws Exception {
		modifyFlagsFail(hostmaster());
		modifyFlagsFail(leadFinance());
		modifyFlagsFail(finance());
		modifyFlagsFail(reseller());
		modifyFlagsFail(batch());
		modifyFlagsFail(registrar());
	}
	
	private void modifyFlagsFail(AuthenticatedUser user) throws Exception {
		try {
			modifyFlags(user);
			AssertJUnit.fail("AccessDeniedException was expected to be thrown!");
		} catch (AccessDeniedException e) {
//			this is expected!
		}		
	}

	@Test
	public void testFlagsInNicHandle() throws Exception {
		NicHandle nh = nicHandleAppService.get(technicalLead(), "AAE359-IEDR");
		AssertJUnit.assertTrue(nh.getAccount().isAgreementSigned());
		AssertJUnit.assertTrue(nh.getAccount().isTicketEdit());
		
		nh = nicHandleAppService.get(technicalLead(), "AAB069-IEDR");
		AssertJUnit.assertFalse(nh.getAccount().isAgreementSigned());
		AssertJUnit.assertFalse(nh.getAccount().isTicketEdit());
	}
	
	@Test
	public void testFlagsInTicket() throws Exception {
		// no flags should be set
		TicketModel t = ticketAppService.view(technicalLead(), 256750);
		AssertJUnit.assertFalse(t.getTicket().getOperation().getResellerAccountField().getNewValue().isTicketEdit());
		AssertJUnit.assertFalse(t.getTicket().getOperation().getResellerAccountField().getNewValue().isAgreementSigned());
		
		// flags set (guestAccount)
		t = ticketAppService.view(technicalLead(), 256744);
		AssertJUnit.assertTrue(t.getTicket().getOperation().getResellerAccountField().getNewValue().isTicketEdit());
		AssertJUnit.assertTrue(t.getTicket().getOperation().getResellerAccountField().getNewValue().isAgreementSigned());
	}
	
	@Test
	public void testFlagsInNicHandleHist() throws Exception {		
		String nhid = "AAE359-IEDR";
		NicHandle nh = nicHandleAppService.get(technicalLead(), nhid);
		// check the state at start
		AssertJUnit.assertTrue(nh.getAccount().isAgreementSigned());
		AssertJUnit.assertTrue(nh.getAccount().isTicketEdit());
		LimitedSearchResult<HistoricalObject<NicHandle>> hist = nicHandleAppService.history(technicalLead(), nhid, 0, 10);
		AssertJUnit.assertEquals(0, hist.getTotalResults());
		
		nicHandleAppService.save(technicalLead(), nh, "aaa");
		
		// nic handle saved, history record generated. both flags should be true.
		hist = nicHandleAppService.history(technicalLead(), nhid, 0, 10);
		HistoricalObject<NicHandle> rec = hist.getResults().get(0);
		AssertJUnit.assertTrue(rec.getObject().getAccount().isAgreementSigned());
		AssertJUnit.assertTrue(rec.getObject().getAccount().isTicketEdit());
		Thread.sleep(1000);
		/* change account flags: 
		 * flags in the nh should change, 
		 * flags in the history should remain true/true
		 */
		Account account = accountAppService.get(technicalLead(), 1L);
		account.setTicketEdit(false);
		accountAppService.save(technicalLead(), account, new AccountWrapper(account), "nnn");
				
		nh = nicHandleAppService.get(technicalLead(), nhid);
		AssertJUnit.assertTrue(nh.getAccount().isAgreementSigned());
		AssertJUnit.assertFalse(nh.getAccount().isTicketEdit());
		
		hist = nicHandleAppService.history(technicalLead(), nhid, 0, 10);
		rec = hist.getResults().get(0); 
		AssertJUnit.assertTrue(rec.getObject().getAccount().isAgreementSigned());
		AssertJUnit.assertTrue(rec.getObject().getAccount().isTicketEdit());
		Thread.sleep(1000);
		// save nh: flags in the first history record should remain trye/true, flags in the new history record should be the same as in the nh.
		nicHandleAppService.save(technicalLead(), nh, "aaa");
		
		hist = nicHandleAppService.history(technicalLead(), nhid, 0, 10);
		rec = hist.getResults().get(0); // the new history record 
		AssertJUnit.assertTrue(rec.getObject().getAccount().isAgreementSigned());
		AssertJUnit.assertFalse(rec.getObject().getAccount().isTicketEdit());
		
		rec = hist.getResults().get(1); // the old history record
		AssertJUnit.assertTrue(rec.getObject().getAccount().isAgreementSigned());
		AssertJUnit.assertTrue(rec.getObject().getAccount().isTicketEdit());
		
		
	}
	
	@Test
	public void testFlagsInTicketHist() throws Exception {
		long ticketId = 256745;
		TicketModel ticket = ticketAppService.edit(technicalLead(), ticketId);
		Account rec = ticket.getTicket().getOperation().getResellerAccountField().getNewValue();
		// check the state at start
		AssertJUnit.assertTrue(rec.isAgreementSigned());
		AssertJUnit.assertTrue(rec.isTicketEdit());

		TicketModel hist = ticketAppService.history(technicalLead(), ticketId);
		AssertJUnit.assertEquals(0, hist.getHistory().size());
		
		ticketAppService.update(technicalLead(), ticketId, ticket.getTicket().getOperation(), " asd ", false, false);
		
		// ticket saved, history record generated. both flags should be true.
		hist = ticketAppService.history(technicalLead(), ticketId);
		rec = hist.getHistory().get(0).getObject().getOperation().getResellerAccountField().getNewValue();
		AssertJUnit.assertTrue(rec.isAgreementSigned());
		AssertJUnit.assertTrue(rec.isTicketEdit());
		Thread.sleep(1000);
		
		/* change account flags: 
		 * flags in the nh should change, 
		 * flags in the history should remain true/true
		 */
		Account account = accountAppService.get(technicalLead(), 1L);
		account.setTicketEdit(false);
		accountAppService.save(technicalLead(), account, new AccountWrapper(account), "nnn");
		
		ticket = ticketAppService.edit(technicalLead(), ticketId);
		rec = ticket.getTicket().getOperation().getResellerAccountField().getNewValue();
		AssertJUnit.assertTrue(rec.isAgreementSigned());
		AssertJUnit.assertFalse(rec.isTicketEdit());
		
		hist = ticketAppService.history(technicalLead(), ticketId);
		rec = hist.getHistory().get(0).getObject().getOperation().getResellerAccountField().getNewValue();
		AssertJUnit.assertTrue(rec.isAgreementSigned());
		AssertJUnit.assertTrue(rec.isTicketEdit());			
	}
	
	@Test(expectedExceptions=TicketEditFlagException.class)
	public void testTicketEditOff() throws Exception {
		// use ticket for account with the ticketEdit flag off`
		TicketModel t = ticketAppService.edit(technicalLead(), 256750);
		AssertJUnit.assertFalse(t.getTicket().getOperation().getResellerAccountField().getNewValue().isTicketEdit());
		ticketAppService.update(technicalLead(), 256750, t.getTicket().getOperation(), "sth", false, false);
	}

    /**
     * This is test for feature #3078
     * @throws Exception
     */
    @Test
    public void testForceUpdateTicketEditOff() throws Exception {
        // use ticket for account with the ticketEdit flag off`
        TicketModel t = ticketAppService.edit(technicalLead(), 256750);
        AssertJUnit.assertFalse(t.getTicket().getOperation().getResellerAccountField().getNewValue().isTicketEdit());
        ticketAppService.update(technicalLead(), 256750, t.getTicket().getOperation(), "sth", false, true);
    }

    @Test
	public void testTicketEditOn() throws Exception {
		// use ticket for account with the ticketEdit flag on`
		TicketModel t = ticketAppService.edit(technicalLead(), 256745);
		AssertJUnit.assertTrue(t.getTicket().getOperation().getResellerAccountField().getNewValue().isTicketEdit());
		ticketAppService.update(technicalLead(), 256745, t.getTicket().getOperation(), "sth", false, false);
	}
	
/*
 * users
 */
	private AuthenticatedUser technicalLead() throws Exception {
		if (technicalLead == null)
			technicalLead = authenticate("IDL2-IEDR");
		return technicalLead;
	}		
	
	private AuthenticatedUser authenticate(String name) throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException {
		return authenticationService.authenticate(name, "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
	}

	private AuthenticatedUser hostmaster() throws Exception {
		if (hostmaster == null) 
			hostmaster = authenticate("AAA906-IEDR");
		return hostmaster;
	}
	
	private AuthenticatedUser hostmasterLead() throws Exception {
		if (hostmasterLead == null)
			hostmasterLead = authenticate("AAA967-IEDR");
		return hostmasterLead;
	}
	
	private AuthenticatedUser technical() throws Exception {
		if (technical == null)
			technical = authenticate("AAE553-IEDR");
		return technical;
	}
	
	private AuthenticatedUser registrar() throws Exception {
		if (registrar == null)
			registrar = authenticate("AAH296-IEDR");
		return registrar;
	}

	private AuthenticatedUser finance() throws Exception {
		return authenticate("AAG45-IEDR");
	}

	private AuthenticatedUser leadFinance() throws Exception {
		return authenticate("AAG061-IEDR");
	}

	private AuthenticatedUser batch() throws Exception {
		return authenticate("AAB069-IEDR");
	}

	private AuthenticatedUser customerService()throws Exception {
		return authenticate("AAA442-IEDR");
	}

	private AuthenticatedUser reseller() throws Exception {
		return authenticate("AAA22-IEDR");
	}

	private AuthenticatedUser guest() throws Exception {
		return authenticate("AA11-IEDR");
	}
}
