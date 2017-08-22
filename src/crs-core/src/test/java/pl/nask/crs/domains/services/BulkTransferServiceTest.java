package pl.nask.crs.domains.services;

import java.util.Arrays;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle.NHStatus;
import pl.nask.crs.nichandle.service.NicHandleService;

public class BulkTransferServiceTest extends AbstractContextAwareTest {
	@Autowired
	BulkTransferService service;
	
	@Autowired
	HistoricalDomainService domainHistService;
	
	@Autowired
	DomainService domainService;
	
	@Autowired
	NicHandleService nicHandleService;
	
	@Mocked
	EmailTemplateSenderImpl emailTemplateSender;
	
	private long gainingAccount = 101; 
	private long losingAccount = 113; // has only one domain: mhcb.ie
	private String domainName = "mhcb.ie";

	private String hostmaster = "IDL2-IEDR";
	
	@Test
	public void bulkTransferShouldLeaveTraceInDomainHist() throws Exception {
		// having
		prepareDomainsForTransfer();					
		LimitedSearchResult<HistoricalObject<Domain>> domainHist = domainHistService.findHistory(new HistoricalDomainSearchCriteria(domainName), 0, 1000, null);
		
		// when
		makeBulkTransferWithDomain(true);
		
		// than 3 hist entries will be created: one when the domain is prepared for the bulk transfer, than by DSM and at the end when setting tech contact
		LimitedSearchResult<HistoricalObject<Domain>> newDomainHist = domainHistService.findHistory(new HistoricalDomainSearchCriteria(domainName), 0, 1001, null);
		AssertJUnit.assertEquals(domainHist.getTotalResults() + 3, newDomainHist.getTotalResults());
	}

	private void prepareDomainsForTransfer() throws Exception {
		domainService.forceDSMState(Arrays.asList(domainName), 17, new OpInfo(hostmaster, null, "test")); // make sure the domain is in the right state
		nicHandleService.alterStatus("TDI2-IEDR", NHStatus.Active, hostmaster, hostmaster, "test");
	}

	private long makeBulkTransferWithDomain(boolean transferAll) throws Exception {
		long transferId = service.createBulkTransferProcess(losingAccount, gainingAccount, "new bulk transfer request");
		service.addDomains(transferId, Arrays.asList(domainName));
		if (transferAll) {
			service.transferAll(null, transferId, hostmaster);
		} else {
			service.transferValid(null, transferId, hostmaster);
		}
		
		return transferId;
	}
	
	@Test
	public void emailId84ShouldBeSendWhenClosingTransferRequest() throws Exception {
		// having
		prepareDomainsForTransfer();				
		long transferId = makeBulkTransferWithDomain(false);
		
		// expect
		expectationsForEmail84();
		
		// when
		service.closeTransferRequest(transferId , "nostmaster");
	}
	
	private void expectationsForEmail84() throws Exception {		
		new NonStrictExpectations() {{
			emailTemplateSender.sendEmail(EmailTemplateNamesEnum.BULK_TRANSFER_COMPLETED.getId(), withInstanceOf(EmailParameters.class));minTimes=1;
			forEachInvocation = new Object() {
				void validate(int templateId, EmailParameters params) {
					Assert.assertNotNull(params.getParameterValue(ParameterNameEnum.BILL_C_NAME.getName(), false));
				}
			};
		}};
	}

	@Test
	public void emailId84ShouldBeSendWhenForcedCloseOfTransferRequest() throws Exception {
		// having
		prepareDomainsForTransfer();				
		long transferId = makeBulkTransferWithDomain(false);
		
		// expect
		expectationsForEmail84();
		
		// when
		service.forceCloseTransferRequest(transferId , "nostmaster");
	}
	
	@Test
	public void emailId84ShouldBeSendWhenTransferringAllDomains() throws Exception {
		// having
		prepareDomainsForTransfer();				
		// expect
		expectationsForEmail84();
		
		// when
		long transferId = makeBulkTransferWithDomain(true);
	}

}
