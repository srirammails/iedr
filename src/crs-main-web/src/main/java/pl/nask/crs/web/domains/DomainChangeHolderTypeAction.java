package pl.nask.crs.web.domains;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class DomainChangeHolderTypeAction extends AuthenticatedUserAwareAction {
	private final DomainAppService service;
	
	private String domainName;
	
	private DomainHolderType newHolderType;
	
	private String hostmastersRemark;

	public DomainChangeHolderTypeAction(DomainAppService service) {
		this.service = service;
	}
	
	public String updateHolderType() throws Exception {
        try {
            service.updateHolderType(getUser(), domainName, newHolderType, hostmastersRemark);
        } catch (DomainIllegalStateException e) {
            addActionError("Invalid domain state: " + e.getMessage());
            return ERROR;
        }
        return SUCCESS;
	}
	
	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public DomainHolderType getNewHolderType() {
		return newHolderType;
	}

	public void setNewHolderType(DomainHolderType newHolderType) {
		this.newHolderType = newHolderType;
	}
	
	public void setHostmastersRemark(String hostmastersRemark) {
		this.hostmastersRemark = hostmastersRemark;
	}
	
	public String getHostmastersRemark() {
		return hostmastersRemark;
	}
}

