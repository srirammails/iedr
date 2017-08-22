package pl.nask.crs.commons.email.service;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import java.util.List;

public interface EmailTemplateService {

    LimitedSearchResult<EmailTemplate> findEmailTemplate(EmailTemplateSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy);

    SearchResult<EmailTemplate> findEmailTemplate(EmailTemplateSearchCriteria criteria);

    EmailTemplate getEmailTemplate(int id);

    EmailTemplate save(EmailTemplate template, OpInfo op);

    void onGroupDeleted(EmailGroup group, OpInfo op);
}
