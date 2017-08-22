package pl.nask.crs.accounts.services;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.nichandle.AbstractContextAwareTest;

/**
 * @author Kasia Fulara
 * @author Marianna Mysiorska
 */
public class AccountSearchServiceTest extends AbstractContextAwareTest {

    @Autowired
    AccountSearchService accountSearchService;

    @Test
    public void testGetAccountList() {
        List<Account> result = accountSearchService.getAccounts();
        AssertJUnit.assertNotNull("Account list is null", result);
    }

    @Test
    public void testGetAccount() {
        Account a = accountSearchService.getAccount(1l);
        AssertJUnit.assertNotNull("Account is null", a);
        AssertJUnit.assertNotNull("Account name is null", a);
        AssertJUnit.assertNotSame("Account name is empty", "", a);
    }

    @Test
    public void testFindAccount(){
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        LimitedSearchResult<Account> result = accountSearchService.findAccount(criteria, 0, 10, null);
    }
}
