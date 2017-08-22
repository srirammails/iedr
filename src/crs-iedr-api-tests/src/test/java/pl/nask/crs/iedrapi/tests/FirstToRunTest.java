package pl.nask.crs.iedrapi.tests;

import static org.testng.Assert.*;
import ie.domainregistry.ieapi_account_1.ChkDepositDataType;
import ie.domainregistry.ieapi_account_1.ResDataType;
import ie.domainregistry.ieapi_account_1.ResRecordType;
import ie.domainregistry.ieapi_domain_1.ResDomainType;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * This tests have to be run as first to pass
 */
public class FirstToRunTest extends TestUtil {

    String result = null;

    @Test
    public void accountCheckDepositTest() throws Exception {
        loginIDL2();

        result = sendRequest(xmlFileToString("/commands/account_pay_check_deposit.xml"));
        assertResultMatch("account pay check deposit ok", result, "/commands/account_pay_check_deposit_response.xml");
        ChkDepositDataType responseValue = (ChkDepositDataType) getResultDataValueFromString(result);
        ChkDepositDataType exampleValue = (ChkDepositDataType) getResultDataValueFromFile("/commands/account_pay_check_deposit_response.xml");
        assertEquals(responseValue.getActualBalance(), exampleValue.getActualBalance(), "account pay check deposit actualBalance");
        assertEquals(responseValue.getAvailableBalance(), exampleValue.getAvailableBalance(), "account pay check deposit availableBalance");
        assertEquals(responseValue.getTransDate(), exampleValue.getTransDate(), "account pay check deposit transDate");                
    }
    
    @AfterMethod
    public void logOutAfterTest() {
    	logout();
    }

    
    @Test(enabled=false, description="refactoring needed/BPR")
    public void accountQueryCurrRenRegTest() {
        loginApit5();
        result = sendRequest(xmlFileToString("/commands/account_query_curr_ren_reg.xml"));
        assertResultMatch("account curr ren reg query", result, "/commands/account_query_curr_ren_reg_response.xml");
        
        ResDataType resValue = (ResDataType) getResultDataValueFromString(result);
        ResDataType exValue = (ResDataType) getResultDataValueFromFile("/commands/account_query_curr_ren_reg_response.xml");
        compareResRecords(resValue.getResRecord(), exValue.getResRecord(), "account curr ren reg query data");
    }

    @Test(enabled=false, description="refactoring needed/BPR")
    public void accountQueryTranTest() {
    	loginApit5();

        result = sendRequest(xmlFileToString("/commands/account_query_tran.xml"));
        assertResultMatch("account tran query", result, "/commands/account_query_tran_response.xml");
        ResDataType resValue = (ResDataType) getResultDataValueFromString(result);
        ResDataType exValue = (ResDataType) getResultDataValueFromFile("/commands/account_query_tran_response.xml");
        compareResRecords(resValue.getResRecord(), exValue.getResRecord(), "account tran query data");
    }
    
    @Test(enabled=false, description="refactoring needed/BPR")
    public void accountQueryTranAdvTest() {
        loginApit5();
        
        result = sendRequest(xmlFileToString("/commands/account_query_tran_adv.xml"));
        assertResultMatch("account tran adv query", result, "/commands/account_query_tran_adv_response.xml");
        ResDataType resValue = (ResDataType) getResultDataValueFromString(result);
        ResDataType exValue = (ResDataType) getResultDataValueFromFile("/commands/account_query_tran_adv_response.xml");
        compareResRecords(resValue.getResRecord(), exValue.getResRecord(), "account tran adv query data");
    }

    @Test(enabled=false, description="refactoring needed/BPR")
    public void accountQueryFutRenTest() {
    	loginApit5();

        String commandAsString = prepareRequest(xmlFileToString("/commands/account_query_fut_ren.xml"), generateRenewalMonthForNewDomain());
        result = sendRequest(commandAsString);
        assertResultMatch("account fut ren query", result, "/commands/account_query_fut_ren_response.xml");

        ResDataType resValue = (ResDataType) getResultDataValueFromString(result);
        ResDataType exValue = (ResDataType) getResultDataValueFromFile("/commands/account_query_fut_ren_response.xml");
        compareResRecords(resValue.getResRecord(), exValue.getResRecord(), "account fut ren query data");
    }  

    private String prepareRequest(String requestAsString, String newMonthValue) {
        return requestAsString.replace("MONTH_TO_REPLACE", newMonthValue);
    }

