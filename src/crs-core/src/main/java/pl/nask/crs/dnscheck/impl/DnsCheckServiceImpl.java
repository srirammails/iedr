package pl.nask.crs.dnscheck.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.email.DnsErrorEmailParameters;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsCheckServiceImpl implements DnsCheckService {
    private final static Logger LOG = Logger.getLogger(DnsCheckServiceImpl.class);
    private String checkDnsExecPath;
    private EmailTemplateSender emailTemplateSender;
    private EmailDisablerDAO emailDisablerDAO;

    public DnsCheckServiceImpl(String checkDnsExecPath, EmailTemplateSender emailTemplateSender, EmailDisablerDAO emailDisablerDAO) {
        Validator.assertNotNull(checkDnsExecPath, "checkDns exec path");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        this.checkDnsExecPath = checkDnsExecPath;
        this.emailTemplateSender = emailTemplateSender;
        this.emailDisablerDAO = emailDisablerDAO;
    }

    @Override
    public void check(String username, String domainName, String nsName, String nsAddress) throws DnsCheckProcessingException, HostNotConfiguredException {
        Validator.assertNotEmpty(domainName, "domainName");
        Validator.assertNotEmpty(nsName, "nsName");
        try {
            boolean isGlue = isGlueRecord(domainName, nsName);
            String scriptNsArgument = isGlue ? nsAddress : nsName;
            ProcessBuilder pb = new ProcessBuilder(checkDnsExecPath, scriptNsArgument, domainName);
            Process process = pb.start();
            StringBuilder standardMessage = getMessage(process.getInputStream());
            StringBuilder errorMessage = getMessage(process.getErrorStream());
            if (process.waitFor() != 0) {
                sendError(username, domainName, nsName, errorMessage.toString());
                throw new DnsCheckProcessingException("Cannot run ckdnsExec with ns: " + nsName +  "domain: " + domainName + "because: " + errorMessage);
            } else {
                validateNsMessage(standardMessage, nsName);
            }
        } catch (IOException e) {
            sendError(username, domainName, nsName, e.toString());
            throw new DnsCheckProcessingException(e);
        } catch (InterruptedException e) {
            sendError(username, domainName, nsName, e.toString());
            throw new DnsCheckProcessingException(e);
        }
    }

    private void sendError(String username, String domainName, String nsName, String errorMessage) {
        try {
        	String nicHandle = emailDisablerDAO.getNicHandleByDomainName(domainName);
        	EmailParameters params = new DnsErrorEmailParameters(domainName, nsName, errorMessage, username, nicHandle);
        	emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DNS_CHECK_ERROR.getId(), params);
        } catch (Exception e) {
            LOG.error("Error sending dns failure email", e);
        }
    }

    private boolean isGlueRecord(String domainName, String nsName) {
        return nsName.toLowerCase().endsWith(domainName.toLowerCase());
    }

    private StringBuilder getMessage(InputStream inputStream) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder ret = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                ret.append(line);
                ret.append("\n");
            }
            return ret;
        } finally {
            inputStream.close();
        }
    }

    private void validateNsMessage(StringBuilder sb, String nsName) throws HostNotConfiguredException {
        int lastFatal = sb.lastIndexOf("FATAL");
        if (lastFatal != -1 && sb.indexOf("OK", lastFatal) == -1){
            throw new HostNotConfiguredException(nsName, getFirstFatalMessage(sb), sb.toString());
        }
    }

    private String getFirstFatalMessage(StringBuilder sb) {
        int firstFatal = sb.indexOf("FATAL");
        int endOfLineWithFatal = sb.indexOf("\n", firstFatal);
        return sb.substring(firstFatal + 6, endOfLineWithFatal);
    }

}
