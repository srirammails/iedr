package pl.nask.crs.accounts.dao;

import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalHistoricalAccount;
import pl.nask.crs.accounts.search.HistoricalAccountSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.AbstractContextAwareTest;

/**
 * @author Marianna Mysiorska
 */
public class HistoricalAccountDAOTest extends AbstractContextAwareTest {

    @Autowired
    HistoricalAccountDAO historicalAccountDAO;

    @Autowired
    AccountDAO accountDAO;

    @Test
    public void findHistoricalAccount(){
        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(120L);
        SearchResult<HistoricalObject<Account>> result = historicalAccountDAO.find(criteria);
        assertEquals(result.getResults().size(), 8);
    }

    @Test
    public void testFlagsAreCorrectlyInsertedAndTakenOut() {
        Date aDate = new Date(((new Date().getTime())/1000)*1000);
        final long accountId = 120l;
        final String changedBy = "testNH";

        Account account = accountDAO.get(accountId);
        account.setTicketEdit(true);
        account.setAgreementSigned(true);
        accountDAO.update(account);

        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(accountId);
        SearchResult<HistoricalObject<Account>> result = historicalAccountDAO.find(criteria);
        assertEquals(8, result.getResults().size());

        historicalAccountDAO.create(accountId, aDate, changedBy);

        result = historicalAccountDAO.find(criteria);
        assertEquals(9, result.getResults().size());
        HistoricalObject<Account> hisAcc = result.getResults().get(0);
        assertTrue(hisAcc.getObject().isAgreementSigned());
        assertTrue(hisAcc.getObject().isTicketEdit());
    }

// TODO: CRS-72
//    @Test
//    public void findHistoricalAccountCheckFields(){
//        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
//        criteria.setId(105L);
//        SearchResult<HistoricalObject<Account>> result = historicalAccountDAO.find(criteria);
//        assertEquals(result.getResults().size(), 1);
//        HistoricalObject<Account> hisAcc = result.getResults().get(0);
//        assertTrue(hisAcc.getObject().getName().equals("UTV Internet"));
//        assertTrue(hisAcc.getObject().getBillingContact().getNicHandle().equals("DNA10-IEDR"));
//        assertTrue(hisAcc.getObject().getAddress().equals("Ormeau Road\r\nBelfast\r\nBT7 1EB"));
//        assertTrue(hisAcc.getObject().getCounty().equals("Co. Antrim"));
//        assertTrue(hisAcc.getObject().getCountry().equals("Northern Ireland"));
//        assertTrue(hisAcc.getObject().getWebAddress().equals("Web"));
//        assertTrue(hisAcc.getObject().getPhone().equals("+44 1232 201 555"));
//        assertTrue(hisAcc.getObject().getFax().equals("+44 1232 201 555"));
//        assertTrue(hisAcc.getObject().getStatus().equals("Active"));
//        assertEquals(hisAcc.getObject().getStatusChangeDate().getTime(), 1031349600000L);
//        assertTrue(hisAcc.getObject().getTariff().equals("Silver"));
//        assertEquals(hisAcc.getObject().getCreationDate().getTime(), 1031349600000L);
//        assertEquals(hisAcc.getObject().getChangeDate().getTime(), 1051709340000L);
//        assertTrue(hisAcc.getObject().getInvoiceFreq().equals("Monthly"));
//        assertTrue(hisAcc.getObject().getNextInvMonth().equals("January"));
//        assertTrue(hisAcc.getObject().getRemark().equals("Updated Address"));
//        assertEquals(hisAcc.getChangeDate().getTime(), 1051705740000L);
//        assertTrue(hisAcc.getChangedBy().equals("IH4-IEDR"));
//    }
//                                                                    
//    @Test
//    public void createHistoricalAccount(){
//        Date date = new Date(1051701740000L);
//        historicalAccountDAO.create(105L, date, "MM-IEDR");
//        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
//        criteria.setId(105L);
//        SearchResult<HistoricalObject<Account>> result = historicalAccountDAO.find(criteria);
//        assertEquals(result.getResults().size(), 2);
//        HistoricalObject<Account> hisAcc = result.getResults().get(1);
//        assertTrue(hisAcc.getObject().getName().equals("UTV Internet"));
//        assertTrue(hisAcc.getObject().getBillingContact().getNicHandle().equals("DNA10-IEDR"));
//        assertTrue(hisAcc.getObject().getAddress().equals("Ormeau Road\r\nBelfast\r\nBT7 1EB"));
//        assertTrue(hisAcc.getObject().getCounty().equals("Co. Antrim"));
//        assertTrue(hisAcc.getObject().getCountry().equals("Northern Ireland"));
//        assertTrue(hisAcc.getObject().getWebAddress().equals("http://www.utv.ie"));
//        assertTrue(hisAcc.getObject().getPhone().equals("+44 1232 201 555"));
//        assertTrue(hisAcc.getObject().getFax().equals("+44 1232 201 555"));
//        assertTrue(hisAcc.getObject().getStatus().equals("Active"));
//        assertEquals(hisAcc.getObject().getStatusChangeDate().getTime(), 1031349600000L);
//        assertTrue(hisAcc.getObject().getTariff().equals("TradeAChar"));
//        assertEquals(hisAcc.getObject().getCreationDate().getTime(), 1031349600000L);
//        assertEquals(hisAcc.getObject().getChangeDate().getTime(), 1164276307000L);
//        assertTrue(hisAcc.getObject().getInvoiceFreq().equals("Monthly"));
//        assertTrue(hisAcc.getObject().getNextInvMonth().equals("January"));
//        assertTrue(hisAcc.getObject().getRemark().equals("Updated Address"));
//        assertEquals(hisAcc.getChangeDate().getTime(), 1051701740000L);
//        assertTrue(hisAcc.getChangedBy().equals("MM-IEDR"));
//    }

}
