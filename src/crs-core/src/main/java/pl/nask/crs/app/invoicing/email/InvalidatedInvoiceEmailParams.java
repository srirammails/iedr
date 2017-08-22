package pl.nask.crs.app.invoicing.email;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvalidatedInvoiceEmailParams implements EmailParameters {

    private NicHandle billingNH;
    private List<String> domains;
    private String username;

    public InvalidatedInvoiceEmailParams(NicHandle billingNH, List<String> domains, String username) {
        this.billingNH = billingNH;
        this.domains = domains;
        this.username = username;
    }

    public String getLoggedInNicHandle()
    {
        return username;
    }

    public String getAccountRelatedNicHandle()
    {
        return billingNH.getNicHandleId();
    }

    public String getDomainName()
    {
        return null; // No admin verification for a list of domains
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return ParameterNameEnum.asList();
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param) {
            case BILL_C_NIC:
                return billingNH.getNicHandleId();
            case BILL_C_EMAIL:
                return billingNH.getEmail();
            case DOMAIN:
                return asString(domains);
            default:
                //throw new IllegalArgumentException("Wong parameter name: " + param);
                return null;
        }
    }

    private String asString(List<String> domains) {
        StringBuilder sb = new StringBuilder();
        for (String domain: domains) {
            if (sb.length() != 0)
                sb.append(",");
            sb.append(domain);
        }
        return sb.toString();
    }
}
