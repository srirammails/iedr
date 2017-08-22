package pl.nask.crs.accounts.dao;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static pl.nask.crs.accounts.helpers.AccountTestHelper.compareAccounts;
import static pl.nask.crs.accounts.helpers.AccountTestHelper.create1;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.nichandle.AbstractContextAwareTest;

/**
 * @author Kasia Fulara
 * @author Marianna Mysiorska
 */
public class AccountDAOTest extends AbstractContextAwareTest {

    @Autowired
    AccountDAO accountDAO;

    @Test
    public void testFind() {
        List<Account> a = accountDAO.find(null).getResults();
    }

    @Test
    public void testGet() {
        Account a = accountDAO.get(1L);
        Account expected = create1();
        compareAccounts(a, expected, true);
    }

    @Test
    public void testGetEmptyResult(){
        Account a = accountDAO.get(55555L);
        assertNull(a);
    }

    @Test
    public void testFindAllAccounts(){
        SearchResult<Account> result = accountDAO.find(null);
        assertEquals(result.getResults().size(), 49);
    }

    @Test
    public void testFindAccountById(){
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setId(1L);
        SearchResult<Account> result = accountDAO.find(criteria);
        assertEquals(result.getResults().size(), 1);
        compareAccounts(result.getResults().get(0), create1(), true);
    }

    @Test
    public void testFindAccountByNicHandle(){
        AccountSearchCriteria criteira = new AccountSearchCriteria();
        criteira.setNicHandle("I");
        SearchResult<Account> res = accountDAO.find(criteira);
        Assert.assertEquals(11, res.getResults().size());
    }

    @Test
    public void testFindAccountByAccountName(){
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setName("E");
        SearchResult<Account> res = accountDAO.find(criteria);
        Assert.assertEquals(res.getResults().size(), 4);
    }

    @Test
    public void testFindAccountByDomainName(){
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setDomainName("T");
        SearchResult<Account> res = accountDAO.find(criteria);
        Assert.assertEquals(res.getResults().size(), 6);
    }

    @Test
    public void testFindAllCriteria(){
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setId(1L);
        criteria.setNicHandle("GU");
        criteria.setNicHandle("I");
        criteria.setDomainName("T");
        SearchResult<Account> res = accountDAO.find(criteria);
        Assert.assertEquals(res.getResults().size(), 1);
    }

    @Test
    public void testFindLimited(){
        SearchResult<Account> res = accountDAO.find(null, 1, 10, null);

    }

    @Test
    public void testUpdate(){
        Account a1 = create1();
        a1.setAddress("new address");
        a1.setBillingContact(new Contact("AAA22-IEDR", "IEG Design ", "NHEmail000002@server.kom"));
        a1.setChangeDate(new Date());
        a1.setCountry("new country");
        a1.setCounty("new county");
        a1.setFax("new fax");
        a1.setInvoiceFreq("new freq");
        a1.setName("new name");
        a1.setNextInvMonth("new month");
        a1.setPhone("new phone");
        a1.setRemark("new remark");
        a1.setTariff("new tariff");
        a1.setWebAddress("new web addresss");
        a1.setStatus("new status");
        a1.setAgreementSigned(true);
        a1.setTicketEdit(true);
        a1.setVatCategory("S");
        accountDAO.update(a1);
        Account a2 = accountDAO.get(1L);
        compareAccounts(a1, a2, true);
    }

    @Test
    public void testInsertAccountFlagOnDuplicate() {
        Account a = accountDAO.get(180l);
        assertFalse(a.isTicketEdit());
        assertFalse(a.isAgreementSigned());
        a.setTicketEdit(true);
        a.setAgreementSigned(false);
        accountDAO.update(a);
        Account b = accountDAO.get(180l);
        assertTrue(b.isTicketEdit());
        assertFalse(b.isAgreementSigned());
    }

