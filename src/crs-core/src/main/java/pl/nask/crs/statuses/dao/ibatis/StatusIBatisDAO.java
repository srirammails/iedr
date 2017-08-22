package pl.nask.crs.statuses.dao.ibatis;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.dao.ibatis.SqlMapClientLoggingDaoSupport;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusIBatisDAO<STATUS, KEY> extends SqlMapClientLoggingDaoSupport implements
        GenericDAO<STATUS, KEY> {

    private final String getQueryId;
    private final String findQueryId;

    public StatusIBatisDAO(String getQueryId, String findQueryId) {
        this.getQueryId = getQueryId;
        this.findQueryId = findQueryId;
    }

    @Override
    public void create(STATUS status) {
        throw new UnsupportedOperationException();
    }

    /**
     * searches for object with id given as a parameter
     *
     * @param id id of the searched object. In case of status id = Status or
     *           FailCd column
     * @return found status or null if no status was found
     */
    @Override
    public STATUS get(KEY id) {
        if (getQueryId == null)
            throw new UnsupportedOperationException("status dao get");
        STATUS status = performQueryForObject(getQueryId, id);
        return status;
    }

    /**
     * Searches for objects matching given criteria. The result is limited by
     * the offset and limit arguments.
     *
     * @param statusSearchCriteria
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public LimitedSearchResult<STATUS> find(
            SearchCriteria<STATUS> statusSearchCriteria, long offset, long limit) {
        throw new UnsupportedOperationException();
    }

    /**
     * searches for objects matching given criteria.
     *
     * @param statusSearchCriteria
     * @return
     */
    @Override
    public SearchResult<STATUS> find(SearchCriteria<STATUS> statusSearchCriteria) {
        if (findQueryId == null)
            throw new UnsupportedOperationException("status dao find");
        List<STATUS> statusList = performQueryForList(findQueryId);
        return new SearchResult<STATUS>(statusSearchCriteria, null, statusList);
    }

    @Override
    public boolean lock(KEY id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(STATUS status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(KEY id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(STATUS status) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param sortBy is ignored in this implementation
     */
    @Override
    public LimitedSearchResult<STATUS> find(SearchCriteria<STATUS> criteria,
                                            long offset, long limit, List<SortCriterion> sortBy) {
        logger.warn("Sorting is ignored in this implementation!");
        return find(criteria, offset, limit);
    }

    /**
     * @param sortBy is ignored in this implementation
     */
    @Override
    public SearchResult<STATUS> find(SearchCriteria<STATUS> criteria,
                                     List<SortCriterion> sortBy) {
        logger.warn("Sorting is ignored in this implementation!");
        return find(criteria);
    }
}