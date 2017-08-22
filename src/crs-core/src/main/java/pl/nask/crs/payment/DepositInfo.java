package pl.nask.crs.payment;

import pl.nask.crs.commons.MoneyUtils;
import java.math.BigDecimal;

public class DepositInfo extends Deposit {

	private BigDecimal reservedFunds;

	public DepositInfo(Deposit deposit, BigDecimal reservedFunds) {
		super(deposit.getNicHandleId(),
                deposit.getNicHandleName(),
				deposit.getTransactionDate(),
				deposit.getOpenBal(),
				deposit.getCloseBal(),
                deposit.getTransactionAmount(),
                deposit.getTransactionType(),
				deposit.getOrderId(),
                deposit.getCorrectorNH(),
                deposit.getRemark());
		this.reservedFunds = reservedFunds;
	}
	
    public BigDecimal getCloseBalIncReservaions() {
		return MoneyUtils.substract(BigDecimal.valueOf(getCloseBal()), reservedFunds);
	}
    
    public BigDecimal getReservedFunds() {
		return reservedFunds;
	}
}
