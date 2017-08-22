package pl.nask.crs.app.triplepass;

import static pl.nask.crs.app.triplepass.TriplePassHelper.isAdminCancelled;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isFullPassed;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isModificationTicket;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isRegistrationTicket;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isTransferTicket;
import static pl.nask.crs.app.triplepass.TriplePassHelper.needsDnsCheck;
import static pl.nask.crs.app.triplepass.TriplePassHelper.needsFinancialCheck;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.app.triplepass.exceptions.TechnicalCheckException;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.CustomerStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;

/**
 * 
 * @author Artur Gniadzik
 *
 */
public class TriplePassAppServiceImpl implements TriplePassAppService {
	private static final Logger LOG = Logger.getLogger(TriplePassAppServiceImpl.class);

	private TicketAppService ticketAppService;
    private TriplePassSupportService triplePassSupportService;
    private TicketSearchService ticketSearchService;
    private DomainSearchService domainSearchService;

	public TriplePassAppServiceImpl(TicketAppService ticketAppService, TriplePassSupportService triplePassSupportService,
                                    TicketSearchService ticketSearchService, DomainSearchService domainSearchService) {
		this.ticketAppService = ticketAppService;
        this.triplePassSupportService = triplePassSupportService;
        this.ticketSearchService = ticketSearchService;
        this.domainSearchService = domainSearchService;
	}

    @Override
    public void triplePass() {
        List<Ticket> tickets = getAdminPassedTickets();
        for (Ticket ticket : tickets) {
            String domainName = ticket.getOperation().getDomainNameField().getNewValue();
            performTriplePass(domainName);
        }
        List<Domain> domains = getGIBODomains();
        for (Domain domain : domains) {
            performTriplePass(domain.getName());
        }
    }

    private void performTriplePass(String domainName) {
        try {
            LOG.info("Performing triple pass for domain: " + domainName);
            triplePass(null, domainName);
        } catch (Exception e) {
            LOG.error("Something wrong with domain: " + domainName, e);
        }
    }

