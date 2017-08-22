package pl.nask.crs.iedrapi.tests;

import static org.testng.Assert.assertEquals;
import ie.domainregistry.ieapi_contact_1.CreDataType;
import ie.domainregistry.ieapi_contact_1.InfDataType;
import ie.domainregistry.ieapi_contact_1.ResDataType;

import java.util.Date;

import javax.xml.bind.JAXBException;

import org.testng.annotations.Test;

import pl.nask.crs.commons.utils.DateUtils;

/**
 * @author: Marcin Tkaczyk
 */
public class ContactTest extends TestUtil {
    String result = null;

    @Test
    public void contactInfoTest() throws Exception {
       
    	loginApiTest();

        result = sendRequest(xmlFileToString("/commands/contact_info.xml"));
        assertResultMatch("contact info result - ok", result, "/commands/contact_info_response_ok.xml");
        InfDataType responseValue = (InfDataType) getResultDataValueFromString(result);
        InfDataType exampleValue = (InfDataType) getResultDataValueFromFile("/commands/contact_info_response_ok.xml");
        // another test: let the compiler check, if the type is correct
        Date d = responseValue.getCrDate();
        compareContactsInfo(exampleValue, responseValue);

        result = sendRequest(xmlFileToString("/commands/contact_info_not_exist.xml"));
        assertResultMatch("contact info - not exist", result, "/commands/response_contact_id_not_exist.xml");

        result = sendRequest(xmlFileToString("/commands/contact_info_wihout_id.xml"));
        assertResultMatch("contact info - without id", result, "/commands/response_contact_id_mandatory.xml");

        
        logout();
        loginApit4();
        
        result = sendRequest(xmlFileToString("/commands/contact_info.xml"));
        assertResultMatch("contact info - managed by another reseller", result, "/commands/response_contact_managed_by_another_registrar.xml");
        
        logout();
    }

    private void compareContactsInfo(InfDataType exampleValue, InfDataType responseValue) {
        assertEquals(responseValue.getId(), exampleValue.getId(), "contact info result data : id");
        assertEquals(responseValue.getAccount(), exampleValue.getAccount(), "contact info result data : account");
        assertEquals(responseValue.getCompanyName().trim(), exampleValue.getCompanyName().trim(), "contact info result data : company name");
        assertEquals(responseValue.getCountry(), exampleValue.getCountry(), "contact info result data : country");
        assertEquals(responseValue.getCounty(), exampleValue.getCounty(), "contact info result data : county");
        assertEquals(responseValue.getEmail(), exampleValue.getEmail(),"contact info result data : email");
        assertEquals(responseValue.getFax(), exampleValue.getFax(), "contact info result data : fax");
        assertEquals(responseValue.getName().trim(), exampleValue.getName().trim(), "contact info result data : name");
        assertEquals(responseValue.getStatus(), exampleValue.getStatus(), "contact info result data : status");
        assertEquals(responseValue.getVoice(), exampleValue.getVoice(), "contact info result data : voice");
        assertEquals(responseValue.getAddr().trim(), exampleValue.getAddr().trim(), "contact info result data : addr");
        // if it fails check, if InfDataType.crDate is a Date
        assertEquals(responseValue.getCrDate(), exampleValue.getCrDate(), "contact info result data - creation date");
    }

