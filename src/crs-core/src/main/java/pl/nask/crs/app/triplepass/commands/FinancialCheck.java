package pl.nask.crs.app.triplepass.commands;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import pl.nask.crs.app.triplepass.email.FinanacialCheckEmailParameters;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.DepositInfo;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionDetails;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.services.TicketService;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public abstract class FinancialCheck {

    private static final Logger LOG = Logger.getLogger(FinancialCheck.class);

    protected final Ticket ticket;

    protected final TicketService ticketService;

    protected final PaymentService paymentService;
    
    protected final DepositService depositService;

    protected EmailTemplateSender emailTemplateSender;

    protected NicHandleSearchService nicHandleSearchService;
    
    protected DomainSearchService domainSearchService;

    protected FinancialCheck(Ticket ticket, TicketService ticketService, PaymentService paymentService, EmailTemplateSender emailTemplateSender, NicHandleSearchService nicHandleSearchService, DepositService depositService, DomainSearchService domainSearchService) {
        this.ticket = ticket;
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.emailTemplateSender = emailTemplateSender;
        this.nicHandleSearchService = nicHandleSearchService;
        this.depositService = depositService;
		this.domainSearchService = domainSearchService;
    }

    protected FinancialCheck(Ticket ticket, TicketService ticketService, PaymentService paymentService, DepositService depositService) {
        this.ticket = ticket;
        this.ticketService = ticketService;
        this.paymentService = paymentService;
		this.depositService = depositService;
    }

    public abstract void performFinancialCheck(AuthenticatedUser user) throws FinancialCheckException;

    protected void setReservationReadyAndTicketFinancialPassed(Ticket t, Reservation reservation) throws TicketNotFoundException {
        setReservationAndTransactionReady(reservation);
        setFinancialStatusPassed(t.getId());
    }
    
    protected void setFinancialStatusPassed(long id) throws TicketNotFoundException {
    	LOG.info("Setting financial status to Passed, ticketId=" + id);
    	ticketService.updateFinanacialStatus(id, FinancialStatusEnum.PASSED, "Financial");		
	}

	protected void setFinancialStatusStalled(long ticketId) throws TicketNotFoundException {
    	LOG.info("Setting financial status to Stalled, ticketId=" + ticketId);
        ticketService.updateFinanacialStatus(ticketId, FinancialStatusEnum.STALLED, "Financial");
    }

    protected void setReservationAndTransactionReady(Reservation reservation) {
        try {
            reservation.setReadyForSettlement(true);
            paymentService.updateReservation(reservation);
            paymentService.setTransactionFinanciallyPassed(reservation.getTransactionId());
        } catch (TransactionNotFoundException e) {
            throw new IllegalStateException("Transaction not found for reservation", e);
        }
    }

    protected DepositInfo getOrInitDeposit(String billingNH) {
        try {
            return depositService.viewDeposit(billingNH);
        } catch (DepositNotFoundException e) {
            return depositService.initDeposit(billingNH);
        }
    }

    protected boolean hasRegistrarSufficientFunds(DepositInfo depositInfo, BigDecimal amountWithVat) {
        return depositInfo.getCloseBalIncReservaions().compareTo(amountWithVat) == 0 || depositInfo.getCloseBalIncReservaions().compareTo(amountWithVat) == 1;
    }

    protected boolean hasRegistrarSufficientFunds(String billingNH, BigDecimal amountWithVat) {
        try {
            DepositInfo depositInfo = depositService.viewDeposit(billingNH);
            return hasRegistrarSufficientFunds(depositInfo, amountWithVat);
        } catch (DepositNotFoundException e) {
            return false;
        }
    }

    protected boolean isTransactionInvalidated(long transactionId) {
        try {
            Transaction transaction = paymentService.getTransaction(transactionId);
            return transaction.isInvalidated();
        } catch (TransactionNotFoundException e) {
            LOG.error("Transaction not found, transactionId=" + transactionId);
            throw new IllegalStateException("Transaction not found, transactionId=" + transactionId);
        }
    }
    

    protected void sendConfirmationEmail(AuthenticatedUser user, String billingNHName, String domainName, BigDecimal value) {
    	try {
    		DomainOperation.DomainOperationType operationType = ticket.getOperation().getType();
    		if (operationType == DomainOperationType.REG) {
    			String username = (user == null) ? null : user.getUsername();
    			NicHandle billingNH = nicHandleSearchService.getNicHandle(billingNHName);
    			int years = ticket.getDomainPeriod().getYears();
    			String domainHolder = ticket.getOperation().getDomainHolderField().getNewValue();
    			TransactionDetails details = null;
    			FinanacialCheckEmailParameters params = null;
   				details = new TransactionDetails(domainName, domainHolder, years, OperationType.REGISTRATION, value);
   				params = new FinanacialCheckEmailParameters(username, billingNH, domainName, operationType, details, value);
   				emailTemplateSender.sendEmail(EmailTemplateNamesEnum.NREG_ADP_MONEY_RESEVED.getId(), params);
    		}
    	} catch (Exception e) {
    		LOG.warn("Problem with email during financial check occured ", e);
    	}
    }
}
