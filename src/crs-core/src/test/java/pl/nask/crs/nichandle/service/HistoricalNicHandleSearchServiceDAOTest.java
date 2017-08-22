package pl.nask.crs.nichandle.service;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.compareHistoricalNHList;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.createNHABD275;

import java.util.List;

import javax.annotation.Resource;

import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;


/**
 * @author Marianna Mysiorska
 */
public class HistoricalNicHandleSearchServiceDAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalNicHandleSearchService service;

    //TODO: CRS-72
//    @Test
//    public void findHistoricalNicHandlesByNicHandle() {
//        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
//        criteria.setNicHandleId("ABD275-IEDR");
//        SearchResult<HistoricalObject<NicHandle>> result = service.findHistoricalNicHandle(criteria, 0, 15);
//        assertEquals(result.getResults().size(),3);
//        List<HistoricalObject<NicHandle>> actualNicHandles = result.getResults();
//        List<HistoricalObject<NicHandle>> expectedNicHandles = createNHABD275();
//        compareHistoricalNHList(actualNicHandles, expectedNicHandles);
//    }

    @Test
    public void findHistoricalNicHandlesByNicHandleNotExists(){
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId("NOT-EXISTS-IEDR");
        assertEquals(service.findHistoricalNicHandle(criteria, 0, 15).getResults().size(), 0);
    }
}






