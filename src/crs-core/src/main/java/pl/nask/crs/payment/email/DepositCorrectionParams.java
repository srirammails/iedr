package pl.nask.crs.payment.email;

import java.math.BigDecimal;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.nichandle.NicHandle;

public class DepositCorrectionParams extends MapBasedEmailParameters {

	private NicHandle nicHandle;
	private String username;

	public DepositCorrectionParams(String orderId,	BigDecimal roudedAndScaledValue, NicHandle nicHandle, String description, String username) {
		set(ParameterNameEnum.ORDER_ID, orderId);
		set(ParameterNameEnum.BILL_C_EMAIL, nicHandle.getEmail());
		set(ParameterNameEnum.BILL_C_NAME, nicHandle.getName());
		set(ParameterNameEnum.BILL_C_CO_NAME, nicHandle.getCompanyName());
		set(ParameterNameEnum.BILL_C_NIC, nicHandle.getNicHandleId());
		set(ParameterNameEnum.TRANSACTION_VALUE, MoneyUtils.getRoudedAndScaledValue(roudedAndScaledValue, 2));
		set(ParameterNameEnum.DEPOSIT_ACCOUNT_CORRECTION_DESCRIPTION, description);
		this.username = username;
		this.nicHandle = nicHandle;
	}				

    public String getLoggedInNicHandle() {
        return username;
    }

    public String getAccountRelatedNicHandle() {
        return nicHandle.getNicHandleId();
    }

 
}
