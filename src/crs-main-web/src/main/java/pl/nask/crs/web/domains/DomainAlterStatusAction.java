package pl.nask.crs.web.domains;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.domains.DomainStatus;
import pl.nask.crs.domains.NewDomainStatus;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class DomainAlterStatusAction extends AuthenticatedUserAwareAction {
	private final TriplePassAppService triplePassAppService;
	private final DomainAppService domainAppService;
	
	private String domainName;

	private NewDomainStatus newStatus;
	private String remark;
	
	public DomainAlterStatusAction(TriplePassAppService triplePassAppService, DomainAppService domainAppService) {
		super();
		this.triplePassAppService = triplePassAppService;
		this.domainAppService = domainAppService;
	}
	
	/**
	 * alter status of the active domain
	 * 
	 * @return
	 */
	public String alterStatus() {
		if (newStatus == NewDomainStatus.Deleted) {
			domainAppService.enterVoluntaryNRP(getUser(), domainName);
		} else if (newStatus == NewDomainStatus.Active) {
			triplePassAppService.triplePass(getUser(), domainName);
		} else if (newStatus == NewDomainStatus.Reactivate) {
			domainAppService.removeFromVoluntaryNRP(getUser(), domainName);
		} 
		return SUCCESS;
	}
				
	/**
	 * alter status for the fresh, not active GIBO domain
	 * 
	 * @return
	 */
	public String alterStatusForAutoCreatedDomain() {
		if (newStatus == NewDomainStatus.Active) {
			triplePassAppService.triplePass(getUser(), domainName, remark);
		} else {
			triplePassAppService.giboAdminFailed(getUser(), domainName, remark);
		}
		return SUCCESS;		
	}
	
	public void setNewStatus(NewDomainStatus status) {
		this.newStatus = status;
	}
	
	public NewDomainStatus getNewStatus() {
		return newStatus;
	}		
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
}
