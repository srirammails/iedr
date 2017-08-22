package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.dsm.events.CreateBillableDomainRegistrar;
import pl.nask.crs.domains.dsm.events.CreateGIBODomain;
import pl.nask.crs.domains.dsm.events.EnterVoluntaryNRP;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.history.HistoricalObject;

public class DomainStateMachineTest extends AbstractContextAwareTest {
	private String domainName = "dsm0.ie";
	
	private DsmEvent validEvent = new CreateBillableDomainRegistrar(new Date());
	private DsmEvent invalidEvent = EnterVoluntaryNRP.getInstance();
	
	@Autowired
	DomainStateMachine dsm;	
	
    @Autowired
    private DomainDAO domainDao;
    
    @Autowired
    private HistoricalDomainDAO historicalDomainDao;
    OpInfo opInfo = new OpInfo("test");
	
    @Test
	public void testValidateEventWithValidName() {
		boolean res = dsm.validateEvent(domainName, validEvent.getName());
		AssertJUnit.assertTrue("Event is valid", res);
	}
	
    @Test
	public void testValidateEventWithInvalidName() {
		boolean res = dsm.validateEvent(domainName, invalidEvent.getName());
		AssertJUnit.assertFalse("Event is valid", res);
	}
	
    @Test
	public void testGetValidEventNames() {
		List<String> eventNames = dsm.getValidEventNames(domainName);
		AssertJUnit.assertEquals("Number of valid events for domain in DSM_State==0", 5, eventNames.size());
		AssertJUnit.assertTrue(DsmEventName.CreateBillableDomainRegistrar.name(), eventNames.contains(DsmEventName.CreateBillableDomainRegistrar.name()));
	}
	
    @Test
	public void testHandleValidEvent() {
		Domain domain = domainDao.get(domainName);
		AssertJUnit.assertTrue(domain.getDsmState().getId() == 0);
		// this will change the domain state
		dsm.handleEvent(null, domainName, validEvent, opInfo);
		
		domain = domainDao.get(domainName);
		AssertJUnit.assertTrue(domain.getDsmState().getId() != 0);
	}
    
    @Test
	public void testCheckDsmStateAfterEvent() {
		Domain domain = domainDao.get(domainName);
		DsmState state = domain.getDsmState();
		AssertJUnit.assertEquals(0, state.getId());
		AssertJUnit.assertEquals(CustomerType.NA, state.getCustomerType());
		AssertJUnit.assertEquals(DomainHolderType.NA, state.getDomainHolderType());
		AssertJUnit.assertEquals(NRPStatus.NA, state.getNRPStatus());
		AssertJUnit.assertEquals(RenewalMode.NA, state.getRenewalMode());
		Assert.assertNull(state.getWipoDispute());
		// this will change the domain state
		dsm.handleEvent(null, domainName, validEvent, opInfo);
		
		domain = domainDao.get(domainName);
		state = domain.getDsmState();
		AssertJUnit.assertEquals(17, state.getId());
		AssertJUnit.assertEquals(CustomerType.Registrar, state.getCustomerType());
		AssertJUnit.assertEquals(DomainHolderType.Billable, state.getDomainHolderType());
		AssertJUnit.assertEquals(NRPStatus.Active, state.getNRPStatus());
		AssertJUnit.assertEquals(RenewalMode.NoAutorenew, state.getRenewalMode());
		AssertJUnit.assertEquals(false, (boolean) state.getWipoDispute());		
	}
    
    @Test
	public void testCheckDsmStateInDomainHist() {
		dsm.handleEvent(null, domainName, validEvent, opInfo);	
		
		HistoricalDomainSearchCriteria crit = new HistoricalDomainSearchCriteria();
		crit.setDomainName(domainName);
		
		SearchResult<HistoricalObject<Domain>> res = historicalDomainDao.find(crit);
		AssertJUnit.assertEquals(1, res.getResults().size());
		AssertJUnit.assertEquals(0, res.getResults().get(0).getObject().getDsmState().getId());
	}
    
    @Test
    public void testHandleCreateDomainEvent() {
    	NewDomain domain = new NewDomain("dsm012345.ie", "holder", "clazz", "cat", "creator", 1L, "remark", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Nameserver>(), Period.fromYears(1));
    	dsm.handleEvent(null, domain, new CreateGIBODomain(new Date()), opInfo);
    	Domain d = domainDao.get("dsm012345.ie");
    	AssertJUnit.assertNotNull(d);
    }

    @Test
    public void test() {
        boolean valid = dsm.validateEvent("createCCDomain.ie", DsmEventName.TransferRequest);
        AssertJUnit.assertTrue(valid);
    }
}
