package pl.nask.crs.web.accounts;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.payment.DepositInfo;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class AccountDepositViewAction extends AuthenticatedUserAwareAction {
	private final PaymentAppService paymentAppService;
	private final AccountAppService accountAppService;	

	// action parameter
	private long accountId;

	private String depositId;	
	private DepositInfo deposit;
	
	public AccountDepositViewAction(PaymentAppService paymentAppService, AccountAppService accountAppService) {
		this.paymentAppService = paymentAppService;		
		this.accountAppService = accountAppService;
	}
	
	@Override
	public String input() throws Exception {
		refreshDepositInfo();
		return super.input();
	}	
	
	private void refreshDepositInfo() throws AccessDeniedException, AccountNotFoundException {
		try {
			this.deposit = paymentAppService.viewDeposit(getUser(), getDepositId());
		} catch (DepositNotFoundException e) {
			addActionError("No deposit found for the account's billing nichandle, correction/topup will create a new one");
		}
	}

	public String edit() throws Exception {
		return "edit";
	}
	
	private String getDepositId() throws AccessDeniedException, AccountNotFoundException {
		if (depositId == null) {
			Account account = accountAppService.get(getUser(), accountId);
			depositId = account.getBillingContact().getNicHandle();
		}
		
		return depositId;
	}

	public DepositInfo getDeposit() {
		return deposit;
	}
	
	public long getId() {
		return accountId;
	}
	
	public void setId(long accountId) {
		this.accountId = accountId;
	}
}

