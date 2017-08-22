package pl.nask.crs.app.triplepass;

import static pl.nask.crs.app.triplepass.TriplePassHelper.convertContactsToContactList;
import static pl.nask.crs.app.triplepass.TriplePassHelper.convertContactsToStringList;
import static pl.nask.crs.app.triplepass.TriplePassHelper.convertNameservers;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isAdminCancelled;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isAdminPassed;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isCanceled;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isFinancialPassed;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isFullPassed;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isModificationTicket;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isRegistrationTicket;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isTechPassed;
import static pl.nask.crs.app.triplepass.TriplePassHelper.isTransferTicket;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.triplepass.commands.ADPFinancialCheck;
import pl.nask.crs.app.triplepass.commands.CCFinancialCheck;
import pl.nask.crs.app.triplepass.commands.CharityFinancialCheck;
import pl.nask.crs.app.triplepass.commands.GIBORegistrationFC;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.app.triplepass.exceptions.TechnicalCheckException;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.events.CreateBillableDomainDirect;
import pl.nask.crs.domains.dsm.events.CreateBillableDomainRegistrar;
import pl.nask.crs.domains.dsm.events.CreateCharityDomainDirect;
import pl.nask.crs.domains.dsm.events.CreateCharityDomainRegistrar;
import pl.nask.crs.domains.dsm.events.GIBOAdminFailure;
import pl.nask.crs.domains.dsm.events.TransferCancellation;
import pl.nask.crs.domains.dsm.events.TransferToDirect;
import pl.nask.crs.domains.dsm.events.TransferToRegistrar;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionDetails;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.TechStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TriplePassSupportServiceImpl implements TriplePassSupportService {
	private static final Logger LOG = Logger.getLogger(TriplePassSupportService.class); 

    private static final long DIRECT_ACCOUNT_ID = 1L;

    private PaymentService paymentService;
    private TicketService ticketService;
    private TicketSearchService ticketSearchService;
    private DomainStateMachine dsm;
    private DnsCheckService dnsCheckService;
    private DnsNotificationService dnsNotificationService;
    private EmailTemplateSender emailTemplateSender;
    private NicHandleSearchService nicHandleSearchService;
    private DomainSearchService domainSearchService;
    private AccountSearchService accountSearchService;
    private DomainService domainService;
    private ApplicationConfig applicationConfig;
    private DepositService depositService;
    private OpInfo opInfo = new OpInfo("TriplePass");

    public TriplePassSupportServiceImpl(PaymentService paymentService, TicketService ticketService, DomainStateMachine dsm, TicketSearchService ticketSearchService, DnsCheckService dnsCheckService, DnsNotificationService dnsNotificationService, EmailTemplateSender emailTemplateSender, NicHandleSearchService nicHandleSearchService, DomainSearchService domainSearchService, AccountSearchService accountSearchService, DomainService domainService, ApplicationConfig applicationConfig, DepositService depositService) {
        Validator.assertNotNull(paymentService, "payment service");
        Validator.assertNotNull(ticketService, "ticket service");
        Validator.assertNotNull(dsm, "domain state machine");
        Validator.assertNotNull(ticketSearchService, "ticketSearchService");
        Validator.assertNotNull(dnsCheckService, "dnsCheckService");
        Validator.assertNotNull(dnsNotificationService, "dnsNotificationService");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        Validator.assertNotNull(nicHandleSearchService, "nicHandleSearchService");
        Validator.assertNotNull(domainSearchService, "domainSearchService");
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        Validator.assertNotNull(domainService, "domainService");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        Validator.assertNotNull(depositService, "depositService");
        this.paymentService = paymentService;
        this.ticketService = ticketService;
        this.dsm = dsm;
        this.ticketSearchService = ticketSearchService;
        this.dnsCheckService = dnsCheckService;
        this.dnsNotificationService = dnsNotificationService;
        this.emailTemplateSender = emailTemplateSender;
        this.nicHandleSearchService = nicHandleSearchService;
        this.domainSearchService = domainSearchService;
        this.accountSearchService = accountSearchService;
        this.domainService = domainService;
        this.applicationConfig = applicationConfig;
        this.depositService = depositService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void performFinancialCheck(AuthenticatedUser user, long ticketId) throws FinancialCheckException, TicketNotFoundException, TicketIllegalStateException {
    	LOG.info("Performing financial check, ticketId=" + ticketId);
    	Ticket t = refreshTicket(ticketId);
    	assertTrue(isAdminPassed(t), "admin passed", t);
    	assertTrue(isTechPassed(t), "tech passed", t);
    	
    	if (isCanceled(t) || isFinancialPassed(t)) {
    		LOG.info("Financial check passed due to current financial status (passed)");
    		return;
    	}

        Reservation reservation = paymentService.getReservationForTicket(ticketId);

        if (isCharityTicket(t)) {
            new CharityFinancialCheck(t, ticketService, paymentService, depositService).performFinancialCheck(user);
        } else if (isADPTransaction(reservation)) {
            new ADPFinancialCheck(t, reservation == null ? null : reservation.getId(), ticketService, paymentService, emailTemplateSender, nicHandleSearchService, depositService, domainSearchService).performFinancialCheck(user);
        } else {
        	new CCFinancialCheck(t, reservation.getId(), ticketService, paymentService, depositService).performFinancialCheck(user);
        }
    }

    private boolean isADPTransaction(Reservation reservation) throws FinancialCheckException {
    	if (reservation == null)
    		return true; 
    	
    	try {
			return paymentService.getTransaction(reservation.getTransactionId()).isADPTransaction();
		} catch (TransactionNotFoundException e) {
			throw new FinancialCheckException("No transaction found for reservation id=" + reservation.getId(), e); 
		}
	}

	private void assertTrue(boolean check, String expectedState, Ticket t) throws TicketIllegalStateException {
		if (!check)
			throw new TicketIllegalStateException("Expected state was: " + expectedState, t);
	}

	/**
     * This one must run in the transaction!
     * @throws TicketNotFoundException 
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String promoteTicketToDomain(AuthenticatedUser user, long ticketId) throws TicketIllegalStateException, TicketNotFoundException {
    	// refresh the ticket in the current transaction
    	Ticket t = refreshTicket(ticketId);

    	if (!isFullPassed(t)) {
    		throw new TicketIllegalStateException("not fully passed", t);
    	}

        if (!isRegistrationTicket(t)) {
            throw new TicketIllegalStateException("not registration ticket", t);
        }

    	DomainOperation op = t.getOperation();
    	String domainName = op.getDomainNameField().getNewValue();

    	List<String> techContacts = convertContactsToStringList(op.getTechContactsField());
    	List<String> billContacts = convertContactsToStringList(op.getBillingContactsField());
    	List<String> adminContacts = convertContactsToStringList(op.getAdminContactsField());
    	List<Nameserver> nameservers = convertNameservers(op.getNameserversField());
    	NewDomain newDomain = new NewDomain(
    			domainName,
    			op.getDomainHolderField().getNewValue(),
    			op.getDomainHolderClassField().getNewValue(),
    			op.getDomainHolderCategoryField().getNewValue(),
    			t.getCreator().getNicHandle(),
    			op.getResellerAccountField().getNewValue().getId(),
    			t.getRequestersRemark(),
    			techContacts,
    			billContacts,
    			adminContacts,
    			nameservers,
    			t.getDomainPeriod());


    	DsmEvent event;
    	if (isCharityTicket(t)) {
    		Date renewalDate = DateUtils.addYears(new Date(), 1);

    		if (isDirectRegistration(t)) {
    			event = new CreateCharityDomainDirect(renewalDate);
    		} else {
    			event = new CreateCharityDomainRegistrar(renewalDate);
    		}
    	} else {
    		if (isDirectRegistration(t)) {
    			event = new CreateBillableDomainDirect(new Date());
    		} else {
    			event = new CreateBillableDomainRegistrar(new Date());
    		}
    	}

    	dsm.handleEvent(user, newDomain, event, opInfo);

    	ticketService.delete(t.getId());

    	return domainName;
    }

    private Ticket refreshTicket(long ticketId) throws TicketIllegalStateException, TicketNotFoundException {
    	Ticket t = ticketSearchService.lockTicket(ticketId);

    	if (!isRegistrationTicket(t) && !isTransferTicket(t) && !isModificationTicket(t)) {
    		throw new TicketIllegalStateException("not a registration nor transfer ticket", t);
    	}

    	return t;
	}

    private boolean isCharityTicket(Ticket t) {
        return t.isCharity();
    }

    private boolean isDirectRegistration(Ticket t) {
        long accountId = t.getOperation().getResellerAccountField().getNewValue().getId();
        return accountId == DIRECT_ACCOUNT_ID;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String promoteTransferTicket(AuthenticatedUser user, long ticketId) throws TicketIllegalStateException, TicketNotFoundException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
    	// refresh the ticket in the current transaction
    	Ticket t = refreshTicket(ticketId);

    	if (!isFullPassed(t)) {
    		throw new TicketIllegalStateException("not fully passed", t);
    	}

        if (!isTransferTicket(t)) {
            throw new TicketIllegalStateException("not transfer ticket", t);
        }
        Domain oldDomain = domainSearchService.getDomain(t.getOperation().getDomainNameField().getNewValue());
        Domain domain = prepareDomainToTransfer(t);
        domainService.save(domain, opInfo);

    	DsmEvent event;
        if (isDirectRegistration(t)) {
            event = new TransferToDirect(oldDomain, t);
        } else {
        	Reservation r = paymentService.getReservationForTicket(t.getId());
        	if (r == null) {
        		LOG.warn("No reservation found for ticketId = " + t.getId() + ", will trigger TransferToRegistrar without PAY_METHOD");
        		event = new TransferToRegistrar(oldDomain, t);
        	} else {
        		TransactionDetails details = new TransactionDetails(t.getOperation().getDomainNameField().getNewValue(), t.getOperation().getDomainHolderField().getNewValue(), t.getDomainPeriod().getYears(), OperationType.REGISTRATION, r.getTotal());
        		BigDecimal transactionValue = null; 
				try {
					Transaction tr = paymentService.getTransaction(r.getTransactionId());
					transactionValue = MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(tr.getTotalCost());
					event = new TransferToRegistrar(oldDomain, t, r.getPaymentMethod(), details, transactionValue, r.getOrderId());
				} catch (TransactionNotFoundException e) {
					String msg = "No transaction found for id=" + r.getTransactionId() + ", reservationId=" + r.getId() + ", orderId=" + r.getOrderId();
					LOG.error(msg);
					throw new IllegalStateException (msg, e);
				}
        	}
        }

    	dsm.handleEvent(user, domain, event, opInfo);

    	ticketService.delete(t.getId());

    	return domain.getName();
    }

    @Override
    public String promoteModificationTicket(long ticketId) throws TicketIllegalStateException, TicketNotFoundException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        Ticket t = refreshTicket(ticketId);
        if (!isFullPassed(t)) {
            throw new TicketIllegalStateException("not fully passed", t);
        }

        if (!isModificationTicket(t)) {
            throw new TicketIllegalStateException("not modification ticket", t);
        }

        Domain domain = prepareDomainToModify(t);
        domainService.save(domain, opInfo);
        ticketService.delete(t.getId());
        return domain.getName();
    }

    private Domain prepareDomainToModify(Ticket t) throws DomainNotFoundException {
        DomainOperation op = t.getOperation();
        String domainName = op.getDomainNameField().getNewValue();
        Domain domain = domainSearchService.getDomain(domainName);

        List<Contact> billContacts = convertContactsToContactList(op.getBillingContactsField());
        List<Contact> techContacts = convertContactsToContactList(op.getTechContactsField());
        List<Contact> adminContacts = convertContactsToContactList(op.getAdminContactsField());
        List<Nameserver> nameservers = convertNameservers(op.getNameserversField());

        domain.setBillingContacts(billContacts);
        domain.setTechContacts(techContacts);
        domain.setAdminContacts(adminContacts);
        domain.setNameservers(nameservers);

        domain.setHolder(op.getDomainHolderField().getNewValue());
        domain.setHolderClass(op.getDomainHolderClassField().getNewValue());
        domain.setHolderCategory(op.getDomainHolderCategoryField().getNewValue());
        domain.setRemark(t.getHostmastersRemark());

        return domain;
    }

    private Domain prepareDomainToTransfer(Ticket t) throws DomainNotFoundException {
        DomainOperation op = t.getOperation();
        String domainName = op.getDomainNameField().getNewValue();
        Domain domain = domainSearchService.getDomain(domainName);

        Account gainingCustomerAccount = op.getResellerAccountField().getNewValue();
        List<Contact> billContacts = convertContactsToContactList(op.getBillingContactsField());
        List<Contact> techContacts = convertContactsToContactList(op.getTechContactsField());
    	List<Contact> adminContacts = convertContactsToContactList(op.getAdminContactsField());
    	List<Nameserver> nameservers = convertNameservers(op.getNameserversField());

        domain.setResellerAccount(gainingCustomerAccount);
        domain.setBillingContacts(billContacts);
        domain.setTechContacts(techContacts);
        domain.setAdminContacts(adminContacts);
        domain.setNameservers(nameservers);
        domain.setAuthCode(null);
        domain.setAuthCodeExpirationDate(null);

        return domain;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void performTechnicalCheck(AuthenticatedUser user, long ticketId, boolean interactive) throws TicketIllegalStateException, TicketNotFoundException, TechnicalCheckException, HostNotConfiguredException {
    	LOG.info("Performing technical check, ticketId=" + ticketId);
        Ticket t = refreshTicket(ticketId);
        assertTrue(isAdminPassed(t), "admin passed", t);
        if (isCanceled(t) || isTechPassed(t)) {
        	LOG.info("Tech check passed due to current tech status (passed)");
            return;
        }

        final String username = (user == null) ? null : user.getUsername();
        final String domainName = t.getOperation().getDomainNameField().getNewValue();
        List<NameserverChange> nameserverChanges = t.getOperation().getNameserversField();

        Set<String> failedNameservers = new HashSet<String>();
        for (NameserverChange ns : nameserverChanges) {
            final String nsName = ns.getName().getNewValue();
            if (nsName != null) {
                failedNameservers.add(nsName);
            }
        }
        try {
            for (NameserverChange ns : nameserverChanges) {
                String nsName = ns.getName().getNewValue();
                String nsIpAddress = ns.getIpAddress().getNewValue();
                if (nsName != null) {
                    dnsCheckService.check(username, domainName, nsName, nsIpAddress);
                    failedNameservers.remove(nsName);
                }
            }
        } catch (DnsCheckProcessingException e) {
            throw new TechnicalCheckException(e);
        } catch (HostNotConfiguredException e) {
        	LOG.info("Tech check failed", e);
            setTechnicalStatusStalled(t.getId());
            //TODO refactor to send immediately?
            if (interactive)
                throw e;
            else {
                addDnsNotification(getTechNH(t), domainName, e);
                return;
            }
        } finally {
            dnsNotificationService.removeAllNotificationsExceptFor(domainName, failedNameservers);
        }
        setTechnicalStatusPassed(t.getId());
        sendDNSSuccessEmail(user, t);
    }

    private void sendDNSSuccessEmail(AuthenticatedUser user, Ticket t) {
        try {
            DomainOperation.DomainOperationType operationType = t.getOperation().getType();
            String username = (user == null) ? null : user.getUsername();
            TicketEmailParameters parameters = new TicketEmailParameters(username, t);
            switch (operationType) {
                case XFER:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.XFER_DNS_CHECK_SUCCESS.getId(), parameters);
                    break;
                case REG:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.NREG_DNS_CHECK_SUCCESS.getId(), parameters);
                    break;
                case MOD:
                case DEL:
                    //skip
                    break;
                default:
                    throw new IllegalArgumentException("Invalid domain operation type: " + operationType);
            }
        } catch (Exception e) {
            LOG.error("Error while sending dns success during transfer email", e);
        }
    }

    private NicHandle getTechNH(Ticket ticket) {
        try {
            SimpleDomainFieldChange<Contact> s = ticket.getOperation().getTechContactsField().get(0);
            return nicHandleSearchService.getNicHandle(s.getNewValue().getNicHandle());
        } catch (NicHandleNotFoundException e) {
            //should ever happen
            throw new IllegalStateException("tech nic handle not found", e);
        }
    }

    private void setTechnicalStatusStalled(long ticketId) throws TicketNotFoundException {
    	LOG.info("Setting tech status to Stalled, ticketId=" + ticketId);
        ticketService.updateTechStatus(ticketId, TechStatusEnum.STALLED, "Technical");
    }

    private void addDnsNotification(NicHandle techNH, String domainName, HostNotConfiguredException e) {
        DnsNotification dnsNotification = new DnsNotification(techNH.getNicHandleId(), techNH.getEmail(), domainName, e.getNsName(), DateFormatUtils.format(new Date(), "HH:mm"), e.getFullOutputMessage(true));
        dnsNotificationService.createNotification(dnsNotification);
    }

    private void setTechnicalStatusPassed(long ticketId) throws TicketNotFoundException {
    	LOG.info("Setting tech status to Passed, ticketId=" + ticketId);
        ticketService.updateTechStatus(ticketId, TechStatusEnum.PASSED, "Technical");
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
	public void performFinancialCheckOnGIBO(AuthenticatedUser user, String domainName, String remark)
			throws FinancialCheckException, DomainNotFoundException {
    	Domain d = domainSearchService.getDomain(domainName);    	
    	// financial check should only be performed on domains with NRP status of PostTransactionAudit or TransactionFailed
    	NRPStatus currentNrp = d.getDsmState().getNRPStatus();
    	
    	switch (currentNrp) {
    	case TransactionFailed:
    	case PostTransactionAudit:
    		new GIBORegistrationFC(domainName, paymentService, domainSearchService, dsm, accountSearchService, depositService, remark, applicationConfig.getGiboDefaultPeriod()).performFinancialCheck(user);
    		break;
    	default: 
    		break;
    	}
    	
	}

    @Transactional(rollbackFor = Exception.class)
	@Override
	public void giboAdminFailed(AuthenticatedUser user, String domainName, OpInfo opInfo) {
		dsm.handleEvent(user, domainName, GIBOAdminFailure.getInstance(), opInfo);
	}

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void performTicketCancellation(AuthenticatedUser user, long ticketId) throws TicketIllegalStateException, TicketNotFoundException, TransactionNotFoundException, PaymentException {
        Ticket t = refreshTicket(ticketId);
        if (!isAdminCancelled(t)) {
            throw new TicketIllegalStateException("ticket not cancelled", t);
        }
        if (t.getOperation().getType() == DomainOperation.DomainOperationType.XFER) {
            dsm.handleEvent(user, t.getOperation().getDomainNameField().getNewValue(), new TransferCancellation(t), opInfo);
        }
    }
}
