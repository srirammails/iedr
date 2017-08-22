package pl.nask.crs.app.utils;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.contacts.services.ContactSearchService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ContactValidator {
    
    private final ContactSearchService contactService;
    private ValidationHelper validationHelper;
    private final ActionSupport as;

    /**
     * 
     * @param as
     *            ActionSupport object which should receive field error
     *            notifications
     * @param contactService
     *            contact service used for nic handle validation
     */
    public ContactValidator(ActionSupport as, ContactSearchService contactService) {
        this.as = as;
        this.validationHelper = new ValidationHelper(as);        
        this.contactService = contactService;
    }

    /**
     * Checks, if the fieldName contains valid nic handle. If not, adds
     * validation message to the ActionSupport and returns false
     * 
     * @param fieldName
     * @param required
     * @param fieldLabel
     * @return
     */
    public boolean validateContact(String fieldName, boolean required, String fieldLabel) {
        if (required) {
            if (!validationHelper.validateStringRequired(fieldName, fieldLabel)) {
                return false;
            }
        }
        if ((!validationHelper.isFieldEmpty(fieldName))
                && (!contactExists(ActionContext.getContext().getValueStack().findString(fieldName)))) {
            as.addFieldError(fieldName, "Provided " + fieldLabel + " is not a valid nichandle");
            return false;
        }
        return true;
    }

    /**
     * checks, if the nic handle given as an argument exists
     * 
     * @param contact
     * @return
     */
    public boolean contactExists(String contact) {
        ContactSearchCriteria c = new ContactSearchCriteria();
        c.setNicHandle(contact);
        LimitedSearchResult<Contact> r = contactService.findContacts(c, 0, 2);
        if (r.getResults().size() == 1)
            return true;
        else
            return false;
    }
}
