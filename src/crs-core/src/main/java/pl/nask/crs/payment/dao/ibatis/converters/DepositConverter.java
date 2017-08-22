package pl.nask.crs.payment.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.payment.dao.ibatis.objects.InternalDeposit;
import pl.nask.crs.payment.Deposit;

/**
 * @author: Marcin Tkaczyk
 */
public class DepositConverter extends AbstractConverter<InternalDeposit, Deposit> {

    protected Deposit _to(InternalDeposit internalDeposit) {
        return new Deposit(
                internalDeposit.getNicHandleId(),
                internalDeposit.getNicHandleName(),
                internalDeposit.getTransactionDate(),
                internalDeposit.getOpenBal(),
                internalDeposit.getCloseBal(),
                internalDeposit.getTransactionAmount(),
                internalDeposit.getTransactionType(),
                internalDeposit.getOrderId(),
                internalDeposit.getCorrectorNH(),
                internalDeposit.getRemark()
        ); 
    }

    protected InternalDeposit _from(Deposit deposit) {
        return new InternalDeposit(
                deposit.getNicHandleId(),
                deposit.getTransactionDate(),
                deposit.getOpenBal(),
                deposit.getCloseBal(),
                deposit.getTransactionAmount(),
                deposit.getTransactionType(),
                deposit.getOrderId(),
                deposit.getCorrectorNH(),
                deposit.getRemark()
        );
    }
}
