package pl.nask.crs.user.service;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.user.InternalLoginUser;
import pl.nask.crs.user.User;
import pl.nask.crs.user.search.UserSearchCriteria;

import java.util.List;

/**
 * @author Marianna Mysiorska
 */
public interface UserSearchService {

    public LimitedSearchResult<User> find(UserSearchCriteria searchCriteria, long offset, long limit);

    public SearchResult<User> find(UserSearchCriteria searchCriteria);

    public User get(String nicHandleId);

    public List<InternalLoginUser> getInternalUsers();
}
