package pl.nask.crs.domains.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainNotificationEmailParams implements EmailParameters {

    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    private Domain domain;
    private String username;

    public DomainNotificationEmailParams(Domain domain, String username) {
        this.domain = domain;
        this.username = username;
    }

    public String getLoggedInNicHandle()
    {
        return username;
    }

    public String getAccountRelatedNicHandle()
    {
    	return domain.getBillingContactNic();
    }

    public String getDomainName()
    {
        return domain.getName();
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
                return domain.getBillingContactNic();
            case BILL_C_EMAIL:
                return domain.getBillingContact().getEmail();
            case BILL_C_NAME:
                return domain.getBillingContact().getName();
            case DOMAIN:
                return domain.getName();
            case REGISTRATION_DATE:
                return FORMATTER.format(domain.getRegistrationDate());
            case RENEWAL_DATE:
                return FORMATTER.format(domain.getRenewDate());
            case DAYS_TO_RENEWAL:
            	return "" + DateUtils.diffInDays(new Date(), domain.getRenewDate());
            case SUSPENSION_DATE:
                return domain.getSuspensionDate() == null ? "" : FORMATTER.format(domain.getSuspensionDate());
            case DELETION_DATE:
                return domain.getDeletionDate() == null ? "" : FORMATTER.format(domain.getDeletionDate());
            default:
                //throw new IllegalArgumentException("Wrong parameter name: " + param);
                return null;
        }
    }
}
