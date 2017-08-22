package pl.nask.crs.domains.dao;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.domains.transfer.BulkTransferRequest;

public interface BulkTransferDAO extends GenericDAO<BulkTransferRequest, Long> {

	long createBulkTransferProcess(long losingAccount, long gainingAccount, String remarks);

	void addDomainToTransfer(long bulkTransferId, String domain);

	void markDomainAsTransferred(long bulkTransferId, String name, String hostmasterHandle, Date date);

	void removeDomainFromTransfer(long id, String domainName);

	boolean isNotTransferred(long id, String domainName);

	void closeTransfer(long id, String hostmasterHandle, Date date);

	boolean hasUnsettledReservations(String name);

	List<String> findDomainNamesWithSameContactNotInTransfer(long bulkTransferId, String nicHandle);

	int countAssociatedDomainsAccounts(String nicHandle);
}
