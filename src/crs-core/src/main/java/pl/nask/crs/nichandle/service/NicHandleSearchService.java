package pl.nask.crs.nichandle.service;

import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleDeletedException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import java.util.List;

/**
 * @author Marianna Mysiorska
 */
public interface NicHandleSearchService {

     public NicHandle getNicHandle(String nicHandleId)
             throws NicHandleNotFoundException;

     public SearchResult<NicHandle> findNicHandle(NicHandleSearchCriteria criteria);

     public LimitedSearchResult<NicHandle> findNicHandle(NicHandleSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy);

     public void confirmNicHandleNotDeleted(String nicHandleId)
            throws NicHandleNotFoundException, NicHandleDeletedException;

     public void confirmNicHandleActive(String nicHandleId)
            throws NicHandleNotFoundException, NicHandleNotActiveException;
}
