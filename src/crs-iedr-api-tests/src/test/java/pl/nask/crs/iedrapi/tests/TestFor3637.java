package pl.nask.crs.iedrapi.tests;

import org.testng.annotations.Test;


/**
 * Tests issue 3637 from redmine
 * 
 * Using this command (ticket:delete) allowed me to delete a ticket for a domain name on another users account. 
 * All actions should only allow the user to submit actions on domains on their own account.
 * For example while logged in as NTG1-IEDR. I was able to delete a ticket from account AAE553-IEDR.
 * 
 * @author Artur Gniadzik
 *
 */
public class TestFor3637 extends TestUtil {
		
	@Test
	public void testAccessDenied() {
		// ticket no: 259921, 'belongs' to API TESTS
		// user: APIT3-IEDR, account API TESTS 2
		loginApit3();
		
		String REQUEST_CMD = "/commands/ticket_delete_access_denied.xml";
		String RESPONSE_CMD =  "/commands/ticket_delete_wrong_account_response.xml";
		
		String res = sendRequest(xmlFileToString(REQUEST_CMD));
		
		assertResultMatch("ticket delete", res, RESPONSE_CMD);		
	}
}