    @Test
    public void createAccount(){
        Contact contact = new Contact("AAA22-IEDR", "IEG Design ");
        Account a1 = new Account("name", contact, "Active", "address", "county", "country", "webAddress", "invFreq", "nextInvM", "phone", "fax", "tariff", "remark", new Date(), new Date(), new Date(), false, false);
        Long newId = accountDAO.createAccount(a1);
        Account a2 = new Account(newId, a1.getName(), a1.getBillingContact(), a1.getStatus(), a1.getAddress(), a1.getCounty(), a1.getCountry(), a1.getWebAddress(), a1.getInvoiceFreq(), a1.getNextInvMonth(), a1.getPhone(), a1.getFax(), a1.getTariff(), a1.getRemark(), a1.getCreationDate(), a1.getStatusChangeDate(), a1.getChangeDate(), false, false, null);
        Account a3 = accountDAO.get(newId);
        compareAccounts(a2, a3, false);
    }

    @Test
    public void createAccountAgreementSigned(){
        Contact contact = new Contact("AAA22-IEDR", "IEG Design ");
        Account a1 = new Account("name", contact, "Active", "address", "county", "country", "webAddress", "invFreq", "nextInvM", "phone", "fax", "tariff", "remark", new Date(), new Date(), new Date(), true, false);
        Long newId = accountDAO.createAccount(a1);
        Account a2 = new Account(newId, a1.getName(), a1.getBillingContact(), a1.getStatus(), a1.getAddress(), a1.getCounty(), a1.getCountry(), a1.getWebAddress(), a1.getInvoiceFreq(), a1.getNextInvMonth(), a1.getPhone(), a1.getFax(), a1.getTariff(), a1.getRemark(), a1.getCreationDate(), a1.getStatusChangeDate(), a1.getChangeDate(), true, false, null);
        Account a3 = accountDAO.get(newId);
        compareAccounts(a2, a3, false);
    }
    
    // exception is expected since ticketEdit flag can be set only, if agreementSigned flag is set
    @Test(expectedExceptions=IllegalStateException.class)
    public void createAccountTicketEdit() {
        Contact contact = new Contact("AAA22-IEDR", "IEG Design ");
        Account a1 = new Account("name", contact, "Active", "address", "county", "country", "webAddress", "invFreq", "nextInvM", "phone", "fax", "tariff", "remark", new Date(), new Date(), new Date(), false, true);
        Long newId = accountDAO.createAccount(a1);
        Account a2 = new Account(newId, a1.getName(), a1.getBillingContact(), a1.getStatus(), a1.getAddress(), a1.getCounty(), a1.getCountry(), a1.getWebAddress(), a1.getInvoiceFreq(), a1.getNextInvMonth(), a1.getPhone(), a1.getFax(), a1.getTariff(), a1.getRemark(), a1.getCreationDate(), a1.getStatusChangeDate(), a1.getChangeDate(), false, true, null);
        Account a3 = accountDAO.get(newId);
        compareAccounts(a2, a3, false);
    }

    // the right way: set both: agreementSigned flag and tiketEdit flag 
    @Test
    public void createAccountTicketEditAS() {
        Contact contact = new Contact("AAA22-IEDR", "IEG Design ");
        Account a1 = new Account("name", contact, "Active", "address", "county", "country", "webAddress", "invFreq", "nextInvM", "phone", "fax", "tariff", "remark", new Date(), new Date(), new Date(), true, true);
        Long newId = accountDAO.createAccount(a1);
        Account a2 = new Account(newId, a1.getName(), a1.getBillingContact(), a1.getStatus(), a1.getAddress(), a1.getCounty(), a1.getCountry(), a1.getWebAddress(), a1.getInvoiceFreq(), a1.getNextInvMonth(), a1.getPhone(), a1.getFax(), a1.getTariff(), a1.getRemark(), a1.getCreationDate(), a1.getStatusChangeDate(), a1.getChangeDate(), true, true, null);
        Account a3 = accountDAO.get(newId);
        compareAccounts(a2, a3, false);
    }

