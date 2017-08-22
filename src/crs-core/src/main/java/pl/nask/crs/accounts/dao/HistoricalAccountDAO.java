package pl.nask.crs.accounts.dao;

import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SearchCriteria;

import java.util.Date;

/**
 * @author Marianna Mysiorska
 */
public interface HistoricalAccountDAO {

    public void create(Long id, Date changeDate, String changedBy);

    SearchResult<HistoricalObject<Account>> find(SearchCriteria<HistoricalObject<Account>> criteria);

}
