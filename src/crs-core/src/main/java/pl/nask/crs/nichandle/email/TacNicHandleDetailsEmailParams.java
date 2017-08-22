package pl.nask.crs.nichandle.email;

import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.nichandle.NicHandle;

public class TacNicHandleDetailsEmailParams extends MapBasedEmailParameters {

	private String nicHandle; 
	private String username;


	public TacNicHandleDetailsEmailParams(NicHandle creator, NicHandle nicHandle, NicHandle billingNh, String username) {
		set(ParameterNameEnum.CREATOR_C_EMAIL, creator.getEmail());
		set(ParameterNameEnum.CREATOR_C_NAME, creator.getName());
		set(ParameterNameEnum.BILL_C_EMAIL, billingNh == null ? creator.getEmail() : billingNh.getEmail());
		set(ParameterNameEnum.NIC, nicHandle.getNicHandleId());
		set(ParameterNameEnum.NIC_EMAIL, nicHandle.getEmail());
		set(ParameterNameEnum.NIC_NAME, nicHandle.getName());
		set(ParameterNameEnum.REGISTRAR_NAME, billingNh == null ? "(Unknown)" : billingNh.getName());

    	this.nicHandle = nicHandle.getNicHandleId();
    	this.username = username;
	}

	public String getLoggedInNicHandle() {
		return username;
	}

	public String getAccountRelatedNicHandle() {
		return nicHandle;
	}

}
