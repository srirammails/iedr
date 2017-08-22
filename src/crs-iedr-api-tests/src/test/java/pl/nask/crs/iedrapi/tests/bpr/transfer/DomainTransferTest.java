package pl.nask.crs.iedrapi.tests.bpr.transfer;

import ie.domainregistry.ieapi_domain_1.AppDataType;
import org.testng.annotations.Test;
import pl.nask.crs.iedrapi.tests.TestUtil;

import static org.testng.Assert.assertTrue;
import static pl.nask.crs.iedrapi.tests.TestHelper.compareTicketsSkipAdminContactAndDates;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainTransferTest extends TestUtil{
    String result = null;

    @Test
    public void domainTransferTest() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer_response.xml");
        AppDataType responseValue = (AppDataType) getResultDataValueFromString(result);
        AppDataType exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data");

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer.xml"));
        assertResultMatch("domain transfer - ticket is currently being processed by a hostmaster ", result, "/commands/bpr/transfer/domain_transfer_pending.xml");

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_already_managed_by_reseller.xml"));
        assertResultMatch("domain transfer - already mandaged by reseller", result, "/commands/bpr/transfer/domain_transfer_already_managed_by_reseller_response.xml");

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_ticket_managed_by_another_reseller.xml"));
        assertResultMatch("domain transfer - ticket mandaged by another reseller", result, "/commands/bpr/transfer/domain_transfer_pending.xml");

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_charity.xml"));
        assertResultMatch("domain charity transfer ok", result, "/commands/bpr/transfer/domain_transfer_charity_response.xml");
        responseValue = (AppDataType) getResultDataValueFromString(result);
        exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer_charity_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain charity transfer data");

        logout();

        loginApit3();

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_reseller_dafaults_not_defined.xml"));
        assertResultMatch("domain transfer - ticket mandaged by another reseller", result, "/commands/bpr/transfer/domain_transfer_reseller_dafaults_not_defined_response.xml");

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_incorrect_state.xml"));
        assertResultMatch("domain transfer - ticket mandaged by another reseller", result, "/commands/bpr/transfer/domain_transfer_incorrect_state_response.xml");

        logout();
    }

    @Test
    public void  feature4907domainTransferTest() {
        AppDataType responseValue = null;
        AppDataType exampleValue = null;
        ie.domainregistry.ieapi_ticket_1.InfDataType actualInfo = null;
        ie.domainregistry.ieapi_ticket_1.InfDataType expectedInfo = null;
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer2.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer2_response.xml");
        responseValue = (AppDataType) getResultDataValueFromString(result);
        exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer2_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data");

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer2_ticket_info.xml"));
        assertResultMatch("ticket info", result, "/commands/bpr/transfer/domain_transfer2_ticket_info_response.xml");
        actualInfo = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromString(result);
        expectedInfo = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer2_ticket_info_response.xml");

        compareTicketsSkipAdminContactAndDates(actualInfo, expectedInfo);

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer3.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer3_response.xml");
        responseValue = (AppDataType) getResultDataValueFromString(result);
        exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer3_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data");

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer3_ticket_info.xml"));
        assertResultMatch("ticket info", result, "/commands/bpr/transfer/domain_transfer3_ticket_info_response.xml");
        actualInfo = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromString(result);
        expectedInfo = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer3_ticket_info_response.xml");

        compareTicketsSkipAdminContactAndDates(actualInfo, expectedInfo);

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer4.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer4_response.xml");
        responseValue = (AppDataType) getResultDataValueFromString(result);
        exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer4_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data");

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer4_ticket_info.xml"));
        assertResultMatch("ticket info", result, "/commands/bpr/transfer/domain_transfer4_ticket_info_response.xml");
        actualInfo = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromString(result);
        expectedInfo = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer4_ticket_info_response.xml");

        compareTicketsSkipAdminContactAndDates(actualInfo, expectedInfo);

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer5.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer5_response.xml");
        responseValue = (AppDataType) getResultDataValueFromString(result);
        exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer5_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data");

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer5_ticket_info.xml"));
        assertResultMatch("ticket info", result, "/commands/bpr/transfer/domain_transfer5_ticket_info_response.xml");
        actualInfo = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromString(result);
        expectedInfo = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer5_ticket_info_response.xml");

        compareTicketsSkipAdminContactAndDates(actualInfo, expectedInfo);

        logout();
    }
    
    @Test
    public void attemptToTransferNonCharityDomainWithoutSpecifyingPeriodShouldFailWIthReasonCode276() {
        loginApiTest();

    	result = testRR("non charity domain transfer", "/commands/bpr/transfer/domain_transfer_non_charity_bad.xml", "/commands/bpr/transfer/domain_transfer_276_response.xml");
    }

}
