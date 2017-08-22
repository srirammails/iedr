package pl.nask.crs.app.commons.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.ValidationHelper;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.CommonSupportService;
import pl.nask.crs.app.commons.DomainSettings;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.app.commons.exceptions.DomainIncorrectStateException;
import pl.nask.crs.app.commons.exceptions.DomainNotBillableException;
import pl.nask.crs.app.commons.register.CharityRegistrationNotPossibleException;
import pl.nask.crs.app.commons.register.RegisterCharityDomain;
import pl.nask.crs.app.commons.register.RegisterDomain;
import pl.nask.crs.app.commons.register.RegisterDomainCC;
import pl.nask.crs.app.commons.register.RegisterGIBODomain;
import pl.nask.crs.app.commons.transfer.TransferDomain;
import pl.nask.crs.app.commons.transfer.TransferDomainCC;
import pl.nask.crs.app.commons.transfer.TransferDomainCharity;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.app.tickets.exceptions.DomainNameExistsOrPendingException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.app.utils.TicketConverter;
import pl.nask.crs.app.utils.UserValidator;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactUtils;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.AuthCode;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.dsm.events.TransferCancellation;
import pl.nask.crs.domains.exceptions.AuthCodePortalEmailException;
import pl.nask.crs.domains.exceptions.AuthCodePortalLimitException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.CustomerStatusEnum;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalStatus;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

public class CommonAppServiceImpl implements CommonAppService {

    private final static Logger LOG = Logger.getLogger(CommonAppServiceImpl.class);
    private final AppServicesRegistry appServicesRegistry;
    private final DomainStateMachine dsm;
    private final ServicesRegistry servicesRegistry;
    private final ApplicationConfig applicationConfig;
    private final CommonSupportService commonSupportService;

