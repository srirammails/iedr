package pl.nask.crs.web.email;

import pl.nask.crs.app.email.EmailTemplateAppService;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.web.GenericSearchAction;

public class EmailTemplatesSearchAction extends GenericSearchAction<EmailTemplate, EmailTemplateSearchCriteria> {

    private boolean showAll;

    public EmailTemplatesSearchAction(EmailTemplateAppService emailTemplateAppService) {
        super(emailTemplateAppService);
    }

    @Override
    protected EmailTemplateSearchCriteria createSearchCriteria() {
        return new EmailTemplateSearchCriteria();
    }

    @Override
    protected int getPageSize() {
        return showAll ? Integer.MAX_VALUE : super.getPageSize();
    }

    @Override
    protected void updateSearchCriteria(EmailTemplateSearchCriteria searchCriteria) {
        Integer lall = -1;
        if (lall.equals(searchCriteria.getId()))
            searchCriteria.setId(null);

        if ("".equals(searchCriteria.getName()))
            searchCriteria.setName(null);
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }
}
