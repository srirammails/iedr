package pl.nask.crs.iedrapi.tests.bpr.requestdomainregistration;

import ie.domainregistry.ieapi_domain_1.AppDataType;
import org.testng.annotations.Test;
import pl.nask.crs.iedrapi.tests.TestUtil;

import static org.testng.Assert.assertEquals;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SC05RegistrarCharityReg extends TestUtil {

    String result = null;

    @Test
    public void createCharityDomainOKTest() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_charity.xml"));
        assertResultMatch("domain create charity", result, "/commands/bpr/requestdomainregistration/domain_create_charity_response.xml");
        AppDataType responseValue = (AppDataType) getResultDataValueFromString(result);
        AppDataType exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/requestdomainregistration/domain_create_charity_response.xml");
        assertEquals(responseValue.getName(), exampleValue.getName(), "domain create result data");

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_charity.xml"));
        assertResultMatch("domain create - domain exists", result, "/commands/bpr/requestdomainregistration/domain_create_domain_exists_response.xml");

        logout();

    }
    
    @Test
    public void createCharityDomainWrongPeriod() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_charity_wrong_period.xml"));
        assertResultMatch("domain create charity", result, "/commands/bpr/requestdomainregistration/domain_create_not_admissible_period_response.xml");

        logout();
    }

    @Test
    public void createCharityDomainTooLongChy() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/requestdomainregistration/domain_create_charity_too_long_chy.xml"));
        assertResultMatch("domain create charity", result, "/commands/bpr/requestdomainregistration/domain_create_charity_too_long_chy_response.xml");

        logout();

    }

}
