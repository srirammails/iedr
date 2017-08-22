package pl.nask.crs.iedrapi.tests;


import ie.domainregistry.ieapi_ticket_1.InfDataType;
import ie.domainregistry.ieapi_ticket_1.ResDataType;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static pl.nask.crs.iedrapi.tests.TestHelper.*;

public class TicketTest extends TestUtil {

	@Test
	public void ticketInfoTest() {
		String REQUEST_CMD = "/commands/ticket_info.xml";
		String RESPONSE_CMD =  "/commands/ticket_info_response.xml";
		
		loginDad1();
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket info", result, RESPONSE_CMD);
        InfDataType actual = (InfDataType) getResultDataValueFromString(result);
        InfDataType expected = (InfDataType) getResultDataValueFromFile(RESPONSE_CMD);

        compareTickets(actual, expected);
        
		logout();
	}

	@Test
	public void ticketInfoWrongAccountTest() {
		String REQUEST_CMD = "/commands/ticket_info.xml";
		String RESPONSE_CMD =  "/commands/ticket_wrong_account_response.xml";
		
		login();
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket info", result, RESPONSE_CMD);

		logout();
	}

	@Test
	public void ticketInfoNotExistsTest() {
		String REQUEST_CMD = "/commands/ticket_info_wrong_name.xml";
		String RESPONSE_CMD =  "/commands/ticket_info_wrong_name_response.xml";
		
		login();
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket info", result, RESPONSE_CMD);
		logout();
	}
	
	@Test
	public void ticketInfoNotExistsTIDTest() {
		String REQUEST_CMD = "/commands/ticket_info_tid.xml";
		String RESPONSE_CMD =  "/commands/ticket_info_response_tid.xml";
		
		login();
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket info", result, RESPONSE_CMD);
		logout();
	}

	@Test
	public void ticketQueryAllTest() {
		loginDad1();
		
		String REQUEST_CMD = "/commands/ticket_query_all.xml";
		String RESPONSE_CMD =  "/commands/ticket_query_all_response.xml";
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket query", result, RESPONSE_CMD);
        ResDataType actual = (ResDataType) getResultDataValueFromString(result);
        ResDataType expected = (ResDataType) getResultDataValueFromFile(RESPONSE_CMD);
        assertEquals(actual.getTotalPages(), expected.getTotalPages());
        assertEquals(actual.getDomain().toArray(), expected.getDomain().toArray());
		
		logout();
	}
	
	@Test
	public void ticketQueryTransfersTest() {
		loginDad1();
		
		String REQUEST_CMD = "/commands/ticket_query_transfers.xml";
		String RESPONSE_CMD =  "/commands/ticket_query_transfers_response.xml";
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket query", result, RESPONSE_CMD);
		logout();
	}
	
	@Test
	public void ticketQueryModificationsTest() {
		loginDad1();
		
		String REQUEST_CMD = "/commands/ticket_query_modifications.xml";
		String RESPONSE_CMD =  "/commands/ticket_query_modifications_response.xml";
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket query", result, RESPONSE_CMD);
		
		logout();
	}
	
	@Test
	public void ticketQueryRegistrationsTest() {
		loginDad1();
		
		String REQUEST_CMD = "/commands/ticket_query_registrations.xml";
		String RESPONSE_CMD =  "/commands/ticket_query_registrations_response.xml";
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket query", result, RESPONSE_CMD);
        ResDataType actual = (ResDataType) getResultDataValueFromString(result);
        ResDataType expected = (ResDataType) getResultDataValueFromFile(RESPONSE_CMD);
        assertEquals(actual.getTotalPages(), expected.getTotalPages());
        assertEquals(actual.getDomain().toArray(), expected.getDomain().toArray());
		
		logout();
	}
	
	@Test
	public void ticketDeleteTest() {
		loginApiTest();
		String REQUEST_CMD = "/commands/ticket_delete.xml";
		String RESPONSE_CMD =  "/commands/ticket_delete_response.xml";
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket delete result", result, RESPONSE_CMD);
		
		logout();
	}
	
