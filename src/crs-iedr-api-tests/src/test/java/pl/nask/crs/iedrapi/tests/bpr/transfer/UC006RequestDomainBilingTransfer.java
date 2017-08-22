package pl.nask.crs.iedrapi.tests.bpr.transfer;

import ie.domainregistry.ieapi_domain_1.AppDataType;
import org.testng.annotations.Test;
import pl.nask.crs.iedrapi.tests.TestUtil;

import static org.testng.Assert.assertTrue;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class UC006RequestDomainBilingTransfer extends TestUtil {

    private String result = null;

    @Test
    public void SC01Test() {

        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_sc01.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer_sc01_response.xml");
        AppDataType responseValue = (AppDataType) getResultDataValueFromString(result);
        AppDataType exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer_sc01_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data ok");
        //new status (XPA)
        logout();
    }

    @Test
    public void SC03Test() {

        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_sc03.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer_sc03_response.xml");
        AppDataType responseValue = (AppDataType) getResultDataValueFromString(result);
        AppDataType exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer_sc03_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data ok");
        //new status(XPA)
        logout();
    }

    @Test
    public void SC04Test() {

        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_sc04.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer_sc04_response.xml");
        AppDataType responseValue = (AppDataType) getResultDataValueFromString(result);
        AppDataType exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer_sc04_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data ok");
        //new status(XPV)
        logout();
    }

    @Test
    public void SC05Test() {

        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_sc05.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer_sc05_response.xml");
        AppDataType responseValue = (AppDataType) getResultDataValueFromString(result);
        AppDataType exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer_sc05_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data ok");
        //new status(XPI)
        logout();
    }

    @Test
    public void SC07Test() {

        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_sc07.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer_sc07_response.xml");
        AppDataType responseValue = (AppDataType) getResultDataValueFromString(result);
        AppDataType exampleValue = (AppDataType) getResultDataValueFromFile("/commands/bpr/transfer/domain_transfer_sc07_response.xml");
        assertTrue(responseValue.getName().equals(exampleValue.getName()), "domain transfer data ok");
        //status(XPA)
        logout();
    }

    @Test(dependsOnMethods = "SC01Test")
    public void SC11Test() {

        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/transfer/domain_transfer_sc01.xml"));
        assertResultMatch("domain transfer ok", result, "/commands/bpr/transfer/domain_transfer_pending.xml");
        logout();
    }
}
