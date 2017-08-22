package pl.nask.crs.nichandle.dao;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.compareNicHandle;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.compareNicHandleNotExactDates;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.createNHAA11;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.createNHnew;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandle.NHStatus;
import pl.nask.crs.nichandle.Vat;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleDAOTest extends AbstractContextAwareTest {

    @Resource
    NicHandleDAO nicHandleDAO;

//TODO: CRS-72
//    @Test
//    public void getNicHandle() {
//        NicHandle actualNicHandle = nicHandleDAO.get("AA11-IEDR");
//        NicHandle expectedNicHandle = createNHAA11();
//        compareNicHandleNotExactDates(actualNicHandle, expectedNicHandle);
//
//        actualNicHandle = nicHandleDAO.get("AAE359-IEDR");
//        expectedNicHandle = createNHAAE359();
//        compareNicHandleNotExactDates(actualNicHandle, expectedNicHandle);
//
//    }
    
    @Test
    public void getNicHandleAccountFlags() {
        NicHandle actualNicHandle = nicHandleDAO.get("AAE359-IEDR");
        assertEquals("nic handle", "AAE359-IEDR", actualNicHandle.getNicHandleId());
        assertEquals("nicHandle.account.id", 1L, actualNicHandle.getAccount().getId());
        // Feature #2373
        assertTrue("agreement signed", actualNicHandle.getAccount().isAgreementSigned());
        assertTrue("ticket edit", actualNicHandle.getAccount().isTicketEdit());
    }
    
    

    @Test
    public void getNullNicHandle() {
        NicHandle actualNicHandle = nicHandleDAO.get("ABCD");
        NicHandle expectedNicHandle = null;
        compareNicHandle(actualNicHandle, expectedNicHandle);
    }

    @Test
    public void findAllNicHandles() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        assertEquals(934, result.getResults().size());
    }

//TODO: CRS-72
//    @Test
//    public void findNicHandlesByNicHandle() {
//        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
//        criteria.setNicHandleId("AAE");
//        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
//        List<NicHandle> actualNicHandles = result.getResults();
//        List<NicHandle> expectedNicHandles = createNHAAE();
//        compareNicHandleList(actualNicHandles, expectedNicHandles);
//    }
//
//    @Test
//    public void findNicHandlesByNicHandleWithLimit() {
//        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
//        criteria.setNicHandleId("AAE");
//        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 1);
//        List<NicHandle> expectedNicHandles = createNHAAELimited();
//        compareNicHandleList(result.getResults(), expectedNicHandles);
//    }

    @Test
    public void findNicHandlesByName() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setName("Ad");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(5,actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByCompanyName() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setCompanyName("Ne");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(6, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByEmail() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setEmail("NHEmail0008");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(100, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByAccountNumber() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setAccountNumber(1l);
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(57, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByDomainName(){
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setDomainName("c");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(5, actualNicHandles.size());        
    }

    @Test
    public void findNicHandlesByAllCriteria() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId("A");
        criteria.setName("T");
        criteria.setCompanyName("I");
        criteria.setEmail("NHEmail");
        criteria.setAccountNumber(1l);
        criteria.setDomainName("a");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(0, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByAllCriteriaWithLimit() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId("A");
        criteria.setName("T");
        criteria.setCompanyName("I");
        criteria.setEmail("NHEmail");
        criteria.setAccountNumber(1l);
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 1);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(1, actualNicHandles.size());
    }

    @Test
    public void findNicHandleNotExists() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId("NOT-EXISTS-IEDR");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        assertEquals(0, result.getResults().size());
    }

    @Test
    public void findByAddressTest() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setAddress("address");
        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 100, null);
        assertEquals(4, result.getTotalResults());
        assertEquals(4, result.getResults().size());
    }

    @Test
    public void findByCountryTest() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setCountry("USA");
        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 100, null);
        assertEquals(18, result.getTotalResults());
        assertEquals(18, result.getResults().size());
    }

    @Test
    public void findByCountyTest() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setCounty("Texas");
        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 100, null);
        assertEquals(2, result.getTotalResults());
        assertEquals(2, result.getResults().size());
    }

    @Test
    public void findPhoneTest() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setPhone("333");
        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 100, null);
        assertEquals(1, result.getTotalResults());
        assertEquals(1, result.getResults().size());
    }


    @Test
    public void updateNicHandle() {
        HashSet<String> faxes = new HashSet<String>();
        HashSet<String> phones = new HashSet<String>();
        faxes.add("fax 1");
        phones.add("phone 1");
        NicHandle nicHandle = createNHAA11();
        nicHandle.setAddress("address 1");
        nicHandle.setCompanyName("company name 1");
        nicHandle.setCountry("country 1");
        nicHandle.setCounty("county 1");
        nicHandle.setEmail("email 1");
        nicHandle.setFaxes(faxes);
        nicHandle.setName("name 1");
        nicHandle.setPhones(phones);
        nicHandle.setVat(new Vat("new number"));
        nicHandle.setVatCategory("B");
        nicHandleDAO.update(nicHandle);
        NicHandle actualNicHandle = nicHandleDAO.get("AA11-IEDR");
        compareNicHandleNotExactDates(actualNicHandle, nicHandle);
    }

    @Test
    public void updateNicHandle2() {
        HashSet<String> faxes = new HashSet<String>();
        HashSet<String> phones = new HashSet<String>();
        faxes.add("fax 1");
        faxes.add("fax 2");
        faxes.add("fax 3");
        phones.add("phone 1");
        phones.add("phone 2");
        phones.add("phone 3");
        NicHandle nicHandle = createNHAA11();
        nicHandle.setAccount(new Account(104L));
        nicHandle.setAddress("address 1");
        nicHandle.setCompanyName("company name 1");
        nicHandle.setCountry("country 1");
        nicHandle.setCounty("county 1");
        nicHandle.setEmail("email 1");
        nicHandle.setFaxes(faxes);
        nicHandle.setName("name 1");
        nicHandle.setPhones(phones);
        nicHandle.setVat(new Vat(null));
        nicHandleDAO.update(nicHandle);
        NicHandle actualNicHandle = nicHandleDAO.get("AA11-IEDR");
        compareNicHandleNotExactDates(actualNicHandle, nicHandle);
    }

    @Test
    public void insertOrUpdatePayment() {
        String NHwithoutVatData = "AHM506-IEDR";
        String NHwithVatData = "AA11-IEDR";
        NicHandle nhWithoutVat = nicHandleDAO.get(NHwithoutVatData);
        assertNull(nhWithoutVat.getVat().getVatNo());
        nhWithoutVat.setVat(new Vat("new vat"));
        nicHandleDAO.update(nhWithoutVat);
        NicHandle nhWithoutVatAfterUpdate = nicHandleDAO.get(NHwithoutVatData);
        assertEquals("new vat", nhWithoutVatAfterUpdate.getVat().getVatNo());

        NicHandle nhWithVat = nicHandleDAO.get(NHwithVatData);
        assertNotNull(nhWithVat.getVat().getVatNo());
        nhWithVat.setVat(new Vat("new vat 2"));
        nicHandleDAO.update(nhWithVat);
        NicHandle nhWithVatAfterUpdate = nicHandleDAO.get(NHwithVatData);
        assertEquals("new vat 2", nhWithVatAfterUpdate.getVat().getVatNo());

    }

    @Test
    public void insertNicHandle() {
        NicHandle nicHandle = createNHnew();
        nicHandleDAO.create(nicHandle);
        NicHandle dbNicHandle = nicHandleDAO.get("MYMY-IEDR");
        compareNicHandle(dbNicHandle, nicHandle);
    }
