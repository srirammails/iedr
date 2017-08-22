package pl.nask.crs.dnscheck.email;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsNotificationEmailParameters implements EmailParameters {

    private String nicHandleId;
    private String email;
    private String message;
    private String domain;
    private String registrarNicHandleId;

    public DnsNotificationEmailParameters(String nicHandleId, String registrarNicHandleId, String email, String domain, String message) {
        this.nicHandleId = nicHandleId;
        this.registrarNicHandleId = registrarNicHandleId;
        this.message = message;
        this.domain = domain;
        this.email = email;
    }

    public String getLoggedInNicHandle()
    {
        return null; // Sent by a job
    }

    public String getAccountRelatedNicHandle()
    {
        return registrarNicHandleId;
    }

    public String getDomainName()
    {
        return null; // Notification unrelated to a domain
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return Arrays.asList(new ParameterName[] {
                ParameterNameEnum.TECH_C_EMAIL,
                ParameterNameEnum.DNS_FAILURES,
                ParameterNameEnum.TECH_C_NIC,
                ParameterNameEnum.DOMAIN
        });
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum parameter = ParameterNameEnum.forName(name);
        switch (parameter) {
            case TECH_C_EMAIL:
                return email;
            case DNS_FAILURES:
                return message;
            case TECH_C_NIC:
                return nicHandleId;
            case DOMAIN:
                return domain;
            default:
                //throw new IllegalArgumentException("wrong parameter name");
                return null;
        }
    }
}
