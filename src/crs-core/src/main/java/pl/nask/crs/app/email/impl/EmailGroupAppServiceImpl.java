package pl.nask.crs.app.email.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.nask.crs.app.email.EmailGroupAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.search.EmailGroupSearchCriteria;
import pl.nask.crs.commons.email.service.EmailGroupService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.Collections;
import java.util.List;

public class EmailGroupAppServiceImpl implements EmailGroupAppService {

    private EmailGroupService service;

    public EmailGroupAppServiceImpl(EmailGroupService service) {
        this.service = service;
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<EmailGroup> search(AuthenticatedUser user, EmailGroupSearchCriteria searchCriteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
        return service.findEmailGroups(searchCriteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public EmailGroup get(AuthenticatedUser user, long id) {
        return service.get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AuthenticatedUser user, EmailGroup group) {
        service.update(group, new OpInfo(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(AuthenticatedUser user, EmailGroup group) {
        service.create(group, new OpInfo(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(AuthenticatedUser user, EmailGroup group) {
        service.delete(group, new OpInfo(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailGroup> getAllGroups(AuthenticatedUser user) {
        return service.findEmailGroups(new EmailGroupSearchCriteria(), Collections.singletonList(new SortCriterion("id", true))).getResults();
    }

}
