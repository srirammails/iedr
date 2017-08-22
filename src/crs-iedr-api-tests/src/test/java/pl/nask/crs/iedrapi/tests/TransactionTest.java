package pl.nask.crs.iedrapi.tests;

import org.testng.annotations.Test;

/**
 * Checks, if the transactions are configured on IEDR-API side  
 * 
 * @author Artur Gniadzik
 *
 */
public class TransactionTest extends TestUtil {

	
	@Test
	public void makeTest() {
		loginApiTest();
		String REQUEST_CMD = "/commands/ticket_modify_no_contact.xml";
		String RESPONSE_CMD =  "/commands/ticket_modify_no_contact_response.xml";
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket modify status result", result, RESPONSE_CMD);
		// check again to verify, if the transaction was rolled back
		// (if it was not, the ticket already checked-out error will be received)

		result = sendRequest(xmlFileToString(REQUEST_CMD));        
		assertResultMatch("ticket modify status result", result, RESPONSE_CMD);
		
		logout();

	}
}
