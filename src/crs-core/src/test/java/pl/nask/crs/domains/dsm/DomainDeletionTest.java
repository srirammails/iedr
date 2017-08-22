package pl.nask.crs.domains.dsm;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.domains.services.HistoricalDomainService;
import pl.nask.crs.history.HistoricalObject;

public class DomainDeletionTest extends AbstractContextAwareTest {
	@Autowired
	private DomainStateMachine dsm;
	
	@Autowired
	private HistoricalDomainService hist;
	
	@Autowired
	private DomainSearchService domainSearchService;
	
	@Autowired
	private DomainService domainService;
	
	int finalDsmState = 387;
	String domainName = "castlebargolfclub.ie";

	OpInfo opInfo = new OpInfo("test");

	
	@Test
	public void domainDeletionShouldLeaveTraceInDomainHist() throws Exception {
		
		Domain domain = domainSearchService.getDomain(domainName);
		AssertJUnit.assertEquals(17, domain.getDsmState().getId());
		
		HistoricalDomainSearchCriteria crit = new HistoricalDomainSearchCriteria();
		crit.setDomainName(domainName);
		LimitedSearchResult<HistoricalObject<Domain>> history = hist.findHistory(crit, 0, 1000, null);
		AssertJUnit.assertFalse(containsDsmState(history.getResults(), finalDsmState));
		
		dsm.handleEvent(null, domainName, DsmEventName.RenewalDatePasses, opInfo);
		dsm.handleEvent(null, domainName, DsmEventName.SuspensionDatePasses, opInfo);
		dsm.handleEvent(null, domainName, DsmEventName.DeletionDatePasses, opInfo);
		
		domain = domainSearchService.getDomain(domainName);
		AssertJUnit.assertEquals(385, domain.getDsmState().getId());
		
		dsm.handleEvent(null, domainName, DsmEventName.DeletedDomainRemoval, opInfo);
		history = hist.findHistory(crit, 0, 1000, null);
		AssertJUnit.assertTrue(containsDsmState(history.getResults(), 385));
		AssertJUnit.assertTrue(containsDsmState(history.getResults(), finalDsmState));
	}
	
	@Test
	public void domainDeletionShouldLeaveTraceInDeletedDomainsReport() throws Exception {
		Domain domain = domainSearchService.getDomain(domainName);
		AssertJUnit.assertEquals(17, domain.getDsmState().getId());
		// for the sake of the test: set the renewal date so the counted deletion date is in the past but not more than one year.		
		domain.setRenewDate(DateUtils.getCurrDate(-90)); //90 days ago
		OpInfo opInfo = new OpInfo("test");
		domainService.save(domain, opInfo);
		
		assertDeletedDomainsContainDomainName(false);		
		
		dsm.handleEvent(null, domainName, DsmEventName.RenewalDatePasses, opInfo);
		assertDeletedDomainsContainDomainName(false);
		dsm.handleEvent(null, domainName, DsmEventName.SuspensionDatePasses, opInfo);
		assertDeletedDomainsContainDomainName(false);
		dsm.handleEvent(null, domainName, DsmEventName.DeletionDatePasses, opInfo);
		assertDeletedDomainsContainDomainName(false);		
		dsm.handleEvent(null, domainName, DsmEventName.DeletedDomainRemoval, opInfo);
		assertDeletedDomainsContainDomainName(true);
	}

	private void assertDeletedDomainsContainDomainName( boolean contains) {
		DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();

		LimitedSearchResult<DeletedDomain> deletedDomains = domainSearchService.findDeletedDomains(criteria , 0, 1000, null);
		boolean found = false;
		for (DeletedDomain d: deletedDomains.getResults()) {
			found = found || d.getDomainName().equalsIgnoreCase(domainName);
		}
		
		AssertJUnit.assertEquals(contains, found);
	}

	private boolean containsDsmState(List<HistoricalObject<Domain>> results, int stateId) {
		for (HistoricalObject<Domain> d: results) {
			if (d.getObject().getDsmState().getId() == stateId)
				return true;
		}
		return false;
	}

}
