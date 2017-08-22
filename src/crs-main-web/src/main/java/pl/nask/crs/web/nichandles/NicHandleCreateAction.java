package pl.nask.crs.web.nichandles;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NicHandleCreateWrapper;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleCreateAction extends AuthenticatedUserAwareAction {

    public static final String INPUT = "input";
    public static final String CREATE = "create";

    private NicHandleAppService nicHandleAppService;
    private AccountSearchService accountSearchService;

    private List<NicHandle.NHStatus> statuses;
    private NicHandleCreateWrapper nicHandleCreateWrapper;
    private NicHandle nicHandle;
    private String hostmastersRemark = "Created";
    private String nicHandleId;
    private List<Country> countries;
    // nicHandleId of NicHandle, which data will be used to create this NicHandle
    private String createFrom;
    private String previousAction;

    public NicHandleCreateAction(NicHandleAppService nicHandleAppService, AccountSearchService accountSearchService,
                                 CountryFactory countryFactory) {
        Validator.assertNotNull(nicHandleAppService, "nichandle app service");
        Validator.assertNotNull(accountSearchService, "account search service");
        Validator.assertNotNull(countryFactory, "country factory");
        this.nicHandleAppService = nicHandleAppService;
        this.accountSearchService = accountSearchService;
        this.countries = countryFactory.getEntries();
    }

    
    public void setCreateFrom(String createFrom) {
        this.createFrom = createFrom;
    }


    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public NicHandle getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(NicHandle nicHandle) {
        this.nicHandle = nicHandle;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public NicHandleCreateWrapper getNicHandleCreateWrapper() {
        return nicHandleCreateWrapper;
    }

    public void setNicHandleCreateWrapper(NicHandleCreateWrapper nicHandleCreateWrapper) {
        this.nicHandleCreateWrapper = nicHandleCreateWrapper;
    }

    public List<NicHandle.NHStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<NicHandle.NHStatus> statuses) {
        this.statuses = statuses;
    }

    public NicHandleAppService getNicHandleAppService() {
        return nicHandleAppService;
    }

    public void setNicHandleAppService(NicHandleAppService nicHandleAppService) {
        this.nicHandleAppService = nicHandleAppService;
    }

    public String input() throws Exception {
        nicHandleCreateWrapper = new NicHandleCreateWrapper();
        if (createFrom != null)
            nicHandleCreateWrapper.fillFrom(nicHandleAppService.get(getUser(), createFrom));
        return INPUT;
    }   

    public List<Country> getCountries() {
        return countries;
    }

    public String create() throws Exception {
        try {
            statuses = nicHandleAppService.getStatusList();
            nicHandleCreateWrapper.setEmail(nicHandleCreateWrapper.getEmail().toLowerCase());
            nicHandle = nicHandleAppService.create(getUser(), nicHandleCreateWrapper.makeNewNicHandle(), hostmastersRemark, true);
            nicHandleId = nicHandle.getNicHandleId();
            return CREATE;
        } catch (AccountNotActiveException ex) {
            addActionError("Account " + ex.getAccountId() + " is not Active.");
            return ERROR;
        } catch (AccountNotFoundException ex) {
            addActionError("Account " + ex.getAccountId() + "not found in the system.");
            return ERROR;
        } catch (NicHandleEmailException ex) {
            nicHandleId = ex.getNicHandleId();
            addActionError(ex.getMessage());
            return "emailError";
        }
    }
    
    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }
}