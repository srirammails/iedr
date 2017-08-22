package pl.nask.crs.accounts.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Kasia Fulara
 * @author Marianna Mysiorska
 */
public class AccountSearchServiceImpl implements AccountSearchService {
    
    private AccountDAO dao;
    static final List<SortCriterion> NAME_SORT_CRITERIA = Arrays.asList(new SortCriterion("name", true));
    static final long GENERAL_EXLUDED_ACCOUNT_ID = 315;

    public AccountSearchServiceImpl(AccountDAO dao) {
        this.dao = dao;
    }

    public List<Account> getAccounts() {
        return dao.find(new AccountSearchCriteria(), NAME_SORT_CRITERIA).getResults();
    }

    public List<Account> getAccounts(AccountSearchCriteria criteria) {
        return dao.find(criteria, NAME_SORT_CRITERIA).getResults();
    }

    @Override
    public List<Account> getAccountsWithExclusion() {
        return getAccountsWithExclusion(null, NAME_SORT_CRITERIA);
    }

    @Override
    public List<Account> getAccountsWithExclusion(List<Long> additionalExcludedAccounts, List<SortCriterion> sortBy) {
        List<Account> accounts =  dao.find(new AccountSearchCriteria(), sortBy).getResults();
        List<Account> retAccounts = new ArrayList<Account>(accounts.size());
        for (Account account : accounts) {
            if (account.getId() != GENERAL_EXLUDED_ACCOUNT_ID && !containsId(additionalExcludedAccounts, account.getId())) {
                retAccounts.add(account);
            }
        }
        return retAccounts;
    }

    private boolean containsId(List<Long> additionalExcludedAccounts, long id) {
        return !Validator.isEmpty(additionalExcludedAccounts) && additionalExcludedAccounts.contains(id);
    }

    public Account getAccount(long id) {
        return dao.get(id);
    }

    public List<Account> getAccountsForDocuments() {
        List<Account> accounts = getAccounts();
        List<Account> retAccounts = new ArrayList<Account>(accounts.size());
        for (Account account : accounts)
            if (account.getId() != GENERAL_EXLUDED_ACCOUNT_ID && account.getId() > 2 && account.isActive())
                retAccounts.add(account);
        return retAccounts;
    }

    public LimitedSearchResult<Account> findAccount(AccountSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
    	List<SortCriterion> fiteredOrderBy = AccountSortCriteria.filterValid(orderBy);
        return dao.find(criteria, offset, limit, fiteredOrderBy);
    }

    public void confirmAccountActive(long id) 
            throws AccountNotActiveException, AccountNotFoundException {
        Account account = dao.get(id);
        if (account == null)
            throw new AccountNotFoundException(id);
        if (!"Active".equals(account.getStatus()))
            throw new AccountNotActiveException(id);
    }

    @Override
    public List<InternalRegistrar> getRegistrarsForLogin() {
        return dao.getRegistrarForInternalLogin();
    }
}
