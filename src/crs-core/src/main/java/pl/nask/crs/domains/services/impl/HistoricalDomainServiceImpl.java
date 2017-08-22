package pl.nask.crs.domains.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.domains.services.HistoricalDomainService;
import pl.nask.crs.history.HistoricalObject;

/**
 * @author Piotr Tkaczyk
 */
public class HistoricalDomainServiceImpl implements HistoricalDomainService {


    HistoricalDomainDAO historicalDomainDao;

    private static Map<String, String> sortingCriteria = new HashMap<String, String>();
    static {
        sortingCriteria.put("changeId", "changeId");
    	sortingCriteria.put("changeDate", "histChangeDate");
    	sortingCriteria.put("name", "name");
    	sortingCriteria.put("holder", "holder");
    	sortingCriteria.put("changedByNicHandle", "changedByNicHandle");
    }

    public HistoricalDomainServiceImpl(HistoricalDomainDAO historicalDomainDao) {
        Validator.assertNotNull(historicalDomainDao, "historical domain dao");
        this.historicalDomainDao = historicalDomainDao;
    }
 
    public LimitedSearchResult<HistoricalObject<Domain>> findHistory(HistoricalDomainSearchCriteria criteria, int offset, int limit, List<SortCriterion> sortBy) {
        return historicalDomainDao.find(criteria, offset, limit, sortingCriteria(sortBy));
    }

    private List<SortCriterion> sortingCriteria(List<SortCriterion> sortBy) {
        if (sortBy == null)
            return null;
        List<SortCriterion> l = new ArrayList<SortCriterion>();
        for (SortCriterion c : sortBy) {
            if (sortingCriteria.containsKey(c.getSortBy())) {
            	String mappedSortBy = sortingCriteria.get(c.getSortBy());
            	if (mappedSortBy != null) {
            		l.add(new SortCriterion(mappedSortBy, c.isAscending()));
            	} else {
            		l.add(c);
            	}
            }
        }
        
        return l;
    }

}
