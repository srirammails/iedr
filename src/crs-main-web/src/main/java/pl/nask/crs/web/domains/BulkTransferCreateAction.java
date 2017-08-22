package pl.nask.crs.web.domains;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class BulkTransferCreateAction extends AuthenticatedUserAwareAction {
	private final AccountSearchService accountSearchService;
	private final BulkTransferAppService bulkTransferAppService;
	
	private List<Account> accounts;

	private Long losingAccountId;
	private Long gainingAccountId;
	private String remarks;
	private long id;

	public BulkTransferCreateAction(AccountSearchService accountSearchService, BulkTransferAppService bulkTransferAppService) {
		this.accountSearchService = accountSearchService;
		this.bulkTransferAppService = bulkTransferAppService;
	}		
	
	public String create() {
		this.id = bulkTransferAppService.createBulkTransferProcess(getUser(), losingAccountId, gainingAccountId, remarks);
		return SUCCESS;
	}

	public Long getLosingAccountId() {
		return losingAccountId;
	}

	public void setLosingAccountId(Long losingAccountId) {
		this.losingAccountId = losingAccountId;
	}

	public Long getGainingAccountId() {
		return gainingAccountId;
	}

	public void setGainingAccountId(Long gainingAccountId) {
		this.gainingAccountId = gainingAccountId;
	}

	public List<Account> getAccounts() {
		if (accounts == null) {
			accounts = accountSearchService.getAccounts();			
		}
		return accounts;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public long getId() {
		return id;
	}
}
