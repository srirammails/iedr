package pl.nask.crs.app.commons.register;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;

/**
 *
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 *
 * @author Artur Gniadzik
 *
 * Implements logic for UC001-SC01: Registrar Basic (registering the domain using ADP payment)
 */
public class RegisterDomain {
	protected final static Logger LOG = Logger.getLogger(RegisterDomain.class);

	protected final AppServicesRegistry appRegistry;
    protected final ServicesRegistry registry;
	protected final AuthenticatedUser user;
	protected final TicketRequest request;

	public RegisterDomain(AppServicesRegistry appRegistry, ServicesRegistry registry, AuthenticatedUser user, TicketRequest request) {
		this.appRegistry = appRegistry;
        this.registry = registry;
		this.user = user;
		this.request = request;
	}

    public long run() throws NicHandleNotFoundException, AccessDeniedException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, PaymentException, DomainIsCharityException, DuplicatedAdminContact,
            NicHandleNotActiveException, EmptyRemarkException {
        try {
            long id = createRegistrationTicket();
            sendRegistrationApplicationEmail(user, id);
            return id;
        } catch (pl.nask.crs.ticket.exceptions.NicHandleNotFoundException e) {
            throw new NicHandleNotFoundException(e.getNicHandle());
        }
    }

    protected void sendRegistrationApplicationEmail(AuthenticatedUser user, long ticketId) {
        try {
            String username = (user == null) ? null : user.getUsername();
            Ticket ticket = registry.getTicketSearchService().getTicket(ticketId);
            TicketEmailParameters parameters = new TicketEmailParameters(username, ticket);
            DomainOperation.DomainOperationType type = ticket.getOperation().getType();
            registry.getEmailTemplateSender().sendEmail(EmailTemplateNamesEnum.NREG_APPLICATION.getId(), parameters);
        } catch (Exception e) {
            LOG.warn("Error while sending new registration application email", e);
        }
    }

	public static void checkPeriodInYears(TicketRequest request) throws NotAdmissiblePeriodException {
		try {
			if (request.getRegPeriod().getYears() != 1) 
				throw new NotAdmissiblePeriodException(request.getRegPeriod().getYears(), "Y");
		} catch (IllegalStateException e) {
			throw new NotAdmissiblePeriodException(request.getRegPeriod().getMonths(), "M");
		}
	}

    protected long createRegistrationTicket() throws NicHandleNotFoundException, AccessDeniedException, pl.nask.crs.ticket.exceptions.NicHandleNotFoundException, NotAdmissiblePeriodException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException, DomainIsNotCharityException, DomainIsCharityException, DuplicatedAdminContact, NicHandleNotActiveException, EmptyRemarkException {
        NicHandle creatorNh = registry.getNicHandleSearchService().getNicHandle(user.getUsername());
        Account account = registry.getAccountSearchService().getAccount(creatorNh.getAccount().getId());

        // check the registration period
        int periodInYears = request.getRegPeriod().getYears();
        registry.getPaymentService().getProductPrice(Period.fromYears(periodInYears), OperationType.REGISTRATION, user.getUsername());

        return appRegistry.getTicketAppService().create(user, request.toTicket(user.getUsername(), account.getId(), user.getUsername(), DomainOperation.DomainOperationType.REG));
    }
}
