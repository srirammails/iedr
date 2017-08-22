package pl.nask.crs.app.email;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.List;


public interface EmailTemplateAppService extends AppSearchService<EmailTemplate, EmailTemplateSearchCriteria> {

    @Override
    LimitedSearchResult<EmailTemplate> search(AuthenticatedUser user, EmailTemplateSearchCriteria criteria, long offset, long limit,
                                         List<SortCriterion> orderBy) throws AccessDeniedException;

    EmailTemplate get(AuthenticatedUser user, int id);

    EmailTemplate saveEditableFields(AuthenticatedUser user, EmailTemplate emailTemplate);

}
