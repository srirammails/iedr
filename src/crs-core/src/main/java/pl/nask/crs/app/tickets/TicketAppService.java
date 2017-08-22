package pl.nask.crs.app.tickets;

import java.util.List;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.contacts.exceptions.ContactNotActiveException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.InvalidStatusException;
import pl.nask.crs.ticket.exceptions.NicHandleNotFoundException;
import pl.nask.crs.ticket.exceptions.TicketAlreadyCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketCheckedOutToOtherException;
import pl.nask.crs.ticket.exceptions.TicketEditFlagException;
import pl.nask.crs.ticket.exceptions.TicketEmailException;
import pl.nask.crs.ticket.exceptions.TicketNameserverCountException;
import pl.nask.crs.ticket.exceptions.TicketNameserverException;
import pl.nask.crs.ticket.exceptions.TicketNotCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

/**
 * It's an application layer service that provides ticket operations
 * tailored for the ticket view need.
 * All operations are performed in a context of a logged-in user and may end up
 * throwing an access denied exception.
 *
 * @author Patrycja Wegrzynowicz
 */
public interface TicketAppService extends AppSearchService<Ticket, TicketSearchCriteria> {

	// redeclared - this is to allow to make a permission check. you remove it - you break the tests.
	@Override
    LimitedSearchResult<Ticket> search(AuthenticatedUser user, TicketSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException;

    TicketModel view(AuthenticatedUser user, long ticketId) throws AccessDeniedException, TicketNotFoundException;

    TicketModel history(AuthenticatedUser user, long ticketId) throws AccessDeniedException, TicketNotFoundException;

    TicketModel revise(AuthenticatedUser user, long ticketId) throws AccessDeniedException, TicketNotFoundException;

    TicketModel edit(AuthenticatedUser user, long ticketId) throws AccessDeniedException, TicketNotFoundException;

    void checkOut(AuthenticatedUser user, long ticketId) throws AccessDeniedException, TicketNotFoundException, TicketAlreadyCheckedOutException;

    void checkIn(AuthenticatedUser user, long ticketId, AdminStatus status) throws AccessDeniedException, TicketNotFoundException, TicketNotCheckedOutException, TicketCheckedOutToOtherException;

    void alterStatus(AuthenticatedUser user, long ticketId, AdminStatus status) throws AccessDeniedException, TicketNotFoundException, TicketCheckedOutToOtherException, TicketEmailException, TicketNotCheckedOutException;
    void adminDocSent(AuthenticatedUser user, long id) throws AccessDeniedException, TicketNotFoundException, TicketCheckedOutToOtherException, TicketEmailException, TicketNotCheckedOutException;;
    void adminRenew(AuthenticatedUser user, long id) throws AccessDeniedException, TicketNotFoundException, TicketCheckedOutToOtherException, TicketEmailException, TicketNotCheckedOutException;;

    void reassign(AuthenticatedUser user, long ticketId, String hostmasterHandle) throws AccessDeniedException, TicketNotFoundException, TicketNotCheckedOutException;

    void save(AuthenticatedUser user, long ticketId, DomainOperation failureReasons, String hostmastersRemark) throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException, TicketCheckedOutToOtherException, TicketNotCheckedOutException;

    void accept(AuthenticatedUser user, long ticketId, String hostmastersRemark) throws AccessDeniedException, TicketNotFoundException, TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException;
    void accept(AuthenticatedUser user, long ticketId, String requestersRemark, String hostmastersRemark) throws AccessDeniedException, TicketNotFoundException, TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException;
    
    void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation failureReasons, String hostmastersRemark) throws AccessDeniedException, TicketNotFoundException, InvalidStatusException, EmptyRemarkException, TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException;
    void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation failureReasons, String requestersRemark, String hostmastersRemark) throws AccessDeniedException, TicketNotFoundException, InvalidStatusException, EmptyRemarkException, TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException;
    
    void update(AuthenticatedUser user, long ticketId, DomainOperation domainOperation, String hostmastersRemark, boolean clikPaid, boolean forceUpdate) throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException, TicketCheckedOutToOtherException, TicketNotCheckedOutException, ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketNameserverCountException, TicketNameserverException;
    void update(AuthenticatedUser user, long ticketId, DomainOperation domainOperation, String requestersRemark, String hostmastersRemark, boolean clikPaid, boolean forceUpdate) throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException, TicketCheckedOutToOtherException, TicketNotCheckedOutException, ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketNameserverException;
    void simpleUpdate(AuthenticatedUser user, long ticketId, DomainOperation domainOperation, String requestersRemark, String hostmastersRemark, boolean clikPaid, boolean forceUpdate) throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException, TicketAlreadyCheckedOutException, ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketNameserverException;

    long create(AuthenticatedUser completeUser, Ticket ticket)
            throws AccessDeniedException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDontMatchCategoryException, NicHandleNotFoundException, DomainNotFoundException, ValidationException,
            NicHandleRecreateException, DomainIsNotCharityException, DomainIsCharityException, DuplicatedAdminContact,
            pl.nask.crs.nichandle.exception.NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

	/**
	 * Gets one ticket for given domain or <code>NULL</code> if there are no tickets about given domain
	 * @param user logged in user, used for authorization
     * @param domainName name of domain of searched ticket
     *
     * @return null if no ticket if found, full ticket (including dns data) if ticket exists
     * @throws pl.nask.crs.app.tickets.exceptions.TooManyTicketsException if there is more than one ticket
     *     for given domain
	 */
	Ticket getTicketForDomain(AuthenticatedUser user, String domainName);

    /**
     * The same as {@link TicketAppService#getTicketForDomain(pl.nask.crs.security.authentication.AuthenticatedUser, java.lang.String)} but without authorization checks, used only internally
     * @param domainName
     * @return
     */
    Ticket internalGetTicketForDomain(String domainName);

    void sendTicketExpirationEmails(AuthenticatedUser user);
}

