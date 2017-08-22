package pl.nask.crs.commons.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Patrycja Wegrzynowicz, Artur Gniadzik
 */
public class GenericIBatisDAO<DTO, KEY> extends SqlMapClientLoggingDaoSupport
        implements GenericDAO<DTO, KEY> {

    private String createQueryId;

    private String getQueryId;

    private String lockQueryId;

    private String updateQueryId;

    private String deleteQueryId;

    private String findQueryId;

    private String limitedFindQueryId;

    private String countFindQueryId;
    
    private Map<String, String> sortMapping;

    /**
     * Sets mapping for the sorting parameters. Mapping is made from entries
     * from[i]->to[i], which means that both tables must have the same size.
     * 
     * If from parameter is null than no mapping will be defined, which means
     * that all sorting params will be passed-by to a query. If from parameter
     * is not null, than only sorting params, which are in the map will be
     * accepted. If to parameter is null, than no conversion will be made
     * (parameters will be stored as from[i]->from[i])
     * 
     * @param from
     * @param to
     */
    public void setSortMapping(String[] from, String[] to) {
        if (from == null) {
            setSortMapping(null);
        } else {
            String[] toArray;
            if (to==null)
                toArray = from;
            else
                toArray = to;
            
            if (from.length != toArray.length)
                throw new IllegalArgumentException("From and To arrays have different length");

            sortMapping = new HashMap<String, String>();
            for (int i = 0; i < from.length; i++) {
                sortMapping.put(from[i], toArray[i]);
            }
        }        
    }

    /**
     * Sets mapping for the sorting parameters.
     * 
     * @param map
     *            if null, parameters will be simply passed-by to a query
     * 
     * 
     */
    public void setSortMapping(Map<String, String> map) {
        this.sortMapping = map;
    }
    public void setCreateQueryId(String createQueryId) {
        this.createQueryId = createQueryId;
    }

    public void setGetQueryId(String getQueryId) {
        this.getQueryId = getQueryId;
    }

    public void setLockQueryId(String lockQueryId) {
        this.lockQueryId = lockQueryId;
    }

    public void setUpdateQueryId(String updateQueryId) {
        this.updateQueryId = updateQueryId;
    }

    public void setDeleteQueryId(String deleteQueryId) {
        this.deleteQueryId = deleteQueryId;
    }

    public void setFindQueryId(String findQueryId) {
        this.findQueryId = findQueryId;
    }

    public void setLimitedFindQueryId(String limitedFindQueryId) {
        this.limitedFindQueryId = limitedFindQueryId;
    }

    public void setCountFindQueryId(String countFindQueryId) {
        this.countFindQueryId = countFindQueryId;
    }

    public void create(DTO dto) {
        if (createQueryId == null)
            throw new UnsupportedOperationException("generic dao create");
        Validator.assertNotNull(dto, "the object to be created");
        performInsert(createQueryId, dto);
    }

    public DTO get(KEY id) {
        if (getQueryId == null)
            throw new UnsupportedOperationException("generic dao get");
        Validator.assertNotNull(id, "the id of the object to be retrieved");
        return (DTO) performQueryForObject(getQueryId, id);
    }

    public boolean lock(KEY id) {
        if (lockQueryId == null)
            throw new UnsupportedOperationException("generic dao lock");
        
        Validator.assertNotNull(id, "the id of the object to be locked");
        return performQueryForObject(lockQueryId, id) != null;
    }

    public void update(DTO dto) {
        if (updateQueryId == null)
            throw new UnsupportedOperationException("generic dao update");
        Validator.assertNotNull(dto, "the object to be updated");
        performUpdate(updateQueryId, dto);
    }


	public void deleteById(KEY id) {
        if (deleteQueryId == null)
            throw new UnsupportedOperationException("generic dao delete");
        Validator.assertNotNull(id, "the id of the object to be deleted");
        performDelete(deleteQueryId, id);
    }

	public void delete(DTO dto) {
        if (deleteQueryId == null)
            throw new UnsupportedOperationException("generic dao delete");
        Validator.assertNotNull(dto, "the object to be deleted");
        performDelete(deleteQueryId, dto);
    }


	public SearchResult<DTO> find(SearchCriteria<DTO> criteria) {
        return find(criteria, null);
    }

    public LimitedSearchResult<DTO> find(SearchCriteria<DTO> criteria,
            long offset, long limit) {
        return find(criteria, offset, limit, null);
    }

    public LimitedSearchResult<DTO> find(SearchCriteria<DTO> criteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        if ((findQueryId == null && limitedFindQueryId == null)
                || countFindQueryId == null) {
            String msg = "generic dao limited find : " 
                +
            ((findQueryId == null && limitedFindQueryId == null) ? "No limitedFindQueryId or findQueryId defined. " : "")
                + (countFindQueryId == null ? " No countFindQueryId defined." : "");
            throw new UnsupportedOperationException(msg);
                    
        }
        
        String queryId = limitedFindQueryId != null ? limitedFindQueryId
                : findQueryId;
        
        return performFind(queryId, countFindQueryId, criteria, offset, limit, sortBy);        
    }

    protected LimitedSearchResult<DTO> performFind(String findQUeryId, String countQueryId, SearchCriteria<DTO> criteria, long offset, long limit, List<SortCriterion> sortBy) {
    	FindParameters params = new FindParameters(criteria).setOrderBy(sortBy).setLimit(offset, limit);
        Integer total = performQueryForObject(countQueryId, params);
        
        List<DTO> list;
        if (total == 0) {
        	list = new ArrayList<DTO>();
        } else {
        	list = performQueryForList(findQUeryId, params);
        }
        
        return new LimitedSearchResult<DTO>(criteria, sortBy, limit, offset,
                list, total);
    }

    protected LimitedSearchResult<DTO> performFind(String findQUeryId, String countQueryId, FindParameters params) {
        Integer total = performQueryForObject(countQueryId, params);

        List<DTO> list;
        if (total == 0) {
        	list = new ArrayList<DTO>();
        } else {
        	list = performQueryForList(findQUeryId, params);
        }
        return new LimitedSearchResult<DTO>(null, null, params.getLimit(), params.getOffset(), list, total);
    }

    public SearchResult<DTO> find(SearchCriteria<DTO> criteria,
            List<SortCriterion> sortBy) {
        if (findQueryId == null)
            throw new UnsupportedOperationException("generic dao find");
        
        FindParameters params = new FindParameters(criteria).setOrderBy(sortBy);
        List<DTO> list = performQueryForList(findQueryId, params);
        return new SearchResult<DTO>(criteria, sortBy, list);
    }

    protected <T> List<T> performQueryForList(String queryId, FindParameters params) {
        Map<String, Object> parameterMap = params.getMap();
        return performQueryForList(queryId, parameterMap);
    }

    protected <T> T performQueryForObject(String queryId, FindParameters params) {
        Map<String, Object> parameterMap = params.getMap();
        return performQueryForObject(queryId, parameterMap);
    }

    public class FindParameters {
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	public FindParameters() {
    		map.put("criteria", new HashMap<String, Object>());
		}
    	
    	public FindParameters(SearchCriteria criteria) {    		
    		map.put("criteria", criteria);    		
    	}
    	
    	public FindParameters setOrderBy(List<SortCriterion> sortBy) {
    		map.put("sortCriteria", mapSortCriteria(sortBy, sortMapping));
    		return this;
    	}
    	
    	public FindParameters setOrderBy(List<SortCriterion> sortBy, Map<String, String> sortMap) {
    		map.put("sortCriteria", mapSortCriteria(sortBy, sortMap));
    		return this;
    	}
    	
    	public FindParameters setLimit(long offset, long limit) {
    		map.put("offset", offset);
            map.put("limit", limit);
            return this;
    	}
    	
    	public Map<String, Object> getMap() {
    		return map;
    	}
    	
    	protected List<SortCriterion> mapSortCriteria(List<SortCriterion> sortBy, Map<String, String> sortMapping) {
            if (sortBy == null || sortBy.size() == 0 || sortMapping == null)
                return sortBy;
            List<SortCriterion> l = new ArrayList<SortCriterion>(sortBy.size());
            for (SortCriterion c : sortBy) {
                String v = sortMapping.get(c.getSortBy());
                if (v != null)
                    l.add(new SortCriterion(v, c.isAscending()));
            }
            
            return l;
        }

		public FindParameters addCriterion(String key, Object value) {
			Object criteria = map.get("criteria");
			if (criteria instanceof Map) {
				((Map) criteria).put(key, value);				
			} else {
				throw new IllegalStateException("Can't add criterion if the criteria was added during the construction of the object");
			}
			return this;
		}
		
		public FindParameters addSpecialParameter(String key, Object value) {
			Object criteria = map.get("special");
			if (criteria == null) {
				criteria = new HashMap<String, Object>();
				map.put("special", criteria);
			}			
			((Map) criteria).put(key, value);				
			
			return this;
		}

        public long getOffset() {
            return (Long)map.get("offset");
        }

        public long getLimit() {
            return (Long)map.get("limit");
        }
    }
}
