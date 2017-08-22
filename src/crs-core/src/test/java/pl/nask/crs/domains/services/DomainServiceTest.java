package pl.nask.crs.domains.services;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.ContactUtils;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.BillingStatus;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.nameservers.IPAddress;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.history.HistoricalObject;

/**
 * @author Kasia Fulara
 * @author Marianna Mysiorska
 */
public class DomainServiceTest extends AbstractContextAwareTest {

    @Resource
    private DomainService domainService;
    @Resource
    private DomainDAO domainDAO;
    @Resource
    private HistoricalDomainDAO historicalDomainDAO;

    @Resource
    private Dictionary<String, BillingStatus> billingStatusFactory;

    @Test
    public void testSave() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        assertNotNull(domain);

        domain.setRemark("test");
        domainService.save(domain, new OpInfo("nichandle"));

        domain = domainDAO.get("castlebargolfclub.ie");
        Assert.assertTrue(domain.getRemark().contains("nichandle"));
    }

    @Test
    public void testChangeNameservers() throws Exception {
        String domainName = "theweb.ie";
        Domain domain = domainDAO.get(domainName);
        assertNotNull(domain);
        IPAddress ipaddress1 = new IPAddress("1.1.1.1");
        Nameserver nameserver1 = new Nameserver("name2", ipaddress1, 0);
        List<Nameserver> nameservers = new ArrayList<Nameserver>();
        nameservers.add(nameserver1);
        domain.setNameservers(nameservers);
        domain.setRemark("remark");
        domainService.save(domain, new OpInfo("GEORGE-IEDR"));
        Domain domain2 = domainDAO.get(domainName);
        assertEquals(domain2.getNameservers().size(), 1);
        assertTrue(domain2.getNameservers().get(0).getName().equals("name2"));
        assertTrue(domain2.getNameservers().get(0).getIpAddress().getAddress().equals("1.1.1.1"));
        SearchResult<HistoricalObject<Domain>> result = findHistory(domainName);
        Domain lastDomainHist = result.getResults().get(14).getObject();
        assertEquals(lastDomainHist.getNameservers().size(), 2);
        assertTrue(lastDomainHist.getNameservers().get(0).getName().equals("CSF14.SSLSITE.COM"));
        assertTrue(lastDomainHist.getNameservers().get(0).getIpAddress().getAddress().equals(""));
    }
    
    private SearchResult<HistoricalObject<Domain>> findHistory(String domainName) {
    	HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName(domainName);
        SearchResult<HistoricalObject<Domain>> result = historicalDomainDAO.find(criteria);
        return result;
    }
    

    @Test
    public void createDomain() throws Exception {
    	NewDomain domain = prepareDomain("createdDomain.ie", "APIT4-IEDR", "Natural Person", "Personal Name");
        domainService.create(null, domain, new OpInfo("APIT4-IEDR"));
        Domain dbDomain = domainDAO.get("createdDomain.ie");

        assertEquals("name", domain.getName(), dbDomain.getName());
        assertEquals("holder", domain.getHolder(), dbDomain.getHolder());
        assertEquals("holder class", domain.getHolderClass(), dbDomain.getHolderClass());
        assertEquals("holder category", domain.getHolderCategory(), dbDomain.getHolderCategory());
        assertEquals("reseller account", domain.getResellerAccountId(), dbDomain.getResellerAccount().getId());
        // remarks are being modified by underlying logic
//        assertEquals("remark", domain.getRemark(), dbDomain.getRemark());

        assertEquals("creator", domain.getCreator(), dbDomain.getCreator().getNicHandle());
        assertEquals("admin contacts", ContactUtils.stringsAsContacts(domain.getAdminContacts()), dbDomain.getAdminContacts());
        assertEquals("tech contacts", ContactUtils.stringsAsContacts(domain.getTechContacts()), dbDomain.getTechContacts());
        assertEquals("billing contacts", ContactUtils.stringsAsContacts(domain.getBillingContacts()), dbDomain.getBillingContacts());
        assertEquals("nameservers", domain.getNameservers(), dbDomain.getNameservers());
        assertEquals("dsm state", 23, dbDomain.getDsmState().getId());
    }

    private NewDomain prepareDomain(String domainName, String creatorNH, String holderClass, String holderCategory) {

        List<String> adminContacts = new ArrayList<String>();
        adminContacts.add("APIT1-IEDR");

        List<String> techContacts = new ArrayList<String>();
        techContacts.add("APIT1-IEDR");

        List<String> billingContacts = new ArrayList<String>();
        billingContacts.add(creatorNH);

        List<Nameserver> nsList = new ArrayList<Nameserver>();
        nsList.add(new Nameserver("dns.testowy.ie", new IPAddress("1.2.3"), 0));

        NewDomain domain = new NewDomain(domainName,
                "Test Holder",
                holderClass,
                holderCategory,
                creatorNH,
                666,
                "Domain create",
                techContacts,
                billingContacts,
                adminContacts,
                nsList, 
                Period.fromYears(1));
        return domain;
    }

    @Test(expectedExceptions = ClassCategoryPermissionException.class)
    public void createDomainWithInaccesibleCategory() throws Exception {
    	NewDomain domain = prepareDomain("domain.ie", "APIT1-IEDR", "Natural Person", "Personal Name");
        domainService.create(null, domain, new OpInfo("APIT1-IEDR"));
    }

    @Test(expectedExceptions = HolderCategoryNotExistException.class)
    public void createDomainWithNotExistingCategory() throws Exception {
    	NewDomain domain = prepareDomain("domain.ie", "APIT1-IEDR", "Natural Person", "Not existing category");
        domainService.create(null, domain, new OpInfo("APIT1-IEDR"));
    }

    @Test(expectedExceptions = HolderClassNotExistException.class)
    public void createDomainWithNotExistingClass() throws Exception {
    	NewDomain domain = prepareDomain("domain.ie", "APIT1-IEDR", "Not existing class", "Personal Name");
        domainService.create(null, domain, new OpInfo("APIT1-IEDR"));
    }

    @Test(expectedExceptions = ClassDontMatchCategoryException.class)
    public void createDomainWithClassNotMatchingCategory() throws Exception {
    	NewDomain domain = prepareDomain("domain.ie", "APIT1-IEDR", "Natural Person", "Personal Trading Name");
        domainService.create(null, domain, new OpInfo("APIT1-IEDR"));
    }
    
    @Test
    public void histRecordShouldBeCreatedWhenSavingDomainChanges() throws Exception {
    	String domainName = "castlebargolfclub.ie";
    	SearchResult<HistoricalObject<Domain>> history = findHistory(domainName);
    	// just to be sure: historical chngDate should be not less than domain's change date
    	
    	Domain domain = domainDAO.get(domainName);
        assertNotNull(domain);        
        domain.setRemark("test");                
        
        domainService.save(domain, new OpInfo("nichandle"));

        SearchResult<HistoricalObject<Domain>> newHistory = findHistory(domainName);
        Assert.assertTrue(history.getResults().size() < newHistory.getResults().size(), "New record should be created");
    }
}
