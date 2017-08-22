package pl.nask.crs.iedrapi.tests.bpr.requestdomainregistration;

import ie.domainregistry.ieapi_domain_1.AppDataType;
import ie.domainregistry.ieapi_ticket_1.InfDataType;
import org.testng.annotations.Test;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.iedrapi.tests.TestUtil;

import static org.testng.Assert.assertEquals;
import static pl.nask.crs.iedrapi.tests.TestHelper.compareTickets;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SC01RegistrarBasis extends TestUtil {

    String result = null;

    @Test
    public void domainCreateOKTest() {
    	loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/requestdomainregistration/domain_create_response.xml");
        AppDataType responseValue = (AppDataType) getResultDataValueFromString(result);
        AppDataType exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/requestdomainregistration/domain_create_response.xml");
        assertEquals(responseValue.getName(), exampleValue.getName(), "domain create result data");

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/ticket_info.xml"));
        assertResultMatch("ticket info", result, "/commands/bpr/requestdomainregistration/ticket_info_response.xml");
        InfDataType actual = (InfDataType) getResultDataValueFromString(result);
        InfDataType expected = (InfDataType) getResultDataValueFromFile("/commands/bpr/requestdomainregistration/ticket_info_response.xml");
        XMLGregorianCalendar regExpected = DateUtils.asXmlGregorianCalendar(new Date());
        compareTickets(actual, expected, regExpected);

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create.xml"));
        assertResultMatch("domain create - domain exists", result, "/commands/bpr/requestdomainregistration/domain_create_domain_exists_response.xml");

        logout();
    }

    @Test
    public void domainCreateTestTooFewAdmin() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_too_few_admin.xml"));
        assertResultMatch("domain create - too few admin contacts", result, "/commands/bpr/requestdomainregistration/domain_create_too_few_admin_response.xml");
    	logout();
    }

    @Test
    public void domainCreateTestNameRequired() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_name_mandatory.xml"));
        assertResultMatch("domain create - domain name mandatory", result, "/commands/bpr/requestdomainregistration/response_domain_name_mandatory.xml");
    	logout();
    }

    @Test
    public void domainCreateTestHolderRequired() {
    	loginApiTest();
   	    result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_holder_mandatory.xml"));
   	    assertResultMatch("domain create - holder mandatory", result, "/commands/bpr/requestdomainregistration/domain_create_holder_mandatory_response.xml");
    	logout();
    }

    @Test
    public void domainCreateTestNameSyntaxError() {
    	loginApiTest();
    	result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_name_syntax.xml"));
    	assertResultMatch("domain create - name syntax error", result, "/commands/bpr/requestdomainregistration/domain_create_name_syntax_response.xml");
    	logout();
    }

    @Test
    public void domainCreateTestInvalidPeriod() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_invalid_period_unit.xml"));
        assertResultMatch("domain create - invalid period", result, "/commands/bpr/requestdomainregistration/domain_create_invalid_period_unit_response.xml");
    	logout();
    }

    @Test
    public void domainCreateTestNotAdmissiblePeriod() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_not_admissible_period.xml"));
        assertResultMatch("domain create - invalid period", result, "/commands/bpr/requestdomainregistration/domain_create_not_admissible_period_response.xml");
    	logout();
    }

    @Test
    public void domainCreateTestHolderClassRequired() {
        loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_holder_class_mandatory.xml"));
        assertResultMatch("domain create - holder class mandatory", result, "/commands/bpr/requestdomainregistration/domain_create_holder_class_mandatory_response.xml");
        logout();
    }

    @Test
    public void domainCreateTestHolderCategoryRequired() {
    	loginApiTest();
    	result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_category_mandatory.xml"));
    	assertResultMatch("domain create - holder category mandatory", result, "/commands/bpr/requestdomainregistration/domain_create_category_mandatory_response.xml");
    	logout();
    }

    @Test
    public void domainCreateTestTooLongRemark() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_remark_too_long.xml"));
        assertResultMatch("domain create - holder remark too long", result, "/commands/bpr/requestdomainregistration/domain_create_remark_too_long_response.xml");
    	logout();
    }
    @Test
    public void domainCreateTestTooFewDns() {
        loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_too_few_dns.xml"));
        assertResultMatch("domain create - too few dns", result, "/commands/bpr/requestdomainregistration/domain_create_too_few_dns_response.xml");
        logout();
    }

    @Test
    public void domainCreateTestTooManyDns() {
        loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_too_many_dns.xml"));
        assertResultMatch("domain create - too few dns", result, "/commands/bpr/requestdomainregistration/domain_create_too_many_dns_response.xml");
        logout();
    }

    @Test
    public void domainCreateTestDuplicatedDns() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_duplicated_dnss.xml"));
        assertResultMatch("domain create - duplicated dnss", result, "/commands/bpr/requestdomainregistration/domain_create_duplicated_dnss_response.xml");
    	logout();
    }
    @Test
    public void domainCreateTestDnsSyntaxError1() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_dns_name_syntax1.xml"));
        assertResultMatch("domain create - dns name syntax error", result, "/commands/bpr/requestdomainregistration/domain_create_dns_name_syntax_response1.xml");
    	logout();
    }

    @Test
    public void domainCreateTestDnsSyntaxError2() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_dns_name_syntax2.xml"));
        assertResultMatch("domain create - dns name syntax error", result, "/commands/bpr/requestdomainregistration/domain_create_dns_name_syntax_response2.xml");
    	logout();
    }

    @Test
    public void domainCreateTestDnsIpSyntaxError() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_dns_ip_syntax.xml"));
        assertResultMatch("domain create - dns ip syntax error", result, "/commands/bpr/requestdomainregistration/domain_create_dns_ip_syntax_response.xml");
    	logout();
    }
    @Test
    public void domainCreateTestDnsGlueNotAllowed() {
        loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_glue_not_allowed.xml"));
        assertResultMatch("domain create - dns glue not allowed", result, "/commands/bpr/requestdomainregistration/domain_create_glue_not_allowed_response.xml");
        logout();
    }
    @Test
    public void domainCreateTestDnsGlueRequired() {
    	loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_glue_required.xml"));
        assertResultMatch("domain create - dns glue required", result, "/commands/bpr/requestdomainregistration/domain_create_glue_required_response.xml");
    	logout();
    }

    @Test
    public void domainCreateWrongPaymentMethod() {
        loginApiTest();
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_cc_deposit_simultaneously.xml"));
        assertResultMatch("domain create - dns glue required", result, "/commands/bpr/requestdomainregistration/domain_create_cc_deposit_simultaneously_response.xml");
        logout();
    }

    @Test(dependsOnMethods = {"domainCreateOKTest"})
    public void domainCreateClassCategoryNE() {
    	loginApits1();

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_class_not_exist.xml"));
        assertResultMatch("domain autocreate not existing class", result, "/commands/response_class_not_exist.xml");

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_category_not_exist.xml"));
        assertResultMatch("domain autocreate not existing class", result, "/commands/response_category_not_exist.xml");

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_class_category_mismatch.xml"));
        assertResultMatch("domain autocreate class and category mismatch", result, "/commands/response_class_category_mismatch.xml");

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_without_permission_to_category.xml"));
        assertResultMatch("domain autocreate category permission denied", result, "/commands/bpr/requestdomainregistration/domain_create_without_permission_to_category_response.xml");

        logout();
    }

}
