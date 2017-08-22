package pl.nask.crs.commons.email.service.impl;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailSendingException;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"classpath:commons-config-test.xml", "classpath:email-test-config.xml"})
public class EmailSenderTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    EmailSender emailSender;

    private Email email = new Email();
    {
        email.setSubject("subject");
        email.setText("test");
        email.setToList(Arrays.asList("to@to.to"));
        email.setCcList(Arrays.asList("cc@cc.cc"));
        email.setBccList(Arrays.asList("bcc@bcc.bcc"));
    }

    @Test(expectedExceptions = EmailSendingException.class)
    public void sendEmailWithAdditionalBccTest() throws Exception {
        emailSender.sendEmail(email);
    }
}
