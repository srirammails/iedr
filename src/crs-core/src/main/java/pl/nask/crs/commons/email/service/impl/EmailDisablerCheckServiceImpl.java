package pl.nask.crs.commons.email.service.impl;

import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.service.EmailDisablerCheckService;

public class EmailDisablerCheckServiceImpl implements EmailDisablerCheckService {
    private EmailDisablerDAO disablerDAO;
    private EmailTemplateDAO emailTemplateDAO;

    public EmailDisablerCheckServiceImpl(EmailDisablerDAO disablerDAO, EmailTemplateDAO emailTemplateDAO) {
        this.disablerDAO = disablerDAO;
        this.emailTemplateDAO = emailTemplateDAO;
    }

    @Override
    public boolean shouldSendToExternal(int templateId, String domain, String owner, String user) {
        // Send if...
        EmailDisabler disabler = disablerDAO.get(new EmailDisablerKey(templateId, owner));
        EmailTemplate template = (disabler != null) ? disabler.getEmailTemplate() : emailTemplateDAO.get(templateId);
        // The email is not suppressible...
        if (template == null || !template.isSuppressible()) {
            return true;
        }
        // Or account owner did not disable email...
        if (disabler == null || !disabler.isDisabled()) {
            return true;
        }
        // Or the current user is an admin or tech contact for the domain.
        if(domain != null && disablerDAO.isNhAdminOrTechForDomain(user, domain)) {
            return true;
        }
        // Otherwise disable.
        return false;
    }
}
