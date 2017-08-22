package pl.nask.crs.nichandle.service;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.compareNicHandleNotExactDates;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.Resource;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleIdDAO;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;

/**
 * @author Marianna Mysiorska
 */

public class NicHandleServiceDAOTest extends AbstractContextAwareTest {

    @Resource
    NicHandleService service;
    @Resource
    NicHandleSearchService searchService;
    @Resource
    NicHandleIdDAO nicHandleIdDAO;

    @Test
    public void alterStatusNicHandleTest() throws Exception{
        service.alterStatus("AA11-IEDR", NicHandle.NHStatus.Renew, "TEST-IEDR", null, "remark");
        NicHandle nicHandle = searchService.getNicHandle("AA11-IEDR");
        assertEquals(nicHandle.getStatus(),NicHandle.NHStatus.Renew);
    }

    @Test (expectedExceptions = NicHandleNotFoundException.class)
    public void alterStatusNicHandleNotExistsTest() throws Exception{
        service.alterStatus("NOT-EXISTS-IEDR", NicHandle.NHStatus.Renew, "TEST-IEDR", null, "remark");
    }

    @Test (expectedExceptions = EmptyRemarkException.class)
    public void alterStatusNicHandleEmptyRemarkTest() throws Exception{
        service.alterStatus("NOT-EXISTS-IEDR", NicHandle.NHStatus.Renew, "TEST-IEDR", null, "");
    }

    @Test (expectedExceptions = EmptyRemarkException.class)
    public void alterStatusNicHandleNullRemarkTest() throws Exception{
        service.alterStatus("NOT-EXISTS-IEDR", NicHandle.NHStatus.Renew, "TEST-IEDR", null, null);
    }

    @Test (expectedExceptions = NicHandleAssignedToDomainException.class)
    public void alterStatusNicHandleAssignedToDomainTest() throws Exception{
        service.alterStatus("ACB865-IEDR", NicHandle.NHStatus.Deleted, "TEST-IEDR", null, "remark");
    }

    @Test
    public void confirmNicHandleIsNotAssingedToAnyDomain() throws Exception{
        service.confirmNicHandleIsNotAssignedToAnyDomain("AA11-IEDR");
    }

    @Test (expectedExceptions = NicHandleAssignedToDomainException.class)
    public void confirmNicHandleIsAssingedToAnyDomain() throws Exception{
        service.confirmNicHandleIsNotAssignedToAnyDomain("AAB069-IEDR");
    }

    @Test
    public void createNicHandleTest() throws Exception{
        HashSet<String> phones = new HashSet<String>();
        HashSet<String> faxes = new HashSet<String>();
        phones.add("22");
        phones.add("33");
        faxes.add("333");
        NicHandle nicHandle = service.createNicHandle("name", "companyName", "email@aaa.aaa", "address", "", "Poland", 101L, "Irish Domains", phones, faxes, "nicHandleRemark", "TEST-IEDR", "222333444", true, null, true, true);
        NicHandle nicHandleDB = searchService.getNicHandle(nicHandle.getNicHandleId());
        nicHandle.setNicHandleRemark(nicHandleDB.getNicHandleRemark());
        compareNicHandleNotExactDates(nicHandle, nicHandleDB);
        nicHandleIdDAO.update(194694L);
    }

