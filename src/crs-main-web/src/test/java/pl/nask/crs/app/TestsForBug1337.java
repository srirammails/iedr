package pl.nask.crs.app;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.AbstractCrsTest;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.impl.AccountExportException;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.wrappers.DomainWrapper;
import pl.nask.crs.app.domains.wrappers.NameserverStub;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.domains.services.HistoricalDomainService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.security.authentication.InvalidPasswordException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.PasswordExpiredException;

public class TestsForBug1337 extends AbstractCrsTest {
    @Autowired
    AccountAppService accountAppService;

    @Autowired
    AuthenticationService authenticationService;

     @Autowired
    DomainAppService domainAppService;

    @Autowired
    HistoricalDomainService historicalDomainService;

    @Autowired
    NicHandleAppService nicHandleAppService;

    AuthenticatedUser user;

    @BeforeMethod
	public void authenticateUser() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException {
        user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

// deprecated test case since bug#1410
//    /**
//     * test for bug #1337. Case: don't generate historical record if the record
//     * already exists in the database
//     *
//     */
//    @Test
//    public void testSaveAccountNoHistGenerated() throws AccessDeniedException, AccountNotFoundException, NicHandleNotFoundException, NicHandleIsAccountBillingContactException, NicHandleEmailException, EmptyRemarkException, ContactNotFoundException, ContactCannotChangeException {
//        long accountId = 0L;
//
//        Account account = accountAppService.get(user, accountId);
//        int histSize = accountAppService.history(user, accountId).size();
//        Assert.assertNotNull("Account", account);
//        accountAppService.save(user, account, new AccountWrapper(account), "modify");
//        int newHistSize = accountAppService.history(user, accountId).size();
//        Assert.assertEquals("History size", histSize, newHistSize);
//    }

//    deprecated test case since bug#1410
//     @Test
//    public void testSaveDomainNoHistGenerated() throws Exception {
//        DomainWrapper d = domainAppService.edit(user, "kabulholidays.ie");
//
//        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
//        criteria.setDomainName("kabulholidays.ie");
//        long histSize = historicalDomainService.findHistory(criteria, 0, 1, null).getTotalResults();
//
//        d.setRemark("testing 1337");
//        domainAppService.save(user, d.getDomain());
//        long newHistSize = historicalDomainService.findHistory(criteria, 0, 1, null).getTotalResults();
//        Assert.assertEquals("History size", histSize, newHistSize);
//    }
     
//    deprecated test case since bug#1410
//    @Test
//    public void testSaveNicHandleNoHistGenerated() throws NicHandleNotFoundException, AccessDeniedException, EmptyRemarkException, AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException {
//        String nicHandleId = "AGP620-IEDR";
//        NicHandle nicHandle = nicHandleAppService.get(user, nicHandleId);
//        long histSize = nicHandleAppService.history(user, nicHandleId, 0, 1).getTotalResults();
//        Assert.assertNotNull("nicHandle", nicHandle);
//
//        nicHandleAppService.save(user, nicHandle, "testing 1337");
//        long newHistSize = nicHandleAppService.history(user, nicHandleId, 0, 1).getTotalResults();
//        Assert.assertEquals("History size", newHistSize, histSize);                
//    }
    
    
    /**
     * Checks, if the proper history record is generated
     * @throws AccountExportException 
     * 
     */
    @Test
    public void testSaveAccount() throws Exception {
        long accountId = 1L;

        Account account = accountAppService.get(user, accountId);
        String oldAddress = account.getAddress();
        int histSize = accountAppService.history(user, accountId).size();
        AssertJUnit.assertNotNull("Account", account);
        
        account.setAddress("new address"+System.currentTimeMillis());
        account = accountAppService.save(user, account, new AccountWrapper(account), "modify");
        Date oldChangeDate = account.getChangeDate(); 
        List<HistoricalObject<Account>> newHist = accountAppService.history(user, accountId);
        int newHistSize = newHist.size();
        AssertJUnit.assertEquals("History size", histSize+1, newHistSize);
        
        AssertJUnit.assertEquals("Changed date", newHist.get(0).getChangeDate(), oldChangeDate);
        AssertJUnit.assertEquals("Account address", oldAddress, newHist.get(0).getObject().getAddress());
    }

    @Test
    public void testSaveDomain() throws Exception {
        DomainWrapper oldDomain = new DomainWrapper(domainAppService.edit(user, "castlebargolfclub.ie"));        
                	
        
        // oldDns would be modified if  we use old 'd' object for modification, so once again:
        DomainWrapper d = new DomainWrapper(domainAppService.edit(user, "castlebargolfclub.ie"));
        
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName("castlebargolfclub.ie");
        long histSize = historicalDomainService.findHistory(criteria, 0, 1, null).getTotalResults();

        d.setRemark("testing 1337");
        d.setDomainHolder("New domain holder "+System.currentTimeMillis());
        d.getDnsWrapper().setNewIps(new String[] {"", "127.0.0.1"});
        d.getDnsWrapper().setNewNames(new String[] {"", "127.0.0.1"});
        d.setAdminContact1("RIH12-IEDR");
        d.setAdminContact2("AAI538-IEDR");
        d.setTechContact("AAI538-IEDR");
        d.setBillContact("AAI538-IEDR");
        domainAppService.save(user, d.getDomain());                
        
        //! reload domain after saving:
        d = new DomainWrapper(domainAppService.edit(user, "castlebargolfclub.ie"));
        
        LimitedSearchResult<HistoricalObject<Domain>> newHist = historicalDomainService.findHistory(criteria, 0, 100, null);
        long newHistSize = newHist.getTotalResults();
        HistoricalObject<Domain> histRec = newHist.getResults().get((int) newHistSize-1);
        AssertJUnit.assertEquals("History size", newHistSize, histSize+1);
        //check, if the domain hist is ok
        AssertJUnit.assertEquals("Domain holder", oldDomain.getDomainHolder(), histRec.getObject().getHolder());
        AssertJUnit.assertEquals("Change date", d.getDomain().getChangeDate(), histRec.getChangeDate());

        // check dns entries
        List<NameserverStub> oldDns = oldDomain.getDnsWrapper().getCurrentNameservers();
        AssertJUnit.assertEquals("Number of nameservers", oldDns.size(), histRec.getObject().getNameservers().size());
        for (int i=0; i<oldDns.size(); i++ ) {
            AssertJUnit.assertEquals("Nameserver ["+i+"] name", oldDns.get(i).getName(), histRec.getObject().getNameservers().get(i).getName());
            AssertJUnit.assertEquals("Nameserver ["+i+"] ip", oldDns.get(i).getIp(), histRec.getObject().getNameservers().get(i).getIpAddressAsString());
        }

        // check contacts
        checkContacts("Admin contact", oldDomain.getAdminContacts(), histRec.getObject().getAdminContacts());
        checkContacts("Billing contact", oldDomain.getBillingContacts(), histRec.getObject().getBillingContacts());
        checkContacts("Tech contact", oldDomain.getTechContacts(), histRec.getObject().getTechContacts());
    }
    private void checkContacts(String msg, List<Contact> expected,	List<Contact> actual) {
    	AssertJUnit.assertEquals(msg+" : size", expected.size(), actual.size());
		for (int i=0; i<expected.size(); i++) {
			AssertJUnit.assertEquals(msg +" ["+i+"]", actual.get(i), expected.get(i));
		}
	}

	@Test
    public void testSaveNicHandle() throws Exception {
        String nicHandleId = "AA11-IEDR";
        NicHandle oldNicHandle = nicHandleAppService.get(user, nicHandleId);
        NicHandle nicHandle = nicHandleAppService.get(user, nicHandleId);        
        long histSize = nicHandleAppService.history(user, nicHandleId, 0, 1).getTotalResults();
        AssertJUnit.assertNotNull("nicHandle", nicHandle);
        nicHandle.setAddress("new address "+System.currentTimeMillis());
        nicHandle.getPhones().add("dddddddd");
        nicHandle.getFaxes().add("dddddddd");
        nicHandle.getVat().setVatNo("7777788888");
        
        nicHandle = nicHandleAppService.save(user, nicHandle, "testing 1337");
        LimitedSearchResult<HistoricalObject<NicHandle>> newHist = nicHandleAppService.history(user, nicHandleId, 0, 1);
        HistoricalObject<NicHandle> histRec = newHist.getResults().get(0);
        long newHistSize = newHist.getTotalResults();
        AssertJUnit.assertEquals("History size", newHistSize, histSize+1);
        // check nh modification
        AssertJUnit.assertEquals("Address", oldNicHandle.getAddress(), histRec.getObject().getAddress());
        AssertJUnit.assertEquals("Change date", nicHandle.getChangeDate(), histRec.getChangeDate());
        // check telecom modification
        HistoricalObject<NicHandle> dbHistRec = nicHandleAppService.getHistorical(user, histRec.getObject().getNicHandleId(), histRec.getChangeId());
        AssertJUnit.assertEquals("Faxes", oldNicHandle.getFaxes(), dbHistRec.getObject().getFaxes());
        AssertJUnit.assertEquals("Phones", oldNicHandle.getPhones(), dbHistRec.getObject().getPhones());
        
        // check payment modification
        AssertJUnit.assertEquals("VAT No", oldNicHandle.getVat(), dbHistRec.getObject().getVat());
    }
}
