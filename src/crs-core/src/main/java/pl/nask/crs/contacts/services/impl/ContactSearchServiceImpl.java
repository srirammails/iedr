package pl.nask.crs.contacts.services.impl;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.contacts.services.ContactSearchService;

/**
 * @author Patrycja Wegrzynowicz
 */
public class ContactSearchServiceImpl implements ContactSearchService {

    private ContactDAO contactDao;

    public ContactSearchServiceImpl(ContactDAO contactDao) {
        Validator.assertNotNull(contactDao, "contact dao");
        this.contactDao = contactDao;
    }

    public LimitedSearchResult<Contact> findContacts(ContactSearchCriteria criteria, long offset, long limit) {
        return contactDao.find(criteria, offset, limit);
    }

    public String getDefaultTechContact(String billingContact) {
        return contactDao.getDefaultTechContact(billingContact);
    }
}
