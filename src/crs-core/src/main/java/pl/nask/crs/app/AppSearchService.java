package pl.nask.crs.app;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.List;

/**
 * Application search service interface.
 * <p/>
 * <p/>
 * (C) Copyright 2008 NASK Software Research & Development Department
 *
 * @author Artur Gniadzik
 * @param <T>
 * managed object type
 */
public interface AppSearchService<T, SEARCH_CRITERIA_TYPE extends SearchCriteria<T>> {
    public LimitedSearchResult<T> search(AuthenticatedUser user, SEARCH_CRITERIA_TYPE criteria, long offset, long limit,
                                         List<SortCriterion> orderBy) throws AccessDeniedException;
}