    @Test
    public void truncateDatesOnCreation() {
        // bacause Date holds only YYYY-MM-DD, to check that rounding does not
        // occur we should check 23:59:59.xxxxxx times
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);

        Date baseDate = cal.getTime();
        long baseTime = baseDate.getTime();
        final Date expected = new Date(baseTime);
        final Date expectedSqlDate = DateUtils.truncate(expected, Calendar.DATE);
        final Date expectedSqlTimestamp = DateUtils.truncate(expected, Calendar.SECOND);

        final Date a1Date = new Date(baseTime);
        final Date a2Date = new Date(baseTime + 100);
        final Date a3Date = new Date(baseTime + 499);
        final Date a4Date = new Date(baseTime + 999);
        long a1Id = accountDAO.createAccount(testAccountWithDates(a1Date));
        long a2Id = accountDAO.createAccount(testAccountWithDates(a2Date));
        long a3Id = accountDAO.createAccount(testAccountWithDates(a3Date));
        long a4Id = accountDAO.createAccount(testAccountWithDates(a4Date));

        final Account a1 = accountDAO.get(a1Id);
        assertEquals(a1.getCreationDate(), expectedSqlDate);
        assertEquals(a1.getStatusChangeDate(), expectedSqlDate);
        assertEquals(a1.getChangeDate(), expectedSqlTimestamp);

        final Account a2 = accountDAO.get(a2Id);
        assertEquals(a2.getCreationDate(), expectedSqlDate);
        assertEquals(a2.getStatusChangeDate(), expectedSqlDate);
        assertEquals(a2.getChangeDate(), expectedSqlTimestamp);

        final Account a3 = accountDAO.get(a3Id);
        assertEquals(a3.getCreationDate(), expectedSqlDate);
        assertEquals(a3.getStatusChangeDate(), expectedSqlDate);
        assertEquals(a3.getChangeDate(), expectedSqlTimestamp);

        final Account a4 = accountDAO.get(a4Id);
        assertEquals(a4.getCreationDate(), expectedSqlDate);
        assertEquals(a4.getStatusChangeDate(), expectedSqlDate);
        assertEquals(a4.getChangeDate(), expectedSqlTimestamp);
    }

    @Test
    public void truncateDatesOnUpdate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);

        Date baseDate = cal.getTime();
        long baseTime = baseDate.getTime();
        final Date expected = new Date(baseTime);
        final Date expectedSqlDate = DateUtils.truncate(expected, Calendar.DATE);
        final Date expectedSqlTimestamp = DateUtils.truncate(expected, Calendar.SECOND);

        final Date a1Date = new Date(baseTime);
        final Date a4Date = new Date(baseTime + 999);
        long a1Id = accountDAO.createAccount(testAccountWithDates(a1Date));

        final Account a1 = accountDAO.get(a1Id);
        a1.setStatus("Inactive");
        Date statusChangeDate = a1.getChangeDate();
        statusChangeDate.setTime(baseTime + 999);
        a1.setChangeDate(a4Date);
        accountDAO.update(a1);
        Account a2 = accountDAO.get(a1Id);
        assertEquals(a2.getCreationDate(), expectedSqlDate);
        assertEquals(a2.getStatusChangeDate(), expectedSqlDate);
        assertEquals(a2.getChangeDate(), expectedSqlTimestamp);
    }

    private Account testAccountWithDates(Date aDate) {
        Contact contact = new Contact("AAA22-IEDR", "IEG Design ");
        return new Account("name", contact, "Active", "address", "county", "country", "webAddress", "invFreq", "nextInvM", "phone", "fax", "tariff", "remark", aDate, aDate, aDate, true, true);
    }

    @Test
    public void test() {
        Account a = accountDAO.get(103L);
    }

    @Test
    public void getInternalRegistrars() {
        List<InternalRegistrar> registrars = accountDAO.getRegistrarForInternalLogin();
        assertEquals(registrars.size(), 8);
    }

}
