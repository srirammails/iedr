package pl.nask.crs.payment.service;

import static pl.nask.crs.commons.MoneyUtils.getValueInLowestCurrencyUnit;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.comapareDeposit;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.Period;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositInfo;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotEnoughtDepositFundsException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DepositTest extends AbstractContextAwareTest {

    @Resource
    DepositService depositService;    
    
    @Resource 
    PaymentService paymentService;
    
    @Resource
    DomainDAO domainDAO;

    @Test
    public void viewDepositTest() throws Exception {
        DepositInfo deposit = depositService.viewDeposit("APITEST-IEDR");
        AssertJUnit.assertEquals("APITEST-IEDR", deposit.getNicHandleId());
        Assert.assertEquals(deposit.getOpenBal(), 7000, 0.0001, "open balance");
        AssertJUnit.assertEquals("close balance", 2594.0, deposit.getCloseBal(), 0.0001);
        AssertJUnit.assertEquals("reserved funds", BigDecimal.valueOf(206.38), deposit.getReservedFunds());
        AssertJUnit.assertEquals("close balance including reservations", BigDecimal.valueOf(2387.62), deposit.getCloseBalIncReservaions());
    }

    @Test
    public void initDepositOKTest() throws Exception {
        Deposit newDeposit = depositService.initDeposit("APIT4-IEDR");
        Deposit fromDB = depositService.viewDeposit("APIT4-IEDR");
        comapareDeposit(newDeposit, fromDB);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void initDepositExistingTest() {
        depositService.initDeposit("APITEST-IEDR");
    }

    @Test
    public void makeDepositCorrectionUp() throws Exception {    	
    	String depositId = "APIT5-IEDR";
    	DepositInfo deposit = depositService.viewDeposit(depositId);
    	AssertJUnit.assertNotNull(deposit);
    	int amount = 1051;
    	Deposit newDeposit = depositService.correctDeposit(depositId, amount, "APITEST-IEDR", "payment by a cheque");
    	AssertJUnit.assertEquals("Actual balance", getValueInLowestCurrencyUnit(deposit.getCloseBal()) + amount, getValueInLowestCurrencyUnit(newDeposit.getCloseBal()));
        AssertJUnit.assertEquals("transactionAmount", amount, getValueInLowestCurrencyUnit(newDeposit.getTransactionAmount()));
        AssertJUnit.assertEquals("transaction type", DepositTransactionType.MANUAL, newDeposit.getTransactionType());
        AssertJUnit.assertNotNull("orderId", newDeposit.getOrderId());
        AssertJUnit.assertEquals("corrector nh", "APITEST-IEDR", newDeposit.getCorrectorNH());
        AssertJUnit.assertEquals("remark", "payment by a cheque", newDeposit.getRemark());
    }
    
    @Test
    public void makeDepositCorrectionDown() throws Exception {
    	String depositId = "APIT5-IEDR";
    	DepositInfo deposit = depositService.viewDeposit(depositId);
    	AssertJUnit.assertNotNull(deposit);
    	int amount = -1389;
    	Deposit newDeposit = depositService.correctDeposit(depositId, amount, "APITEST-IEDR", "payment by a cheque");
    	AssertJUnit.assertEquals("Actual balance", getValueInLowestCurrencyUnit(deposit.getCloseBal()) + amount, getValueInLowestCurrencyUnit(newDeposit.getCloseBal()));
        AssertJUnit.assertEquals("transactionAmount", amount, getValueInLowestCurrencyUnit(newDeposit.getTransactionAmount()));
        AssertJUnit.assertEquals("transaction type", DepositTransactionType.MANUAL, newDeposit.getTransactionType());
        AssertJUnit.assertNotNull("orderId", newDeposit.getOrderId());
        AssertJUnit.assertEquals("corrector nh", "APITEST-IEDR", newDeposit.getCorrectorNH());
        AssertJUnit.assertEquals("remark", "payment by a cheque", newDeposit.getRemark());
    }
    
    @Test
    public void makeDepositCorrectionDownInsufficientFunds() throws Exception {
    	String depositId = "APIT5-IEDR";
    	DepositInfo deposit = depositService.viewDeposit(depositId);
    	AssertJUnit.assertNotNull(deposit);
    	int amount = -100011;
    	Deposit newDeposit = depositService.correctDeposit(depositId, amount, "APITEST-IEDR", "payment by a cheque");
    	AssertJUnit.assertEquals("Actual balance", getValueInLowestCurrencyUnit(deposit.getCloseBal()) + amount, getValueInLowestCurrencyUnit(newDeposit.getCloseBal()));
        AssertJUnit.assertEquals("transactionAmount", amount, getValueInLowestCurrencyUnit(newDeposit.getTransactionAmount()));
        AssertJUnit.assertEquals("transaction type", DepositTransactionType.MANUAL, newDeposit.getTransactionType());
        AssertJUnit.assertNotNull("orderId", newDeposit.getOrderId());
        AssertJUnit.assertEquals("corrector nh", "APITEST-IEDR", newDeposit.getCorrectorNH());
        AssertJUnit.assertEquals("remark", "payment by a cheque", newDeposit.getRemark());
    }
    
    @Test
    public void makeDepositCorrectionDownInsufficientFundsDueToUnsettledReservations() throws Exception {
    	String depositId = "APITEST-IEDR";
    	DepositInfo deposit = depositService.viewDeposit(depositId);
    	AssertJUnit.assertNotNull(deposit);
    	BigDecimal amountWithReservations = deposit.getCloseBalIncReservaions();    	
    	// make a reservation to make sure the available funds are lower than the closing balance
    	Map<String, Period> domainsWithPeriods = new HashMap<String, Period>();
    	String dname = "payDomain.ie";
    	domainsWithPeriods.put(dname, Period.fromYears(1));
    	Domain domain = domainDAO.get(dname);
    	domainDAO.update(domain, 17);
		paymentService.payADP(null, "APITEST-IEDR", null , domainsWithPeriods, false);
		try {
			Deposit newDeposit = depositService.correctDeposit(depositId, (int) - (amountWithReservations.doubleValue() * 100), "APITEST-IEDR", "payment by a cheque");
			AssertJUnit.fail("NotEnoughDepositFundsException expected to be thrown, instead got " + newDeposit);
		} catch (NotEnoughtDepositFundsException e) {
			// 
		}    	
    }
    
    @Test
    public void makeDepositCorrectionUpNewDeposit() throws Exception {
    	String depositId = "KCB1-IEDR";
    	try {
    		depositService.viewDeposit(depositId);
    		AssertJUnit.fail("There supposed to be no deposit with id=" + depositId);
    	} catch (DepositNotFoundException e) {
    		// this was expected!
    	}
    	
    	int amount = 1059;
    	Deposit newDeposit = depositService.correctDeposit(depositId, amount, "APITEST-IEDR", "payment by a cheque");
    	AssertJUnit.assertEquals("Actual balance", amount, getValueInLowestCurrencyUnit(newDeposit.getCloseBal()));
        AssertJUnit.assertEquals("transactionAmount", amount, getValueInLowestCurrencyUnit(newDeposit.getTransactionAmount()));
        AssertJUnit.assertEquals("transaction type", DepositTransactionType.MANUAL, newDeposit.getTransactionType());
        AssertJUnit.assertNotNull("orderId", newDeposit.getOrderId());
        AssertJUnit.assertEquals("corrector nh", "APITEST-IEDR", newDeposit.getCorrectorNH());
        AssertJUnit.assertEquals("remark", "payment by a cheque", newDeposit.getRemark());
    }
    
    

    @Test
    public void depositFundsOfflineTest() throws Exception {
        String depositId = "APIT5-IEDR";
        DepositInfo deposit = depositService.viewDeposit(depositId);
        AssertJUnit.assertNotNull(deposit);
        int amount = 100599;
        Deposit newDeposit = depositService.depositFundsOffline(null, depositId, amount, "APITEST-IEDR", "payment by a cheque");
        AssertJUnit.assertEquals("Actual balance", getValueInLowestCurrencyUnit(deposit.getCloseBal()) + amount, getValueInLowestCurrencyUnit(newDeposit.getCloseBal()));
        AssertJUnit.assertEquals("transactionAmount", amount, getValueInLowestCurrencyUnit(newDeposit.getTransactionAmount()));
        AssertJUnit.assertEquals("transaction type", DepositTransactionType.TOPUP, newDeposit.getTransactionType());
        AssertJUnit.assertNotNull("orderId", newDeposit.getOrderId());
        AssertJUnit.assertEquals("corrector nh", "APITEST-IEDR", newDeposit.getCorrectorNH());
        AssertJUnit.assertEquals("remark", "payment by a cheque", newDeposit.getRemark());
    }

    @Test
    public void depositFundsOfflineUpNewDepositTest() throws Exception {
        String depositId = "KCB1-IEDR";
        try {
            depositService.viewDeposit(depositId);
            AssertJUnit.fail("There supposed to be no deposit with id=" + depositId);
        } catch (DepositNotFoundException e) {
            // this was expected!
        }

        int amount = 1009;
        Deposit newDeposit = depositService.depositFundsOffline(null, depositId, amount, "APITEST-IEDR", "payment by a cheque");
        AssertJUnit.assertEquals("Actual balance", amount, getValueInLowestCurrencyUnit(newDeposit.getCloseBal()));
        AssertJUnit.assertEquals("transactionAmount", amount, getValueInLowestCurrencyUnit(newDeposit.getTransactionAmount()));
        AssertJUnit.assertEquals("transaction type", DepositTransactionType.TOPUP, newDeposit.getTransactionType());
        AssertJUnit.assertNotNull("orderId", newDeposit.getOrderId());
        AssertJUnit.assertEquals("corrector nh", "APITEST-IEDR", newDeposit.getCorrectorNH());
        AssertJUnit.assertEquals("remark", "payment by a cheque", newDeposit.getRemark());
    }
}