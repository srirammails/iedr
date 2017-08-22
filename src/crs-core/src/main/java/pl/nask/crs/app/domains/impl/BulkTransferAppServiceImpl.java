package pl.nask.crs.app.domains.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.domains.services.BulkTransferException;
import pl.nask.crs.domains.services.BulkTransferService;
import pl.nask.crs.domains.services.BulkTransferValidationException;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class BulkTransferAppServiceImpl implements BulkTransferAppService {
	private BulkTransferService service; 
	
	public BulkTransferAppServiceImpl(BulkTransferService service) {
		this.service = service;
	}

	@Override
	@Transactional
	public long createBulkTransferProcess(AuthenticatedUser user, long losingAccount, long gainingAccount, String remarks) {
		return service.createBulkTransferProcess(losingAccount, gainingAccount, remarks);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addDomains(AuthenticatedUser user, long bulkTransferId, List<String> domainNames) throws BulkTransferValidationException {
		service.addDomains(bulkTransferId, domainNames);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<BulkTransferRequest> findTransfers(AuthenticatedUser user) {
		return service.findTransfers();
	}
	
	@Override
	@Transactional(readOnly=true)
	public BulkTransferRequest getTransferRequest(AuthenticatedUser user, long id) {
		return service.getTransferRequest(id);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
    public void removeDomain(AuthenticatedUser user, long id, String domainName) throws BulkTransferValidationException {
		service.removeDomain(id, domainName);
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public void closeTransferRequest(AuthenticatedUser user, long id) throws BulkTransferValidationException {
		service.closeTransferRequest(id, user.getUsername());		
	}
	
	@Override
	@Transactional
	public void forceCloseTransferRequest(AuthenticatedUser user, long id) {	
		service.forceCloseTransferRequest(id, user.getUsername());
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public void transferAll(AuthenticatedUser user, long id) throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException {
		service.transferAll(user, id, user.getUsername());
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public void transferValid(AuthenticatedUser user, long id) throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException {
		service.transferValid(user, id, user.getUsername());
	}
}
