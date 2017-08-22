package pl.nask.crs.accounts.services;

import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.search.HistoricalAccountSearchCriteria;

/**
 * @author Marianna Mysiorska
 */
public interface HistoricalAccountSearchService {

    public SearchResult<HistoricalObject<Account>> findHistoricalNicHandle(HistoricalAccountSearchCriteria criteria);
}
