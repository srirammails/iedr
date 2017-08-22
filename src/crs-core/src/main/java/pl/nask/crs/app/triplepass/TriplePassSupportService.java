package pl.nask.crs.app.triplepass;

import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.app.triplepass.exceptions.TechnicalCheckException;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketEmailException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface TriplePassSupportService {

    public void performFinancialCheck(AuthenticatedUser user, long ticketId) throws FinancialCheckException, TicketIllegalStateException, TicketNotFoundException;

    /**
     * promotes the registration ticket to a domain object
     * @param user logged in user of the application performing the operation
     * @param ticketId ID of the registration ticket to be promoted to a domain object. Only fully passed registration tickets may be promoted
     * @return the name of the created domain
     * @throws TicketIllegalStateException if the ticket is not a registration ticket or it's not fully passed. 
     * @throws TicketNotFoundException if there is no ticket with such id
     */

	String promoteTicketToDomain(AuthenticatedUser user, long ticketId) throws TicketIllegalStateException, TicketNotFoundException;

    String promoteTransferTicket(AuthenticatedUser user, long ticketId) throws TicketIllegalStateException, TicketNotFoundException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    String promoteModificationTicket(long ticketId) throws TicketIllegalStateException, TicketNotFoundException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException;

    public void performTechnicalCheck(AuthenticatedUser user, long ticketId, boolean interactive) throws TicketIllegalStateException, TicketNotFoundException, TechnicalCheckException, HostNotConfiguredException;

	public void performFinancialCheckOnGIBO(AuthenticatedUser user, String domainName, String remark) throws FinancialCheckException, DomainNotFoundException ;

	public void giboAdminFailed(AuthenticatedUser user, String domainName, OpInfo opInfo);

    public void performTicketCancellation(AuthenticatedUser user, long ticketId) throws TicketIllegalStateException, TicketNotFoundException, TransactionNotFoundException, PaymentException;
}
