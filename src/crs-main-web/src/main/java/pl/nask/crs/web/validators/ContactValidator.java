package pl.nask.crs.web.validators;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.contacts.services.ContactSearchService;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class ContactValidator extends FieldValidatorSupport {
    private ContactSearchService service;

    public ContactValidator(ContactSearchService service) {
        Validator.assertNotNull(service, "service");
        this.service = service;
        setDefaultMessage("Not a valid contact");
    }
    
    
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        String name = fieldValue == null ? null : fieldValue.toString();

        if (!Validator.isEmpty(name) && !contactExists(name)) {
            addFieldError(getFieldName(), object);
        }
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
        LimitedSearchResult<Contact> r = service.findContacts(c, 0, 2);
        if ((r.getResults().size() == 1) && contact.equalsIgnoreCase(r.getResults().get(0).getNicHandle()))
            return true;
        else
            return false;
    }

}
