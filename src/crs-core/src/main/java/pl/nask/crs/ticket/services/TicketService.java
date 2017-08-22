package pl.nask.crs.ticket.services;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.contacts.exceptions.ContactNotActiveException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface TicketService {

    int PASSED = 1;

    int NEW = 0;

    void checkOut(long ticketId, String hostmasterHandle) throws TicketNotFoundException, TicketAlreadyCheckedOutException;

    void checkIn(long ticketId, AdminStatus status, String hostmasterHandle, String superHostmasterHandle)
            throws TicketNotFoundException, TicketNotCheckedOutException, TicketCheckedOutToOtherException;

    void alterStatus(AuthenticatedUser user, long ticketId, AdminStatus status, String hostmasterHandle, String superHostmasterHandle)
            throws TicketNotFoundException, TicketCheckedOutToOtherException, TicketEmailException, TicketNotCheckedOutException;

    void reassign(long ticketId, String hostmasterHandle) throws TicketNotFoundException, TicketNotCheckedOutException;

    void save(long ticketId, DomainOperation domainOperation, String requestersRemark, String hostmastersRemark, String hostmasterHandle, String superHostmasterHandle)
            throws TicketNotFoundException, EmptyRemarkException, TicketCheckedOutToOtherException, TicketNotCheckedOutException;
    
    void accept(AuthenticatedUser user, long ticketId, String hostmastersRemark, String requestersRemark, String hostmasterHandle, String superHostmasterHandle)
            throws TicketNotFoundException, TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException;

    void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation failureReasons, String requestersRemark, String hostmastersRemark, String hostmasterHandle, String superHostmasterHandle)
            throws TicketNotFoundException, InvalidStatusException, EmptyRemarkException, TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException;

    void update(long ticketId, DomainOperation domainOperation, String requestersRemark, String hostmastersRemark, boolean clikPaid, String hostmasterHandle, String superHostmasterHandle, boolean forceUpdate)
            throws TicketNotFoundException, EmptyRemarkException, TicketCheckedOutToOtherException, TicketNotCheckedOutException, ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketNameserverException;

    /**
     * Update without check out.
     *
     * @param ticketId
     * @param domainOperation
     * @param requestersRemark
     * @param hostmastersRemark
     * @param clikPaid
     * @param hostmasterHandle
     * @param superHostmasterHandle
     * @param forceUpdate
     * @throws TicketNotFoundException
     * @throws EmptyRemarkException
     * @throws ContactNotActiveException
     * @throws TicketEditFlagException
     * @throws ContactNotFoundException
     * @throws TicketAlreadyCheckedOutException when ticket is checked out
     */
    void simpleUpdate(long ticketId, DomainOperation domainOperation, String requestersRemark, String hostmastersRemark, boolean clikPaid, String hostmasterHandle, String superHostmasterHandle, boolean forceUpdate)
            throws TicketNotFoundException, EmptyRemarkException, ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketAlreadyCheckedOutException, TicketNameserverException;

    long createTicket(Ticket newTicket);

	void delete(long ticketId);

    void deleteAndNotify(AuthenticatedUser user, long ticketId) throws TicketEmailException;

    void updateFinanacialStatus(long ticketId, FinancialStatus newFinancialStatus, String hostmasterHandle) throws TicketNotFoundException;

    void updateTechStatus(long ticketId, TechStatus newTechStatus, String hostmasterHandle) throws TicketNotFoundException;

    void updateAdminStatus(long ticketId, AdminStatus newAdminStatus, String hostmasterHandle) throws TicketNotFoundException;
    void updateAdminStatus(long ticketId, AdminStatus newAdminStatus, String hostmasterHandle, String hostmasterRemarks) throws TicketNotFoundException;

    void updateCustomerStatus(long ticketId, CustomerStatus newCustomerStatus, String hostmasterHandle) throws TicketNotFoundException;

    void sendEmail(AuthenticatedUser user, Ticket ticket, TicketEmailType emailType) throws TicketEmailException;

    void sendTicketExpirationEmail(AuthenticatedUser user, Ticket ticket, int daysRemaining);
}
