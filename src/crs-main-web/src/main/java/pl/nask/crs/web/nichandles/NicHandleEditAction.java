package pl.nask.crs.web.nichandles;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.AccountNumberWrapper;
import pl.nask.crs.app.nichandles.wrappers.NicHandleWrapper;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleEditAction extends AuthenticatedUserAwareAction {

    public static final String INPUT = "input";
    public static final String SAVE = "save";

    private String nicHandleId;
    private String hostmastersRemark;
    private NicHandle nicHandle;
    private AccountNumberWrapper accountNumberWrapper;
    private NicHandleWrapper wrapper;
    private NicHandleAppService nicHandleAppService;
    private AccountSearchService accountSearchService;
    private List<Country> countries;
    private PaymentAppService paymentAppService;

    public NicHandleEditAction(NicHandleAppService nicHandleAppService, AccountSearchService accountSearchService,
                               CountryFactory countryFactory, PaymentAppService paymentAppService) {
        Validator.assertNotNull(nicHandleAppService, "nichandle app service");
        Validator.assertNotNull(accountSearchService, "account search service");
        Validator.assertNotNull(countryFactory, "country factory");
        Validator.assertNotNull(paymentAppService, "paymentAppService");
        this.nicHandleAppService = nicHandleAppService;
        this.accountSearchService = accountSearchService;
        this.countries = countryFactory.getEntries();
        this.paymentAppService = paymentAppService;
    }

    public List<String> getCategories() {
        return paymentAppService.getVatCategories(getUser());
    }

    public AccountNumberWrapper getAccountNumberWrapper() {
        return accountNumberWrapper;
    }

    public void setAccountNumberWrapper(AccountNumberWrapper accountNumberWrapper) {
        this.accountNumberWrapper = accountNumberWrapper;
    }

    public void setWrapper(NicHandleWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public NicHandleWrapper getWrapper() {
        return wrapper;
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    private List<NicHandle.NHStatus> statuses;

    public List<NicHandle.NHStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<NicHandle.NHStatus> statuses) {
        this.statuses = statuses;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public NicHandleAppService getNicHandleAppService() {
        return nicHandleAppService;
    }

    public void setNicHandleAppService(NicHandleAppService nicHandleAppService) {
        this.nicHandleAppService = nicHandleAppService;
    }

    public NicHandle getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(NicHandle nicHandle) {
        this.nicHandle = nicHandle;
    }

    public String input() throws Exception {
        nicHandle = nicHandleAppService.get(getUser(), nicHandleId);
        wrapper = new NicHandleWrapper(nicHandle);
        accountNumberWrapper = new AccountNumberWrapper(nicHandle.getAccount().getId());
        return INPUT;
    }

    public String save() throws Exception {
        try {
            statuses = nicHandleAppService.getStatusList();
            nicHandle.setEmail(nicHandle.getEmail().toLowerCase());
            nicHandle.setAccount(new Account(accountNumberWrapper.getAccountNumber()));
            if (Validator.isEmpty(nicHandle.getVatCategory())) {
                nicHandle.setVatCategory(null);
            }
            nicHandle = nicHandleAppService.save(getUser(), nicHandle, hostmastersRemark);
            wrapper = new NicHandleWrapper(nicHandle);
            nicHandleId = nicHandle.getNicHandleId();
            return SAVE;
        } catch (NicHandleIsAccountBillingContactException ex) {
            addActionError("Account cannot be changed. The nic handle " + ex.getNicHandleId() + " is billing contact for the account nr " + ex.getAccountId());
            return ERROR;
        } catch (AccountNotActiveException ex) {
            addActionError("Account " + ex.getAccountId() + " is not Active.");
            return ERROR;
        } catch (AccountNotFoundException ex) {
            addActionError("Account " + ex.getAccountId() + "not found in the system.");
            return ERROR;
        }
    }

    public List<Country> getCountries() {
        return countries;
    }
}
