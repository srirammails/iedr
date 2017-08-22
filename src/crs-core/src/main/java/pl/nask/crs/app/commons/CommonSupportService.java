package pl.nask.crs.app.commons;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.exceptions.TicketEmailException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public interface CommonSupportService {

    void cleanupTicket(AuthenticatedUser user, OpInfo opInfo, long ticketId) throws TicketNotFoundException, PaymentException, TicketEmailException, TransactionNotFoundException;

}
