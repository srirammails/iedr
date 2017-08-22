package pl.nask.crs.nichandle.service;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.impl.NicHandleSearchServiceImpl;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.*;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.LimitedSearchResult;
import static org.easymock.EasyMock.*;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleSearchServiceTest {

    protected NicHandleSearchService searchService;
    protected NicHandleDAO mockDAO;

    @BeforeMethod
	public void before(){
        mockDAO = createMock(NicHandleDAO.class);
        searchService = new NicHandleSearchServiceImpl(mockDAO);
        expect(mockDAO.get("AA11-IEDR")).andStubReturn(createNHAA11());
        expect(mockDAO.get("NOT-EXISTS-IEDR")).andStubReturn(null);
        expect(mockDAO.find(criteria1)).andStubReturn(new SearchResult<NicHandle>(criteria1, null, createNHAAE()));
        expect(mockDAO.find(criteria1, 0, 1, null)).andStubReturn(new LimitedSearchResult<NicHandle>(criteria1, null, 0, 1, createNHAAELimited(), 2));
        replay(mockDAO);
    }

    @Test
    public void getNicHandleTest() throws Exception{
        NicHandle nicHandle = searchService.getNicHandle("AA11-IEDR");
        compareNicHandle(nicHandle, createNHAA11());
    }

    @Test (expectedExceptions = NicHandleNotFoundException.class)
    public void getNicHandleNotExistsTest() throws Exception {
        searchService.getNicHandle("NOT-EXISTS-IEDR");
    }

    @Test
    public void findNicHandlesByNicHandleTest(){
        SearchResult<NicHandle> result = searchService.findNicHandle(criteria1);
        compareNicHandleList(result.getResults(), createNHAAE());
    }

    @Test
    public void findNicHandlesByNicHandleWithLimitTest() {
        LimitedSearchResult<NicHandle> result = searchService.findNicHandle(criteria1, 0, 1, null);
        compareNicHandleList(result.getResults(), createNHAAELimited());
    }


}
