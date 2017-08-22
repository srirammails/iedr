package pl.nask.crs.domains.transfer;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BulkTransferRequestTest {

	@Test(expectedExceptions=Exception.class)
	public void isFullyCompletedFailsIfTransferNotInitialized() {
		BulkTransferRequest req = new BulkTransferRequest();
		req.isFullyCompleted();
	}
	
	@Test
	public void isFullyCompletedShouldReturnTrueWhenAllDomainsAreTransferred() {
		BulkTransferRequest req = new BulkTransferRequest();
		List<TransferredDomain> requestedDomains = new ArrayList<TransferredDomain>();
		requestedDomains.add(new TransferredDomain("domain1.ie", new Date()));
		requestedDomains.add(new TransferredDomain("domain2.ie", new Date()));
		req.setRequestedDomains(requestedDomains );
		AssertJUnit.assertTrue("Transfer fully completed", req.isFullyCompleted());
	}
	
	@Test
	public void isFullyCompletedShouldReturnFalseWhenNotAllDomainsAreTransferred() {
		BulkTransferRequest req = new BulkTransferRequest();
		List<TransferredDomain> requestedDomains = new ArrayList<TransferredDomain>();
		requestedDomains.add(new TransferredDomain("domain1.ie", null));
		requestedDomains.add(new TransferredDomain("domain2.ie", new Date()));
		req.setRequestedDomains(requestedDomains );
		AssertJUnit.assertFalse("Transfer not fully completed", req.isFullyCompleted());
	}
	
	@Test
	public void isClosedShouldReturnFalseIfCompletionDateIsNotSet() {
		BulkTransferRequest req = new BulkTransferRequest();		
		AssertJUnit.assertFalse(req.isClosed());
	}
	
	@Test
	public void isClosedShouldReturnTrueIfCompletionDateIsSet() {
		BulkTransferRequest req = new BulkTransferRequest();
		req.setCompletionDate(new Date());
		AssertJUnit.assertTrue(req.isClosed());
	}
}

