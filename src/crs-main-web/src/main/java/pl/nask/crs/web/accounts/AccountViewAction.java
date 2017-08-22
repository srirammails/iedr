package pl.nask.crs.web.accounts;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.utils.ValidationHelper;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;
import pl.nask.crs.contacts.Contact;

/**
 * @author Marianna Mysiorska
 */
public class AccountViewAction extends AuthenticatedUserAwareAction {

    public static final String VIEW = "view";
    public static final String SEARCH = "search";

    private AccountAppService accountAppService;

    private Account account;
    private AccountWrapper wrapper;
    private List<HistoricalObject<Account>> accountHist;
    private List<String> statuses;
    private Long id;

    private int historicalSelected = -1;
    private long changeId = -1;

    private ValidationHelper vh = new ValidationHelper(this);

    private String previousAction;
    private String hostmastersRemark;
    private String newStatus;


    public AccountViewAction(AccountAppService accountAppService) {
        Validator.assertNotNull(accountAppService, "account app service");
        this.accountAppService = accountAppService;
    }

    public AccountWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(AccountWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<HistoricalObject<Account>> getAccountHist() {
        return accountHist;
    }

    public void setAccountHist(List<HistoricalObject<Account>> accountHist) {
        this.accountHist = accountHist;
    }

    public AccountAppService getAccountAppService() {
        return accountAppService;
    }

    public void setAccountAppService(AccountAppService accountAppService) {
        this.accountAppService = accountAppService;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHistoricalSelected() {
        return historicalSelected;
    }

    public void setChangeDate(long changeId) {
        this.changeId = changeId;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public boolean isHistory() {
        return this.historicalSelected >= 0;
    }

    public boolean hasCurrent() throws Exception {
        return accountAppService.get(getUser(), id) != null;
    }

    public String view() throws Exception {
        statuses = accountAppService.getStatusList();
        accountHist = accountAppService.history(getUser(), id);
        if (changeId >= 0 && accountHist != null && !accountHist.isEmpty()) {
            for (int i = 0; i < accountHist.size(); i++) {
                HistoricalObject<Account> hObject = accountHist.get(i);
                if (hObject.getChangeId() == changeId) {
                    account = hObject.getObject();
                    wrapper = new AccountWrapper(account);
                    historicalSelected = i;
                }
            }
        } else {
            account = accountAppService.get(getUser(), id);
            wrapper = new AccountWrapper(account);
        }
        return VIEW;
    }

    public String alterStatus() throws Exception {
        try {
            statuses = accountAppService.getStatusList();
            boolean valid = true;
            if (vh.isFieldEmpty("hostmastersRemark")) {
                addActionError("Hostmaster's remark must be filled");
                valid = false;
            }
            if (newStatus == null) {
                addActionError("Status must be set");
                valid = false;
            }
            if (valid) {
                accountAppService.alterStatus(getUser(), id, newStatus, hostmastersRemark);
                account = accountAppService.get(getUser(), id);
                wrapper = new AccountWrapper(account);
                accountHist = accountAppService.history(getUser(), id);
                return VIEW;
            } else {
                return ERROR;
            }
        } catch (NicHandleAssignedToDomainException ex){
            addActionError("Cannot alter status to DELETED. The nic handle " + ex.getNicHandleId() + " is assigned to some domain.");
            return ERROR;
        }
    }

    public String makeContactInfo(Contact contact){
        return ContactHelper.makeContactInfo(contact);
    }

}
