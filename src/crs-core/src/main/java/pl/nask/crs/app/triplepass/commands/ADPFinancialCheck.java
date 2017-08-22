package pl.nask.crs.app.triplepass.commands;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import pl.nask.crs.app.triplepass.email.FinanacialCheckEmailParameters;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositInfo;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.TransactionDetails;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.ReservationNotFoundException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.TicketService;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ADPFinancialCheck extends FinancialCheck {
	private final static Logger LOG = Logger.getLogger(ADPFinancialCheck.class);

    protected Long reservationId;

    public ADPFinancialCheck(Ticket ticket, Long reservationId, TicketService ticketService, PaymentService paymentService, EmailTemplateSender emailTemplateSender, NicHandleSearchService nicHandleSearchService, DepositService depositService, DomainSearchService domainSearchService) {
        super(ticket, ticketService, paymentService, emailTemplateSender, nicHandleSearchService, depositService, domainSearchService);
        this.reservationId = reservationId;
    }

    @Override
    public void performFinancialCheck(AuthenticatedUser user) throws FinancialCheckException {
        try {
            String billingNH = ticket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
            String domainName = ticket.getOperation().getDomainNameField().getNewValue();
            boolean emailNotificationAlreadySent = (ticket.getFinancialStatus().getId() == FinancialStatusEnum.STALLED.getId());

            if (reservationId == null) {
                reservationId = paymentService.createADPReservation(billingNH, domainName, ticket.getDomainPeriod(), getOperationType(), ticket.getId());
            }
            Reservation reservation = paymentService.lockForUpdate(reservationId);
            DepositInfo depositInfo = getOrInitDeposit(billingNH);

            if (isTransactionInvalidated(reservation.getTransactionId())) {
            	setFinancialStatusStalled(ticket.getId());
            } else if (hasRegistrarSufficientFunds(depositInfo, reservation.getTotal())) {
                setReservationReadyAndTicketFinancialPassed(ticket, reservation);
                sendConfirmationEmail(user, billingNH, domainName, reservation.getTotal());
            } else {
            	setFinancialStatusStalled(ticket.getId());
            	if (!emailNotificationAlreadySent) {
            		sendNotificationEmail(user, depositInfo, domainName, reservation.getTotal());
            	}
            }
        } catch (NotAdmissiblePeriodException e) {
            throw new FinancialCheckException(e);
        } catch (TicketNotFoundException e) {
            throw new FinancialCheckException(e);
        } catch (NicHandleNotFoundException e) {
            throw new FinancialCheckException(e);
        } catch (ReservationNotFoundException e) {
            throw new FinancialCheckException(e);
        }
    }
    
    protected void sendNotificationEmail(AuthenticatedUser user, Deposit deposit, String domainName, BigDecimal value) {
    	try {
            String username = (user == null) ? null : user.getUsername();
    		DomainOperation.DomainOperationType operationType = ticket.getOperation().getType();
    		NicHandle billingNH = nicHandleSearchService.getNicHandle(deposit.getNicHandleId());
    		int years = ticket.getDomainPeriod().getYears();
    		String domainHolder = ticket.getOperation().getDomainHolderField().getNewValue();
    		TransactionDetails details = null;
    		FinanacialCheckEmailParameters params = null;
    		switch (operationType) {
    		case REG:
    			details = new TransactionDetails(domainName, domainHolder, years, OperationType.REGISTRATION, value);
    			params = new FinanacialCheckEmailParameters(username, billingNH, domainName, operationType, details, value);
    			emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_NREG.getId(), params);
    			break;
    		case XFER:
    			Domain d = domainSearchService.getDomain(domainName);
    			details = new TransactionDetails(domainName, domainHolder, years, OperationType.TRANSFER, d.getRegistrationDate(), d.getRenewDate(), value);
    			params = new FinanacialCheckEmailParameters(username, billingNH, domainName, operationType, details, value);
    			emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_XFER.getId(), params);
    			break;
    		default:
    			throw new IllegalArgumentException("Invalid operation type: " + operationType);
    		}
    	} catch (Exception e) {
    		LOG.warn("Problem with email during financial check occured ", e);
    	}
    }

    private OperationType getOperationType() {
        switch (ticket.getOperation().getType()) {
            case REG:
                return OperationType.REGISTRATION;
            case XFER:
                return OperationType.TRANSFER;
            default:
                throw new IllegalStateException("Illegal ticket type: " + ticket.getOperation().getType());
        }
    }

}
