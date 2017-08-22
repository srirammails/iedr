package pl.nask.crs.domains.dao;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.DomainNotification;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.NotificationType;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.nameservers.IPAddress;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;
import pl.nask.crs.domains.search.TransferDirection;
import pl.nask.crs.domains.search.TransferedDomainSearchCriteria;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 * @author Marianna Mysiorska
 * @author Artur Gniadzik
 */
public class DomainDAOTest extends AbstractContextAwareTest {

    @Autowired
    DomainDAO domainDAO;

    @Test
    public void testGet() {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        assertNotNull(domain);
    }

    @Test
    public void testLock() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);
        //EqualsBuilder.reflectionEquals(expected, actual);
    }  

    @Test
    public void testUpdateDomainAdminContacts() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        List<Contact> contacts = actual.getAdminContacts();
        int size = contacts.size();
        contacts.add(new Contact("admin-nh"));
        domainDAO.update(actual);

        actual = domainDAO.get(domainName);
        assertEquals(actual.getAdminContacts().size(), size + 1);
    }

    @Test
    public void testUpdateDomainHolder() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        actual.setHolder("test holder");
        domainDAO.update(actual);

        actual = domainDAO.get(domainName);
        assertEquals(actual.getHolder(), "test holder");
    }

    @Test
    public void testUpdateHolderClass() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        actual.setHolderClass("Sole Trader");
        domainDAO.update(actual);

        actual = domainDAO.get(domainName);
        assertEquals(actual.getHolderClass(), "Sole Trader");
    }

    @Test
    public void testUpdateHolderCategory() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        actual.setHolderCategory("Personal Trading Name");
        domainDAO.update(actual);

        actual = domainDAO.get(domainName);
        assertEquals(actual.getHolderCategory(), "Personal Trading Name");
    }

    @Test
    public void testUpdateDNS() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        List<Nameserver> list = actual.getNameservers();
        list.set(0, new Nameserver("a.a.a", null, 1));
        domainDAO.update(actual);

        actual = domainDAO.get(domainName);
        assertEquals(actual.getNameserver("a.a.a").getName(), list.get(0).getName());
    }

    @Test
    public void testGetPreviousHolder() {
        SearchCriteria<Domain> criteria = new DomainSearchCriteria("Hilfiger.ie", "Tommy Hilfiger Licensing, Inc.");
        String holder = domainDAO.getPreviousHolder(criteria);
        assertNotNull(holder);
    }
    
    @Test
    public void testFindDomainNamesWithDomainHolder() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setExactDomainHolder("Brian Prendergast");
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 3, domains.size());
    }
    
    @Test
    public void testFindDomainNamesNoAutorenew() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew);
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 46, domains.size());
    }
    
    @Test
    public void testFindDomainNamesAutorenew() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainRenewalModes(RenewalMode.Autorenew);
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 5, domains.size());
    }
    
    @Test
    public void testFindDomainNamesAutorenewOrNoAutorenew() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew, RenewalMode.Autorenew);
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 51, domains.size());
    }
    
    @Test
    public void testFindDomainNamesWithWrongDomainHolder() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setExactDomainHolder("Brian Prendergas");
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 0, domains.size());
    }

    @Test
    public void testLimitedFindByEmptyCriteria() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(110, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByDomainName() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainName("castlebargolfclub.ie");
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(1, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByAccount() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setAccountId(122L);
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(6, found.getTotalResults());
    }

    @Test
    public void testFindByNotAccount() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNotAccountId(122L);
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(104, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByDomainHolder() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainHolder("astleba");
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(3, found.getTotalResults());
    }

    @Test
    public void testLimitedFindNicHandle() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNicHandle("AA");
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 5);
        assertEquals(5, found.getResults().size());
        assertEquals(22, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByMultipleContactType() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNicHandle("APIT1-IEDR");
        List<String> contactTypeList = new ArrayList<String>();
        contactTypeList.add("A");
        contactTypeList.add("T");
        criteria.setContactType(contactTypeList);
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 5);
        assertEquals(5, found.getResults().size());
        assertEquals(44, found.getTotalResults());
    }

    @Test
    public void testFindLimitedByAllCriteria(){
        DomainSearchCriteria allCriteria = new DomainSearchCriteria();
        List<String> billingStatusList = new ArrayList<String>();
        billingStatusList.add("Y");
        allCriteria.setAccountId(1L);
        allCriteria.setDomainHolder("T");
        allCriteria.setDomainName("T");
        allCriteria.setNicHandle("A");
        allCriteria.setRegistrationFrom(new Date(0L));
        allCriteria.setRegistrationTo(new Date());
        allCriteria.setRenewFrom(new Date(0L));
        allCriteria.setRenewTo(new Date(9999999999999L));
        LimitedSearchResult<Domain> found = domainDAO.find(allCriteria, 1, 3);
        assertEquals(3, found.getResults().size());
        assertEquals(7, found.getTotalResults());
    }

    @Test
    public void createDomainTest() {

        List<Contact> adminContacts = new ArrayList<Contact>();
        adminContacts.add(new Contact("APIT1-IEDR"));

        List<Contact> techContacts = new ArrayList<Contact>();
        techContacts.add(new Contact("APIT1-IEDR"));

        List<Contact> billingContacts = new ArrayList<Contact>();
        billingContacts.add(new Contact("IH4-IEDR"));

        List<Nameserver> nsList = new ArrayList<Nameserver>();
        nsList.add(new Nameserver("dns.testowy.ie", new IPAddress("1.2.3")));

        Date date = DateUtils.setMilliseconds(new Date(), 999);

        Domain domain = new Domain("createdDomain.ie",
                "Test Holder",
                "Sole Trader",
                "Personal Trading Name",
                new Contact("APIT1-IEDR"),
                new Account(666),
                date,
                date,
                "Domain create",
                date,
                false,
                techContacts,
                billingContacts,
                adminContacts,
                nsList);

        domainDAO.createDomain(domain);
        Domain dbDomain = domainDAO.get("createdDomain.ie");

        final Date expectedSqlDate = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
        final Date expectedSqlTimestamp = DateUtils.truncate(date, Calendar.SECOND);
        assertEquals(domain.getName(), dbDomain.getName());
        assertEquals(domain.getHolder(), dbDomain.getHolder());
        assertEquals(domain.getHolderClass(), dbDomain.getHolderClass());
        assertEquals(domain.getHolderCategory(), dbDomain.getHolderCategory());
        assertEquals(domain.getResellerAccount(), dbDomain.getResellerAccount());
        assertEquals(expectedSqlDate, dbDomain.getRegistrationDate());
        assertEquals(expectedSqlDate, dbDomain.getRenewDate());
        assertEquals(expectedSqlTimestamp, dbDomain.getChangeDate());
        assertEquals(domain.getRemark(), dbDomain.getRemark());
        assertEquals(domain.isClikPaid(), dbDomain.isClikPaid());
        assertEquals(0, domain.getDsmState().getId());
    }

    @Test
    public void saveChangeDateTest() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getSuspensionDate());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setChangeDate(crDate);
        domainDAO.update(d);

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getChangeDate());
        Assert.assertEquals(d.getChangeDate(), DateUtils.truncate(crDate, Calendar.SECOND));
    }

    @Test
    public void saveRenewDateTest() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getGiboRetryTimeout());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setRenewDate(crDate);
        domainDAO.update(d);

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getRenewDate());
        Assert.assertEquals(d.getRenewDate(), DateUtils.truncate(crDate, Calendar.DATE));
    }

    @Test
    public void saveGIBORetryTimeoutTest() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getGiboRetryTimeout());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setGiboRetryTimeout(crDate);
        domainDAO.update(d);

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getGiboRetryTimeout());
        Assert.assertEquals(d.getGiboRetryTimeout(), DateUtils.truncate(crDate, Calendar.SECOND));
    }
    
    @Test
    public void saveSuspensionDateTest() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getSuspensionDate());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setSuspensionDate(crDate);
        domainDAO.update(d);

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getSuspensionDate());
        Assert.assertEquals(d.getSuspensionDate(), DateUtils.truncate(crDate, Calendar.DATE));
    }
    
    @Test
    public void saveDeletionDateTest() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getDeletionDate());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setDeletionDate(crDate);
        domainDAO.update(d);

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getDeletionDate());
        Assert.assertEquals(d.getDeletionDate(), DateUtils.truncate(crDate, Calendar.DATE));
    }

    @Test
    public void saveChangeAuthCodeExpirationDate() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getAuthCodeExpirationDate());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setAuthCodeExpirationDate(crDate);
        domainDAO.update(d);

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getAuthCodeExpirationDate());
        Assert.assertEquals(d.getAuthCodeExpirationDate(), DateUtils.truncate(crDate, Calendar.DATE));
    }

    @Test
    public void findDomainForCurrentRenewalTest() {
        Date fromDate = new Date(105, 4, 6);//2005-05-06
        LimitedSearchResult<Domain> result = domainDAO.findDomainsForCurrentRenewal("APITEST-IEDR", fromDate, null, null, 0, 5, null);
        Assert.assertEquals(9, result.getTotalResults());
        Assert.assertEquals(5, result.getResults().size());
        assertInCorrectState(result);

        Date toDate = new Date(109, 4, 6);
        result = domainDAO.findDomainsForCurrentRenewal("APITEST-IEDR", null, toDate, null, 0, 5, null);
        Assert.assertEquals(7, result.getTotalResults());
        Assert.assertEquals(5, result.getResults().size());
        assertInCorrectState(result);

        fromDate = new Date(109, 4, 6);
        toDate = new Date(123, 4, 6);
        result = domainDAO.findDomainsForCurrentRenewal("APITEST-IEDR", fromDate, toDate, null, 0, 5, null);
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        assertInCorrectState(result);

        result = domainDAO.findDomainsForCurrentRenewal("APITEST-IEDR", null, null, "payDomain.ie", 0, 5, null);
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        assertInCorrectState(result);
    }

    private void assertInCorrectState(LimitedSearchResult<Domain> result) {
        for (Domain domain : result.getResults()) {
            DsmState state = domain.getDsmState();
            Assert.assertEquals(DomainHolderType.Billable, state.getDomainHolderType());
            Assert.assertEquals(RenewalMode.NoAutorenew, state.getRenewalMode());
            AssertJUnit.assertFalse(NRPStatus.PostTransactionAudit.equals(state.getNRPStatus()));
        }
    }

    @Test
    public void findDomainForFutureRenewalTest() {
        LimitedSearchResult<Domain> result = domainDAO.findDomainsForFutureRenewal("APITEST-IEDR", 5, null, 0, 5, null);
        Assert.assertEquals(2, result.getTotalResults());
        Assert.assertEquals(2, result.getResults().size());
        assertInCorrectState(result);

        result = domainDAO.findDomainsForFutureRenewal("APITEST-IEDR", 1, null, 0, 5, null);
        Assert.assertEquals(0, result.getTotalResults());
        Assert.assertEquals(0, result.getResults().size());
        assertInCorrectState(result);
    }

    @Test
    public void findDomainForFutureRenewalWithPendingReservationTest() {
        LimitedSearchResult<Domain> result = domainDAO.findDomainsForFutureRenewal("APITEST-IEDR", 8, null, 0, 5, null);
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        assertInCorrectState(result);
        Domain d = result.getResults().get(0);
        AssertJUnit.assertTrue(d.hasPendingCCReservations() || d.hasPendingADPReservations());
    }

    @Test
    public void findDomainWithNrpStatusActive() {
    	DomainSearchCriteria crit = new DomainSearchCriteria();
    	crit.setActive(true);
    	SearchResult<Domain> res = domainDAO.find(crit);
    	AssertJUnit.assertFalse("Empty result", res.getResults().isEmpty());
    	for (Domain d: res.getResults()) {
    		AssertJUnit.assertTrue(crit.getNrpStatuses().contains(d.getDsmState().getNRPStatus())); 
    	}
    }
    
    @Test
    public void findDomainWithNrpStatusNRP() {
    	DomainSearchCriteria crit = new DomainSearchCriteria();
    	crit.setActive(false);
    	SearchResult<Domain> res = domainDAO.find(crit);
    	AssertJUnit.assertFalse("Empty result", res.getResults().isEmpty());
    	for (Domain d: res.getResults()) {
    		AssertJUnit.assertTrue(crit.getNrpStatuses().contains(d.getDsmState().getNRPStatus())); 
    	}
    }
    
    @Test
    public void testZoneDoublePublish() {
    	domainDAO.zonePublish("castlebargolfclub.ie");
    	domainDAO.zonePublish("castlebargolfclub.ie");
    	Domain d = domainDAO.get("castlebargolfclub.ie");
    	AssertJUnit.assertFalse(d.isPublished());
    }
    
    @Test
    public void testZonePublishCommit() {
    	domainDAO.zonePublish("castlebargolfclub.ie");
    	domainDAO.zonePublish("castlebargolfclub.ie");
    	domainDAO.zoneCommit();
    	Domain d = domainDAO.get("castlebargolfclub.ie");
    	AssertJUnit.assertTrue(d.isPublished());
    }
    
    @Test
    public void testZoneUnpublish() {
    	domainDAO.zonePublish("castlebargolfclub.ie");
    	domainDAO.zoneCommit();
    	domainDAO.zoneUnpublish("castlebargolfclub.ie");
    	Domain d = domainDAO.get("castlebargolfclub.ie");
    	AssertJUnit.assertFalse(d.isPublished());
    }

    @Test
    public void findBySuspendedDateTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setSuspFrom(new Date(105, 4, 6));//2005-05-06
        criteria.setSuspTo(new Date(108, 4, 6));
        LimitedSearchResult<Domain> result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(1, result.getTotalResults());

        criteria = new DomainSearchCriteria();
        criteria.setSuspFrom(new Date(108, 4, 6));
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(0, result.getTotalResults());

        criteria = new DomainSearchCriteria();
        criteria.setSuspTo(new Date(105, 4, 6));//2005-05-06
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(0, result.getTotalResults());
    }

    @Test
    public void findByDeletedDateTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDelFrom(new Date(105, 4, 6));//2005-05-06
        criteria.setDelTo(new Date(108, 4, 6));
        LimitedSearchResult<Domain> result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(1, result.getTotalResults());

        criteria = new DomainSearchCriteria();
        criteria.setDelFrom(new Date(108, 4, 6));
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(0, result.getTotalResults());

        criteria = new DomainSearchCriteria();
        criteria.setDelTo(new Date(105, 4, 6));//2005-05-06
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(0, result.getTotalResults());
    }

    @Test
    public void findDeletedDomainsTest() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        LimitedSearchResult<DeletedDomain> result = domainDAO.findDeletedDomains(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 4);
        Assert.assertEquals(result.getResults().size(), 4);

        criteria = new DeletedDomainSearchCriteria();
        criteria.setAccountId(194L);
        result = domainDAO.findDeletedDomains(criteria, 0, 10, null);
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        Assert.assertEquals("Co. Antrim", result.getResults().get(0).getCounty());
        Assert.assertEquals("Northern Ireland", result.getResults().get(0).getCountry());

        criteria = new DeletedDomainSearchCriteria();
        criteria.setAccountId(666L);
        result = domainDAO.findDeletedDomains(criteria, 0, 10, null);
        Assert.assertEquals(3, result.getTotalResults());
        Assert.assertEquals(3, result.getResults().size());

        criteria = new DeletedDomainSearchCriteria();
        criteria.setRenewalFrom(new Date(110, 0, 1));
        result = domainDAO.findDeletedDomains(criteria, 0, 10, null);
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());

        criteria = new DeletedDomainSearchCriteria();
        criteria.setDeletionFrom(new Date(110, 0, 1));
        criteria.setDeletionTo(new Date(120, 0, 1));
        result = domainDAO.findDeletedDomains(criteria, 0, 10, null);
        Assert.assertEquals(4, result.getTotalResults());
        Assert.assertEquals(4, result.getResults().size());
    }

    @Test
    public void findByTransferDateTest() {
    	Date now = new Date();
    	Date futureDate = org.apache.commons.lang.time.DateUtils.addYears(now, 2);
    	Date passedDate = org.apache.commons.lang.time.DateUtils.addYears(now, -2);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setTransferFrom(passedDate);
        criteria.setTransferTo(futureDate);
        LimitedSearchResult<Domain> result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 3);

        criteria = new DomainSearchCriteria();
        criteria.setTransferFrom(futureDate);
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 0);
        
        criteria = new DomainSearchCriteria();
        criteria.setTransferTo(passedDate);
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 0);
    }

    @Test
    public void  findAllTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setAccountId(122L);
        List<Domain> found = domainDAO.findAll(criteria, null);
        assertEquals(6, found.size());
    }