    public CommonAppServiceImpl(AppServicesRegistry registry, DomainStateMachine dsm, ServicesRegistry servicesRegistry, ApplicationConfig applicationConfig, CommonSupportService commonSupportService) {
        Validator.assertNotNull(registry, "serviceRegistry");
        Validator.assertNotNull(dsm, "dsm");
        Validator.assertNotNull(servicesRegistry, "servicesRegistry");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        this.appServicesRegistry = registry;
        this.dsm = dsm;
        this.servicesRegistry = servicesRegistry;
        this.applicationConfig = applicationConfig;
        this.commonSupportService = commonSupportService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long registerDomain(AuthenticatedUser user, TicketRequest request, PaymentRequest paymentRequest)
            throws NicHandleNotFoundException, AccessDeniedException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, PaymentException, DomainIsCharityException, DuplicatedAdminContact,
            NicHandleNotActiveException, EmptyRemarkException {
        UserValidator.validateLoggedIn(user);
        if (request.isCharity()) {
            return new RegisterCharityDomain(appServicesRegistry, servicesRegistry, user, request).run();
        } else if (paymentRequest == null) {
            return new RegisterDomain(appServicesRegistry, servicesRegistry, user, request).run();
        } else {
            return new RegisterDomainCC(appServicesRegistry, servicesRegistry, user, request, paymentRequest).run();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerGIBODomain(AuthenticatedUser user, TicketRequest request) throws CharityRegistrationNotPossibleException,
            NotAdmissiblePeriodException, DomainNameExistsOrPendingException, NicHandleNotFoundException,
            NicHandleNotActiveException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDontMatchCategoryException, ClassCategoryPermissionException {
        UserValidator.validateLoggedIn(user);
        new RegisterGIBODomain(appServicesRegistry, servicesRegistry, user, request).run();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long transfer(AuthenticatedUser user, TicketRequest request, PaymentRequest paymentRequest) throws TicketNotFoundException,
            NicHandleNotFoundException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException,
            AccountNotFoundException, DomainIsNotCharityException, PaymentException, DomainIsCharityException,
            InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException, AccessDeniedException, NicHandleNotActiveException, EmptyRemarkException {
        UserValidator.validateLoggedIn(user);
        if (request.isCharity()) {
            return new TransferDomainCharity(appServicesRegistry, servicesRegistry, dsm, user, request).run();
        } else if (paymentRequest == null) {
            return new TransferDomain(appServicesRegistry, servicesRegistry, dsm, user, request).run();
        } else {
            return new TransferDomainCC(appServicesRegistry, servicesRegistry, dsm, user, request, paymentRequest).run();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(AuthenticatedUser user, long ticketId) throws TicketNotFoundException, CancelTicketException, PaymentException {
        UserValidator.validateLoggedIn(user);
        Ticket ticket = servicesRegistry.getTicketSearchService().getTicket(ticketId);
        Reservation reservation = servicesRegistry.getPaymentService().getReservationForTicket(ticketId);
        verifyNotTriplePassed(ticket);
        verifyNotSettled(reservation);

        switch (ticket.getOperation().getType()) {
            case REG:
                cancelTransaction(user, reservation);
                break;
            case XFER:
                cancelTransaction(user, reservation);
                runTransferCancellationEvent(user, new OpInfo(user), ticket);
                break;
            case MOD:
            case DEL:
                break;
            default:
                throw new IllegalStateException("Illegal ticket type: " + ticket.getOperation().getType());
        }
        servicesRegistry.getTicketService().updateCustomerStatus(ticketId, CustomerStatusEnum.CANCELLED, user.getUsername());
    }

    @Override
    @Transactional
    public void zonePublished(AuthenticatedUser user, List<String> domainNames) {
        servicesRegistry.getDomainService().zonePublished(domainNames);
    }

    @Override
    @Transactional
    public void zoneUnpublished(AuthenticatedUser user, List<String> domainNames) {
        servicesRegistry.getDomainService().zoneUnpublished(domainNames);
    }

    @Override
    @Transactional
    public void zoneCommit(AuthenticatedUser user) {
        servicesRegistry.getDomainService().zoneCommit();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTransferPossible(AuthenticatedUser user, String domainName) {
        UserValidator.validateLoggedIn(user);
        try {
            NicHandle nh = servicesRegistry.getNicHandleSearchService().getNicHandle(user.getUsername());
            Domain d = servicesRegistry.getDomainSearchService().getDomain(domainName);
            if (isSameReseller(d, nh)) {
                LOG.info("Transfer of domain: " + domainName + " not possible: same reseller.");
                return false;
            }
            if (isPendingTicket(domainName)) {
                LOG.info("Transfer of domain: " + domainName + " not possible: other pending transfer tickets.");
                return false;
            }
            if (!isXferEventAvailable(domainName)) {
                LOG.info("Transfer of domain: " + domainName + " not possible: transfer event not available.");
                return false;
            }
            return true;
        } catch (DomainNotFoundException e) {
            LOG.info("Transfer of domain: " + domainName + " not possible: domain not found.");
            return false;
        } catch (NicHandleNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean isSameReseller(Domain domain, NicHandle nicHandle) {
        if (isDirect(nicHandle)) {
            return domain.getBillingContact().getNicHandle().equalsIgnoreCase(nicHandle.getNicHandleId());
        } else {
            return domain.getResellerAccount().getId() == nicHandle.getAccount().getId();
        }
    }

    private boolean isDirect(NicHandle nicHandle) {
        long guestAccountId = applicationConfig.getGuestAccountId();
        return nicHandle.getAccount().getId() == guestAccountId;
    }

    private boolean isPendingTicket(String domainName) {
        TicketSearchCriteria cr = new TicketSearchCriteria();
        cr.setTicketType(DomainOperationType.XFER, DomainOperationType.DEL);
        cr.setDomainName(domainName);
        List<String> res = servicesRegistry.getTicketSearchService().findDomainNames(cr, 0, 1);
        return !res.isEmpty();
    }

    private boolean isXferEventAvailable(String domainName) {
        List<String> invalidDomains = servicesRegistry.getDomainService().checkEventAvailable(Arrays.asList(domainName), DsmEventName.TransferRequest);
        return invalidDomains.size() == 0;
    }

    private void verifyNotSettled(Reservation reservation) throws CancelTicketException {
        if (reservation != null && reservation.isSettled()) {
            throw new CancelTicketException("Ticket reservation settled");
        }
    }

    private void verifyNotTriplePassed(Ticket ticket) throws CancelTicketException {
        if (isTriplePassed(ticket)) {
            throw new CancelTicketException("Ticket triple passed");
        }
    }

    private boolean isTriplePassed(Ticket ticket) {
        return ticket.getFinancialStatus().getId() == FinancialStatusEnum.PASSED.getId();
    }

    private void cancelTransaction(AuthenticatedUser user, Reservation reservation) throws PaymentException {
        try {
            if (reservation != null && !reservation.isSettled()) {
                servicesRegistry.getPaymentService().cancelTransaction(user, reservation.getTransactionId());
            } else {
                LOG.warn("Transaction cancellation skipped due to reservation state: " + reservation);
            }
        } catch (TransactionNotFoundException e) {
            //should never happen
            throw new IllegalStateException("Transaction not found");
        }
    }

    private void runTransferCancellationEvent(AuthenticatedUser user, OpInfo opInfo, Ticket t) {
        dsm.handleEvent(user, t.getOperation().getDomainNameField().getNewValue(), new TransferCancellation(t), opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long modifyDomain(AuthenticatedUser user, String domainName, String domainHolder, String domainClass,
                             String domainCategory, List<String> adminContacts, List<String> techContacts,
                             List<Nameserver> nameservers, RenewalMode renewalMode, String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, DuplicatedAdminContact, NameserversValidationException {
        UserValidator.validateLoggedIn(user);
        Validator.assertNotEmpty(domainName, "domainName");
        Domain oldDomain = servicesRegistry.getDomainSearchService().getDomain(domainName);

        boolean renewalModeChanged = (renewalMode != null && renewalMode != oldDomain.getDsmState().getRenewalMode());

        validatedDomainToModify(domainName, user, renewalModeChanged);
        if (renewalModeChanged) {
            try {
            	// permission check must be made before renewal mode is changed.
            	appServicesRegistry.getDomainAppService().modifyRenewalMode(user, Arrays.asList(domainName), renewalMode);
            } catch (DomainIllegalStateException e) {
                throw new DomainIncorrectStateException("Cannot modify renewal mode.", e.getDomain().getName());
            }
        }

        Long ticketId = modifyDomainHolderAndContacts(user, domainName, domainHolder, domainClass, domainCategory, adminContacts, techContacts, nameservers, customerRemark, oldDomain);
        DnsModification dnsModification = newDnsModification(user, Arrays.asList(domainName), nameservers, customerRemark);
        dnsModification.perform();
        return ticketId;
    }

    private DnsModification newDnsModification(AuthenticatedUser user, List<String> domainNames, List<Nameserver> nameservers, String customerRemark) {
    	String requesterRemark = Validator.isEmpty(customerRemark) ? "DNS change made" : customerRemark;
    	DnsModification dnsModification = new DnsModification(user, domainNames, nameservers, requesterRemark);
    	dnsModification.setServiceRegistry(servicesRegistry);
    	dnsModification.setApplicationConfig(applicationConfig);
    	return dnsModification;
	}
    
	@Override
    public void modifyNameservers(AuthenticatedUser user, List<String> domainNames, List<Nameserver> nameservers, String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException,
            AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException,
            NicHandleNotActiveException, ValidationException, DnsCheckProcessingException, HostNotConfiguredException, DuplicatedAdminContact, NameserversValidationException {
        UserValidator.validateLoggedIn(user);
        Validator.assertNotEmpty(domainNames, "domainNames");
        
        DnsModification dnsModification = newDnsModification(user, domainNames, nameservers, customerRemark);
        dnsModification.setCompareIp(false);
        dnsModification.perform();
    }

    @Override
    public void validateNameservers(AuthenticatedUser user, String domain, List<Nameserver> nameservers) throws HostNotConfiguredException, DnsCheckProcessingException {
        DnsCheckService dnsCheck = servicesRegistry.getDnsCheckService();
        for (Nameserver ns : nameservers) {
            dnsCheck.check(user.getUsername(), domain, ns.getName(), ns.getIpAddressAsString());
        }
    }


    private void validatedDomainToModify(String domainName, AuthenticatedUser user, boolean isRenewalModeChange) throws DomainNotFoundException, NicHandleNotFoundException, ValidationException {
        Domain domain = servicesRegistry.getDomainSearchService().getDomain(domainName);
        NicHandle nh = servicesRegistry.getNicHandleSearchService().getNicHandle(user.getUsername());
        long accountId = nh.getAccount().getId();
        if (accountId != domain.getResellerAccount().getId()) {
            throw new DomainManagedByAnotherResellerException(domain.getName());
        }
        if (isRenewalModeChange) {
            validateIsBillable(domain);
        }
        ValidationHelper.checkIsNotWIPO(domain);
        ValidationHelper.checkNotInTransferPendingActiveOrPostTransactionAudit(domain);
    }

    private void validateIsBillable(Domain domain) throws DomainNotBillableException {
        if (domain.getDsmState().getDomainHolderType() != DomainHolderType.Billable) {
            throw new DomainNotBillableException(domain.getName(), domain.getDsmState().getDomainHolderType().getDescription());
        }
    }

    private Long modifyDomainHolderAndContacts(AuthenticatedUser user, String domainName, String domainHolder, String domainClass, String domainCategory, List<String> adminContacts, List<String> techContacts, List<Nameserver> nameservers, String customerRemark, Domain oldDomain)
            throws HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, NicHandleNotFoundException, DomainNotFoundException, ValidationException, EmptyRemarkException, DuplicatedAdminContact, AccessDeniedException, NicHandleNotActiveException {
        try {
            if (isHolderModify(domainHolder, domainClass, domainCategory, oldDomain) || isContactsModify(adminContacts, techContacts, oldDomain)) {
                Ticket newTicket = prepareModificationTicket(user, domainName, domainHolder, domainClass, domainCategory, adminContacts, techContacts, nameservers, customerRemark);
                return appServicesRegistry.getTicketAppService().create(user, newTicket);
            }
            return null;
        } catch (pl.nask.crs.ticket.exceptions.NicHandleNotFoundException e) {
            throw new NicHandleNotFoundException(e.getNicHandle());
        } catch (NicHandleRecreateException e) {
            //should never happen
            throw new IllegalStateException(e);
        } catch (DomainIsNotCharityException e) {
            //should never happen
            throw new IllegalStateException(e);
        } catch (DomainIsCharityException e) {
            //should never happen
            throw new IllegalStateException(e);
        }
    }

    private boolean isHolderModify(String domainHolder, String domainClass, String domainCategory, Domain oldDomain) {
        return (domainHolder != null && !domainHolder.equals(oldDomain.getHolder()))
                || (domainClass != null && !domainClass.equals(oldDomain.getHolderClass()))
                || (domainCategory != null && !domainCategory.equals(oldDomain.getHolderCategory()));
    }

    private boolean isContactsModify(List<String> adminContacts, List<String> techContacts, Domain oldDomain) {
        return listModified(adminContacts, oldDomain.getAdminContacts())
                || listModified(techContacts, oldDomain.getTechContacts());
    }

    private boolean listModified(List<String> newList, List<Contact> oldList) {
        removeEmptyContactsNames(newList);
        removeEmptyContacts(oldList);
        if (newList == null)
            return false;

        if (oldList == null)
            return true;

        if (newList.size() != oldList.size())
            return true;

        for (int i = 0; i < newList.size(); i++) {
            String newValue = newList.get(i);
            Contact c = oldList.get(i);
            if (newValue != null && c != null && !newValue.equals(c.getNicHandle())) {
                return true;
            } else if (c == null)
                return true;
        }

        return false;
    }

    private void removeEmptyContactsNames(List<String> list) {
        if (!Validator.isEmpty(list)) {
            for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
                String contact = iterator.next();
                if (Validator.isEmpty(contact)) {
                    iterator.remove();
                }
            }
        }
    }

    private void removeEmptyContacts(List<Contact> list) {
        if (Validator.isEmpty(list)) {
            for (Iterator<Contact> iterator = list.iterator(); iterator.hasNext(); ) {
                Contact contact = iterator.next();
                if (Validator.isEmpty(contact.getNicHandle())) {
                    iterator.remove();
                }
            }
        }
    }

    private Ticket prepareModificationTicket(AuthenticatedUser user, String domainName, String domainHolder, String domainClass, String domainCategory, List<String> adminContacts, List<String> techContacts, List<Nameserver> nameservers, String customerRemark) throws DomainNotFoundException, EmptyRemarkException {
        Domain domain = servicesRegistry.getDomainSearchService().getDomain(domainName);

        List<SimpleDomainFieldChange<Contact>> adminList = prepareContacts(domain.getAdminContacts(), adminContacts);
        List<SimpleDomainFieldChange<Contact>> techList = prepareContacts(domain.getTechContacts(), techContacts);
        List<SimpleDomainFieldChange<Contact>> billingList = prepareContacts(domain.getBillingContacts(), null);

        //TODO nameserver change is redundant in modification ticket
        List<NameserverChange> nsList = TicketConverter.asNameserverChangeList(nameservers == null ? domain.getNameservers() : nameservers);

        String domainHolderForTicket = domainHolder == null ? domain.getHolder() : domainHolder;
        String domainClassForTicket = domainClass == null ? domain.getHolderClass() : domainClass;
        String domainCategoryForTicket = domainCategory == null ? domain.getHolderCategory() : domainCategory;

        DomainOperation domainOp = TicketConverter.asDomainOperation(DomainOperation.DomainOperationType.MOD, domain.getRenewDate(),
                domain.getName(), domainHolderForTicket, domainClassForTicket, domainCategoryForTicket,
                domain.getResellerAccount().getId(), null, adminList, techList, billingList, nsList);

        InternalStatus aStatus = new InternalStatus();
        aStatus.setId(0);
        InternalStatus tStatus = new InternalStatus();
        tStatus.setId(0);

        Date crDate = new Date();
        if (Validator.isEmpty(customerRemark)) {
        	throw new EmptyRemarkException();
        }
        return new Ticket(domainOp, aStatus, crDate, tStatus, crDate,
        		customerRemark , null, new Contact(user.getUsername()),
                crDate, crDate, null, false, false, null, null, FinancialStatusEnum.NEW, crDate);
    }

    private List<SimpleDomainFieldChange<Contact>> prepareContacts(List<Contact> domainContacts, List<String> newContacts) {
        List<SimpleDomainFieldChange<Contact>> contactsList = null;
        if (newContacts == null) {
            contactsList = TicketConverter.asDomainFieldChangeContactList(ContactUtils.contactsAsStrings(domainContacts));
        } else {
            contactsList = TicketConverter.asDomainFieldChangeContactList(newContacts);
        }
        return contactsList;
    }

    @Override
    public void cleanupTickets(AuthenticatedUser user, OpInfo opInfo) {
        LOG.info("Ticket cleanup started.");
        for (Ticket ticket : getTicketsToRemove()) {
            cleanupTicket(user, opInfo, ticket);
        }
        LOG.info("Ticket cleanup finished");
    }

    private void cleanupTicket(AuthenticatedUser user, OpInfo opInfo, Ticket ticket) {
        try {
            commonSupportService.cleanupTicket(user, opInfo, ticket.getId());
        } catch (Exception e) {
            LOG.error("Exception during cleanup of ticket id: " + ticket.getId(), e);
        }
    }

    private List<Ticket> getTicketsToRemove() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setCustomerStatus(CustomerStatusEnum.CANCELLED);
        List<Ticket> customerCancelled = servicesRegistry.getTicketSearchService().findAll(criteria, null);

        criteria = new TicketSearchCriteria();
        criteria.setAdminStatus(AdminStatusEnum.CANCELLED.getId());
        List<Ticket> adminCancelled = servicesRegistry.getTicketSearchService().findAll(criteria, null);

        criteria = new TicketSearchCriteria();
        int ticketExpirationPeriod = applicationConfig.getTicketExpirationPeriod();
        criteria.setTo(DateUtils.getCurrDate(-ticketExpirationPeriod));
        List<Ticket> expired = servicesRegistry.getTicketSearchService().findAll(criteria,null);

        List<Ticket> ret = new ArrayList<Ticket>();
        ret.addAll(customerCancelled);
        ret.addAll(adminCancelled);
        ret.addAll(expired);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentSummary reauthoriseTransaction(AuthenticatedUser user, int transactionId, PaymentRequest paymentRequest)
            throws DomainNotFoundException, TransactionNotFoundException, TicketNotFoundException, NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException {
        Transaction transaction = servicesRegistry.getPaymentService().getTransaction(transactionId);
        validateState(transaction);
        // all registration/transfer transaction reservations are for the same product, vat
        Reservation reservation = transaction.getReservations().get(0);
        Ticket ticket = servicesRegistry.getTicketSearchService().getTicket(reservation.getTicketId());
        return servicesRegistry.getPaymentService().reauthoriseTransaction(transaction, ticket, paymentRequest);
    }

    @Override
    public DomainSettings getDomainSettings(AuthenticatedUser user) {
        return new DomainSettings(applicationConfig.getNameserverMinCount(), applicationConfig.getNameserverMaxCount(), applicationConfig.getAuthCodeFailureLimit());
    }

    private void validateState(Transaction transaction) {
        if (!transaction.isInvalidated() || transaction.isSettlementEnded()) {
            throw new IllegalStateException("Transaction invalid state for reauthorisation, transactionId=" + transaction.getId());
        }
    }

    @Override
    public void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode, int failureCount) throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        servicesRegistry.getDomainService().verifyAuthCode(user, domainName, authCode, failureCount);
    }

    @Override
    public AuthCode generateOrProlongAuthCode(AuthenticatedUser user, String domainName) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        validateDomainAuthcodeManagement(user, domainName);
        return servicesRegistry.getDomainService().getOrCreateAuthCode(user.getUsername(), domainName);
    }

    @Override
    public void sendAuthCodeByEmail(AuthenticatedUser user, String domainName) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        validateDomainAuthcodeManagement(user, domainName);
        servicesRegistry.getDomainService().sendAuthCodeByEmail(user, domainName);
    }

    @Override
    public void sendAuthCodeFromPortal(String domainName, String emailAddress) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException, AuthCodePortalEmailException, AuthCodePortalLimitException {
        validateEmailWithDomain(domainName, emailAddress);
        servicesRegistry.getDomainService().sendAuthCodeFromPortal(domainName, emailAddress);
    }

    private void validateEmailWithDomain(String domainName, String emailAddress) throws DomainNotFoundException, AuthCodePortalEmailException {
        Domain domain = servicesRegistry.getDomainSearchService().getDomain(domainName);
        List<Contact> contactList = new ArrayList<Contact>();
        contactList.addAll(domain.getBillingContacts());
        contactList.addAll(domain.getAdminContacts());
        for (Contact contact : contactList) {
            if (contact.getEmail().equalsIgnoreCase(emailAddress)) {
                return;
            }
        }
        throw new AuthCodePortalEmailException();
    }

    private void validateDomainAuthcodeManagement(final AuthenticatedUser user, String domainName) {
        Domain domain = servicesRegistry.getDomainSearchService().getDomain(domainName);
        List<Contact> contactList = new ArrayList<Contact>();
        contactList.addAll(domain.getBillingContacts());
        contactList.addAll(domain.getAdminContacts());
        final Predicate<Contact> contactIsLoggedUser = new Predicate<Contact>() {
            @Override
            public boolean apply(Contact contact) {
                return contact.getNicHandle().equals(user.getUsername());
            }
        };
        if (Iterables.all(contactList, Predicates.not(contactIsLoggedUser))) {
            throw new AccessDeniedException("Only admin-c or bill-c can generate authcode");
        }
    }

}