package pl.nask.crs.dnscheck.impl;

import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.email.DnsErrorEmailParameters;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsCheckTestService implements DnsCheckService {

    private EmailTemplateSender emailTemplateSender;

    public DnsCheckTestService(EmailTemplateSender emailTemplateSender) {
        this.emailTemplateSender = emailTemplateSender;
    }

    /**
     * will throw HostNotConfiguredException if the nsName starts with 'bad'
     */
    @Override
    public void check(String username, String domainName, String nsName, String nsAddress) throws DnsCheckProcessingException, HostNotConfiguredException {
        if (nsName != null && nsName.startsWith("bad")) {
            throw new HostNotConfiguredException(nsName, "Starts with 'bad', so it's bad", "OK First line is OK\nFATAL message for starts with 'bad', so it's bad");
        }
        if (nsName != null && nsName.startsWith("error")) {
            String errMsg = "Starts with 'error', so it`s error";
            sendError(username, domainName, nsName, errMsg);
            throw new DnsCheckProcessingException(errMsg);
        }
    }

    private void sendError(String username, String domainName, String nsName, String errorMessage) {
        try {
            String nicHandle = "AA11-IEDR";
            EmailParameters params = new DnsErrorEmailParameters(domainName, nsName, errorMessage, username, nicHandle);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DNS_CHECK_ERROR.getId(), params);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
