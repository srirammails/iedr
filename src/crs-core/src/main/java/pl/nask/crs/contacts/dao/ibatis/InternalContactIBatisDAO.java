package pl.nask.crs.contacts.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InternalContactIBatisDAO extends GenericIBatisDAO<InternalContact, String> {

    public InternalContactIBatisDAO() {
        setCreateQueryId("contact.createContact");
        setFindQueryId("contact.findContacts");
        setCountFindQueryId("contact.countContacts");
        setUpdateQueryId("contact.updateContact");
        setDeleteQueryId("contact.deleteContact");
        setGetQueryId("contact.getContactByNicHandleId");
    }

    public String getDefaultTechContact(String billingContact) {
        return performQueryForObject("contact.getDefaultTechContact", billingContact);
    }

    public String getContactStatus(String contactNicHandleId) {
        return performQueryForObject("contact.getContactStatus", contactNicHandleId);
    }
}
