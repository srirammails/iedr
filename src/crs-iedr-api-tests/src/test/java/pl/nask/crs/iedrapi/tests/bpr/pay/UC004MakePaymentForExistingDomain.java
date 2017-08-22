package pl.nask.crs.iedrapi.tests.bpr.pay;

import ie.domainregistry.ieapi_account_1.PayDataType;
import org.testng.annotations.Test;
import pl.nask.crs.iedrapi.tests.TestUtil;

import static org.testng.Assert.*;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class UC004MakePaymentForExistingDomain extends TestUtil {

    private String result = null;

    @Test
    public void payADPWithTestFlagTest() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_test_with_deposit.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/account_pay_test_with_deposit_response.xml");
        PayDataType responseValue = (PayDataType) getResultDataValueFromString(result);
        PayDataType exampleValue = (PayDataType) getResultDataValueFromFile("/commands/bpr/pay/account_pay_test_with_deposit_response.xml");
        assertEquals(responseValue.getFee(), exampleValue.getFee(), "domain create fee");
        assertEquals(responseValue.getVat().setScale(2), exampleValue.getVat().setScale(2), "domain create vat");
        assertEquals(responseValue.getTotal().setScale(2), exampleValue.getTotal().setScale(2), "domain create total");
        assertNotNull(responseValue.getOrderId(), "domain create orderId");

        logout();
    }

    @Test(dependsOnMethods = {"payADPWithTestFlagTest"})
    public void SC01Test() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_with_deposit.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/account_pay_with_deposit_response.xml");
        PayDataType responseValue = (PayDataType) getResultDataValueFromString(result);
        PayDataType exampleValue = (PayDataType) getResultDataValueFromFile("/commands/bpr/pay/account_pay_with_deposit_response.xml");
        assertEquals(responseValue.getFee(), exampleValue.getFee(), "domain create fee");
        assertEquals(responseValue.getVat(), exampleValue.getVat(), "domain create vat");
        assertEquals(responseValue.getTotal(), exampleValue.getTotal(), "domain create total");
        assertNotNull(responseValue.getOrderId(), "domain create orderId");

        logout();
    }

    @Test
    public void payCCWithTestFlagTest() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_test_with_CC.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/response_pay_one_rm2yr_domain.xml");
        PayDataType responseValue = (PayDataType) getResultDataValueFromString(result);
        PayDataType exampleValue = (PayDataType) getResultDataValueFromFile("/commands/bpr/pay/response_pay_one_rm2yr_domain.xml");
        assertEquals(responseValue.getFee(), exampleValue.getFee(), "domain create fee");
        assertEquals(responseValue.getVat().setScale(2), exampleValue.getVat().setScale(2), "domain create vat");
        assertEquals(responseValue.getTotal().setScale(2), exampleValue.getTotal().setScale(2), "domain create total");
        assertNotNull(responseValue.getOrderId(), "domain create orderId");

        logout();
    }

    @Test
    public void SC09Test() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_charity_domain.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/response_incorect_domain_state_for_payment.xml");

        logout();
    }

    @Test
    public void SC12Test() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_NRPP_domain.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/response_incorect_domain_state_for_payment.xml");

        logout();
    }

    @Test
    public void SC13Test() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_NRPT_domain.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/response_incorect_domain_state_for_payment.xml");

        logout();
    }

    @Test(enabled = false)//TODO dsm update required
    public void SC14Test() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_voluntary_mailed_domain.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/response_pay_one_rm2yr_domain.xml");
        PayDataType responseValue = (PayDataType) getResultDataValueFromString(result);
        PayDataType exampleValue = (PayDataType) getResultDataValueFromFile("/commands/bpr/pay/response_pay_one_rm2yr_domain.xml");
        assertEquals(responseValue.getFee(), exampleValue.getFee(), "domain create fee");
        assertEquals(responseValue.getVat(), exampleValue.getVat(), "domain create vat");
        assertEquals(responseValue.getTotal(), exampleValue.getTotal(), "domain create total");
        assertNotNull(responseValue.getOrderId(), "domain create orderId");

        logout();

    }

    @Test(enabled = false)//TODO dsm update required
    public void SC15Test() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_voluntary_suspended_domain.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/response_pay_one_rm2yr_domain.xml");
        PayDataType responseValue = (PayDataType) getResultDataValueFromString(result);
        PayDataType exampleValue = (PayDataType) getResultDataValueFromFile("/commands/bpr/pay/response_pay_one_rm2yr_domain.xml");
        assertEquals(responseValue.getFee(), exampleValue.getFee(), "domain create fee");
        assertEquals(responseValue.getVat(), exampleValue.getVat(), "domain create vat");
        assertEquals(responseValue.getTotal(), exampleValue.getTotal(), "domain create total");
        assertNotNull(responseValue.getOrderId(), "domain create orderId");

        logout();

    }

    @Test
    public void SC19Test() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_some_non_billable_domain.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/response_incorect_domain_state_for_payment.xml");

        logout();
    }

    @Test
    public void SC21Test() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_NRPIM_autorenew_domain.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/response_pay_one_std1year_domain.xml");
        PayDataType responseValue = (PayDataType) getResultDataValueFromString(result);
        PayDataType exampleValue = (PayDataType) getResultDataValueFromFile("/commands/bpr/pay/response_pay_one_std1year_domain.xml");
        assertEquals(responseValue.getFee(), exampleValue.getFee(), "domain create fee");
        assertEquals(responseValue.getVat().setScale(2), exampleValue.getVat().setScale(2), "domain create vat");
        assertEquals(responseValue.getTotal().setScale(2), exampleValue.getTotal().setScale(2), "domain create total");
        assertNotNull(responseValue.getOrderId(), "domain create orderId");

        logout();
    }

}
