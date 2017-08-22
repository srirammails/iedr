package pl.nask.crs.contacts.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public class ConvertingContactDAO extends ConvertingGenericDAO<InternalContact, Contact, String> implements ContactDAO {

    private InternalContactIBatisDAO internalDAO;

    public ConvertingContactDAO(InternalContactIBatisDAO internalDAO, Converter<InternalContact, Contact> internalConverter) {
        super(internalDAO, internalConverter);
        this.internalDAO = internalDAO;
    }

    public String getDefaultTechContact(String billingContact) {
        return internalDAO.getDefaultTechContact(billingContact);
    }

    public String getContactStatus(String contactNicHandleId) {
        return internalDAO.getContactStatus(contactNicHandleId);
    }
}
