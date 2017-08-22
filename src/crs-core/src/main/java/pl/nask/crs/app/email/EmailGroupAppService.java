package pl.nask.crs.app.email;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.search.EmailGroupSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.List;

public interface EmailGroupAppService extends AppSearchService<EmailGroup, EmailGroupSearchCriteria> {

    @Override
    LimitedSearchResult<EmailGroup> search(AuthenticatedUser user, EmailGroupSearchCriteria searchCriteria, long offset, long limit,
                                                     List<SortCriterion> orderBy) throws AccessDeniedException;
    EmailGroup get(AuthenticatedUser user, long id);

    void update(AuthenticatedUser user, EmailGroup group);

    void create(AuthenticatedUser user, EmailGroup group);

    void delete(AuthenticatedUser user, EmailGroup group);

    List<EmailGroup> getAllGroups(AuthenticatedUser user);
}
