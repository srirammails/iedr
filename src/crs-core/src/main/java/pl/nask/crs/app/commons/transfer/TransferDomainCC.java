package pl.nask.crs.app.commons.transfer;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransferDomainCC extends TransferDomain {

    private final PaymentRequest paymentRequest;

    public TransferDomainCC(AppServicesRegistry appRegistry, ServicesRegistry registry, DomainStateMachine dsm, AuthenticatedUser user, TicketRequest request, PaymentRequest paymentRequest) {
        super(appRegistry, registry, dsm, user, request);
        this.paymentRequest = paymentRequest;
    }

    @Override
    public long run() throws TicketNotFoundException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException,
            AccountNotFoundException, DomainIsNotCharityException, PaymentException, DomainIsCharityException,
            InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException, AccessDeniedException,
            NicHandleNotActiveException, EmptyRemarkException {
        try {
            checkAuthCode();
            checkRenewalPeriod();
            long ticketId = createTransferTicketAndLockDomain();
            // transfer request does not carry information about the domain holder, must retrieve it from the transfered domain.
            Domain d = registry.getDomainSearchService().getDomain(request.getDomainName());
            registry.getPaymentService().createCCReservation(user, user.getUsername(), request.getDomainName(), d.getHolder(), request.getRegPeriod(), OperationType.TRANSFER, paymentRequest, ticketId);
            runTransferEvent(user, registry.getTicketSearchService().getTicket(ticketId));
            return ticketId;
        } catch (pl.nask.crs.ticket.exceptions.NicHandleNotFoundException e) {
            throw new NicHandleNotFoundException(e.getNicHandle());
        }
    }
}
