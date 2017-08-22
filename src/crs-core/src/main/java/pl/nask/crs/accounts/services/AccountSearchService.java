package pl.nask.crs.accounts.services;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import java.util.List;

/**
 * @author Kasia Fulara
 */
public interface AccountSearchService {

    List<Account> getAccounts();

    List<Account> getAccounts(AccountSearchCriteria criteria);

    List<Account> getAccountsWithExclusion();

    List<Account> getAccountsWithExclusion(List<Long> additionalExcludedAccounts, List<SortCriterion> sortBy);

    Account getAccount(long id);

    List<Account> getAccountsForDocuments();

    LimitedSearchResult<Account> findAccount(AccountSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy);

    void confirmAccountActive(long id)
            throws AccountNotActiveException, AccountNotFoundException;

    List<InternalRegistrar> getRegistrarsForLogin();

}
