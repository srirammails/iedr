package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.payment.DepositTopUp;
import pl.nask.crs.payment.dao.ibatis.objects.InternalDeposit;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;

import java.util.*;

/**
 * @author: Marcin Tkaczyk
 */
public class InternalDepositIbatisDAO extends GenericIBatisDAO<InternalDeposit, String> {

    private static final String NIC_HANDLE_ID_PARAM = "nicHandleId";
    private static final String FROM_DATE_PARAM = "fromDate";
    private static final String TO_DATE_PARAM = "toDate";
    private static final String OFFSET = "offset";
    private static final String LIMIT = "limit";

    public InternalDepositIbatisDAO() {
        setLockQueryId("deposit.getLockedDepositByNicHandleId");
        setCreateQueryId("deposit.insertDeposit");
        setGetQueryId("deposit.getDepositByNicHandleId");
        setLimitedFindQueryId("deposit.findDeposits");
        setCountFindQueryId("deposit.countFindDeposits");
    }

    public void update(InternalDeposit internalDeposit) {
        String nicHandle = internalDeposit.getNicHandleId();
        performInsert("deposit.insertHistoricalDeposit", nicHandle);
        performUpdate("deposit.updateDeposit", internalDeposit);
    }

    public LimitedSearchResult<DepositTopUp> getDepositTopUp(String nicHandleId, Date fromDate, Date toDate, long offset, long limit) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(NIC_HANDLE_ID_PARAM, nicHandleId);
        params.put(FROM_DATE_PARAM, fromDate);
        params.put(TO_DATE_PARAM, toDate);
        params.put(OFFSET, offset);
        params.put(LIMIT, limit);

        Integer total = performQueryForObject("deposit.getTopUpHistoryCount", params);
        List<DepositTopUp> list;
        if (total == 0) {
            list = Collections.emptyList();
        } else {
            list = performQueryForList("deposit.getTopUpHistory", params);
        }
        return new LimitedSearchResult<DepositTopUp>(null, null, limit, offset, list, total);
    }

    public LimitedSearchResult<InternalDeposit> findHistory(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        FindParameters parameters = new FindParameters(criteria);
        if (isAllSearch(offset, limit)) {
            parameters.setLimit(offset, Long.MAX_VALUE);
        } else {
            parameters.setLimit(offset, limit);
        }
        parameters.setOrderBy(sortBy);
        return performFind("deposit.findHistory", "deposit.countFindHistory", parameters);
    }

    private boolean isAllSearch(long offset, long limit) {
        return offset == 0 && limit == 0;
    }

    public LimitedSearchResult<InternalDeposit> findDepositWithHistory(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        FindParameters parameters = new FindParameters(criteria);
        if (isAllSearch(offset, limit)) {
            parameters.setLimit(offset, Long.MAX_VALUE);
        } else {
            parameters.setLimit(offset, limit);
        }
        parameters.setOrderBy(sortBy);
        return performFind("deposit.findDepositWithHistory", "deposit.countFindDepositWithHistory", parameters);
    }
}
