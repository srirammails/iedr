package pl.nask.crs.domains.email;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.domains.dsm.DsmEventName;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class DSMForceEmailParams extends MapBasedEmailParameters {

	private String domainName;
	private String nicHandle; 
	private String username;

    public DSMForceEmailParams(String domainName, DsmEventName eventName, OpInfo opInfo, String nicHandle, String username) {
    	set(ParameterNameEnum.DOMAIN, domainName);
    	set(ParameterNameEnum.DSM_EVENT_NAME, eventName.name());
    	set(ParameterNameEnum.NIC, opInfo.getActorName());

    	this.domainName = domainName;
    	this.nicHandle = nicHandle;
    	this.username = username;
    }

    public String getLoggedInNicHandle() {
        return username;
    }

    public String getAccountRelatedNicHandle() {
        return nicHandle;
    }

    public String getDomainName() {
        return domainName;
    }
    
}
