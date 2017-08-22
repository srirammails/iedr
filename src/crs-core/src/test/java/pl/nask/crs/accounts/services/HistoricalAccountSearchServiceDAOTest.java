package pl.nask.crs.accounts.services;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

import javax.annotation.Resource;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.search.HistoricalAccountSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.AbstractContextAwareTest;

/**
 * @author Marianna Mysiorska
 */
public class HistoricalAccountSearchServiceDAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalAccountSearchService service;

//TODO: CRS-72
//    @Test
//    public void findHistoricalAccountById(){
//        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
//        criteria.setId(120L);
//        SearchResult<HistoricalObject<Account>> result = service.findHistoricalNicHandle(criteria);
//        assertEquals(result.getResults().size(), 8);
//        HistoricalObject<Account> a = result.getResults().get(0);
//        assertEquals(a.getChangeDate().getTime(), 1174484236000L);
//        assertEquals(a.getChangedBy(), "GEORGE-IEDR");
//        assertTrue(a.getObject().getAddress().equals("M.S. House\r\nStrand Road\r\nBray"));
//        assertTrue(a.getObject().getBillingContact().getNicHandle().equals("CID3-IEDR"));
//        assertEquals(a.getObject().getChangeDate().getTime(), 1174487836000L);
//        assertTrue(a.getObject().getCountry().equals("Ireland"));
//        assertTrue(a.getObject().getCounty().equals("Co. Wicklow"));
//        assertEquals(a.getObject().getCreationDate().getTime(), 1031349600000L);
//        assertEquals(a.getObject().getBillingContact().getEmail(), null);
//        assertTrue(a.getObject().getFax().equals("+35312862989"));
//        assertEquals(a.getObject().getId(), 120L);
//        assertTrue(a.getObject().getInvoiceFreq().equals("Monthly"));
//        assertTrue(a.getObject().getName().equals("Cloch Internet"));
//        assertTrue(a.getObject().getNextInvMonth().equals("January"));
//        assertTrue(a.getObject().getPhone().equals("+35312763669"));
//        assertTrue(a.getObject().getRemark().equals("CID3-IEDR - Activated"));
//        assertTrue(a.getObject().getStatus().equals("Active"));
//        assertEquals(a.getObject().getStatusChangeDate().getTime(), 1114639200000L);
//        assertTrue(a.getObject().getTariff().equals("TradeBChar"));
//        assertTrue(a.getObject().getWebAddress().equals("http://www.cloch.com/"));
//    }

    @Test
    public void findHistoricalAccountByIdNotExists(){
        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(1234567890L);
        assertEquals(service.findHistoricalNicHandle(criteria).getResults().size(), 0);
    }
}
