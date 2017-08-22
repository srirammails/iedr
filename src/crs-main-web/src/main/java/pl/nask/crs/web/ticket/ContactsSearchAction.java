package pl.nask.crs.web.ticket;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.ContactAppService;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.web.GenericSearchAction;

/**
 * @author Patrycja Wegrzynowicz
 */
public class ContactsSearchAction extends GenericSearchAction<Contact, ContactSearchCriteria> {
    private String nicHandleField;
    
    public String getNicHandleField() {
        return nicHandleField;
    }
    
    public void setNicHandleField(String nicHandleField) {
        this.nicHandleField = nicHandleField;
    }
           
    public ContactsSearchAction(AppSearchService<Contact, ContactSearchCriteria> searchService) {
        super(searchService);
    }
    

    @Override
    protected int getPageSize() {
        return 15;
    }

    @Override
    protected ContactSearchCriteria createSearchCriteria() {
        return new ContactSearchCriteria();
    }
    @Override
    public String execute() throws Exception {
        return search();
    }
    
    @Override
    protected void resetSearch() {
        setHiddenSearchCriteria(getSearchCriteria());
    }

}