    private String generateRenewalMonthForNewDomain() {
        Date currDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currDate);
        c.add(Calendar.YEAR, 1);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String monthAsString = month > 9 ? String.valueOf(month) : "0" + month;
        String ret = year + "-" + monthAsString;
        return ret;
    }


    private void compareResRecords(List<ResRecordType> actualList, List<ResRecordType> exampleList, String message) {
        assertEquals(actualList.size(), exampleList.size(), message + " : invoices count");
        for (int i = 0; i < actualList.size(); i++) {
            assertEquals(actualList.get(i).getAmount(), exampleList.get(i).getAmount(), message + " : amount");
            assertEquals(actualList.get(i).getDomain(), exampleList.get(i).getDomain(), message + " : domain");
            assertEquals(actualList.get(i).getHolder(), exampleList.get(i).getHolder(), message + " : holder");
            assertEquals(actualList.get(i).getInvoice(), exampleList.get(i).getInvoice(), message + " : invoice");
            assertEquals(actualList.get(i).getMsdDate(), exampleList.get(i).getMsdDate(), message + " : msd date");
            assertEquals(actualList.get(i).getStatus(), exampleList.get(i).getStatus(), message + " : status");
            assertEquals(actualList.get(i).getVat(), exampleList.get(i).getVat(), message + " : vat");
            assertEquals(actualList.get(i).getRegDate(), exampleList.get(i).getRegDate(), message + " : registration date");
            assertEquals(actualList.get(i).getRenDate(), exampleList.get(i).getRenDate(), message + " : renewal date");
            assertEquals(actualList.get(i).getTrnDate(), exampleList.get(i).getTrnDate(), message + " : trnasfer date");
        }
    }
    
    @Test
    public void domainQueryAllNoResult() {
    	loginAHL863();
    	
        result = sendRequest(xmlFileToString("/commands/domain_query_all.xml"));
        assertResultMatch("domain query all - no result", result, "/commands/domain_query_all_no_result_response.xml");
    }
    
    @Test
    public void domainQueryAll() {
    	loginIDL2();

        result = sendRequest(xmlFileToString("/commands/domain_query_all.xml"));
        assertResultMatch("domain query all result ok", result, "/commands/domain_query_all_response.xml");
        
        compareQueryData("all", result, "/commands/domain_query_all_response.xml");
    }
    
    @Test
    public void domainQueryNoReservationInfoAttached() {
    	loginIDL2();

        result = sendRequest(xmlFileToString("/commands/domain_query_all.xml"));
        // no domain in the result should contain the reservation flag
        ie.domainregistry.ieapi_domain_1.ResDataType responseValue = (ie.domainregistry.ieapi_domain_1.ResDataType) getResultDataValueFromString(result);
        Assert.assertTrue(responseValue.getDomain().size() > 0, "at least one domain should be returned");
        
        for (ResDomainType d: responseValue.getDomain()) {
        	Assert.assertNull(d.isReservationPending(), "reservationPending flag should not be set");
        }
    }
    
    @Test
    public void domainQueryReservationInfoAttached() {
    	loginApiTest();

        result = sendRequest(xmlFileToString("/commands/domain_query_all_attachReservationInfo.xml"));
        // all domains in the result should contain the reservation flag
        ie.domainregistry.ieapi_domain_1.ResDataType responseValue = (ie.domainregistry.ieapi_domain_1.ResDataType) getResultDataValueFromString(result);
        Assert.assertTrue(responseValue.getDomain().size() > 0, "at least one domain should be returned");
        
        boolean falseValueFound = false;
        boolean trueValueFound = false;
        
        for (ResDomainType d: responseValue.getDomain()) {
        	Assert.assertNotNull(d.isReservationPending(), "reservationPending flag should be set");
        	falseValueFound |= !d.isReservationPending();
        	trueValueFound |= d.isReservationPending();
        }
        
        Assert.assertTrue(falseValueFound, "false value found");
        Assert.assertTrue(trueValueFound, "true value found");
    }
    

    private void compareQueryData(String msg, String result, String expectedResultPath) {
        ie.domainregistry.ieapi_domain_1.ResDataType responseValue = null;
        ie.domainregistry.ieapi_domain_1.ResDataType exampleValue = null;
        
    	responseValue = (ie.domainregistry.ieapi_domain_1.ResDataType) getResultDataValueFromString(result);
        exampleValue = (ie.domainregistry.ieapi_domain_1.ResDataType) getResultDataValueFromFile(expectedResultPath);
        compareQueryData(responseValue, exampleValue, "all");
	}

    @Test
    public void domainQueryAllWithContact() {
    	loginIDL2();

        result = sendRequest(xmlFileToString("/commands/domain_query_all_contact.xml"));
        assertResultMatch("domain query all contact result ok", result, "/commands/domain_query_all_contact_response.xml");
        compareQueryData("all with contact", result, "/commands/domain_query_all_contact_response.xml");
    	
    }
    
    @Test
    public void domainQueryAllWithCOntactType() {
        loginIDL2();   
        
        testWith("/commands/domain_query_all_contact_contacttype_1.xml", "/commands/domain_query_all_contact_contacttype_1_response.xml", "all with contact and contact type");
    }

    private void testWith(String request, String expectedResponsePath, String message) {
        result = sendRequest(xmlFileToString(request));
        assertResultMatch(message + ": result ok", result, expectedResponsePath);
        compareQueryData("all with contact and contact type",result, expectedResponsePath);		
	}

	@Test
    public void domainQueryAllWithContactType2() {
    	loginIDL2();

        result = sendRequest(xmlFileToString("/commands/domain_query_all_contact_contacttype_2.xml"));
        assertResultMatch("domain query all contact contacttype result ok", result, "/commands/domain_query_all_no_result_response.xml");
    }

    @Test
    public void domainQueryPageOutOfRange() {
        loginIDL2();

        result = sendRequest(xmlFileToString("/commands/domain_query_page_out_of_range.xml"));
        assertResultMatch("domain query - page out of range", result, "/commands/domain_query_page_out_of_range_response.xml");
    }
    
    @Test 
    public void domainQueryWithRenewalDate1() {
    	loginApiTest();
    	testWith("/commands/domain_query_with_renewal_date_1.xml", "/commands/domain_query_with_renewal_date_1_response.xml", "domain query - with renewal date range");
    }
    
    @Test 
    public void domainQueryWithRenewalDate2() {
    	loginApiTest();

        result = sendRequest(xmlFileToString("/commands/domain_query_with_renewal_date_2.xml"));
        assertResultMatch("domain query all - contacttype without contact", result, "/commands/domain_query_with_renewal_date_2_response.xml");
        compareQueryData("with renewal date", result, "/commands/domain_query_with_renewal_date_2_response.xml");
    }
    
    @Test
    public void domainQueryWithNrpStatus() {

        loginApiTest();
        
        result = sendRequest(xmlFileToString("/commands/domain_query_with_nrp_status.xml"));
        assertResultMatch("domain query trnasfer - subtype is mandatory", result, "/commands/domain_query_with_nrp_status_response.xml");
        compareQueryData("with nrp status", result, "/commands/domain_query_with_nrp_status_response.xml");
    }

    @Test
    public void domainQueryTransferTest() {
        ie.domainregistry.ieapi_domain_1.ResDataType responseValue = null;
        ie.domainregistry.ieapi_domain_1.ResDataType exampleValue = null;

        loginACB865();

        result = sendRequest(xmlFileToString("/commands/domain_query_with_transfer_outbound.xml"));
        assertResultMatch("domain query transfer result ok", result, "/commands/domain_query_with_transfer_outbound_response.xml");
        responseValue = (ie.domainregistry.ieapi_domain_1.ResDataType) getResultDataValueFromString(result);
        exampleValue = (ie.domainregistry.ieapi_domain_1.ResDataType) getResultDataValueFromFile("/commands/domain_query_with_transfer_outbound_response.xml");
        compareQueryData(responseValue, exampleValue, "with transfer type outbound");

        result = sendRequest(xmlFileToString("/commands/domain_query_with_transfer_inbound.xml"));
        assertResultMatch("domain query transfer result ok", result, "/commands/domain_query_with_transfer_inbound_response.xml");
        responseValue = (ie.domainregistry.ieapi_domain_1.ResDataType) getResultDataValueFromString(result);
        exampleValue = (ie.domainregistry.ieapi_domain_1.ResDataType) getResultDataValueFromFile("/commands/domain_query_with_transfer_inbound_response.xml");
        compareQueryData(responseValue, exampleValue, "with transfer type inbound");

        result = sendRequest(xmlFileToString("/commands/domain_query_with_transfer_date.xml"));
        assertResultMatch("domain query transfer result ok", result, "/commands/response_ok_no_result.xml");

        logout();
    }

    private void compareQueryData(ie.domainregistry.ieapi_domain_1.ResDataType responseValue, ie.domainregistry.ieapi_domain_1.ResDataType exampleValue, String queryType) {
        assertEquals(responseValue.getPage(), exampleValue.getPage(), "domain query " + queryType + " result data : page");
        assertEquals(responseValue.getTotalPages(), exampleValue.getTotalPages(), "domain query " + queryType + " result data : total pages");
        assertSameDomainNames(responseValue.getDomain(), exampleValue.getDomain(), queryType);
        //assertEquals(responseValue.getDomain().size(), exampleValue.getDomain().size(), "domain query " + queryType + " result data : domain count");
    }

	private void assertSameDomainNames(List<ResDomainType> actualList, List<ResDomainType> expectedList, String queryType) {
		Set<String> actualSet = domainsAsSet(actualList);
		Set<String> expectedSet = domainsAsSet(expectedList);
		
		if (actualSet.containsAll(expectedSet) && expectedSet.containsAll(actualSet))
			return;
		fail("domain query " + queryType + " result data: domains. Expected " + expectedSet + ", got " + actualSet);
	}

	private Set<String> domainsAsSet(List<ResDomainType> actualList) {
		Set<String> res = new HashSet<String>();
		for (ResDomainType t: actualList) {
			res.add(t.getName());
		}
		return res;
	}

}
