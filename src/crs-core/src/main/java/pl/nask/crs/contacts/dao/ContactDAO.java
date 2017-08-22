package pl.nask.crs.contacts.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.contacts.Contact;

/**
 * @author Patrycja Wegrzynowicz
 * @author Marianna Mysiorska
 */
public interface ContactDAO extends GenericDAO<Contact, String> {

    String getDefaultTechContact(String billingContact);

    String getContactStatus(String contactNicHandleId);
}
