package pl.nask.crs.iedrapi.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import ie.domainregistry.ieapi_account_1.DepositFundsDataType;

import ie.domainregistry.ieapi_account_1.PayDataType;
import org.testng.annotations.Test;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */

/**
 * This tests can run user with configured test account in realex system
 */
public class AccountWithRealexTest extends TestUtil {

    String result = null;

    @Test
    public void accountDepositFundsTest() throws Exception {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/account_pay_deposit_funds.xml"));
        assertResultMatch("account pay deposit funds", result, "/commands/account_pay_deposit_funds_response.xml");
        DepositFundsDataType responseValue = (DepositFundsDataType) getResultDataValueFromString(result);
        DepositFundsDataType exampleValue = (DepositFundsDataType) getResultDataValueFromFile("/commands/account_pay_deposit_funds_response.xml");
        assertEquals(responseValue.getOldValue(), exampleValue.getOldValue(), "account deposit funds data : old value");
        assertEquals(responseValue.getNewValue(), exampleValue.getNewValue(), "account deposit funds data : new value");

        result = sendRequest(xmlFileToString("/commands/account_pay_deposit_bad_exp_date.xml"));
        assertResultMatch("account pay deposit funds bad exp date", result, "/commands/account_pay_deposit_bad_exp_date_response.xml");

        logout();

    }

    @Test
    public void accountDepositFundsCVNTest() {
        loginIDL2();
        result = sendRequest(xmlFileToString("/commands/account_pay_deposit_cvn_presind_missing.xml"));
        assertResultMatch("missing presence indicator", result, "/commands/response_cvn_presind_is_madatory.xml");

        result = sendRequest(xmlFileToString("/commands/account_pay_deposit_bad_cvn_number.xml"));
        assertResultMatch("invalid cvn number", result, "/commands/response_invalid_cvn_number.xml");

        result = sendRequest(xmlFileToString("/commands/account_pay_deposit_bad_cvn_presind.xml"));
        assertResultMatch("invalid presence indicator", result, "/commands/response_invalid_presence_indicator.xml");

        logout();
    }

    @Test
    public void payCCHappyPathTest() {
        loginApiTest();

        result = sendRequest(xmlFileToString("/commands/bpr/pay/account_pay_with_CC.xml"));
        assertResultMatch("domain create result ok", result, "/commands/bpr/pay/response_pay_one_rm2yr_domain.xml");
        PayDataType responseValue = (PayDataType) getResultDataValueFromString(result);
        PayDataType exampleValue = (PayDataType) getResultDataValueFromFile("/commands/bpr/pay/response_pay_one_rm2yr_domain.xml");
        assertEquals(responseValue.getFee(), exampleValue.getFee(), "domain create fee");
        assertEquals(responseValue.getVat(), exampleValue.getVat(), "domain create vat");
        assertEquals(responseValue.getTotal(), exampleValue.getTotal(), "domain create total");
        assertNotNull(responseValue.getOrderId(), "domain create orderId");
        assertFalse(responseValue.getOrderId().trim().equals("0"), "domain create orderId");

        logout();

    }

}
