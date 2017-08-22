package pl.nask.crs.iedrapi.tests.bpr.requestdomainregistration;

import static org.testng.Assert.assertEquals;
import static pl.nask.crs.iedrapi.tests.TestHelper.compareDomainsInfo;
import ie.domainregistry.ieapi_domain_1.CreDataType;
import ie.domainregistry.ieapi_domain_1.InfDataType;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.iedrapi.tests.TestUtil;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SC08GIBOBasic extends TestUtil {

    String result;
    
    @BeforeMethod
    public void login() {
    	loginApits1();    
    }
    
    @AfterMethod
    public void logout() {
    	super.logout();
    }

    @Test
    public void domainAutoCreateTest() {
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_autocreate.xml"));
        assertResultMatch("domain autocreate", result, "/commands/bpr/requestdomainregistration/domain_autocreate_response.xml");
        CreDataType responseValue = (CreDataType) getResultDataValueFromString(result);
        CreDataType exampleValue = (CreDataType) getResultDataValueFromFile("/commands/bpr/requestdomainregistration/domain_autocreate_response.xml");
        assertEquals(responseValue.getName(), exampleValue.getName(), "domain autocreate data");

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_info_autocreate_test.xml"));
        InfDataType infoResponseValue = (InfDataType) getResultDataValueFromString(result);
        InfDataType infoExampleValue = (InfDataType) getResultDataValueFromFile("/commands/bpr/requestdomainregistration/domain_info_autocreate_test_response.xml");
        compareDomainsInfo(infoResponseValue, infoExampleValue);
    }
    
    @Test(dependsOnMethods = {"domainAutoCreateTest"})
    public void domainAutoCreateInvalidName() {
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_autocreate_invalid_name.xml"));
        assertResultMatch("domain autocreate - invalid domain name",result, "/commands/bpr/requestdomainregistration/domain_autocreate_invalid_name_response.xml");
    }
    
    @Test
    public void domainAutoCreateInvalidPeriod() {
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_autocreate_invalid_period.xml"));
        assertResultMatch("domain autocreate - invalid domain name",result, "/commands/bpr/requestdomainregistration/domain_autocreate_invalid_period_response.xml");
    }

    @Test(dependsOnMethods = {"domainAutoCreateTest"})
    public void domainAutoCreateClassNE() {

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_autocreate_class_not_exist.xml"));
        assertResultMatch("domain autocreate not existing class", result, "/commands/response_class_not_exist.xml");
    }

    @Test(dependsOnMethods = {"domainAutoCreateTest"})
    public void domainAutoCreateCategoryNE() {
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_autocreate_category_not_exist.xml"));
        assertResultMatch("domain autocreate not existing class", result, "/commands/response_category_not_exist.xml");
    }

    @Test(dependsOnMethods = {"domainAutoCreateTest"})
    public void domainAutoCreateNameInUse() {
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_autocreate.xml"));
        assertResultMatch("domain autocreate exists or pending registration", result, "/commands/bpr/requestdomainregistration/domain_autocreate_domain_name_exist_or_pending_response.xml");
    }

    @Test(dependsOnMethods = {"domainAutoCreateTest"})
    public void domainAutoCreateClassCategoryMismatch() {
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_autocreate_class_category_mismatch.xml"));
        assertResultMatch("domain autocreate class and category mismatch", result, "/commands/response_class_category_mismatch.xml");
    }

    @Test(dependsOnMethods = {"domainAutoCreateTest"})    
    public void domainAutoCreatePermissionDenied() {
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_autocreate_category_permission_denied.xml"));
        assertResultMatch("domain autocreate category permission denied", result, "/commands/response_category_permission_denied.xml");
    }
    
    @Test    
    public void domainAutoCreateCharityNotPossible() {
        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_autocreate_charity.xml"));
        assertResultMatch("domain autocreate category permission denied", result, "/commands/response_charity_registration_not_possible.xml");
    }
}
