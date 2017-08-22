package pl.nask.crs.regression;

import java.util.Map;
import java.util.Set;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.utils.Validator;

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public abstract class AbstractEmailsTest extends AbstractTransactionalTestNGSpringContextTests {

	@Mocked
	EmailTemplateSenderImpl emailTemplateSender;
	
	protected void createExpectations(final int templateId, final Set<ParameterNameEnum> populatedParams) throws Exception {
		new NonStrictExpectations() {{
			if (templateId != -1) {
				emailTemplateSender.sendEmail(templateId, withInstanceOf(EmailParameters.class)); 				
			} else {
				emailTemplateSender.sendEmail(anyInt, withInstanceOf(EmailParameters.class)); 
			}
			forEachInvocation = new Object() {
				public void verify(int templateId, EmailParameters params) {
					System.err.println("Template ID: " + templateId);
					for (ParameterNameEnum param: populatedParams) {
						assertNotEmpty(params, param);
					}
				}

				private void assertNotEmpty(EmailParameters params, ParameterNameEnum name) {
					AssertJUnit.assertTrue("params should contain " + name, params.getParameterNames().contains(name));					
					AssertJUnit.assertFalse("param should not be empty: " + name, Validator.isEmpty(params.getParameterValue(name.getName(), false)));
					AssertJUnit.assertFalse("param should not be replaced with param's name: " + name, Validator.isEqual(name.getName(), params.getParameterValue(name.getName(), false)));
					System.out.println(name + " : " + params.getParameterValue(name.getName(), false));
				}
			};minTimes=1;
		}};
	}
	
	protected void createExpectations(final int templateId, final Map<ParameterNameEnum, String> populatedValues) throws Exception {
		createExpectations(templateId, populatedValues, 1, 100);
	}
	
	protected void createExpectations(final int templateId, final Map<ParameterNameEnum, String> populatedValues, final int minTimesExpected, final int maxTimesExpected) throws Exception {
		new NonStrictExpectations() {{
			if (templateId != -1) {
				emailTemplateSender.sendEmail(templateId, withInstanceOf(EmailParameters.class)); 				
			} else {
				emailTemplateSender.sendEmail(anyInt, withInstanceOf(EmailParameters.class)); 
			}
			forEachInvocation = new Object() {
				public void verify(int templateId, EmailParameters params) {
					System.err.println("Template ID: " + templateId);
					for (Map.Entry<ParameterNameEnum, String> entry: populatedValues.entrySet()) {
						assertEqual(params, entry.getKey(), entry.getValue());
					}
				}

				private void assertEqual(EmailParameters params, ParameterNameEnum name, String value) {
					AssertJUnit.assertTrue("params should contain " + name, params.getParameterNames().contains(name));					
//					Assert.assertFalse("param should not be empty: " + name, Validator.isEmpty(params.getParameterValue(name.getName())));
					AssertJUnit.assertEquals("param should be replaced with right value: " + name, value, params.getParameterValue(name.getName(), false));
					System.out.println(name + " : " + params.getParameterValue(name.getName(), false));
				}
			};minTimes=minTimesExpected; maxTimes = maxTimesExpected;
		}};
	}

}