// TODO: CRS-72
//    @Test
//    public void getDsmStatesTest() {
//        List<Integer> states = domainDAO.getDsmStates();
//        Assert.assertEquals(115, states.size());
//    }

    @Test
    public void deleteDomainTest() {
        Domain domain = domainDAO.get("autocreated.ie");
        assertNotNull(domain);
        domainDAO.deleteById(domain.getName(), domain.getChangeDate());
        domain = domainDAO.get("autocreated.ie");
        assertNull(domain);
    }
    
    @Test
    public void testDomainExists() {
    	AssertJUnit.assertTrue(domainDAO.exists("suka.ie"));
    	AssertJUnit.assertFalse(domainDAO.exists("someFakedDomainName.ie"));
    }

    @Test
    public void notificationTest() {
        final Date currentDate = DateUtils.setMilliseconds(new Date(), 999);
        DomainNotification notification = new DomainNotification("domain.ie", NotificationType.RENEWAL, 30, DateUtils.addDays(currentDate, 1));
        DomainNotification expiredNotification = new DomainNotification("domain2.ie", NotificationType.RENEWAL, 60, DateUtils.addDays(currentDate, -1));
        domainDAO.createNotification(notification);
        domainDAO.createNotification(expiredNotification);
        List<DomainNotification> all = domainDAO.getAllNotifications();
        Assert.assertEquals(2, all.size());

        DomainNotification dbNotification = domainDAO.getDomainNotification("domain.ie", NotificationType.RENEWAL, 30);
        Assert.assertNotNull(dbNotification);
        Assert.assertEquals(dbNotification.getExpirationDate(), DateUtils.truncate(DateUtils.addDays(currentDate, 1), Calendar.DATE));
        DomainNotification dbExpiredNotification = domainDAO.getDomainNotification("domain2.ie", NotificationType.RENEWAL, 60);
        Assert.assertNull(dbExpiredNotification);
    }

    @Test
    public void findByHolderTypeCharity() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainHolderTypes(DomainHolderType.Charity);
        SearchResult<Domain> result = domainDAO.find(criteria);
        Assert.assertEquals(3, result.getResults().size());
        for (Domain domain : result.getResults()) {
            Assert.assertEquals(DomainHolderType.Charity, domain.getDsmState().getDomainHolderType());
        }
    }
    
    @Test
    public void findByHolderTypeBillable() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        SearchResult<Domain> result = domainDAO.find(criteria);
        Assert.assertEquals(49, result.getResults().size());
        for (Domain domain : result.getResults()) {
            Assert.assertEquals(DomainHolderType.Billable, domain.getDsmState().getDomainHolderType());
        }
    }

    @Test
    public void findByHolderTypeCharityOrBillable() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();

        criteria.setDomainHolderTypes(DomainHolderType.Charity, DomainHolderType.Billable);
        SearchResult<Domain> result = domainDAO.find(criteria);
        Assert.assertEquals(52, result.getResults().size());
    }


    @Test
    public void fullFindTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        LimitedSearchResult<Domain> result = domainDAO.fullDomainFind(criteria, 0 ,200 ,null);
        Assert.assertEquals(110, result.getTotalResults());
        Assert.assertEquals(110, result.getResults().size());
    }

    @Test
    public void findByCustomerTypeTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setType(CustomerType.Direct);
        LimitedSearchResult<Domain> result = domainDAO.fullDomainFind(criteria, 0 ,200 ,null);
        Assert.assertEquals(17, result.getTotalResults());
        Assert.assertEquals(17, result.getResults().size());
    }

    @Test
    public void findByBillingNHTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setBillingNH("AAE553-IEDR");
        LimitedSearchResult<Domain> result = domainDAO.fullDomainFind(criteria, 0 ,200 ,null);
        Assert.assertEquals(9, result.getTotalResults());
        Assert.assertEquals(9, result.getResults().size());

    }

    @Test
    public void getNsReportTest() {
        LimitedSearchResult<NsReport> reports = domainDAO.getNsReport("APITEST-IEDR", null, 0, 10, null);
        Assert.assertEquals(38, reports.getTotalResults());
        Assert.assertEquals(10, reports.getResults().size());

        NsReportSearchCriteria criteria = new NsReportSearchCriteria();
        criteria.setDnsName("dns1.myhostns.com");
        reports = domainDAO.getNsReport("APITEST-IEDR", criteria, 0, 10, null);
        Assert.assertEquals(6, reports.getTotalResults());
        Assert.assertEquals(6, reports.getResults().size());

        criteria = new NsReportSearchCriteria();
        reports = domainDAO.getNsReport("AAU809-IEDR", criteria, 0, 10, null);
        Assert.assertEquals(6, reports.getTotalResults());
        Assert.assertEquals(6, reports.getResults().size());
    }

    @Test
    public void findTransferedDomainsTest() {
        TransferedDomainSearchCriteria criteria = new TransferedDomainSearchCriteria();
        LimitedSearchResult<Domain> transferedDomains = domainDAO.findTransferedDomains("ACB865-IEDR", criteria, 0, 10, Arrays.asList(new SortCriterion("domainName", true)));
        Assert.assertEquals(2, transferedDomains.getTotalResults());
        Assert.assertEquals(2, transferedDomains.getResults().size());
        criteria.setTransferDirection(TransferDirection.INBOUND);
        transferedDomains = domainDAO.findTransferedDomains("ACB865-IEDR", criteria, 0, 10, Arrays.asList(new SortCriterion("domainName", true)));
        Assert.assertEquals(1, transferedDomains.getTotalResults());
        Assert.assertEquals(1, transferedDomains.getResults().size());
        criteria.setTransferDirection(TransferDirection.OUTBOUND);
        transferedDomains = domainDAO.findTransferedDomains("ACB865-IEDR", criteria, 0, 10, Arrays.asList(new SortCriterion("domainName", true)));
        Assert.assertEquals(1, transferedDomains.getTotalResults());
        Assert.assertEquals(1, transferedDomains.getResults().size());
    }

    @Test
    public void findDomainByRenewalDate() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setRenewalDate(new Date(108, 7, 5));//2008-08-05
        List<Domain> domains = domainDAO.findAll(criteria, null);
        Assert.assertEquals(1, domains.size());
    }
    
    @Test
    public void createTransferRecordShouldNotFail() {
        domainDAO.createTransferRecord("castlbargolfclub.ie", new Date(), "NTG1-IEDR", "NH4-IEDR");
    }
    
    @Test
    public void creatingTransferRecordShouldAffectNumberOfTransferredDomains() {
        TransferedDomainSearchCriteria criteria = new TransferedDomainSearchCriteria();
        LimitedSearchResult<Domain> transferedDomains = domainDAO.findTransferedDomains("NTG1-IEDR", criteria, 0, 10, Arrays.asList(new SortCriterion("domainName", true)));
        long numberOfResults = transferedDomains.getTotalResults();

        final Date transferDate = DateUtils.setMilliseconds(new Date(), 999);
        domainDAO.createTransferRecord("castlebargolfclub.ie", transferDate, "NTG1-IEDR", "NH4-IEDR");

        transferedDomains = domainDAO.findTransferedDomains("NTG1-IEDR", criteria, 0, 10, Arrays.asList(new SortCriterion("domainName", true)));
        Assert.assertEquals(numberOfResults + 1, transferedDomains.getTotalResults());
    }
    
    /*
     * this checks, if one can search for a domain which has different billing and (admin/tech) contact.
     * ex: makingadifference.ie
     * billingC: AAG061-IEDR
     * adminC: ADM152-IEDR
     */
    @Test
    public void testFindDomainNamesForBillingContactWithOtherContactRequired() {
    	DomainSearchCriteria crit = new DomainSearchCriteria();
    	crit.setNicHandle("ADM152-IEDR");
    	crit.setBillingNH("AAG061-IEDR");
    	List<String> res = domainDAO.findDomainNames(crit, 0, 10);
    	assertResultContainsDomain(res, "makingadifference.ie");    	
    }
    
    /*
     * this checks, if one can search for a domain which has different contacts
     * ex: makingadifference.ie
     * billingC: AAG061-IEDR
     * adminC: ADM152-IEDR
     */
    @Test
    public void testFindDomainNamesSharing2Contacts() {
    	DomainSearchCriteria crit = new DomainSearchCriteria();
    	crit.setNicHandle("ADM152-IEDR");
    	crit.setSecondContact("AAG061-IEDR");
    	
    	List<String> res = domainDAO.findDomainNames(crit, 0, 10);
    	
    	String domainName = "makingadifference.ie";
		assertResultContainsDomain(res, domainName );
    	
    	// this must also work with the contact types set 
		crit.setContactType(Arrays.asList("A"));
		crit.setSecondContactType(Arrays.asList("B"));
		res = domainDAO.findDomainNames(crit, 0, 10);    	
		assertResultContainsDomain(res, domainName );
		
		crit.setSecondContactType(Arrays.asList("A"));
		res = domainDAO.findDomainNames(crit, 0, 10);  
		Assert.assertEquals(0, res.size());
    }
    
    // this is a regression test, see https://drotest4.nask.net.pl:3000/issues/11093
    @Test
    public void nullCollectionInSecondContactTypeShouldBeIgnored() {
    	DomainSearchCriteria crit = new DomainSearchCriteria();
    	// a mutable list is needed
    	crit.setSecondContactType(new ArrayList(Arrays.asList((String)null)));
    	// no exception should be thrown here
    	domainDAO.findDomainNames(crit, 0, 1);
    }
    
    @Test
    public void attachReservationFlagShouldBeHandledByFindDomains() {
    	DomainSearchCriteria criteria = new DomainSearchCriteria();
    	criteria.setAttachReservationInfo(false);
		SearchResult<Domain> result = domainDAO.find(criteria);
		int resultSize = result.getResults().size();
		
		// no domain should have the reservation flag set to true
		for (Domain d: result.getResults()) {
			AssertJUnit.assertFalse("No domain should have the pendingReservations field set to proper value", (d.hasPendingCCReservations() || d.hasPendingADPReservations()));
		}
		
		criteria.setAttachReservationInfo(true);
		result = domainDAO.find(criteria);
		
		Assert.assertEquals(resultSize, result.getResults().size(), "attachReservationInfo is not a discrimator and the query must return the same number of results regardless which value was set");
		// no domain should have the reservation flag set to true
		Set<String> domainsWithCCReservations = new HashSet<String>(){{
			add("createCCDomain.ie");
		}};
		Set<String> domainsWithADPReservations = new HashSet<String>(){{
			add("futuredomena3.ie");
		}};
		
		for (Domain d: result.getResults()) {
			if (d.hasPendingCCReservations()) {		
				domainsWithCCReservations.remove(d.getName());
				System.err.println(d.getName());
			} 
			if (d.hasPendingADPReservations()) {		
				domainsWithADPReservations.remove(d.getName());
				System.err.println(d.getName());
			}
		}
		
		AssertJUnit.assertTrue("There should be a domain with the pendingReservations set to true", domainsWithCCReservations.isEmpty() && domainsWithADPReservations.isEmpty());
    }

	private void assertResultContainsDomain(List<String> res, String domainName) {
		Assert.assertNotNull(res);
    	AssertJUnit.assertTrue("should get at least one result", res.size() > 0);
    	AssertJUnit.assertTrue("result should contain '" + domainName + "'", res.contains(domainName));
	}

    @Test
    public void getHolderForTicketTest() throws Exception {
        String holder = domainDAO.getDomainHolderForTicket(259912);
        Assert.assertEquals(holder, "Denise O Brien");
    }
}
