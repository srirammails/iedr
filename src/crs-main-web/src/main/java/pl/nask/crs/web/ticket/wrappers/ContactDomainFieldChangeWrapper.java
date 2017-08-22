package pl.nask.crs.web.ticket.wrappers;

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.services.FailureReasonFactory;

/**
 * @author Patrycja Wegrzynowicz
 */
public class ContactDomainFieldChangeWrapper extends
        SimpleDomainFieldChangeWrapper<Contact> {

    public ContactDomainFieldChangeWrapper(
            SimpleDomainFieldChange<Contact> orig,
            FailureReasonFactory frFactory, Field dataField, DomainOperationType type) {
        super(orig, frFactory, dataField, type);
    }

    public Contact getNewValue() {
        return getOrig().getNewValue();
    }

    public void setNewValue(Contact newValue) {
        getOrig().setNewValue(newValue);
    }

    public String getNicHandle() {
        Contact contact = getNewValue();
        return contact == null ? null : contact.getNicHandle();
    }

    public void setNicHandle(String nicHandle) {
        if (nicHandle == null || nicHandle.trim().length() == 0) {
            setNewValue(null);
        } else {
            Contact newContact = new Contact(nicHandle);
            if (!newContact.equals(getNewValue())) {
                setNewValue(newContact);
            }
        }
    }

}