    @Test
    public void createNicHandleMinimalTest() throws Exception{
        NicHandle nicHandle = service.createNicHandle("name", "", "email@aaa.aaa", "address", "", "Poland", 101L, "Irish Domains", null, null, "nicHandleRemark", "TEST-IEDR", null, true, null, true, true);
        NicHandle nicHandleDB = searchService.getNicHandle(nicHandle.getNicHandleId());
        nicHandle.setNicHandleRemark(nicHandleDB.getNicHandleRemark());
        compareNicHandleNotExactDates(nicHandle, nicHandleDB);
        nicHandleIdDAO.update(194694L);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void createNicHandleNoNameTest() throws Exception{
        service.createNicHandle(null, "companyName", "email", "address", "county", "Poland", 101L, "Irish Domains", null, null, "nicHandleRemark", "TEST-IEDR", null, true, null, true, true);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void createNicHandleEmptyNameTest() throws Exception{
        service.createNicHandle("", "companyName", "email", "address", "county", "Poland", 101L, "Irish Domains", null, null, "nicHandleRemark", "TEST-IEDR", null, true, null, true, true);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void createNicHandleNoCompanyNameTest() throws Exception{
        service.createNicHandle("name", null, "email", "address", "county", "Poland", 101L, "Irish Domains", null, null, "nicHandleRemark", "TEST-IEDR", null, true, null, true, true);
    }

    @Test (expectedExceptions = EmptyRemarkException.class)
    public void createNicHandleNoRemarkTest() throws Exception{
        service.createNicHandle("name", "companyName", "email", "address", "county", "Poland", 101L, "Irish Domains", null, null, null, "TEST-IEDR", null, true, null, true, true);
    }

    @Test (expectedExceptions = EmptyRemarkException.class)
    public void createNicHandleEmptyRemarkTest() throws Exception{
        service.createNicHandle("name", "companyName", "email", "address", "county", "Poland", 101L, "Irish Domains", null, null, "", "TEST-IEDR", null, true, null, true, true);
    }

    @Test (expectedExceptions = AccountNotFoundException.class)
    public void createNicHandleNoAccountTest() throws Exception{
        service.createNicHandle("name", "companyName", "email@aa.aa", "address", "", "Poland", 123456789L, "Irish Domains", null, null, "remark", "TEST-IEDR", null, true, null, true, true);
    }

    @Test
    public void saveTest() throws Exception{
        NicHandle nicHandle = searchService.getNicHandle("AA11-IEDR");
        HashSet<String> faxes = new HashSet<String>();
        HashSet<String> phones = new HashSet<String>();
        faxes.add("fax 1");
        faxes.add("fax 2");
        faxes.add("fax 3");
        phones.add("phone 1");
        phones.add("phone 2");
        nicHandle.setAccount(new Account(104L));
        nicHandle.setAddress("new address");
        nicHandle.setCompanyName("new company name");
        nicHandle.setCountry("Poland");
        nicHandle.setCounty("");
        nicHandle.setEmail("new@email.ee");
        nicHandle.setFaxes(faxes);
        nicHandle.setName("new name");
        nicHandle.setPhones(phones);       
        service.save(nicHandle, "remark", "AA11-IEDR", null, null);
        NicHandle nicHandleDB = searchService.getNicHandle("AA11-IEDR");
        compareNicHandleNotExactDates(nicHandle, nicHandleDB);
    }
//    TODO: CRS-72
//    @Test
//    public void saveVatCategoryRegistrarTest() throws Exception {
//        String registrarNHId = "AA11-IEDR";
//        NicHandle nicHandle = searchService.getNicHandle(registrarNHId);
//        assertEquals("A", nicHandle.getVatCategory());
//
//        nicHandle.setVatCategory("S");
//        service.save(nicHandle, "hRemark", "AA11-IEDR", null);
//        NicHandle nicHandleDB = searchService.getNicHandle(registrarNHId);
//        assertEquals("S", nicHandleDB.getVatCategory());
//
//        nicHandle.setCountry("Czech Republic"); // VAT category: A
//        nicHandle.setCounty("N/A");
//        service.save(nicHandle, "hRemark", "AA11-IEDR", null);
//        nicHandleDB = searchService.getNicHandle(registrarNHId);
//        assertEquals("A", nicHandleDB.getVatCategory());
//    }

    @Test
    public void saveVatCategoryDirectTest() throws Exception {
        String directNHId = "ACB863-IEDR";
        NicHandle nicHandle = searchService.getNicHandle(directNHId);
        assertEquals("A", nicHandle.getVatCategory());

        nicHandle.setVatCategory("S");
        service.save(nicHandle, "hRemark", directNHId, null, directNHId);
        NicHandle nicHandleDB = searchService.getNicHandle(directNHId);
        assertEquals("S", nicHandleDB.getVatCategory());

        nicHandle.setCountry("Czech Republic");
        nicHandle.setCounty("N/A");
        service.save(nicHandle, "hRemark", directNHId, null, directNHId);
        nicHandleDB = searchService.getNicHandle(directNHId);
        assertEquals("B", nicHandleDB.getVatCategory());
    }

    @Test (expectedExceptions = NicHandleNotFoundException.class)
    public void saveNotExistsTest() throws Exception{
        Date dt = new Date();
        Timestamp tm = new Timestamp(dt.getTime());
        NicHandle nicHandle = new NicHandle("NOT-EXISTS", "name", new Account(101L), "company name", "address", null, null, "", "Poland", "email@aa.aa", NicHandle.NHStatus.Active, dt, dt, tm, true, "remark", "creator", null, null);
        service.save(nicHandle, "remark", "TEST-IEDR", null, null);
    }

    @Test (expectedExceptions = EmptyRemarkException.class)
    public void saveEmptyRemarkTest() throws Exception{
        NicHandle nicHandle = searchService.getNicHandle("AA11-IEDR");
        service.save(nicHandle, "", "TEST-IEDR", null, null);
    }

    @Test (expectedExceptions = EmptyRemarkException.class)
    public void saveNullRemarkTest() throws Exception{
        NicHandle nicHandle = searchService.getNicHandle("AA11-IEDR");
        service.save(nicHandle, null, "TEST-IEDR", null, null);
    }

    @Test (expectedExceptions = AccountNotFoundException.class)
    public void saveAccountDontExistsTest() throws Exception{
        NicHandle nicHandle = searchService.getNicHandle("AA11-IEDR");
        HashSet<String> faxes = new HashSet<String>();
        HashSet<String> phones = new HashSet<String>();
        nicHandle.setAccount(new Account(12345678L));
        nicHandle.setAddress("new address");
        nicHandle.setCompanyName("new company name");
        nicHandle.setCountry("Poland");
        nicHandle.setCounty("");
        nicHandle.setEmail("new@email.ee");
        nicHandle.setFaxes(faxes);
        nicHandle.setName("new name");
        nicHandle.setPhones(phones);
        service.save(nicHandle, "remark", "TEST-IEDR", null, null);
    }

    @Test (expectedExceptions = NicHandleIsAccountBillingContactException.class)
    public void saveNicHandleIsBillingContactTest() throws Exception{
        NicHandle nicHandle = searchService.getNicHandle("AAA22-IEDR");
        HashSet<String> faxes = new HashSet<String>();
        HashSet<String> phones = new HashSet<String>();
        nicHandle.setAccount(new Account(103L));
        nicHandle.setAddress("new address");
        nicHandle.setCompanyName("new company name");
        nicHandle.setCountry("Poland");
        nicHandle.setCounty("");
        nicHandle.setEmail("new@email.ee");
        nicHandle.setFaxes(faxes);
        nicHandle.setName("new name");
        nicHandle.setPhones(phones);
        service.save(nicHandle, "remark", "TEST-IEDR", null, null);
    }
}
