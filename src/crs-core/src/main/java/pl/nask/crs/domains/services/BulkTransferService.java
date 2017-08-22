package pl.nask.crs.domains.services;

import java.util.List;

import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface BulkTransferService {

	long createBulkTransferProcess(long losingAccount, long gainingAccount, String remarks);

	void addDomains(long bulkTransferId, List<String> domainNames) throws BulkTransferValidationException;

	List<BulkTransferRequest> findTransfers();

	BulkTransferRequest getTransferRequest(long id);

	void removeDomain(long id, String domainName) throws BulkTransferValidationException;

	void closeTransferRequest(long id, String hostmasterHandle) throws BulkTransferValidationException;

	void forceCloseTransferRequest(long id, String hostmasterHandle);

	void transferAll(AuthenticatedUser user, long id, String hostmasterHandle) throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException;

	void transferValid(AuthenticatedUser user, long id, String hostmasterHandle) throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException;

}
