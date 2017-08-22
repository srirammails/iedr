package pl.nask.crs.web.validators;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.contacts.services.ContactSearchService;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.web.domains.DomainEditAction;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import pl.nask.crs.web.ticket.TicketEditAction;

/**
 *  Only account number is validated
 */
public class ContactAccountValidator extends FieldValidatorSupport {
    private ContactSearchService service;
    private NicHandleSearchService nhService;
    private boolean fieldRequired = false;
    private Long validatedAccountNo;


    public ContactAccountValidator(ContactSearchService service, NicHandleSearchService nhService) {
        Validator.assertNotNull(service, "service");
        this.service = service;
        this.nhService = nhService;
        setDefaultMessage("Not a valid contact");
    }


    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        String name = fieldValue == null ? "" : fieldValue.toString();
        long accountNo;
        if (object instanceof DomainEditAction) {
            accountNo = ((DomainEditAction)object).getDomainWrapper().getAccountNo();
        } else if (object instanceof TicketEditAction) {
            accountNo = ((TicketEditAction) object).getTicketWrapper().getResellerAccount().getAccountId();
        } else {
            return;//skip validation
        }
        
        if (getValidatedAccountNo() != null && accountNo != getValidatedAccountNo()) {
            // validation should be done only for the given account number
            return;
        }
        
        if (isFieldRequired()) {
            if (Validator.isEmpty(name)) {
                setDefaultMessage("Field is required");
                addFieldError(getFieldName(), object);
            }
        }

        long contactAccountNo;
        try {
            contactAccountNo = nhService.getNicHandle(name).getAccount().getId();
        } catch (NicHandleNotFoundException e) {
            return;//skip validation
        }
        if (!Validator.isEmpty(name) && contactExists(name) && accountNo != contactAccountNo) {
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


    public void setFieldRequired(boolean fieldRequired) {
        this.fieldRequired = fieldRequired;
    }


    public boolean isFieldRequired() {
        return fieldRequired;
    }


    public void setValidatedAccountNo(Long validatedAccountNo) {
        this.validatedAccountNo = validatedAccountNo;
    }


    public Long getValidatedAccountNo() {
        return validatedAccountNo;
    }

}
