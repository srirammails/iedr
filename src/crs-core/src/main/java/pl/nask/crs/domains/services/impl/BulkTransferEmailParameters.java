package pl.nask.crs.domains.services.impl;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;

public class BulkTransferEmailParameters extends MapBasedEmailParameters {
	
	private String username;
	private String gainingNH;
	private String losingNH;

	public BulkTransferEmailParameters(BulkTransferRequest request, String hostmasterHandle, AccountSearchService accountService, NicHandleSearchService nicHandleSearchService) {
		set(ParameterNameEnum.NIC, hostmasterHandle);
		
		NicHandle gaining = nhFor(accountService, nicHandleSearchService, request.getGainingAccount());
		NicHandle losing = nhFor(accountService, nicHandleSearchService, request.getLosingAccount());
		if (gaining != null) {
			set(ParameterNameEnum.BILL_C_NIC, gaining.getNicHandleId());
			set(ParameterNameEnum.BILL_C_NAME, gaining.getName());
			set(ParameterNameEnum.BILL_C_EMAIL, gaining.getEmail());
			set(ParameterNameEnum.GAINING_BILL_C_EMAIL, gaining.getEmail());
		} 

		if (losing != null) {
			set(ParameterNameEnum.LOSING_BILL_C_EMAIL, losing.getEmail());
			set(ParameterNameEnum.LOSING_BILL_C_NAME, losing.getName());
		}
		
		this.username = hostmasterHandle;
		this.gainingNH = gaining.getNicHandleId();
		this.losingNH = losing.getNicHandleId();
	}
	
	public String getLoggedInNicHandle() {
        return username;
    }
	
    public String getAccountRelatedNicHandle() {
        return gainingNH;
    }

	private NicHandle nhFor(AccountSearchService accountService, NicHandleSearchService nicHandleSearchService, long accountId) {
		Account a = accountService.getAccount(accountId);
		NicHandle nh;
		try {
			nh = nicHandleSearchService.getNicHandle(a.getBillingContact().getNicHandle());
			return nh;
		} catch (NicHandleNotFoundException e) {
			return null;
		}
	}

}
