package pl.nask.crs.web.nichandles;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NicHandleWrapper;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.app.utils.ValidationHelper;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleIsTicketContactException;
import pl.nask.crs.web.displaytag.TableParams;
import pl.nask.crs.web.displaytag.TicketsPaginatedList;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;


/**
 * @author Marianna Mysiorska
 * @author Piotr Tkaczyk
 */
public class NicHandleViewAction extends AuthenticatedUserAwareAction {

    public static final String VIEW = "view";

    private NicHandleAppService nicHandleAppService;
    private AccountSearchService accountSearchService;
	private UserAppService userAppService;


    private NicHandle nicHandle;
    // private List<HistoricalObject<NicHandle>> nicHandleHist;
    TicketsPaginatedList<HistoricalObject<NicHandle>> paginatedResult;
    TableParams tableParams = new TableParams();
    private List<NicHandle.NHStatus> statuses;
    private String nicHandleId;

    // alter status
    private String hostmastersRemark;
    private NicHandle.NHStatus newStatus;

    private ValidationHelper vh = new ValidationHelper(this);

    private String previousAction;

    private int historicalSelected = -1;
    private long changeId = -1;

    private NicHandleWrapper wrapper;

	private boolean hasAccessRecord;

    public NicHandleViewAction(NicHandleAppService nicHandleAppService, AccountSearchService accountSearchService, UserAppService userAppService) {
        Validator.assertNotNull(nicHandleAppService, "nic handle app service");
        Validator.assertNotNull(accountSearchService, "account search service");
        Validator.assertNotNull(userAppService, "user app service");
        this.nicHandleAppService = nicHandleAppService;
        this.accountSearchService = accountSearchService;
        this.userAppService = userAppService; 
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccounts();
    }

    public NicHandle getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(NicHandle nicHandle) {
        this.nicHandle = nicHandle;
    }

   
    public NicHandleAppService getNicHandleAppService() {
        return nicHandleAppService;
    }

    public void setNicHandleAppService(NicHandleAppService nicHandleAppService) {
        this.nicHandleAppService = nicHandleAppService;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public List<NicHandle.NHStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<NicHandle.NHStatus> statuses) {
        this.statuses = statuses;
    }

    public int getHistoricalSelected() {
        return historicalSelected;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public boolean isHistory() {
        return historicalSelected >= 0;
    }

    public boolean hasCurrent() throws Exception {
        return nicHandleAppService.get(getUser(), nicHandleId) != null;
    }

    public String input() throws Exception {
        return view();
    }

    // use input() method as it is not being validated
    public String view() throws Exception {
        statuses = nicHandleAppService.getStatusList();
        int limit = 15;
        LimitedSearchResult<HistoricalObject<NicHandle>> res = nicHandleAppService.history(getUser(), nicHandleId, (tableParams.getPage() - 1) * limit, limit);
        paginatedResult = new TicketsPaginatedList<HistoricalObject<NicHandle>>(res.getResults(), (int) res.getTotalResults(), tableParams.getPage(), limit);
        List<HistoricalObject<NicHandle>> nicHandleHist = res.getResults();
        
        if (changeId >= 0 && nicHandleHist != null && !nicHandleHist.isEmpty()) {
            for (int i = 0; i < nicHandleHist.size(); i++) {
                HistoricalObject<NicHandle> hObject = nicHandleHist.get(i);
                if (hObject.getChangeId() == changeId) {
                    nicHandle = hObject.getObject();
                    historicalSelected = i;
                    wrapper = new NicHandleWrapper(nicHandle);
                }
            }

        } else {
            nicHandle = nicHandleAppService.get(getUser(), nicHandleId);            
            wrapper = new NicHandleWrapper(nicHandle);
        }
        hasAccessRecord = userAppService.getUser(nicHandleId) != null;

        return VIEW;
    }

    public NicHandleWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(NicHandleWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public String alterStatus() throws Exception {
        try {
            statuses = nicHandleAppService.getStatusList();
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
                nicHandleAppService.alterStatus(getUser(), nicHandleId, newStatus, hostmastersRemark);
                return VIEW;
            } else {
                return ERROR;
            }
        } catch (NicHandleIsAccountBillingContactException ex){
            addActionError("Nic handle " + ex.getNicHandleId() + " is a billing contact of an account. Its status cannot be changed to Deleted.");
            return ERROR;
        } catch (NicHandleIsTicketContactException ex){
            addActionError("Nic handle " + ex.getNicHandleId() + " is a ticket contact. Its status cannot be changed to Deleted.");
            return ERROR;
        }
    }


    public NicHandle.NHStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(NicHandle.NHStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public TicketsPaginatedList<HistoricalObject<NicHandle>> getPaginatedResult() {
        return paginatedResult;
    }

    public TableParams getTableParams() {
        return tableParams;
    }

    public void setTableParams(TableParams tableParams) {
        this.tableParams = tableParams;
    }

    public boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }
    
    public boolean isHasAccessRecord() {
		return hasAccessRecord;
	}            
}
