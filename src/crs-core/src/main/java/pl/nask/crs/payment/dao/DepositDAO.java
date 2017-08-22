package pl.nask.crs.payment.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.*;

import java.util.Date;
import java.util.List;

/**
 * @author: Marcin Tkaczyk
 * 
 */
public interface DepositDAO extends GenericDAO<Deposit, String> {

    LimitedSearchResult<DepositTopUp> getTopUpHistory(String nicHandle, Date fromDate, Date toDate, long offset, long limit);

    LimitedSearchResult<Deposit> findHistory(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<Deposit> findDepositWithHistory(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);
}
