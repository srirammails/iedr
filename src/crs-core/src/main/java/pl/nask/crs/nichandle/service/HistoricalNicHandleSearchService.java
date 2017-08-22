package pl.nask.crs.nichandle.service;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;

/**
 * @author Marianna Mysiorska
 */
public interface HistoricalNicHandleSearchService {

    public LimitedSearchResult<HistoricalObject<NicHandle>> findHistoricalNicHandle(HistoricalNicHandleSearchCriteria criteria, int offset, int limit);

    public HistoricalObject<NicHandle> get(String nicHandleId, long changeId);
}
