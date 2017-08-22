package pl.nask.crs.accounts.services.impl;

import pl.nask.crs.accounts.services.HistoricalAccountSearchService;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.HistoricalAccountDAO;
import pl.nask.crs.accounts.search.HistoricalAccountSearchCriteria;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Marianna Mysiorska
 */
public class HistoricalAccountSearchServiceImpl implements HistoricalAccountSearchService {

    private HistoricalAccountDAO historicalAccountDAO;

    public HistoricalAccountSearchServiceImpl(HistoricalAccountDAO historicalAccountDAO) {
        Validator.assertNotNull(historicalAccountDAO, "historical account dao");
        this.historicalAccountDAO = historicalAccountDAO;
    }

    public SearchResult<HistoricalObject<Account>> findHistoricalNicHandle(HistoricalAccountSearchCriteria criteria) {
        return historicalAccountDAO.find(criteria);
    }
}
