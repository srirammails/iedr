package pl.nask.crs.web.accounts;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.payment.exceptions.NotEnoughtDepositFundsException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class AccountDepositEditAction extends AuthenticatedUserAwareAction {
	private final PaymentAppService paymentAppService;
	private final AccountAppService accountAppService;	

	// action parameter
	private long accountId;

	// 
	private String depositId;	
	
	private String remark;
	private double correctionAmount;
    private double topUpAmount;

	public AccountDepositEditAction(PaymentAppService paymentAppService, AccountAppService accountAppService) {
		this.paymentAppService = paymentAppService;		
		this.accountAppService = accountAppService;
	}
	
	public String correct() throws Exception {
        try {
            paymentAppService.correctDeposit(getUser(), getDepositId(), MoneyUtils.getValueInLowestCurrencyUnit(correctionAmount), remark);
        } catch (NotEnoughtDepositFundsException e) {
            addActionError("The user does not have enough funds to deduct this amount.");
            return ERROR;
        }
        return SUCCESS;
	}

    public String topup() throws Exception {
        paymentAppService.depositFundsOffline(getUser(), getDepositId(), MoneyUtils.getValueInLowestCurrencyUnit(topUpAmount), remark);
        return SUCCESS;
    }

	private String getDepositId() throws AccessDeniedException, AccountNotFoundException {
		if (depositId == null) {
			Account account = accountAppService.get(getUser(), accountId);
			depositId = account.getBillingContact().getNicHandle();
		}
		
		return depositId;
	}

	public long getId() {
		return accountId;
	}
	
	public void setId(long accountId) {
		this.accountId = accountId;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public double getCorrectionAmount() {
		return correctionAmount;
	}
	
	public void setCorrectionAmount(double correctionAmount) {
		this.correctionAmount = correctionAmount;
	}

    public double getTopUpAmount() {
        return topUpAmount;
    }

    public void setTopUpAmount(double topUpAmount) {
        this.topUpAmount = topUpAmount;
    }
}

