package pl.nask.crs.user.service.impl;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.HistoricalUserDAO;
import pl.nask.crs.user.search.HistoricalUserSearchCriteria;
import pl.nask.crs.user.service.HistoricalUserSearchService;

public class HistoricalUserSearchServiceImpl implements HistoricalUserSearchService {

    private final HistoricalUserDAO historicalUserDao;
    
    public HistoricalUserSearchServiceImpl(HistoricalUserDAO historicalUserDao) {
        Validator.assertNotNull(historicalUserDao, "historical user DAO");
        this.historicalUserDao = historicalUserDao;
    }
    
    
    public LimitedSearchResult<HistoricalObject<User>> find(HistoricalUserSearchCriteria searchCriteria, long offset, long limit) {
        return historicalUserDao.find(searchCriteria, offset, limit);
    }

}