    private List<Domain> getGIBODomains() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNrpStatuses(Arrays.asList(NRPStatus.TransactionFailed, NRPStatus.PostTransactionAudit));
        return domainSearchService.findAll(criteria, Arrays.asList(new SortCriterion("registrationDate", true)));
    }

    private List<Ticket> getAdminPassedTickets() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setAdminStatus(AdminStatusEnum.PASSED.getId());
        criteria.setCustomerStatus(CustomerStatusEnum.NEW);
        return ticketSearchService.findAll(criteria, Arrays.asList(new SortCriterion("id", true)));
    }

    @Override
    public boolean triplePass(AuthenticatedUser user, String domainName) {
		return triplePass(user, domainName, null);
	}
	
	// IMPORTANT: this method can't be wrapped with a transaction!
    @Override
	public boolean triplePass(AuthenticatedUser user, String domainName, String remark) {
		Ticket t = ticketAppService.internalGetTicketForDomain(domainName);
		if (t == null) {
			LOG.info("No ticket for domain " + domainName + " found, assuming a GIBO domain");
			return performGIBOCheck(user, domainName, remark);
		} else if (!isRegistrationTicket(t) && !isTransferTicket(t) && !isModificationTicket(t)) {
			LOG.warn("Not a registration nor transfer nor modification ticket " + info(t));
			return false;
		} else if (isAdminCancelled(t)) {
            LOG.info("Ticket admin cancelled " + info(t));
            performTicketCancellation(user, t);
            return false;
        }

		if (needsDnsCheck(t)) {
			performDnsCheck(user, t);
            t = ticketAppService.internalGetTicketForDomain(domainName);
		}

		if (needsFinancialCheck(t)) {
			performFinancialCheck(user, t);
            t = ticketAppService.internalGetTicketForDomain(domainName);
		}		

		if (isFullPassed(t)) {
			return performTicketPromotion(user, t);
		} else {
			return false;
		}
		
	}

	private boolean performGIBOCheck(AuthenticatedUser user, String domainName, String remark) {
		try {
			triplePassSupportService.performFinancialCheckOnGIBO(user, domainName, remark);
			return true;
		} catch (FinancialCheckException e) {
			LOG.warn("Financial check failed for GIBO domain: " + domainName, e);
		} catch (DomainNotFoundException e) {
			LOG.warn("No such domain: " + domainName);
		}
		return false;
	}

	private void performFinancialCheck(AuthenticatedUser user, Ticket t) {
		try {
			triplePassSupportService.performFinancialCheck(user, t.getId());
		} catch (TicketNotFoundException e) {
			handle(e, t);
		} catch (FinancialCheckException e) {
			handle(e, t);
		} catch (TicketIllegalStateException e) {
			handle(e, t);
		}
		
	}

	private void handle(FinancialCheckException e, Ticket t) {
		LOG.warn("Financial pass failed, " + info(t), e);
	}

	private void handle(TicketNotFoundException e, Ticket t) {
		LOG.warn("No ticket found " + info(t), e);
	}

	private boolean performTicketPromotion(AuthenticatedUser user, Ticket t) {
		try {
            if (isRegistrationTicket(t)) {
                triplePassSupportService.promoteTicketToDomain(user, t.getId());
                return true;
            } else if (isTransferTicket(t)) {
                triplePassSupportService.promoteTransferTicket(user, t.getId());
                return true;
            } else if (isModificationTicket(t)) {
                triplePassSupportService.promoteModificationTicket(t.getId());
                return true;
            }
		} catch (TicketNotFoundException e) {
			handle(e, t);
		} catch (TicketIllegalStateException e) {
			handle(e, t);
		} catch (DomainNotFoundException e) {
            LOG.warn("Domain not found: " + e.getDomainName() + " for ticket: " + info(t));
        } catch (NicHandleNotFoundException e) {
            LOG.warn("Nichandle not found: " + e.getNicHandleId() + " for ticket: " + info(t));
        } catch (NicHandleNotActiveException e) {
            LOG.warn("Nichandle not active: " + e.getNicHandleId() + " for ticket: " + info(t));
        } catch (EmptyRemarkException e) {
            LOG.warn("Empty remark for ticket: " + info(t));
        }
		return false;
	}

	private void handle(TicketIllegalStateException e, Ticket t) {
		LOG.warn("Illegal state of the ticket " + info(t) + ", got " + info(e.getTicket()));		
	}

    private void handle(TechnicalCheckException e, Ticket t) {
        LOG.warn("Technical pass error, " + info(t) + " Reason: " + e.getMessage());
    }

    private void handle(HostNotConfiguredException e, Ticket t) {
        LOG.warn("DNS Check error, " + info(t) + " Reason: " + e.getMessage());
    }

	private String info(Ticket t) {
		return String.format("ID: %s, domain: %s, type: %s, admin: %s, tech: %s, financial: %s", 
				t.getId(), 
				t.getOperation().getDomainNameField().getNewValue(), 
				t.getOperation().getType(),
				t.getAdminStatus().getDescription(),
				t.getTechStatus().getDescription(),
				t.getFinancialStatus().getDescription());
	}

	private void performDnsCheck(AuthenticatedUser user, Ticket t) {
        try {
            triplePassSupportService.performTechnicalCheck(user, t.getId(), false);
        } catch (TicketIllegalStateException e) {
            handle(e, t);
        } catch (TicketNotFoundException e) {
            handle(e, t);
        } catch (TechnicalCheckException e) {
            handle(e, t);
        } catch (HostNotConfiguredException e) {
            handle(e, t);
        }
    }

    private void performTicketCancellation(AuthenticatedUser user, Ticket t) {
        try {
            triplePassSupportService.performTicketCancellation(user, t.getId());
        } catch (TicketIllegalStateException e) {
            handle(e, t);
        } catch (TicketNotFoundException e) {
            handle(e, t);
        } catch (TransactionNotFoundException e) {
            handle(e, t);
        } catch (PaymentException e) {
            handle(e, t);
        }
    }

    private void handle(TransactionNotFoundException e, Ticket t) {
        LOG.warn("Transaction not found for ticket " + info(t), e);
    }

    private void handle(PaymentException e, Ticket t) {
        LOG.warn("Exception during cancellation of realex cc pre-authorisation  " + info(t), e);
    }

	@Override
	public void giboAdminFailed(AuthenticatedUser user, String domainName, String remark) {		
		triplePassSupportService.giboAdminFailed(user, domainName, new OpInfo(user, remark));
	}
}
