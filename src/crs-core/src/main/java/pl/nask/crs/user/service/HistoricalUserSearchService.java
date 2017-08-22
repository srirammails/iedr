package pl.nask.crs.user.service;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;
import pl.nask.crs.user.search.HistoricalUserSearchCriteria;

public interface HistoricalUserSearchService {
    public LimitedSearchResult<HistoricalObject<User>> find(HistoricalUserSearchCriteria searchCriteria, long offset, long limit);

}
