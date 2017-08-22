package pl.nask.crs.app.commons.transfer;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.dsm.events.TransferRequest;
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
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransferDomain {

    protected final AppServicesRegistry appRegistry;
    protected final ServicesRegistry registry;
    protected final DomainStateMachine dsm;
    protected final AuthenticatedUser user;
    protected final TicketRequest request;

    public TransferDomain(AppServicesRegistry appRegistry, ServicesRegistry registry, DomainStateMachine dsm, AuthenticatedUser user, TicketRequest request) {
        this.appRegistry = appRegistry;
        this.registry = registry;
        this.dsm = dsm;
        this.user = user;
        this.request = request;
    }

    public long run() throws TicketNotFoundException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException,
            AccountNotFoundException, DomainIsNotCharityException, PaymentException, DomainIsCharityException,
            InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException, AccessDeniedException, NicHandleNotActiveException,
            EmptyRemarkException {
        try {
            checkAuthCode();
            if (isDirect()) {
                throw new PaymentException("Deposit payment not possible for Direct account");
            }
            checkRenewalPeriod();
            long ticketId = createTransferTicketAndLockDomain();
            runTransferEvent(user, registry.getTicketSearchService().getTicket(ticketId));
            return ticketId;
        } catch (pl.nask.crs.ticket.exceptions.NicHandleNotFoundException e) {
            throw new NicHandleNotFoundException(e, e.getNicHandle());
        }
    }

    private boolean isDirect() throws NicHandleNotFoundException {
        return registry.getNicHandleSearchService().getNicHandle(user.getUsername()).getAccount().getId() == 1;
    }

    protected long createTransferTicketAndLockDomain() throws NicHandleNotFoundException, AccessDeniedException, pl.nask.crs.ticket.exceptions.NicHandleNotFoundException, NotAdmissiblePeriodException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException, DomainIsNotCharityException, DomainIsCharityException, DuplicatedAdminContact, NicHandleNotActiveException, EmptyRemarkException {
        dsm.validateEvent(request.getDomainName(), DsmEventName.TransferRequest);
        NicHandle NH = registry.getNicHandleSearchService().getNicHandle(user.getUsername());
        Account account = registry.getAccountSearchService().getAccount(NH.getAccount().getId());
        return appRegistry.getTicketAppService().create(user, request.toTicket(user.getUsername(), account.getId(), user.getUsername(), DomainOperation.DomainOperationType.XFER));
    }

    protected void runTransferEvent(AuthenticatedUser user, Ticket t) {
        dsm.handleEvent(user, request.getDomainName(), new TransferRequest(t), new OpInfo(user));
    }

    protected void checkRenewalPeriod() throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        int periodInYears = request.getRegPeriod().getYears();
        registry.getPaymentService().getProductPrice(Period.fromYears(periodInYears), OperationType.TRANSFER, user.getUsername());
    }

    protected void checkAuthCode() throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        registry.getDomainService().verifyAuthCode(user, request.getDomainName(), request.getAuthCode());
    }

}
