package pl.nask.crs.domains.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.domains.dao.BulkTransferDAO;
import pl.nask.crs.domains.transfer.BulkTransferRequest;

public class BulkTransferIBatisDAO extends GenericIBatisDAO<BulkTransferRequest, Long> implements BulkTransferDAO {

	public BulkTransferIBatisDAO() {
		setFindQueryId("bulkTransfer.findTransfers");
		setGetQueryId("bulkTransfer.getTransfer");
		setLockQueryId("bulkTransfer.lock");
	}
	
	@Override
	public long createBulkTransferProcess(long losingAccount,
			long gainingAccount, String remarks) {
		BulkTransferRequest req = new BulkTransferRequest(losingAccount, gainingAccount, remarks);
		performInsert("bulkTransfer.create", req);
		return req.getId();
	}

	@Override
	public void addDomainToTransfer(long bulkTransferId, String domain) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", bulkTransferId);
		map.put("domainName", domain);
		performInsert("bulkTransfer.addDomain", map);		
	}

	@Override
	public void markDomainAsTransferred(long bulkTransferId, String name, String hostmasterHandle, Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", bulkTransferId);
		map.put("domainName", name);
		map.put("transferDate", date);
		map.put("hostmasterHandle", hostmasterHandle);
		performUpdate("bulkTransfer.markAsTransferred", map);
	}
	
	@Override
	public void removeDomainFromTransfer(long id, String domainName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("domainName", domainName);
		performDelete("bulkTransfer.removeDomain", map);		
	}

	@Override
	public boolean isNotTransferred(long id, String domainName) {	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("domainName", domainName);
		return performQueryForObject("bulkTransfer.isNotTransferred", params) != null;
	}
	
	@Override
	public void closeTransfer(long id, String hostmasterHandle, Date date) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("completionDate", date);
		params.put("hostmasterHandle", hostmasterHandle);
		performUpdate("bulkTransfer.closeTransferRequest", params);
	}

	@Override
	public int countAssociatedDomainsAccounts(String nicHandle) {
		return (Integer) performQueryForObject("bulkTransfer.countAssociatedDomainsAccounts", nicHandle);
	}
	@Override
	public List<String> findDomainNamesWithSameContactNotInTransfer(long bulkTransferId, String nicHandle) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transferId", bulkTransferId);
		params.put("adminc", nicHandle);
		return performQueryForList("bulkTransfer.findDomainNamesWithSameContactNotInTransfer", params);
	}
	
	@Override
	public boolean hasUnsettledReservations(String domainName) {
		return 0 != (int) (Integer) performQueryForObject("bulkTransfer.countUnsettledReservations", domainName);
	}
}
