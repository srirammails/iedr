package pl.nask.crs.dnscheck.email;

import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class DnsErrorEmailParameters implements EmailParameters {

    private String domainName;
    private String nsName;
    private String errorMessage;
    private String username;
    private String nicHandle;

    public DnsErrorEmailParameters(String domainName, String nsName, String errorMessage, String username, String nicHandle) {
    	this.domainName = domainName;
        this.nsName = nsName;
        this.errorMessage = errorMessage;
        this.username = username;
        this.nicHandle = nicHandle;
    }

    public String getLoggedInNicHandle()
    {
        return username;
    }

    public String getAccountRelatedNicHandle()
    {
        return nicHandle;
    }

    public String getDomainName()
    {
        return domainName;
    }

    
    @Override
    public List<ParameterName> getParameterNames() {
        return ParameterNameEnum.asList();
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum parameter = ParameterNameEnum.forName(name);
        switch (parameter) {
            case DOMAIN:
                return domainName;
            case DNS1:
                return nsName;
            case DNS_FAILURES:
                return errorMessage;
            default:
                return null;
        }
    }
}
