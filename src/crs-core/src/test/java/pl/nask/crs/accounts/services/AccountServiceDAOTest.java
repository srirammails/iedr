package pl.nask.crs.accounts.services;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static pl.nask.crs.accounts.helpers.AccountTestHelper.compareAccounts;
import static pl.nask.crs.accounts.helpers.AccountTestHelper.compareDatesYMD;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.exceptions.ContactCannotChangeException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;

/**
 * @author Marianna Mysiorska
 */
public class AccountServiceDAOTest extends AbstractContextAwareTest {

    @Resource
    AccountService service;
    @Resource
    AccountSearchService searchService;
    

    @Test
    public void alterStatus() throws Exception{
        service.alterStatus(101L, "Suspended", "TEST-IEDR", null, "remark");
        Account account = searchService.getAccount(101L);
        AssertJUnit.assertTrue("Suspended".equals(account.getStatus()));
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(account.getStatusChangeDate());
        cal2.setTime(new Date());
        AssertJUnit.assertEquals(cal1.get(YEAR), cal2.get(YEAR));
        AssertJUnit.assertEquals(cal1.get(MONTH), cal2.get(MONTH));
        AssertJUnit.assertEquals(cal1.get(DAY_OF_MONTH), cal2.get(DAY_OF_MONTH));
    }

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void alterStatusNoExists() throws Exception{
        service.alterStatus(123456789L, "Suspended", "TEST-IEDR", null, "remark");
    }

    @Test
    public void save() throws Exception {
        Account account = searchService.getAccount(104L);
        account.setName("name1");
        account.setWebAddress("webAddress1");
        account.setAgreementSigned(true);
        account.setTicketEdit(true);
        service.save(account, "remark1", "AAA-IEDR", null);
        Account account2 = searchService.getAccount(104L);
        compareAccounts(account, account2, false);
    }  

    @Test (expectedExceptions = ContactCannotChangeException.class)
    public void saveNicHandleIsBillingContactException() throws Exception {
        Account account = searchService.getAccount(104L);
        account.setBillingContact(new Contact("AAA22-IEDR", "IEG Design "));
        service.save(account, "remark1", "AAA-IEDR", null);
        Account account2 = searchService.getAccount(104L);
        compareAccounts(account, account2, false);
    }

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void saveNotExists() throws Exception{
        Contact contact = new Contact("TEST-IEDR");
        Account account = new Account(123455L, "name", contact, "status", "address", "county", "country", "webAddress", "invoice", "next", "phone", "fax", "tariff", "hostmastersRemark", new Date(), new Date(), new Date(), false, false, null);
        service.save(account, "remark1", "USER-IEDR", null);
    }

    @Test
    public void createAccount() throws Exception {
        Date nowDate = new Date();
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "AA11-IEDR", "hostmastersRemark", true, true);
        service.createAccount(createAccountContener, "hostmastersRemark", "AA11-IEDR", null);
        Account newAccount = createAccountContener.getAccount();
        AssertJUnit.assertEquals("name", newAccount.getName());
        AssertJUnit.assertEquals("webAddress", newAccount.getWebAddress());
        AssertJUnit.assertEquals("AA11-IEDR", newAccount.getBillingContact().getNicHandle());
        compareDatesYMD(nowDate, newAccount.getChangeDate());
        compareDatesYMD(nowDate, newAccount.getCreationDate());
        compareDatesYMD(nowDate, newAccount.getStatusChangeDate());
        AssertJUnit.assertTrue(newAccount.isAgreementSigned());
        AssertJUnit.assertTrue(newAccount.isTicketEdit());
    }

    @Test (expectedExceptions = EmptyRemarkException.class)
    public void createAccountNullRemark() throws Exception{
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "AA11-IEDR", null, false, false);
        service.createAccount(createAccountContener, null, "TEST-IEDR", null);
    }

    @Test (expectedExceptions = EmptyRemarkException.class)
    public void createAccountEmptyRemark() throws Exception{
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "AA11-IEDR", "", false, false);
        service.createAccount(createAccountContener, "", "TEST-IEDR", null);
    }

    @Test (expectedExceptions = ContactNotFoundException.class)
    public void createAccountBillingContactNotExists() throws Exception{
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "NOTEX-IEDR", "remark", false, false);
        service.createAccount(createAccountContener, "remark", "TEST-IEDR", null);
    }

    @Test (expectedExceptions = NicHandleIsAccountBillingContactException.class)
    public void createAccountBillingContactAssignedToAnotherAccount() throws Exception{
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "AAA22-IEDR", "remark", false, false);
        service.createAccount(createAccountContener, "remark", "TEST-IEDR", null);
    }


}
