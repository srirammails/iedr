package pl.nask.crs.contacts.services;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.search.ContactSearchCriteria;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface ContactSearchService {

    LimitedSearchResult<Contact> findContacts(ContactSearchCriteria criteria, long offset, long limit);

    String getDefaultTechContact(String billingContact);

}
