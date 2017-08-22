package pl.nask.crs.iedrapi.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static pl.nask.crs.iedrapi.tests.TestHelper.compareDomainsInfo;
import static pl.nask.crs.iedrapi.tests.TestHelper.compareNsLists;
import static pl.nask.crs.iedrapi.tests.TestHelper.compareStatuses;
import static pl.nask.crs.iedrapi.tests.TestHelper.compareHolders;

import ie.domainregistry.ieapi_domain_1.AppDataType;
import ie.domainregistry.ieapi_domain_1.CheckType;
import ie.domainregistry.ieapi_domain_1.ChkDataType;
import ie.domainregistry.ieapi_domain_1.InfDataType;
import ie.domainregistry.ieapi_domain_1.ResDataType;
import ie.domainregistry.ieapi_ticket_1.ContactType;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * @author: Marcin Tkaczyk
 */

public class DomainTest extends TestUtil {
    String result = null;           
    
    @AfterMethod
    public void logout() {
    	super.logout();
    }
    
    @Test
    public void domainInfoTest() {
    	loginIDL2();

        result = sendRequest(xmlFileToString("/commands/domain_info.xml"));
        assertResultMatch("domain info result", result, "/commands/domain_info_response.xml");
        InfDataType responseValue = (InfDataType) getResultDataValueFromString(result);
        InfDataType exampleValue = (InfDataType) getResultDataValueFromFile("/commands/domain_info_response.xml");
        compareDomainsInfo(responseValue, exampleValue);
    }
    
    @Test
    public void domainInfoNameMandatoryTest() {
    	loginIDL2();

        result = sendRequest(xmlFileToString("/commands/domain_info_name_mandatory.xml"));
        assertResultMatch("domain info - name mandatory", result,  "/commands/response_domain_name_mandatory.xml");
    }
    
    @Test
    public void domainInfoDomainNameNotExistsTest() {
    	loginIDL2();
 
    	result = sendRequest(xmlFileToString("/commands/domain_info_not_exist.xml"));
        assertResultMatch("domain info - domain name not exists", result, "/commands/response_domain_not_exists.xml");
    }
    
    @Test
    public void domainInfoWrongRegistrarTest() {
        loginApiTest();
        
        result = sendRequest(xmlFileToString("/commands/domain_info.xml"));
        assertResultMatch("domain info - managed by another registrar", result, "/commands/response_domain_managed_by_another_registrar.xml");
    }

    @Test
    public void domainCheckTest() throws Exception {
    	loginIDL2();

        result = sendRequest(xmlFileToString("/commands/domain_check.xml"));
        assertResultMatch("domain check result", result, "/commands/domain_check_response.xml");
        ChkDataType responseValue = (ChkDataType)getResultDataValueFromString(result);
        ChkDataType exampleValue = (ChkDataType)getResultDataValueFromFile("/commands/domain_check_response.xml");
        compareCheckTypeLists(responseValue.getCd(), exampleValue.getCd(), "domain query domain data");
    }

    private void compareCheckTypeLists(List<CheckType> responseList, List<CheckType> exampleList, String message) {
        assertEquals(responseList.size(), exampleList.size(), "check data list size");
        for (int i = 0; i < responseList.size(); i++) {
            assertEquals(responseList.get(i).getName().getValue(), exampleList.get(i).getName().getValue(), message + " : name");
            assertEquals(responseList.get(i).getName().isAvail(), exampleList.get(i).getName().isAvail(), message + " : available");
            if (!responseList.get(i).getName().isAvail()) {
                assertNotNull(responseList.get(i).getReason(), "reason when domain is unavailable");
                assertEquals(responseList.get(i).getReason().getCode(), exampleList.get(i).getReason().getCode(), message + " : reason code when domain is unavailable");
            } else {
                assertNull(responseList.get(i).getReason(), "reason when domain is available");
            }
        }
    }

    @Test
    public void domainNrpTest() {
    	loginApiTest();
    	testRR("domain:nrp ok", "/commands/domain_nrp.xml", "/commands/domain_nrp_response.xml");
    }
    
    @Test
    public void domainNrpFailedTest() {
    	loginApiTest();
    	testRR("domain:nrp failed", "/commands/domain_nrp_wrong_domains.xml", "/commands/domain_nrp_failed_response.xml");
    }
    
    @Test
    public void domainNrpCancelTest() {
    	loginApiTest();
    	testRR("domain:nrpCancel ok", "/commands/domain_nrp_cancel.xml", "/commands/domain_nrp_cancel_response.xml");    	
    }
    
    @Test
    public void domainNrpCancelFailedTest() {
    	loginApiTest();
    	testRR("domain:nrpCancel failed", "/commands/domain_nrp_cancel_wrong_domains.xml", "/commands/domain_nrp_cancel_wrong_domains_response.xml");
    }
    
