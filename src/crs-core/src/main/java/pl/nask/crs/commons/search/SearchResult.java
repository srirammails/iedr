package pl.nask.crs.commons.search;

import java.util.Collections;
import java.util.List;

public class SearchResult<T> {

    protected final SearchCriteria<T> criteria;
    protected final List<T> results;

    public SearchResult(SearchCriteria<T> criteria, List<SortCriterion> sortBy,
            List<T> results) {
        this.criteria = criteria;
        this.results = Collections.unmodifiableList(results);
    }

    /**
     * 
     * @return criteria, which were used in search
     */
    public SearchCriteria<T> getCriteria() {
        return criteria;
    }

    /**
     * 
     * @return read-only list of results
     */
    public List<T> getResults() {
        return results;
    }

}
