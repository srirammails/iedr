package pl.nask.crs.app.triplepass.commands;

import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.services.TicketService;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class CharityFinancialCheck extends FinancialCheck {

    public CharityFinancialCheck(Ticket ticket, TicketService ticketService, PaymentService paymentService, DepositService depositService) {
        super(ticket, ticketService, paymentService, depositService);
    }

    @Override
    public void performFinancialCheck(AuthenticatedUser user) throws FinancialCheckException{
        try {
        	setFinancialStatusPassed(ticket.getId());
        } catch (TicketNotFoundException e) {
            throw new FinancialCheckException(e);
        }
    }
}
