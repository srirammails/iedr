package pl.nask.crs.app.accounts;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountEmailException;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
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
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

/**
 * @author Marianna Mysiorska
 */
public interface AccountAppService extends AppSearchService<Account, AccountSearchCriteria> {

    public Account get(AuthenticatedUser user, long id)
            throws AccessDeniedException, AccountNotFoundException;

    public List<HistoricalObject<Account>> history(AuthenticatedUser user, long id)
            throws AccessDeniedException, AccountNotFoundException;

    public void alterStatus(AuthenticatedUser user, long id, String status, String hostmastersRemark)
            throws AccessDeniedException, EmptyRemarkException, AccountNotFoundException, NicHandleAssignedToDomainException;

    //FIXME: refactoring needed!
    public Account save(AuthenticatedUser user, Account account, AccountWrapper wrapper, String hostmastersRemark)
            throws AccessDeniedException, AccountNotFoundException, EmptyRemarkException, ContactNotFoundException, NicHandleNotFoundException, NicHandleIsAccountBillingContactException, ContactCannotChangeException, ExportException;

    public Account create(AuthenticatedUser user, CreateAccountContener createAccountContener, String hostmastersRemark)
            throws AccessDeniedException, ContactNotFoundException, EmptyRemarkException, NicHandleNotFoundException, NicHandleNotActiveException, AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException, AccountEmailException, InvalidCountryException, InvalidCountyException, ExportException;

    public List<String> getStatusList();

	public NewAccount createDirectAccount(NewNicHandle newNicHandle, String password, boolean useTfa) 
			throws AccountNotFoundException, NicHandleNotFoundException, NicHandleEmailException, AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, ExportException, EmptyPasswordException, PasswordTooEasyException;
}
