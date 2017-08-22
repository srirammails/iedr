package pl.nask.crs.commons.email.service.impl;

import javax.annotation.Resource;

import mockit.NonStrictExpectations;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;

public class HtmlEmailsTest extends AbstractTest {
	@Resource
	EmailTemplateSender emailTemplateSender;
	
	@Resource
	EmailSender emailSender;
	
	@Resource
	EmailTemplateDAO emailDao;
	
	int templateId = 73;
	
	
	@Test
	public void emailShouldBeSentAsHtmlIfTemplateHasHtmlFlagSet() throws Exception {
		EmailTemplate template = emailDao.get(templateId);
		Assert.assertTrue(template.isHtml(), "html template");
		
		MapBasedEmailParameters params = new MapBasedEmailParameters();
		params.set(ParameterNameEnum.BILL_C_EMAIL, "asd@asd.asd");
		
		new NonStrictExpectations(emailSender) {{
			emailSender.sendHtmlEmail(withInstanceOf(Email.class)); times=1;
			emailSender.sendEmail(withInstanceOf(Email.class)); times=0;
		}};
		
		emailTemplateSender.sendEmail(templateId, params);
	}
	
	@Test
	public void emailShouldBeSentAsPlainTextIfHtmlFlagIsCleared() throws Exception {
		EmailTemplate template = emailDao.get(1);
		Assert.assertFalse(template.isHtml(), "html template");
		
		MapBasedEmailParameters params = new MapBasedEmailParameters();
		params.set(ParameterNameEnum.BILL_C_EMAIL, "asd@asd.asd");
		
		new NonStrictExpectations(emailSender) {{
			emailSender.sendHtmlEmail(withInstanceOf(Email.class)); times=0;
			emailSender.sendEmail(withInstanceOf(Email.class)); times=1;
		}};
		
		emailTemplateSender.sendEmail(1, params );
	}
}
