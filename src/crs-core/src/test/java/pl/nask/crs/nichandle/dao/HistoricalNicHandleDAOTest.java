package pl.nask.crs.nichandle.dao;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.Vat;
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;

/**
 * @author Marianna Mysiorska
 */
public class HistoricalNicHandleDAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalNicHandleDAO historicalNicHandleDAO;
    @Resource
    NicHandleDAO nicHandleDAO;
// TODO: CRS-72
//    @Test
//    public void findHistoricalNicHandlesByNicHandle() {
//        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
//        criteria.setNicHandleId("ABD275-IEDR");
//        SearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria);
//        assertEquals(result.getResults().size(),3);
//        List<HistoricalObject<NicHandle>> actualNicHandles = result.getResults();
//        List<HistoricalObject<NicHandle>> expectedNicHandles = createNHABD275();
//        compareHistoricalNHList(actualNicHandles, expectedNicHandles);
//    }

    @Test
    public void findHistoricalNicHandlesByNicHandleNotExists(){
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId("NOTEXISTS-IEDR");
        SearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria);
        assertEquals(result.getResults().size(),0);
    }
// TODO: CRS-72
//    @Test
//    public void createHistoricalNicHandle(){
//        historicalNicHandleDAO.create("AA11-IEDR", date, "TEST-IEDR");
//        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
//        criteria.setNicHandleId("AA11-IEDR");
//        SearchResult<HistoricalObject<NicHandle>> results = historicalNicHandleDAO.find(criteria);
//        assertEquals(results.getResults().size(),1);
//        compareHistoricalNicHandles(results.getResults().get(0), createHistoricalNicHandleAA11());
//    }

    @Test
    public void createHistoricalNicHandleWithTelecomAndPayment(){
        NicHandle nicHandle = nicHandleDAO.get("AAA906-IEDR");
        Vat vat = new Vat("ABCDEFGHI");
        Set<String> phones = new HashSet<String>();
        Set<String> faxes = new HashSet<String>();
        phones.add("11111");
        phones.add("22222");
        faxes.add("4444");
        nicHandle.setVat(vat);
        nicHandle.setPhones(phones);
        nicHandle.setFaxes(faxes);
        Date date = DateUtils.setMilliseconds(new Date(), 999);
        nicHandleDAO.update(nicHandle);
        historicalNicHandleDAO.create(nicHandle, date, "TEST-IEDR");
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId("AAA906-IEDR");
        SearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria);
        assertEquals(1, result.getResults().size());
        HistoricalObject<NicHandle> histNH = result.getResults().get(0);

        assertEquals(histNH.getObject().getVat().getVatNo(), "ABCDEFGHI");
        Assert.assertEquals(histNH.getChangeDate(), DateUtils.truncate(date, Calendar.SECOND));
        // to check telecom we have to actually get the object
        HistoricalObject<NicHandle> dbHistNH = historicalNicHandleDAO.get(new HistoricalNicHandleKey(histNH.getObject().getNicHandleId(), histNH.getChangeId()));
        assertEquals(2, dbHistNH.getObject().getPhones().size());
        assertTrue(CollectionUtils.exists(dbHistNH.getObject().getPhones(), new EqualsPredicate<String>("11111")));
        assertTrue(CollectionUtils.exists(dbHistNH.getObject().getPhones(), new EqualsPredicate<String>("22222")));
        assertEquals(1, dbHistNH.getObject().getFaxes().size());
        assertTrue(CollectionUtils.exists(dbHistNH.getObject().getFaxes(), new EqualsPredicate<String>("4444")));
    }

    private static class EqualsPredicate<T> extends CollectionUtils.Predicate<T> {
        private final T obj;

        public EqualsPredicate(T expected) {
            this.obj = expected;
        }

        @Override
        public boolean test(T elem) {
            return obj.equals(elem);
        }
    }
}
