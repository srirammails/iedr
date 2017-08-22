package pl.nask.crs.app;

import pl.nask.crs.app.utils.UserValidator;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.contacts.services.ContactSearchService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.List;

/**
 * Application layer contact search service.
 * <p/>
 * (C) Copyright 2008 NASK Software Research & Development Department
 *
 * @author Artur Gniadzik
 */
public class ContactAppService implements AppSearchService<Contact, ContactSearchCriteria> {

    private ContactSearchService service;

    public ContactAppService(ContactSearchService service) {
        this.service = service;
    }

    public LimitedSearchResult<Contact> search(AuthenticatedUser user, ContactSearchCriteria criteria, long offset, long limit,
                                               List<SortCriterion> orderBy) throws AccessDeniedException {

        UserValidator.validateLoggedIn(user);
        return service.findContacts(criteria, offset, limit);
    }

}