//TODO: CRS-72
//    @Test
//    public void lockNicHandle() {
//        NicHandle nicHandle = nicHandleDAO.lock("AA11-IEDR");
//        compareNicHandleNotExactDates(nicHandle, createNHAA11());
//    }

    @Test
    public void lockNicHandleNotExists() {
        assertFalse(nicHandleDAO.lock("NOT-EXISTS-IEDR"));
    }

    @Test
    public void isContact(){
        assertTrue(nicHandleDAO.getNumberOfAssignedDomains("AAB069-IEDR") > 0);
    }

    @Test
    public void isNotContact() {
        assertTrue(nicHandleDAO.getNumberOfAssignedDomains("AA11-IEDR") == 0);
    }

    @Test
    public void getAccountStatus(){
        assertEquals("Active", nicHandleDAO.getAccountStatus(1));
    }

    @Test
    public void getAccountStatusNotExists(){
        assertNull(nicHandleDAO.getAccountStatus(1234567L));
    }
    
    @Test
    public void testRemoveNicHandle() {
    	String nh = "APIT1-IEDR";
    	assertNotNull(nicHandleDAO.get(nh));
    	nicHandleDAO.deleteById(nh);
    	assertNull(nicHandleDAO.get(nh));
    }
    
    @Test
    public void testRemoveDeletedNicHandleShouldNotAffectOtherNichandles() {
    	String nh = "APIT1-IEDR";
    	assertNotNull(nicHandleDAO.get(nh));
    	nicHandleDAO.deleteMarkedNichandles();
    	assertNotNull(nicHandleDAO.get(nh));
    }
    
    @Test
    public void testRemoveDeletedNicHandle() {
    	String nh = "APIT1-IEDR";
    	NicHandle dto = nicHandleDAO.get(nh);
    	dto.setStatus(NHStatus.Deleted);
    	nicHandleDAO.update(dto);
    	nicHandleDAO.deleteMarkedNichandles();
    	assertNull(nicHandleDAO.get(nh));
    }

    @Test
    public void testFindByCreator() throws Exception {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setCreator("AAU809-IEDR");
        SearchResult<NicHandle> searchResult = nicHandleDAO.find(criteria);
        assertEquals(2, searchResult.getResults().size());
    }
}