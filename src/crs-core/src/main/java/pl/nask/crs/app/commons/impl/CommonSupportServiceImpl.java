package pl.nask.crs.app.commons.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.commons.CommonSupportService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.events.TransferCancellation;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketEmailException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class CommonSupportServiceImpl implements CommonSupportService {

    private final static Logger LOG = Logger.getLogger(CommonSupportServiceImpl.class);

    private final ServicesRegistry servicesRegistry;
    private final DomainStateMachine dsm;


    public CommonSupportServiceImpl(ServicesRegistry servicesRegistry, DomainStateMachine dsm) {
        this.servicesRegistry = servicesRegistry;
        this.dsm = dsm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanupTicket(AuthenticatedUser user, OpInfo opInfo, long ticketId) throws TicketNotFoundException, PaymentException, TicketEmailException, TransactionNotFoundException {
        Ticket ticket = servicesRegistry.getTicketSearchService().lockTicket(ticketId);
        cancelTransaction(user, ticket);
        runTransferCancellationEvent(user, ticket, opInfo);
        servicesRegistry.getTicketService().deleteAndNotify(user, ticket.getId());
    }

    private void cancelTransaction(AuthenticatedUser user, Ticket ticket) throws PaymentException, TransactionNotFoundException {
        Reservation reservation = servicesRegistry.getPaymentService().getReservationForTicket(ticket.getId());
        if (!isTriplePassed(ticket)) {
            if (reservation != null && !reservation.isSettled()) {
                servicesRegistry.getPaymentService().cancelTransaction(user, reservation.getTransactionId());
            } else {
                LOG.error("Transaction cancellation skipped due to reservation state: " + reservation);
            }
        } else {
            LOG.error("Skipping transaction cancellation for ticket due to invalid ticket state, ticketId: " + ticket.getId());
        }
    }

    //duplicated code
    private boolean isTriplePassed(Ticket ticket) {
        return ticket.getFinancialStatus().getId() == FinancialStatusEnum.PASSED.getId();
    }

    private void runTransferCancellationEvent(AuthenticatedUser user, Ticket t, OpInfo opInfo) {
        if (t.getOperation().getType() == DomainOperation.DomainOperationType.XFER) {
            if (dsm.validateEvent(t.getOperation().getDomainNameField().getNewValue(), new TransferCancellation(t))) {
                dsm.handleEvent(user, t.getOperation().getDomainNameField().getNewValue(), new TransferCancellation(t), opInfo);
            }
        }
    }
}
