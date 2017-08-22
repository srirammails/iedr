package pl.nask.crs.user.service.impl;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.user.InternalLoginUser;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.search.UserSearchCriteria;
import pl.nask.crs.user.User;

import java.util.List;

/**
 * @author Marianna Mysiorska
 */
public class UserSearchServiceImpl implements UserSearchService {

    private UserDAO dao;

    public UserSearchServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    public LimitedSearchResult<User> find(UserSearchCriteria searchCriteria, long offset, long limit) {
        return dao.find(searchCriteria, offset, limit);
    }

    public SearchResult<User> find(UserSearchCriteria searchCriteria) {
        return dao.find(searchCriteria);
    }

    public User get(String nicHandleId) {
        return dao.get(nicHandleId);
    }

    @Override
    public List<InternalLoginUser> getInternalUsers() {
        return dao.getInternalUsers();
    }
}
