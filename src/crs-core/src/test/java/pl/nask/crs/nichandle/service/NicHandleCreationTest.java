package pl.nask.crs.nichandle.service;

import java.util.HashSet;

import javax.annotation.Resource;

import mockit.Expectations;
import mockit.Mocked;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleIdDAO;
import pl.nask.crs.nichandle.email.NicHandleEmailParameters;
import pl.nask.crs.nichandle.service.impl.helper.PasswordHelper;

public class NicHandleCreationTest extends AbstractContextAwareTest {
	@Resource
	NicHandleService service;
	@Resource
	NicHandleSearchService searchService;
	@Resource
	NicHandleIdDAO nicHandleIdDAO;
	
	@Mocked
	PasswordHelper passwordHelper;
	
	@Mocked
	EmailTemplateSenderImpl emailSender;
	
	@Test
	public void passwordShouldNotBeSentInNotificationEmail() throws Exception {
	    	HashSet<String> phones = new HashSet<String>();
	        HashSet<String> faxes = new HashSet<String>();
	        phones.add("22");
	        phones.add("33");
	        faxes.add("333");
	        
	        final String newPassword = "password";
	        
	        new Expectations() {{
	        	passwordHelper.generateNewPassword(anyInt);result = newPassword;
	        }};	        	       
	        
	        new Expectations() {{
	        	emailSender.sendEmail(anyInt, withInstanceOf(NicHandleEmailParameters.class));forEachInvocation = new Object() {
	        		public void verify(int templateId, NicHandleEmailParameters params) {	        			
	        			AssertJUnit.assertFalse("Password should not be present in email parameters", newPassword.equals(params.getParameterValue(ParameterNameEnum.PASSWORD_TOKEN.getName(), false)));
	        		}
	        	};
	        }};
	        
	        NicHandle nicHandle = service.createNicHandle("name", "companyName", "email@aaa.aaa", "address", "", "Poland", 101L, "Irish Domains", phones, faxes, "nicHandleRemark", "TEST-IEDR", "222333444", true, null, true, true);
	        
	}
}
