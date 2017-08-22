package pl.nask.crs.app.triplepass.commands;

import java.util.Date;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.events.GIBOAuthorisation;
import pl.nask.crs.domains.dsm.events.GIBOPaymentFailure;
import pl.nask.crs.domains.dsm.events.GIBOPaymentRetryTimeout;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.ReservationNotFoundException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * Financial check for GIBO domains
 * 
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class GIBORegistrationFC extends FinancialCheck {
	private static final Logger LOG = Logger.getLogger(GIBORegistrationFC.class);
	
    private final String domainName;

    private final DomainSearchService domainSearchService;
    
    private final AccountSearchService accSearchService;

    private final DomainStateMachine dsm;

	private final String remark;

	private int giboPeriod;

    public GIBORegistrationFC(String domainName, PaymentService paymentService, DomainSearchService domainSearchService, DomainStateMachine dsm, AccountSearchService accSearchService, DepositService depositService, String remark, int giboPeriod) {
        super(null, null, paymentService, depositService);
        this.domainSearchService = domainSearchService;
        this.domainName = domainName;
        this.dsm = dsm;
        this.accSearchService = accSearchService;
		this.remark = remark;
		this.giboPeriod = giboPeriod;
    }

    @Override
    public void performFinancialCheck(AuthenticatedUser user) throws FinancialCheckException {
        try {
        	OpInfo opInfo = new OpInfo("GiboFC", remark);
        	Domain domain = domainSearchService.getDomain(domainName);
        	GIBOAuthorisation authEvent = GIBOAuthorisation.getInstance();
        	GIBOPaymentFailure failureEvent = GIBOPaymentFailure.getInstance();
        	if (dsm.validateEvent(domainName, authEvent, failureEvent)) {
        		// check the timeout first!
        		if (domain.getGiboRetryTimeout() != null && new Date().after(domain.getGiboRetryTimeout())) {
        			dsm.handleEvent(user, domain, GIBOPaymentRetryTimeout.getInstance(), opInfo);
        		} else {
        			Account account = accSearchService.getAccount(domain.getResellerAccount().getId());
        			String billingNH = account.getBillingContact().getNicHandle();

        			Reservation reservation = paymentService.getNotReadyReservation(billingNH, domainName);

        			if (reservation == null) {
        				Period registrationPeriod = Period.fromYears(giboPeriod);
        				// UGLY! GIBO domains can be registered for 12 months only
        				long reservationId = paymentService.createADPReservation(billingNH, domainName, registrationPeriod, OperationType.REGISTRATION, null);
        				reservation = paymentService.lockForUpdate(reservationId);
        			}
        			if (!isTransactionInvalidated(reservation.getTransactionId()) && hasRegistrarSufficientFunds(billingNH, reservation.getTotal())) {
        				setReservationAndTransactionReady(reservation);
        				dsm.handleEvent(user, domain, authEvent, opInfo);
        			} else {
        				if (NRPStatus.TransactionFailed != domain.getDsmState().getNRPStatus()) {
        					dsm.handleEvent(user, domain, failureEvent, opInfo);
        				} else {
        					LOG.info("Financial check failed for GIBO domain: " + domainName);
        				}
        			}
        		}
        	} else {
        		LOG.info("Domain status prevents from performing this operation (financial check) on it: " + domain.getDsmState().toString());
        	}
        } catch (DomainNotFoundException e) {
            throw new FinancialCheckException(e);
        } catch (NotAdmissiblePeriodException e) {
            throw new FinancialCheckException(e);
        } catch (NicHandleNotFoundException e) {
            throw new FinancialCheckException(e);
        } catch (ReservationNotFoundException e) {
            throw new FinancialCheckException(e);
        }
    }
}