	@Test
	public void ticketDeleteNoSuchObjectTest() {
		loginApiTest();
		String REQUEST_CMD = "/commands/ticket_delete_2.xml";
		String RESPONSE_CMD =  "/commands/ticket_delete_2_response.xml";
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));
        
		assertResultMatch("ticket delete result", result, RESPONSE_CMD);
		
		logout();
	}
	
	@Test
	public void ticketModifyTest() {
        InfDataType responseValue = null;
        InfDataType exampleValue = null;
        String result = null;

        loginApiTest();

        String REQUEST_CMD = "/commands/ticket_modify.xml";
		String RESPONSE_CMD =  "/commands/ticket_modify_response.xml";

		String REQUEST_CMD_2 = "/commands/ticket_info_2.xml";
		String RESPONSE_CMD_2 =  "/commands/ticket_info_2_response.xml";

        result = sendRequest(xmlFileToString(REQUEST_CMD));
		assertResultMatch("ticket modify", result, RESPONSE_CMD);

        // get the ticket and chekh the data
        result = sendRequest(xmlFileToString(REQUEST_CMD_2));
        responseValue = (InfDataType) getResultDataValueFromString(result);
        exampleValue = (InfDataType) getResultDataValueFromFile(RESPONSE_CMD_2);
        compareTickets(responseValue, exampleValue);

        logout();
	}
	
	@Test(dependsOnMethods = {"ticketModifyTest"})
	public void ticketModifyFailed() {
        loginApiTest();
        String result;
        
        result = sendRequest(xmlFileToString("/commands/ticket_modify_to_few_nss.xml"));
        assertResultMatch("ticket modify : to few nameservers", result, "/commands/response_to_few_nameservers.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_to_many_nss.xml"));
        assertResultMatch("ticket modify : to many nameservers", result, "/commands/response_to_many_nameservers.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_not_delegated_to_host.xml"));
        assertResultMatch("ticket modify : ticket not delegated to host", result, "/commands/ticket_modify_not_delegated_to_host_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_already_delegated_to_host.xml"));
        assertResultMatch("ticket modify : ticket already delegated to host", result, "/commands/ticket_modify_already_delegated_to_host_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_dns_syntax_error.xml"));
        assertResultMatch("ticket modify : dns syntax error", result, "/commands/ticket_modify_dns_syntax_error_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_ip_syntax_error.xml"));
        assertResultMatch("ticket modify : ip syntax error", result, "/commands/ticket_modify_ip_syntax_error_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_glue_not_allowed.xml"));
        assertResultMatch("ticket modify : glue not allowed", result, "/commands/ticket_modify_glue_not_allowed_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_glue_required.xml"));
        assertResultMatch("ticket modify : glue required", result, "/commands/ticket_modify_glue_required_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_too_few_admins.xml"));
        assertResultMatch("ticket modify : to few admin contacts", result, "/commands/response_too_few_admins.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_too_few_techs.xml"));
        assertResultMatch("ticket modify : to few tech contacts", result, "/commands/response_too_few_techs.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_too_many_admins.xml"));
        assertResultMatch("ticket modify : to many admin contacts", result, "/commands/response_too_many_admins.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_too_many_tech.xml"));
        assertResultMatch("ticket modify : to many tech contacts", result, "/commands/response_too_many_techs.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_contact_not_exists.xml"));
        assertResultMatch("ticket modify : contact id not exists", result, "/commands/ticket_modify_contact_not_exists_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_contact_syntax_error.xml"));
        assertResultMatch("ticket modify : contact id syntax error", result, "/commands/ticket_modify_contact_syntax_error_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_no_contact.xml"));
        assertResultMatch("ticket modify : contact not associated with ticket", result, "/commands/ticket_modify_no_contact_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_contact_already_associated.xml"));
        assertResultMatch("ticket modify : contact already associated with ticket", result, "/commands/ticket_modify_contact_already_associated_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_deletion_ticket.xml"));
        assertResultMatch("ticket modify : deletion ticket is not allowed to modify", result, "/commands/ticket_modify_deletion_ticket_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_ticket_in_use.xml"));
        assertResultMatch("ticket modify : ticket in use", result, "/commands/ticket_modify_ticket_in_use_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_modify_name_not_exists.xml"));
        assertResultMatch("ticket modify : ticket name not exists", result, "/commands/ticket_modify_name_not_exists_response.xml");

        logout();
	}
	
	@Test
	public void ticketModifyManagedByOtherRegistrar()
	{
        loginIDL2();

        String result = sendRequest(xmlFileToString("/commands/ticket_modify.xml"));
        assertResultMatch("ticket modify : managed by another registrar", result, "/commands/ticket_modify_managed_by_another_registrar_response.xml");

        logout();
    }
	
	@Test
	public void ticketModifyStatusTest() {
		loginApiTest();
		String REQUEST_CMD = "/commands/ticket_modify_status.xml";
		String RESPONSE_CMD =  "/commands/ticket_modify_response.xml";
		
		String result = sendRequest(xmlFileToString(REQUEST_CMD));

		assertResultMatch("ticket modify status result", result, RESPONSE_CMD);
		
		logout();
	}
}
