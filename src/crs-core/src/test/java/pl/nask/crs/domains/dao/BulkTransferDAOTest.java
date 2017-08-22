package pl.nask.crs.domains.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.domains.transfer.TransferredDomain;

public class BulkTransferDAOTest extends AbstractContextAwareTest {
	@Resource
	BulkTransferDAO dao;

	private long gaining = 668;
	private long losing = 667;

	@Test
	public void testCreateTransferRequest() {
		Long transferId = dao.createBulkTransferProcess(losing, 668, "remarks");
		AssertJUnit.assertNotNull("bulkTransferId", transferId);
	}

	@Test
	public void testCreateTwoRequests() {
		long firstTransferId = dao.createBulkTransferProcess(losing, gaining, "firstTransfer");
		long secondTransferId = dao.createBulkTransferProcess(losing, gaining, "secondTransfer");
		Assert.assertFalse(firstTransferId == secondTransferId, "transfer ids must be different");
	}

	@Test
	public void testAddDomainsToOpenRequest() {
		// open a request first
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
		dao.addDomainToTransfer(transferId, "firstDomain");
		dao.addDomainToTransfer(transferId, "secondDomain");
	}

	@Test(expectedExceptions = Exception.class)
	public void testAddDomainTwice() {
		// open a request first
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
		dao.addDomainToTransfer(transferId, "firstDomain");
		BulkTransferRequest tr = dao.get(transferId);
		AssertJUnit.assertEquals("number of domains in transfer", 1, tr.getRequestedDomains().size());

		dao.addDomainToTransfer(transferId, "firstDomain");
		tr = dao.get(transferId);
		AssertJUnit.assertEquals("number of domains in transfer shouldn't change", 1, tr.getRequestedDomains().size());
	}

	@Test
	public void testMarkDomainAsTransferred() {
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
		dao.addDomainToTransfer(transferId, "firstDomain");
		dao.addDomainToTransfer(transferId, "secondDomain");

		dao.markDomainAsTransferred(transferId, "firstDomain", "test", new Date());
	}

	@Test
	public void testRemoveDomainFromTransfer() {
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
		dao.addDomainToTransfer(transferId, "firstDomain");
		dao.addDomainToTransfer(transferId, "secondDomain");

		dao.markDomainAsTransferred(transferId, "firstDomain", "test", new Date());

		dao.removeDomainFromTransfer(transferId, "secondDomain");

		BulkTransferRequest tr = dao.get(transferId);
		AssertJUnit.assertEquals(1, tr.getRequestedDomains().size());
	}

	@Test
	public void testRemoveDomainFromTransferFailed() {
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
		dao.addDomainToTransfer(transferId, "firstDomain");
		dao.addDomainToTransfer(transferId, "secondDomain");

		dao.markDomainAsTransferred(transferId, "firstDomain", "test", new Date());

		dao.removeDomainFromTransfer(transferId, "firstDomain");

		BulkTransferRequest tr = dao.get(transferId);
		AssertJUnit.assertEquals(2, tr.getRequestedDomains().size());
	}

	@Test
	public void testListTransfers() {
		dao.find(null);
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");

		SearchResult<BulkTransferRequest> res = dao.find(null);
		AssertJUnit.assertFalse("result not empty", res.getResults().isEmpty());
	}

	@Test
	public void testGetTransfer() {
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");

		dao.addDomainToTransfer(transferId, "firstDomain");
		dao.addDomainToTransfer(transferId, "secondDomain");

		dao.markDomainAsTransferred(transferId, "firstDomain", "test", new Date());

		BulkTransferRequest req = dao.get(transferId);

		AssertJUnit.assertEquals("id", transferId, req.getId());
		AssertJUnit.assertEquals("gainingAccount", gaining, req.getGainingAccount());
		AssertJUnit.assertEquals("loosingAccount", losing, req.getLosingAccount());
		AssertJUnit.assertEquals("Remarks", "remarks", req.getRemarks());
		AssertJUnit.assertNotNull("Domains not null", req.getRequestedDomains());
		AssertJUnit.assertEquals("number of domains", 2, req.getRequestedDomains().size());
	}

	@Test
	public void testCheckIfNotTransferred() {
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");

		dao.addDomainToTransfer(transferId, "firstDomain");
		dao.addDomainToTransfer(transferId, "secondDomain");

		dao.markDomainAsTransferred(transferId, "firstDomain", "test", new Date());

		AssertJUnit.assertFalse(dao.isNotTransferred(transferId, "firstDomain"));
		AssertJUnit.assertTrue(dao.isNotTransferred(transferId, "secondDomain"));
	}

	@Test
	public void testLockTransfer() {
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
		AssertJUnit.assertTrue(dao.lock(transferId));
		BulkTransferRequest tr1 = dao.get(transferId);
		AssertJUnit.assertEquals(transferId, tr1.getId());
	}

	@Test
	public void testCloseTransfer() {
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
		final Date closingDate = DateUtils.setMilliseconds(new Date(), 999);
		dao.closeTransfer(transferId, "test", closingDate);
		BulkTransferRequest tr = dao.get(transferId);
		AssertJUnit.assertTrue("transfer closed", tr.isClosed());
		AssertJUnit.assertEquals("closing date", DateUtils.truncate(closingDate, Calendar.SECOND), tr.getCompletionDate());

		// try again: second closing should take no effect
		dao.closeTransfer(transferId, "test", DateUtils.addDays(closingDate, 1));
		tr = dao.get(transferId);
		AssertJUnit.assertTrue("closing date didn't change", DateUtils.isSameDay(closingDate, tr.getCompletionDate()));
	}

	@Test
	public void closingDateShouldBeTruncated() {
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
		final Date closingDate = DateUtils.setMilliseconds(new Date(), 999);
		dao.closeTransfer(transferId, "test", closingDate);
		BulkTransferRequest tr = dao.get(transferId);
		AssertJUnit.assertEquals("closing date", DateUtils.truncate(closingDate, Calendar.SECOND), tr.getCompletionDate());
	}

	@Test
	public void transferDateShouldBeTruncated() {
		long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
		dao.addDomainToTransfer(transferId, "firstDomain");
		dao.addDomainToTransfer(transferId, "secondDomain");
		final Date transferDate = DateUtils.setMilliseconds(new Date(), 999);
		dao.markDomainAsTransferred(transferId, "firstDomain", "test", transferDate);
		final List<TransferredDomain> requestedDomains = dao.get(transferId).getRequestedDomains();
		TransferredDomain transferredDomain = null;
		for (TransferredDomain domain : requestedDomains) {
			if (domain.getDomainName().equals("firstDomain")) {
				transferredDomain = domain;
			}
		}
		AssertJUnit.assertEquals(DateUtils.truncate(transferDate, Calendar.SECOND), transferredDomain.getTransferDate());
	}
}


