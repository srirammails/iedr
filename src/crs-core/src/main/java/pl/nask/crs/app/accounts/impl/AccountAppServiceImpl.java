package pl.nask.crs.app.accounts.impl;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountEmailException;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.search.HistoricalAccountSearchCriteria;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.accounts.services.AccountService;
import pl.nask.crs.accounts.services.HistoricalAccountSearchService;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.exceptions.ContactCannotChangeException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

/**
 * @author Marianna Mysiorska
 */
public class AccountAppServiceImpl implements AccountAppService {

    private AccountSearchService accountSearchService;
    private AccountService accountService;
    private HistoricalAccountSearchService historicalAccountSearchService;
    
    private NicHandleService nicHandleService;
    
    public AccountAppServiceImpl(AccountSearchService accountSearchService, AccountService accountService, HistoricalAccountSearchService historicalAccountSearchService, NicHandleService nicHandleService) {
        Validator.assertNotNull(accountSearchService, "account search service");
        Validator.assertNotNull(accountService, "account service");
        Validator.assertNotNull(historicalAccountSearchService, "historical account search service");
        Validator.assertNotNull(nicHandleService, "nic handle service");
        this.accountSearchService = accountSearchService;
        this.accountService = accountService;
        this.historicalAccountSearchService = historicalAccountSearchService;
        this.nicHandleService = nicHandleService;
    }

    @Transactional(readOnly = true)
    public LimitedSearchResult<Account> search(AuthenticatedUser user, AccountSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return accountSearchService.findAccount(criteria, offset, limit, orderBy);
    }

    @Transactional(readOnly = true)
    public Account get(AuthenticatedUser user, long id)
            throws AccessDeniedException, AccountNotFoundException {
        validateLoggedIn(user);
        return accountSearchService.getAccount(id);
    }

    @Transactional(readOnly = true)
    public List<HistoricalObject<Account>> history(AuthenticatedUser user, long id)
            throws AccessDeniedException, AccountNotFoundException {
        validateLoggedIn(user);
        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(id);
        return historicalAccountSearchService.findHistoricalNicHandle(criteria).getResults();
    }

    @Transactional(rollbackFor = Exception.class)
    public void alterStatus(AuthenticatedUser user, long id, String status, String hostmastersRemark)
            throws AccessDeniedException, EmptyRemarkException, AccountNotFoundException, NicHandleAssignedToDomainException {
        validateLoggedIn(user);
        accountService.alterStatus(id, status, user.getUsername(), user.getSuperUserName(), hostmastersRemark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account save(AuthenticatedUser user, Account account, AccountWrapper wrapper, String hostmastersRemark)
            throws AccessDeniedException, AccountNotFoundException, EmptyRemarkException, ContactNotFoundException, NicHandleNotFoundException, ContactCannotChangeException, ExportException {
        validateLoggedIn(user);
        Validator.assertNotNull(account, "account");
        account.setBillingContact(new Contact(wrapper.getNewBillingContactId(), wrapper.getNewBillingContactName(), wrapper.getNewBillingContactEmail()));
        accountService.save(account, hostmastersRemark, user.getUsername(), user.getSuperUserName());
        return accountSearchService.getAccount(account.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = {NicHandleEmailException.class, AccountEmailException.class})
    public Account create(AuthenticatedUser user, CreateAccountContener createAccountContener, String hostmastersRemark)
            throws AccessDeniedException, ContactNotFoundException, EmptyRemarkException, NicHandleNotFoundException, NicHandleNotActiveException, AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException, AccountEmailException, InvalidCountryException, InvalidCountyException, ExportException {
        validateLoggedIn(user);
        Validator.assertNotNull(createAccountContener, "create account contener");
        accountService.createAccount(createAccountContener, hostmastersRemark, user.getUsername(), user.getSuperUserName());
        return createAccountContener.getAccount();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public NewAccount createDirectAccount(NewNicHandle nhw,
    		String password, boolean useTfa) throws AccountNotFoundException, NicHandleNotFoundException, NicHandleEmailException, AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, ExportException, EmptyPasswordException, PasswordTooEasyException {
    	
        NewAccount nh = 
        		nicHandleService.createDirectAccount(
        				nhw.getName(), 
        				nhw.getCompanyName(), 
        				nhw.getEmail(), 
        				nhw.getAddress(), 
        				nhw.getCountry(), 
        				nhw.getCounty(),
        				nhw.getPhones(), 
        				nhw.getFaxes(), 
        				nhw.getVatNo(), password, useTfa);                 
        return nh;
    }

    public List<String> getStatusList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Active");
        list.add("Deleted");
        list.add("Suspended");
        return list;
    }
}