    @Test(enabled=false, description="refactoring needed/BPR")
    public void domainDeleteTest() {
    	loginApiTest();
    	
        result = sendRequest(xmlFileToString("/commands/domain_delete.xml"));
        assertResultMatch("domain delete result ok", result, "/commands/domain_delete_response.xml");
        AppDataType responseValue = (AppDataType) getResultDataValueFromString(result);
        AppDataType exampleValue = (AppDataType) getResultDataValueFromFile("/commands/domain_delete_response.xml");
        assertEquals(responseValue.getName(), exampleValue.getName(), "domain delete result data");

        result = sendRequest(xmlFileToString("/commands/domain_delete_name_mandatory.xml"));
        assertResultMatch("domain delete - domain name mandatory", result, "/commands/response_domain_name_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/domain_delete_not_existing_domain.xml"));
        assertResultMatch("domain delete - domain not exists", result, "/commands/response_domain_not_exists.xml");

        result = sendRequest(xmlFileToString("/commands/domain_delete_managed_by_another_registrar.xml"));
        assertResultMatch("domain delete - domain managed by another registrar", result, "/commands/response_domain_managed_by_another_registrar.xml");

        result = sendRequest(xmlFileToString("/commands/domain_delete_msd_process_pending.xml"));
        assertResultMatch("domain delete - msd process pending", result, "/commands/response_domain_msd_process_pending.xml");

        result = sendRequest(xmlFileToString("/commands/domain_delete_deletion_pending.xml"));
        assertResultMatch("domain delete - deletion pending", result, "/commands/response_domain_deletion_pending.xml");

