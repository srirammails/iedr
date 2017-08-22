package pl.nask.crs.web.domains;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class DomainWipoAction extends AuthenticatedUserAwareAction {
	private final DomainAppService service;
	
	private String domainName;
	
	private String hostmastersRemark;
	
	public DomainWipoAction(DomainAppService service) {
		this.service = service;
	}
	
	public String enterWipo() throws Exception {
		service.enterWipo(getUser(), domainName, hostmastersRemark);
		return SUCCESS;
	}
	
	public String exitWipo() throws Exception {
		service.exitWipo(getUser(), domainName, hostmastersRemark);
		return SUCCESS;
	}
	
	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	public void setHostmastersRemark(String hostmastersRemark) {
		this.hostmastersRemark = hostmastersRemark;
	}
	
	public String getHostmastersRemark() {
		return hostmastersRemark;
	}
}
