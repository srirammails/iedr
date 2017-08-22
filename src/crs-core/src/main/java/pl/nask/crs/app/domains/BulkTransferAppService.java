package pl.nask.crs.app.domains;

import java.util.List;

import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.domains.services.BulkTransferException;
import pl.nask.crs.domains.services.BulkTransferValidationException;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface BulkTransferAppService {

	public long createBulkTransferProcess(AuthenticatedUser user, long losingAccount, long gainingAccount, String remarks);
	
	public void addDomains(AuthenticatedUser user, long bulkTransferId, List<String> domainNames) throws BulkTransferValidationException;
	
	public List<BulkTransferRequest> findTransfers(AuthenticatedUser user);

	public BulkTransferRequest getTransferRequest(AuthenticatedUser user, long id);

	public void removeDomain(AuthenticatedUser user, long id, String domainName) throws BulkTransferValidationException;

	public void closeTransferRequest(AuthenticatedUser user, long id) throws BulkTransferValidationException;
	public void forceCloseTransferRequest(AuthenticatedUser user, long id);

	public void transferAll(AuthenticatedUser user, long id) throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException;
	public void transferValid(AuthenticatedUser user, long id) throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException;
}
