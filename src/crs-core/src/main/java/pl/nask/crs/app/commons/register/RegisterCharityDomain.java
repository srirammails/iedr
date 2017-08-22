package pl.nask.crs.app.commons.register;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class RegisterCharityDomain extends RegisterDomain {

    public RegisterCharityDomain(AppServicesRegistry appRegistry, ServicesRegistry registry, AuthenticatedUser user, TicketRequest request) {
        super(appRegistry, registry, user, request);
    }

    public long run() throws NicHandleNotFoundException, AccessDeniedException,
            NotAdmissiblePeriodException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException, DomainIsNotCharityException,
            DomainIsCharityException, DuplicatedAdminContact, NicHandleNotActiveException, EmptyRemarkException {
        // specific for the charity domains: registration period must be one year.
        // BPR req#0266
        try {
            checkPeriodInYears(request);
            long ticketId = createRegistrationTicket();
            sendRegistrationApplicationEmail(user, ticketId);
            return ticketId;
        } catch (pl.nask.crs.ticket.exceptions.NicHandleNotFoundException e) {
            throw new NicHandleNotFoundException(e.getNicHandle());
        }
    }

}
