package pl.nask.crs.commons.email.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;

import java.util.List;
import java.util.Map;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface EmailTemplateDAO extends GenericDAO<EmailTemplate, Integer> {

    String getNameById(int emailTemplateId);

    SearchResult<EmailTemplate> findAndLockInShareMode(EmailTemplateSearchCriteria criteria);

    SearchResult<EmailTemplate> findAndLockForUpdate(EmailTemplateSearchCriteria criteria);
}