        result = sendRequest(xmlFileToString("/commands/domain_delete_modification_pending.xml"));
        assertResultMatch("domain delete - modification pending", result, "/commands/response_domain_modification_pending.xml");
    }

    @Test
    public void domainModifySuccessfull() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/domain_modify.xml"));
        assertResultMatch("domain modify result ok", result, "/commands/domain_modify_response.xml");
        result = sendRequest(xmlFileToString("/commands/ticket_info_modify_contact_test.xml"));
        ie.domainregistry.ieapi_ticket_1.InfDataType responseValue = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromString(result);
        ie.domainregistry.ieapi_ticket_1.InfDataType exampleValue = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromFile("/commands/ticket_info_modify_contact_test_response.xml");
        List<String> responseContacts = toStringList(responseValue.getContact());
        List<String> exampleContacts = toStringList(exampleValue.getContact());
        assertEquals(responseContacts.size(), exampleContacts.size(), "domain modify contacts count");
        for (String responseContact : responseContacts) {
            assertTrue(exampleContacts.contains(responseContact), responseContact + "does not exist in example contacts list");
        }
    }
    
    @Test(dependsOnMethods="domainModifySuccessfull")
    public void modifyShouldFailWithAdminNotAssociatedError() {    	
    	loginApiTest();
    	result = sendRequest(xmlFileToString("/commands/domain_modify_admin_not_associated.xml"));
    	assertResultMatch("domain modify - admin to remove not associated", result, "/commands/domain_modify_admin_not_associated_response.xml");
    }
    
    @Test(dependsOnMethods="domainModifySuccessfull")
    public void modifyShouldFailWithInvalidDomainStateError() {    	
    	loginApiTest();
    	result = sendRequest(xmlFileToString("/commands/domain_modify_invalid_domain_state.xml"));
        assertResultMatch("domain modify - admin to remove not associated", result, "/commands/domain_modify_invalid_domain_state_response.xml");

    }
    
    @Test(dependsOnMethods="domainModifySuccessfull")
    public void modifyShouldFailWithAdminAlreadyAssociatedError() {    	
    	loginApiTest();
    	result = sendRequest(xmlFileToString("/commands/domain_modify_admin_already_associated.xml"));
    	assertResultMatch("domain modify - admin to add already associated", result, "/commands/domain_modify_admin_already_associated_response.xml");
    }

    @Test
    public void domainModifyMultiFieldTest() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/domain_info_multi_field_test.xml"));
        InfDataType domainResponseValue = (InfDataType) getResultDataValueFromString(result);
        InfDataType domainExampleValue = (InfDataType) getResultDataValueFromFile("/commands/domain_info_multi_field_before_test_response.xml");
        compareNsLists(domainResponseValue.getNs(), domainExampleValue.getNs(), "nss before modify");
        compareStatuses(domainResponseValue.getStatus(), domainExampleValue.getStatus(), "status before modify");
        compareHolders(domainResponseValue.getHolder(), domainExampleValue.getHolder(), "holder before modify");

        result = sendRequest(xmlFileToString("/commands/domain_modify_multi_field.xml"));
        assertResultMatch("domain modify - mofified simultaneously", result, "/commands/domain_modify_multi_field_response.xml");

        result = sendRequest(xmlFileToString("/commands/ticket_info_modify_multi_test.xml"));
        ie.domainregistry.ieapi_ticket_1.InfDataType responseValue = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromString(result);
        ie.domainregistry.ieapi_ticket_1.InfDataType exampleValue = (ie.domainregistry.ieapi_ticket_1.InfDataType) getResultDataValueFromFile("/commands/ticket_info_modify_multi_test_response.xml");
        List<String> responseContacts = toStringList(responseValue.getContact());
        List<String> exampleContacts = toStringList(exampleValue.getContact());
        assertEquals(responseContacts.size(), exampleContacts.size(), "domain modify contacts count");
        for (String responseContact : responseContacts) {
            assertTrue(exampleContacts.contains(responseContact), responseContact + "does not exist in example contacts list");
        }
        compareHolders(responseValue.getHolder(), exampleValue.getHolder(), "holder");

        result = sendRequest(xmlFileToString("/commands/domain_info_multi_field_test.xml"));
        domainResponseValue = (InfDataType) getResultDataValueFromString(result);
        domainExampleValue = (InfDataType) getResultDataValueFromFile("/commands/domain_info_multi_field_after_test_response.xml");
        compareNsLists(domainResponseValue.getNs(), domainExampleValue.getNs(), "nss after modify");
        compareStatuses(domainResponseValue.getStatus(), domainExampleValue.getStatus(), "status after modify");

    }

    @Test
    public void domainModifyDNSTest() throws Exception {
        loginApiTest();
        InfDataType responseValue = null;
        InfDataType exampleValue = null;

        result = sendRequest(xmlFileToString("/commands/domain_modify_already_delegated_to_host.xml"));
        assertResultMatch("domain modify - ns already delegated to host to add", result, "/commands/domain_modify_already_delegated_to_host_response.xml");

        result = sendRequest(xmlFileToString("/commands/domain_modify_not_delegated_to_host.xml"));
        assertResultMatch("domain modify - ns not delegated to host to remove", result, "/commands/domain_modify_not_delegated_to_host_response.xml");

        result = sendRequest(xmlFileToString("/commands/domain_info_dns_test.xml"));
        responseValue = (InfDataType) getResultDataValueFromString(result);
        exampleValue = (InfDataType) getResultDataValueFromFile("/commands/domain_info_dns_test_response.xml");
        compareNsLists(responseValue.getNs(), exampleValue.getNs(), "before modify");

        result = sendRequest(xmlFileToString("/commands/domain_modify_dns_add.xml"));
        assertResultMatch("domain modify add nss result ok", result, "/commands/response_ok_no_result.xml");
        result = sendRequest(xmlFileToString("/commands/domain_info_dns_test.xml"));
        responseValue = (InfDataType) getResultDataValueFromString(result);
        exampleValue = (InfDataType) getResultDataValueFromFile("/commands/domain_info_dns_test_add_response.xml");
        compareNsLists(responseValue.getNs(), exampleValue.getNs(), "add ns test");

        result = sendRequest(xmlFileToString("/commands/domain_modify_dns_rem.xml"));
        assertResultMatch("domain modify rem nss result ok", result, "/commands/response_ok_no_result.xml");
        result = sendRequest(xmlFileToString("/commands/domain_info_dns_test.xml"));
        responseValue = (InfDataType) getResultDataValueFromString(result);
        exampleValue = (InfDataType) getResultDataValueFromFile("/commands/domain_info_dns_test_rem_response.xml");
        compareNsLists(responseValue.getNs(), exampleValue.getNs(), "rem ns test");

        result = sendRequest(xmlFileToString("/commands/domain_modify_dns_add_rem.xml"));
        assertResultMatch("domain modify add rem nss result ok", result, "/commands/response_ok_no_result.xml");
        result = sendRequest(xmlFileToString("/commands/domain_info_dns_test.xml"));
        responseValue = (InfDataType) getResultDataValueFromString(result);
        exampleValue = (InfDataType) getResultDataValueFromFile("/commands/domain_info_dns_test_add_rem_response.xml");
        compareNsLists(responseValue.getNs(), exampleValue.getNs(), "add rem ns test");
    }
    
    @Test
    public void modifyDnsShouldFailWhenHostIsNotConfigured() 
    {
        loginApiTest();
        result = sendRequest(xmlFileToString("/commands/domain_modify_host_not_configured.xml"));
        assertResultMatch("domain modify add nss result ok", result, "/commands/domain_modify_host_not_configured_response.xml");
    }


    private List<String> toStringList(List<ContactType> contactsList) {
        List<String> ret = new ArrayList<String>();
        for (ContactType contact : contactsList) {
            ret.add(contact.getType().value() + contact.getValue());
        }
        return ret;
    }
}
