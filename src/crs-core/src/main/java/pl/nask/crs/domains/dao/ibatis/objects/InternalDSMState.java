package pl.nask.crs.domains.dao.ibatis.objects;

import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.RenewalMode;

public class InternalDSMState extends DsmState {

	public InternalDSMState() {
		super();
	}	
	
	public void setCustomerTypeCode(String type) {
		super.setCustomerType(CustomerType.forCode(type));
	}
	
	protected void setDomainHolderTypeCode(String type) {
		super.setDomainHolderType(DomainHolderType.forCode(type));
	}
	
	@Override
	protected void setId(int id) {
		super.setId(id);
	}
	
	protected void setNRPStatusCode(String status) {
		super.setNRPStatus(NRPStatus.forCode(status));
	}
	
	protected void setRenewalModeCode(String mode) {
		super.setRenewalMode(RenewalMode.forCode(mode));
	}
	
	protected void setWipo(Boolean wipo) {
		super.setWipoDispute(wipo);
	}
}
