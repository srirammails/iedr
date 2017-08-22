package pl.nask.crs.commons.search;

import java.util.List;

/**
 * Encapsulates list of search results limited by given offset and results
 * limit, and the total number of results.
 * 
 * @author Artur Gniadzik
 * 
 * @param <T>
 *            Value Object type
 */
public class LimitedSearchResult<T> extends SearchResult<T> {
    private final Long offset;
    private final Long limit;
    private final long totalResults;

    public LimitedSearchResult(SearchCriteria<T> criteria,
            List<SortCriterion> sortBy, long limit, long offset,
            List<T> results, long totalResults) {
        super(criteria, sortBy, results);
        this.limit = limit;
        this.offset = offset;
        this.totalResults = totalResults;

    }

    /**
     * 
     * @return offset used in the query
     */
    public Long getOffset() {
        return offset;
    }

    /**
     * 
     * @return limit used in the query
     */
    public Long getLimit() {
        return limit;
    }

    /**
     * 
     * @return total results number
     */
    public long getTotalResults() {
        return totalResults;
    }

}
