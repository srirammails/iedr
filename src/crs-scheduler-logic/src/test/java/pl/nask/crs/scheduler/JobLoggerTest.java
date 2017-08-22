package pl.nask.crs.scheduler;

import javax.annotation.Resource;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.impl.CachingEmailSender;
import pl.nask.crs.commons.email.service.impl.EmailSenderImpl;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.log4j.NDCAwareFileAppender;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */



@ContextConfiguration(locations = {"/scheduler-config.xml", "/scheduler-config-test.xml"})
public class JobLoggerTest extends AbstractTransactionalTestNGSpringContextTests {
	@Resource
	SchedulerCron schedulerCron;

	@Mocked
	EmailTemplateSenderImpl templateSender;
	
	@Resource
	EmailSender emailSender;

	@Test
    public void testProcessAwareAppender() {
        NDCAwareFileAppender appender = (NDCAwareFileAppender) Logger.getRootLogger().getAppender("processAware");
        AssertJUnit.assertNotNull(appender);
    }
    
    @Test
    public void emailShouldBeSentWhenErrorIsLogged() throws Exception {
    	new NonStrictExpectations() {{
    		templateSender.sendEmail(EmailTemplateNamesEnum.SCHEDULER_JOB_FINISHED_WITH_ERRORS.getId(), withInstanceOf(EmailParameters.class)); minTimes=1; 
    	}};
    	
    	
    	schedulerCron.start();
    	schedulerCron.invoke("EmptyJobWithError");
    	Thread.sleep(1000);
        schedulerCron.stop();
    }
    
    @Test
    public void emailShouldBeSentWhenJobIsFailed() throws Exception {
    	new NonStrictExpectations() {{
    		templateSender.sendEmail(EmailTemplateNamesEnum.SCHEDULER_JOB_FAILED.getId(), withInstanceOf(EmailParameters.class)); minTimes=1; 
    	}};
    	
    	
    	schedulerCron.start();
    	schedulerCron.invoke("EmptyFailingJob");
    	Thread.sleep(1000);
        schedulerCron.stop();
    }
    
    @Test
    public void emailShouldBeSentEvenIfDbConnectionIsDeadAndEmailTemplateCannotBeRead() throws Exception {
    	new NonStrictExpectations() {{
    		templateSender.sendEmail(anyInt, withInstanceOf(EmailParameters.class)); result=new Exception("BOOM!"); 
    		
    	}};
    	
    	new NonStrictExpectations(emailSender) {{
    		emailSender.sendEmail(withInstanceOf(Email.class)); minTimes=1; forEachInvocation=new Object() {
    			public void sendEmail(Email email) {
    				Assert.assertTrue(email.getText().contains("BOOM!"));
    			}
    		};
		}};
    	
    	
    	schedulerCron.start();
    	schedulerCron.invoke("EmptyFailingJob");
    	Thread.sleep(1000);
        schedulerCron.stop();
    }

}
