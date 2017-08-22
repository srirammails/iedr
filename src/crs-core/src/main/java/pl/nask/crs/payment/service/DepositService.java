package pl.nask.crs.payment.service;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositInfo;
import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.payment.DepositTopUp;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.LimitsPair;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotEnoughtDepositFundsException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface DepositService {

	DepositInfo viewDeposit(String nicHandleId) throws DepositNotFoundException;

    /**
     * Create new deposit for nic handle
     *
     * @param nicHandleId deposit nic handle
     * @return new deposit
     * @throws IllegalStateException when deposit for given nic handle exists
     */
    DepositInfo initDeposit(String nicHandleId);
    
    LimitedSearchResult<DepositInfo> findDeposits(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<Deposit> findHistoricalDeposits(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<Deposit> findDepositWithHistory(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitsPair getDepositLimits();

    DepositInfo depositFunds(AuthenticatedUser user, String nicHandle, PaymentRequest request) throws PaymentException, ExportException;

    LimitedSearchResult<DepositTopUp> getTopUpHistory(String nicHandle, Date fromDate, Date toDate, long offset, long limit);

    /**
     * Manual correction of the deposit. This will create a transaction, which Order_Id will be the hostmaster remark, trancaction type will be 'manual, by {username}'.
     * 
     * @param nicHandle deposit ID to be corrected
     * @param amountInLowestCurrencyUnit the amount of which the deposit should be increased/decreased in lowest currency unit
     * @param username the name of the user who made the correction.
     * @param remark the remark supplied by the user who made the correction (will be used as order id)
     * @return
     * @throws NotEnoughtDepositFundsException 
     * @throws ExportException 
     * @throws NicHandleNotFoundException 
     */
	Deposit correctDeposit(String nicHandle, int amountInLowestCurrencyUnit, String username, String remark) throws NotEnoughtDepositFundsException, NicHandleNotFoundException, ExportException;

    Deposit depositFundsOffline(AuthenticatedUser user, String nicHandle, int amountInLowestCurrencyUnit, String username, String remark) throws NotEnoughtDepositFundsException, NicHandleNotFoundException, ExportException;

	Deposit reduceDeposit(String nicHandleId,
			double amountInStandardCurrencyUnit, String orderId,
			DepositTransactionType transType, String correctorNH, String remark)
			throws NotEnoughtDepositFundsException;
}
