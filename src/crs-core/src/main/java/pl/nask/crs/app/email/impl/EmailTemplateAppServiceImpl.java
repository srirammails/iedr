package pl.nask.crs.app.email.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.nask.crs.app.email.EmailTemplateAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.email.service.EmailTemplateService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.List;

public class EmailTemplateAppServiceImpl implements EmailTemplateAppService {

    private EmailTemplateService emailTemplateService;

    public EmailTemplateAppServiceImpl(EmailTemplateService newEmailTemplateService) {
        Validator.assertNotNull(newEmailTemplateService, "email template service");
        emailTemplateService = newEmailTemplateService;
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<EmailTemplate> search(AuthenticatedUser user, EmailTemplateSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
        return emailTemplateService.findEmailTemplate(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public EmailTemplate get(AuthenticatedUser user, int id) {
        return emailTemplateService.getEmailTemplate(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailTemplate saveEditableFields(AuthenticatedUser user, EmailTemplate emailTemplate) {
        return emailTemplateService.save(emailTemplate, new OpInfo(user));
    }
}
