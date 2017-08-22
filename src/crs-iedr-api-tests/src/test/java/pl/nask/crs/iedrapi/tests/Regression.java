package pl.nask.crs.iedrapi.tests;

import org.testng.annotations.Test;


public class Regression extends TestUtil {
	
	/**
	 * domain:modify contains deprecated domain:autorenew tag, but since the schema validation is turned off, the command is being executed.
	 * Schema validation should be used to pre-check the command, and if it fails (and further command validation doesn't detect any errors), an error should be returned.	  
	 */
	@Test(enabled=true)
	public void testFor6839() {
		loginApiTest();
		testRR("test for 6839", "/regression/6839/request.xml", "/regression/6839/response.xml");
		logout();
	}
}
