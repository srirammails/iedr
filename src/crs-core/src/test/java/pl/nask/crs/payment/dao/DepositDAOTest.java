package pl.nask.crs.payment.dao;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.comapareDeposit;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.payment.DepositTopUp;
import pl.nask.crs.payment.DepositTransactionType;

/**
 * @author: Marcin Tkaczyk
 */
public class DepositDAOTest extends AbstractContextAwareTest {

    @Resource
    DepositDAO depositDAO;

    @Test
    public void getDepositTest() {
        final String nicHandleId = "IDL2-IEDR";
        Deposit deposit = Deposit.newInstance(nicHandleId, new Date(1215502925999L), 7000, 2594, -100, DepositTransactionType.SETTLEMENT,  "20101202171646-D-102", null, null);
        AssertJUnit.assertTrue(depositDAO.lock(nicHandleId));
        Deposit dbDeposit = depositDAO.get(nicHandleId);

        comapareDeposit(deposit, dbDeposit);
        AssertJUnit.assertEquals("IRISH DOMAINS LTD", dbDeposit.getNicHandleName());
    }

    @Test
    public void updateDepositTest() {
        final String nicHandleId = "IDL2-IEDR";
        Deposit deposit = Deposit.newInstance(nicHandleId, new Date(1215502925999L), 1000, 594, 500, DepositTransactionType.TOPUP, "20101202171646-D-202", null, null);
        depositDAO.update(deposit);
        Deposit updated = depositDAO.get(nicHandleId);

        comapareDeposit(deposit, updated);

        deposit = Deposit.newInstance(nicHandleId, new Date(1215502925999L), 1000, 594, 500, DepositTransactionType.MANUAL, "20101202171646-D-202", "correctorNH", "corrector remark");
        depositDAO.update(deposit);
        updated = depositDAO.get(nicHandleId);

        comapareDeposit(deposit, updated);
    }

    @Test
    public void createDepositTest() {
        final String nicHandleId = "DTEST-IEDR";
        Deposit deposit = Deposit.newInstance(nicHandleId, new Date(1215502925999L), 500, 500, 500, DepositTransactionType.INIT, "20101202171646-D-203", null, "init remark");
        depositDAO.create(deposit);
        Deposit dbDeposit = depositDAO.get(nicHandleId);

        comapareDeposit(deposit, dbDeposit);
    }

    @Test
    public void getTopUpHistoryTest() {
        LimitedSearchResult<DepositTopUp> depositTopUps = depositDAO.getTopUpHistory("X-IEDR", new Date(106, 4, 1), new Date(), 0, 10);
        AssertJUnit.assertEquals(5, depositTopUps.getTotalResults());

        depositTopUps = depositDAO.getTopUpHistory("X-IEDR", new Date(106, 4, 1), new Date(), 1, 1);
        AssertJUnit.assertEquals(5, depositTopUps.getTotalResults());
        DepositTopUp depositTopUp = depositTopUps.getResults().get(0);
        AssertJUnit.assertEquals(600, depositTopUp.getTopUpAmount(), 0.0001);
        AssertJUnit.assertEquals(3100, depositTopUp.getClosingBalance(), 0.0001);
    }

    @Test
    public void findTest() {
        LimitedSearchResult<Deposit> searchResult = depositDAO.find(null, 0, 5, Arrays.asList(new SortCriterion("nicHandleId", true)));
        AssertJUnit.assertEquals(8, searchResult.getTotalResults());
        AssertJUnit.assertEquals(5, searchResult.getResults().size());
        AssertJUnit.assertEquals("AAE553-IEDR", searchResult.getResults().get(0).getNicHandleId());

        searchResult = depositDAO.find(null, 0, 5, Arrays.asList(new SortCriterion("nicHandleId", false)));
        AssertJUnit.assertEquals("X-IEDR", searchResult.getResults().get(0).getNicHandleId());

        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("SWD2-IEDR");
        searchResult = depositDAO.find(criteria, 0, 5, null);
        AssertJUnit.assertEquals(1, searchResult.getTotalResults());
        AssertJUnit.assertEquals(1, searchResult.getResults().size());
        AssertJUnit.assertEquals("SWD2-IEDR", searchResult.getResults().get(0).getNicHandleId());

        criteria = new DepositSearchCriteria();
        criteria.setTransactionDateFrom(new Date(108, 0, 1));
        criteria.setTransactionDateTo(new Date(108, 7, 1));
        searchResult = depositDAO.find(criteria, 0, 5, null);
        AssertJUnit.assertEquals(5, searchResult.getTotalResults());
        AssertJUnit.assertEquals(5, searchResult.getResults().size());

        criteria = new DepositSearchCriteria();
        criteria.setRemark("rem");
        searchResult = depositDAO.find(criteria, 0, 5, null);
        AssertJUnit.assertEquals(1, searchResult.getTotalResults());
        AssertJUnit.assertEquals(1, searchResult.getResults().size());

        criteria = new DepositSearchCriteria();
        criteria.setAccountBillNH("APITEST-IEDR");
        searchResult = depositDAO.find(criteria, 0, 5, null);
        AssertJUnit.assertEquals(1, searchResult.getTotalResults());
        AssertJUnit.assertEquals(1, searchResult.getResults().size());
    }

    @Test
    public void findHistoryTest() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("IDL2-IEDR");
        LimitedSearchResult<Deposit> searchResult = depositDAO.findHistory(criteria, 0, 5, null);
        AssertJUnit.assertEquals(419, searchResult.getTotalResults());
        AssertJUnit.assertEquals(5, searchResult.getResults().size());

        criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("SWD2-IEDR");
        searchResult = depositDAO.findHistory(criteria, 0, 5, Arrays.asList(new SortCriterion("id", true)));
        AssertJUnit.assertEquals(20, searchResult.getTotalResults());
        AssertJUnit.assertEquals(1800, searchResult.getResults().get(0).getCloseBal(), 0.0001);
        searchResult = depositDAO.findHistory(criteria, 0, 5, Arrays.asList(new SortCriterion("id", false)));
        AssertJUnit.assertEquals(20, searchResult.getTotalResults());
        AssertJUnit.assertEquals(46.2, searchResult.getResults().get(0).getCloseBal(), 0.0001);

        criteria.setTransactionDateFrom(new Date(108, 7, 1));
        searchResult = depositDAO.findHistory(criteria, 0, 5, Arrays.asList(new SortCriterion("id", true)));
        AssertJUnit.assertEquals(6, searchResult.getTotalResults());
    }

    @Test
    public void findDepositAndHistoryTest() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setTransactionType(DepositTransactionType.TOPUP);
        LimitedSearchResult<Deposit> searchResult = depositDAO.findDepositWithHistory(criteria, 0, 10, null);

        AssertJUnit.assertEquals(5, searchResult.getTotalResults());
        AssertJUnit.assertEquals(5, searchResult.getResults().size());
    }
}
