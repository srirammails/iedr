package pl.nask.crs.web.domains;

import java.util.List;

import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class BulkTransferListAction extends AuthenticatedUserAwareAction {
	private final BulkTransferAppService service;
	private List<BulkTransferRequest> transfers;
	
	public BulkTransferListAction(BulkTransferAppService service) {
		super();
		this.service = service;
	}

	public List<BulkTransferRequest> getTransfers() {
		if (transfers == null) {
			transfers = service.findTransfers(getUser());
		}
		return transfers;
	}
	
}