    @Test
    public void contactCreateTest() throws JAXBException {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/contact_create.xml"));
        assertResultMatch("contact create ok", result, "/commands/contact_create_response.xml");
        CreDataType creationResponseValue = (CreDataType) getResultDataValueFromString(result);
        String newContactId = creationResponseValue.getId();

        result = sendRequest(prepareInfoRequest(xmlFileToString("/commands/contact_info.xml"), newContactId));
        InfDataType responseValue = (InfDataType) getResultDataValueFromString(result);
        InfDataType exampleValue = (InfDataType) getResultDataValueFromFile("/commands/contact_info_contact_create_test_response.xml");
        prepareExampleValue(exampleValue, newContactId);

        compareContactsInfo(exampleValue, responseValue);

        // if it fails check, if CreDataType.crDate is a Date
        // (let the compiler check, if the type is correct)
        Date d = responseValue.getCrDate();

        logout();

        loginApit2();

        result = sendRequest(xmlFileToString("/commands/contact_create_empty_name.xml"));
        assertResultMatch("contact create empty name field", result, "/commands/contact_create_empty_name_response.xml");

        result = sendRequest(xmlFileToString("/commands/contact_create_empty_company_name.xml"));
        assertResultMatch("contact create empty company name field", result, "/commands/response_contact_company_name_field_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_create_empty_address.xml"));
        assertResultMatch("contact create empty address field", result, "/commands/response_contact_address_field_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_create_empty_country.xml"));
        assertResultMatch("contact create empty country field", result, "/commands/response_contact_country_field_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_create_empty_voice.xml"));
        assertResultMatch("contact create empty voice field", result, "/commands/response_contact_voice_field_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_create_empty_email.xml"));
        assertResultMatch("contact create empty email field", result, "/commands/response_contact_email_field_mandatory.xml");

        logout();

        loginAHL863();

        result = sendRequest(xmlFileToString("/commands/contact_create.xml"));
        assertResultMatch("contact create inactive account", result, "/commands/response_inactive_account.xml");

        logout();
    }

    private String prepareInfoRequest(String requestAsString, String newContactId) {
        return requestAsString.replace("APIT1-IEDR", newContactId);
    }

    private void prepareExampleValue(InfDataType exampleValue, String newContactId) {
        exampleValue.setCrDate(DateUtils.startOfDay(new Date()));
        exampleValue.setId(newContactId);
    }

    @Test
    public void contactModifyTest() {
        InfDataType responseValue = null;
        InfDataType exampleValue = null;

        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/contact_info_modify_test.xml"));
        assertResultMatch("contact info before modify test", result, "/commands/contact_info_before_modify_test_response.xml");
        responseValue = (InfDataType) getResultDataValueFromString(result);
        exampleValue = (InfDataType) getResultDataValueFromFile("/commands/contact_info_before_modify_test_response.xml");
        compareContactsInfo(exampleValue, responseValue);

        result = sendRequest(xmlFileToString("/commands/contact_modify.xml"));
        assertResultMatch("contact modify", result, "/commands/contact_modify_response.xml");

        result = sendRequest(xmlFileToString("/commands/contact_info_modify_test.xml"));
        assertResultMatch("contact after before modify test", result, "/commands/contact_info_after_modify_test_response.xml");
        responseValue = (InfDataType) getResultDataValueFromString(result);
        exampleValue = (InfDataType) getResultDataValueFromFile("/commands/contact_info_after_modify_test_response.xml");
        compareContactsInfo(exampleValue, responseValue);

        logout();

        loginApit4();

        result = sendRequest(xmlFileToString("/commands/contact_modify_apit1.xml"));
        assertResultMatch("contact modify - managed by another reseller", result, "/commands/response_contact_managed_by_another_registrar.xml");

        logout();

        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/contact_modify_empty_id.xml"));
        assertResultMatch("contact modify - empty id", result, "/commands/response_contact_id_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_modify_not_existing_id.xml"));
        assertResultMatch("contact modify - id does not exist", result, "/commands/response_contact_id_not_exist.xml");

        result = sendRequest(xmlFileToString("/commands/contact_modify_empty_company_name.xml"));
        assertResultMatch("contact modify empty company name field", result, "/commands/response_contact_company_name_field_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_modify_empty_address.xml"));
        assertResultMatch("contact modify empty address field", result, "/commands/response_contact_address_field_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_modify_empty_country.xml"));
        assertResultMatch("contact modify empty country field", result, "/commands/response_contact_country_field_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_modify_empty_voice.xml"));
        assertResultMatch("contact modify empty voice field", result, "/commands/response_contact_voice_field_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_modify_empty_email.xml"));
        assertResultMatch("contact modify empty email field", result, "/commands/response_contact_email_field_mandatory.xml");

        result = sendRequest(xmlFileToString("/commands/contact_modify_country.xml"));
        assertResultMatch("contact modify country field", result, "/commands/contact_modify_country_response.xml");

        logout();

    }

    @Test
    public void contactQueryTest() throws Exception {
        loginApit3();

        result = sendRequest(xmlFileToString("/commands/contact_query_all.xml"));
        assertResultMatch("contact query all result", result, "/commands/contact_query_all_response.xml");
        ResDataType responseValue = (ResDataType) getResultDataValueFromString(result);
        ResDataType exampleValue = (ResDataType) getResultDataValueFromFile("/commands/contact_query_all_response.xml");
        compareQueryData(responseValue, exampleValue, "all");

        result = sendRequest(xmlFileToString("/commands/contact_query_admin.xml"));
        assertResultMatch("contact query admin - no results", result, "/commands/contact_query_response_no_results.xml");
        
        logout();

        loginIDL2();
        
        result = sendRequest(xmlFileToString("/commands/contact_query_admin.xml"));
        assertResultMatch("contact query admin result", result, "/commands/contact_query_admin_response.xml");
        responseValue = (ResDataType) getResultDataValueFromString(result);
        exampleValue = (ResDataType) getResultDataValueFromFile("/commands/contact_query_admin_response.xml");
        compareQueryData(responseValue, exampleValue, "admin");

        result = sendRequest(xmlFileToString("/commands/contact_query_tech.xml"));
        assertResultMatch("contact query tech result", result, "/commands/contact_query_tech_response.xml");
        responseValue = (ResDataType) getResultDataValueFromString(result);
        exampleValue = (ResDataType) getResultDataValueFromFile("/commands/contact_query_tech_response.xml");
        compareQueryData(responseValue, exampleValue, "tech");

        logout();
    }

    private void compareQueryData(ResDataType responseValue, ResDataType exampleValue, String queryType) {
        assertEquals(responseValue.getPage(), exampleValue.getPage(), "contact query " + queryType + " result data : page");
        assertEquals(responseValue.getTotalPages(), exampleValue.getTotalPages(), "contact query " + queryType + " result data : total pages");
        assertEquals(responseValue.getId(), exampleValue.getId(), "contact query " + queryType + " result data : id");
    }
}
