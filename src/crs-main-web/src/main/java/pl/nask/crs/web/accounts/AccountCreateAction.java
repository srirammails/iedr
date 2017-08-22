package pl.nask.crs.web.accounts;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountTariff;
import pl.nask.crs.accounts.exceptions.AccountEmailException;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountCreateWrapper;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;

/**
 * @author Marianna Mysiorska
 */
public class AccountCreateAction extends AbstractAccountAction {

    public static final String INPUT = "input";
    public static final String CREATE = "create";
    public static final String ERROR_EMAIL = "error_email";
    private static final Logger log = Logger.getLogger(AccountEditAction.class);

    private AccountCreateWrapper accountCreateWrapper;
    private CreateAccountContener createAccountContener;
    private String previousAction;

    private List<HistoricalObject<Account>> accountHist;
    
    public AccountCreateAction(AccountAppService accountAppService, CountryFactory countryFactory, Dictionary<String, AccountTariff> tariffsDictionary, PaymentAppService paymentAppService) {
        super(accountAppService, countryFactory, tariffsDictionary, paymentAppService);
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public CreateAccountContener getCreateAccountContener() {
        return createAccountContener;
    }

    public void setCreateAccountContener(CreateAccountContener createAccountContener) {
        this.createAccountContener = createAccountContener;
    }
    
    public AccountCreateWrapper getAccountCreateWrapper() {
        return accountCreateWrapper;
    }

    public void setAccountCreateWrapper(AccountCreateWrapper accountCreateWrapper) {
        this.accountCreateWrapper = accountCreateWrapper;
    }

    public List<HistoricalObject<Account>> getAccountHist() {
        return accountHist;
    }

    public void setAccountHist(List<HistoricalObject<Account>> accountHist) {
        this.accountHist = accountHist;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public String input() throws Exception {
        hostmastersRemark = "Created";
        accountCreateWrapper = new AccountCreateWrapper();
        return INPUT;
    }

    public String create() throws Exception {
        try {
            setStatuses(accountAppService.getStatusList());
            createAccountContener = accountCreateWrapper.createContener(hostmastersRemark);
            account = accountAppService.create(getUser(), createAccountContener, hostmastersRemark);
            id = account.getId();
            if (!createAccountContener.isAccessGroups())
                addActionError("The billing contact " + createAccountContener.getAccount().getBillingContact().getNicHandle() + " has no permission group assigned. Click the icon next to the billing contact to fix it.");
            account = createAccountContener.getAccount();
            wrapper = new AccountWrapper(account);
            id = account.getId();
            previousAction = "accounts-search";            
            return CREATE;
        } catch (NicHandleIsAccountBillingContactException ex) {
            addActionError("The nic handle " + ex.getNicHandleId() + " is already assigned to the account nr " + ex.getAccountId());
            return ERROR;
        } catch (NicHandleNotActiveException ex) {
            addActionError("The nic handle " + ex.getNicHandleId() + " has not Active status.");
            return ERROR;
        } catch (AccountEmailException ex){
            addActionError("Error sending email");
            account = createAccountContener.getAccount();
            id = account.getId();
            previousAction = "accounts-search";
            return ERROR_EMAIL;
        }
    }
}
