package pl.nask.crs.domains.services;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.history.HistoricalObject;

/**
 * @author Piotr Tkaczyk
 */
public interface HistoricalDomainService {

    LimitedSearchResult<HistoricalObject<Domain>> findHistory(HistoricalDomainSearchCriteria criteria, int offset, int limit,  List<SortCriterion> orderBy);
}
