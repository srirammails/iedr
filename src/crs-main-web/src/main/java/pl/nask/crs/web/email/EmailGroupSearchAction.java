package pl.nask.crs.web.email;

import pl.nask.crs.app.email.EmailGroupAppService;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.search.EmailGroupSearchCriteria;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.web.GenericSearchAction;

public class EmailGroupSearchAction extends GenericSearchAction<EmailGroup, EmailGroupSearchCriteria> {

    public EmailGroupSearchAction(EmailGroupAppService emailGroupAppService) {
        super(emailGroupAppService);
    }

    @Override
    protected EmailGroupSearchCriteria createSearchCriteria() {
        return null;
    }

    @Override
    protected int getPageSize() {
        return Integer.MAX_VALUE;
    }
}
