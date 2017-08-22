package pl.nask.crs.web.nichandles;

import org.apache.struts2.interceptor.SessionAware;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

import java.util.List;

/**
 * @author Marianna Mysiorska
 */
@Deprecated
public class NicHandleBrowserAction extends AuthenticatedUserAwareAction implements SessionAware {

    public static final String SEARCH = "search";

    private NicHandleAppService nicHandleAppService;
    private List<NicHandle.NHStatus> statuses;
    private String nicHandleId;
    private NicHandle nicHandle;
    private NicHandle.NHStatus newStatus;
    private String hostmastersRemark;


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

    public List<NicHandle.NHStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<NicHandle.NHStatus> statuses) {
        this.statuses = statuses;
    }

    public String alterStatus() throws Exception {
        statuses = nicHandleAppService.getStatusList();
        nicHandleAppService.alterStatus(getUser(), nicHandleId, newStatus, hostmastersRemark);
        return SEARCH;
    }

}

