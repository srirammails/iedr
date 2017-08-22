package pl.nask.crs.dnscheck;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import mockit.Expectations;
import mockit.Mocked;
import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.dnscheck.impl.DnsCheckServiceImpl;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class DnsCheckTest extends AbstractTest  {

    private DnsCheckService dnsCheckService;

    @Mocked
    EmailTemplateSender emailTemplateSender;

    @Resource
    EmailDisablerDAO emailDisablerDAO;

    @BeforeMethod
	public void setUp() throws Exception {
        dnsCheckService = new DnsCheckServiceImpl("Passw0rd!", emailTemplateSender, emailDisablerDAO);
    }

    @Test(expectedExceptions = DnsCheckProcessingException.class)
    public void testCheck() throws Exception {

        new Expectations() {{
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DNS_CHECK_ERROR.getId(), withInstanceOf(EmailParameters.class));
        }};

        dnsCheckService.check("AA11-IEDR", "domain", "nsName", "nsAddress");
    }

    @Test
    public void testHostNotInitializedException() {
        HostNotConfiguredException ex = new HostNotConfiguredException("naName", "shortMessage", "OK Full Message\nFATAL first fatal line\nOK It good\nFATAL second fatal line\n");
        Assert.assertEquals(ex.getFullOutputMessage(), "OK Full Message\nFATAL first fatal line\nOK It good\nFATAL second fatal line\n");
        Assert.assertEquals(ex.getFullOutputMessage(true), "FATAL first fatal line\nFATAL second fatal line\n");
    }
}
