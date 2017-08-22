package pl.nask.crs.app.tickets.impl;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.ExceptionObserver;
import pl.nask.crs.app.NoAuthenticatedUserException;
import pl.nask.crs.app.ValidationHelper;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketInfo;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.app.tickets.exceptions.*;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.dns.validator.DomainNameValidator;
import pl.nask.crs.commons.dns.validator.InvalidDomainNameException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.exceptions.ContactNotActiveException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.entities.ClassCategoryEntity;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.service.EntitiesService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.exceptions.InvalidStatusException;
import pl.nask.crs.ticket.exceptions.NicHandleNotFoundException;
import pl.nask.crs.ticket.exceptions.TicketAlreadyCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketCheckedOutToOtherException;
import pl.nask.crs.ticket.exceptions.TicketEditFlagException;
import pl.nask.crs.ticket.exceptions.TicketEmailException;
import pl.nask.crs.ticket.exceptions.TicketNameserverException;
import pl.nask.crs.ticket.exceptions.TicketNotCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketHistorySearchService;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;

/**
 * @author Patrycja Wegrzynowicz, Artur Gniadzik
 */
public class TicketAppServiceImpl implements TicketAppService {
    public static final String ACCEPT_REMARK = "This application has been accepted";

    public static final int MAX_DOMAINS_IN_TICKET = 100;

    public static final long DIRECT_ACCOUNT = 1;

    public static final int TICKET_EXPIRATION_NOTIFICATION_PERIOD = 10;//10 days before ticket expire

    private final TicketSearchService ticketSearchService;

    private final TicketService ticketService;

    private final TicketHistorySearchService ticketHistorySearchService;

    private final DomainDAO domainDao;
    private final TicketDAO ticketDao;

    private final DomainSearchService domainSearchService;

    private final DomainService domainService;

    private final NicHandleSearchService nicHandleSearchService;

    private final NicHandleService nicHandleService;

    private Dictionary<Integer, AdminStatus> adminStatusDictionary;

    private Dictionary<Integer, TechStatus> techStatusDictionary;

    private final PaymentService paymentService;

    private final EntitiesService entitiesService;

    private static enum ContactType {
        ADMIN, TECH
    }

    private final ApplicationConfig applicationConfig;

    public TicketAppServiceImpl(TicketSearchService ticketSearchService,
                                TicketService ticketService, TicketDAO ticketDao,
                                TicketHistorySearchService ticketHistorySearchService,
                                DomainDAO domainDao,
                                DomainSearchService domainSearchService,
                                DomainService domainService,
                                NicHandleSearchService nicHandleSearchService,
                                NicHandleService nicHandleService,
                                Dictionary<Integer, AdminStatus> adminStatusDictionary,
                                Dictionary<Integer, TechStatus> techStatusDictionary,
                                PaymentService paymentService,
                                EntitiesService entitiesService,
                                ApplicationConfig applicationConfig) {

        Validator.assertNotNull(ticketSearchService, "ticket search service");
        Validator.assertNotNull(ticketService, "ticketService");
        Validator.assertNotNull(ticketDao, "ticket DAO");
        Validator.assertNotNull(ticketHistorySearchService,
                "ticket history search service");
        Validator.assertNotNull(domainDao, "domain dao");
        Validator.assertNotNull(domainSearchService, "domain search service");
        Validator.assertNotNull(domainService, "domain service");
        Validator.assertNotNull(nicHandleSearchService, "nicHandle search service");
        Validator.assertNotNull(nicHandleService, "nicHandle service");
        Validator.assertNotNull(adminStatusDictionary, "adminStatusDictionary");
        Validator.assertNotNull(techStatusDictionary, "techStatusDictionary");
        Validator.assertNotNull(paymentService, "paymentService");
        Validator.assertNotNull(entitiesService, "entitiesService");
        Validator.assertNotNull(applicationConfig, "applicationConfig");

        this.ticketSearchService = ticketSearchService;
        this.ticketService = ticketService;
        this.ticketHistorySearchService = ticketHistorySearchService;
        this.domainDao = domainDao;
        this.ticketDao = ticketDao;
        this.domainSearchService = domainSearchService;
        this.domainService = domainService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.nicHandleService = nicHandleService;
        this.adminStatusDictionary = adminStatusDictionary;
        this.techStatusDictionary = techStatusDictionary;
        this.paymentService = paymentService;
        this.entitiesService = entitiesService;
        this.applicationConfig = applicationConfig;
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Ticket> search(AuthenticatedUser user,
                                              TicketSearchCriteria criteria, long offset, long limit,
                                              List<SortCriterion> orderBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return ticketSearchService.find(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketModel view(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException {
        // todo: permission check
        validateLoggedIn(user);
        return getTicketModel(ticketId);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketModel history(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException {
        // todo: permission check
        validateLoggedIn(user);
        try {
            return getTicketModel(ticketId);
        } catch (TicketNotFoundException e) {
            // no current ticket found
            // limit search result to 50 entries
            List<HistoricalObject<Ticket>> history = ticketHistorySearchService
                    .getTicketHistory(ticketId);
            return new TicketModel(null, new TicketInfo(), history);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TicketModel revise(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException {
        // todo: permission check
        validateLoggedIn(user);
        return getTicketModel(ticketId);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketModel edit(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException {
        // todo: permission check
        validateLoggedIn(user);
        return getTicketModel(ticketId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkOut(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, TicketAlreadyCheckedOutException {
        // todo: permission check
        validateLoggedIn(user);
        ticketService.checkOut(ticketId, user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkIn(AuthenticatedUser user, long ticketId, AdminStatus status) throws AccessDeniedException,
            TicketNotFoundException, TicketNotCheckedOutException,
            TicketCheckedOutToOtherException {
        // todo: permission check
        validateLoggedIn(user);
        ticketService.checkIn(ticketId, status, user.getUsername(), user.getSuperUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterStatus(AuthenticatedUser user, long ticketId, AdminStatus status) throws AccessDeniedException,
            TicketNotFoundException, TicketCheckedOutToOtherException, TicketEmailException, TicketNotCheckedOutException {
        // todo: permission check
        validateLoggedIn(user);
        ticketService.alterStatus(user, ticketId, status, user.getUsername(), user.getSuperUserName());
    }
    
    @Override
    @Transactional
    public void adminDocSent(AuthenticatedUser user, long id)
            throws AccessDeniedException, TicketNotFoundException,
            TicketCheckedOutToOtherException, TicketEmailException, TicketNotCheckedOutException {
    	validateLoggedIn(user);
        ticketService.alterStatus(user, id, AdminStatusEnum.DOCUMENTS_SUBMITTED, user.getUsername(), user.getSuperUserName());
    }
    
    
    @Override
    @Transactional
    public void adminRenew(AuthenticatedUser user, long id)
            throws AccessDeniedException, TicketNotFoundException,
            TicketCheckedOutToOtherException, TicketEmailException, TicketNotCheckedOutException {
    	validateLoggedIn(user);
        ticketService.alterStatus(user, id, AdminStatusEnum.RENEW, user.getUsername(), user.getSuperUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reassign(AuthenticatedUser user, long ticketId, String hostmasterHandle) throws AccessDeniedException,
            TicketNotFoundException, TicketNotCheckedOutException {
        // todo: permission check
        validateLoggedIn(user);
        ticketService.reassign(ticketId, hostmasterHandle);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AuthenticatedUser user, long ticketId, DomainOperation failureReasons, String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException,
            EmptyRemarkException, TicketCheckedOutToOtherException,
            TicketNotCheckedOutException {
        validateLoggedIn(user);
        ticketService.save(ticketId, failureReasons, null, hostmastersRemark, user
                .getUsername(), user.getSuperUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = TicketEmailException.class)
    public void accept(AuthenticatedUser user, long ticketId, String hostmastersRemark) throws AccessDeniedException,
            TicketNotFoundException, TicketEmailException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException {
        validateLoggedIn(user);
        if (Validator.isEmpty(hostmastersRemark)) {
            hostmastersRemark = ACCEPT_REMARK;
        }
        ticketService.accept(user, ticketId, null, hostmastersRemark, user.getUsername(), user.getSuperUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = TicketEmailException.class)
    public void accept(AuthenticatedUser user, long ticketId, String requestersRemark, String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException,
            TicketEmailException, TicketCheckedOutToOtherException,
            TicketNotCheckedOutException {
        validateLoggedIn(user);
        if (Validator.isEmpty(hostmastersRemark)) {
            hostmastersRemark = ACCEPT_REMARK;
        }
        Validator.assertNotEmpty(requestersRemark, "requestersRemark");
        ticketService.accept(user, ticketId, requestersRemark, hostmastersRemark, user.getUsername(), user.getSuperUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = TicketEmailException.class)
    public void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation failureReasons, String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException, InvalidStatusException, EmptyRemarkException,
            TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException {
        validateLoggedIn(user);
        ticketService.reject(user, ticketId, status, failureReasons,
                null, hostmastersRemark, user.getUsername(), user.getSuperUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = TicketEmailException.class)
    public void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation failureReasons,
                       String requestersRemark, String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException, InvalidStatusException, EmptyRemarkException,
            TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException {
        validateLoggedIn(user);
        Validator.assertNotEmpty(requestersRemark, "requestersRemark");
        ticketService.reject(user, ticketId, status, failureReasons,
                requestersRemark, hostmastersRemark, user.getUsername(), user.getSuperUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AuthenticatedUser user, long ticketId, DomainOperation domainOperation, String hostmastersRemark,
                       boolean clikPaid, boolean forceUpdate)
            throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException,
            ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketNameserverException {
        validateLoggedIn(user);
        ticketService.update(ticketId, domainOperation, null, hostmastersRemark,
                clikPaid, user.getUsername(), user.getSuperUserName(), forceUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AuthenticatedUser user, long ticketId, DomainOperation domainOperation, String requestersRemark,
                       String hostmastersRemark, boolean clikPaid, boolean forceUpdate)
            throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException, ContactNotActiveException,
            TicketEditFlagException, ContactNotFoundException, TicketNameserverException {
        validateLoggedIn(user);
        Validator.assertNotEmpty(requestersRemark, "requestersRemark");
        ticketService.update(ticketId, domainOperation, requestersRemark, hostmastersRemark,
                clikPaid, user.getUsername(), user.getSuperUserName(), forceUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void simpleUpdate(AuthenticatedUser user, long ticketId, DomainOperation domainOperation,
                             String requestersRemark, String hostmastersRemark, boolean clikPaid, boolean forceUpdate)
            throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException,
            TicketAlreadyCheckedOutException, ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketNameserverException {
        validateLoggedIn(user);
        Validator.assertNotEmpty(requestersRemark, "requestersRemark");
        ticketService.simpleUpdate(ticketId, domainOperation, requestersRemark, hostmastersRemark, clikPaid, user.getUsername(), user.getSuperUserName(), forceUpdate);
    }

    private void validateLoggedIn(AuthenticatedUser user)
            throws NoAuthenticatedUserException {
        if (user == null) {
            throw new NoAuthenticatedUserException();
        }
    }

    private TicketModel getTicketModel(long ticketId)
            throws TicketNotFoundException {
        // getTicketHistory the ticket
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        
        // getTicketHistory additional info about the ticket
        String domainName = ticket.getOperation().getDomainNameField()
                .getNewValue();
        String ticketHolder = ticket.getOperation().getDomainHolderField()
                .getNewValue();

        TicketInfo info = getAdditionalInfo(ticket, domainName, ticketHolder, ticket
                .isHasDocuments());

        // getTicketHistory the history of the ticket
        // limit search result to 50 entries
        List<HistoricalObject<Ticket>> history = ticketHistorySearchService
                .getTicketHistory(ticketId);

        return new TicketModel(ticket, info, history);
    }

    private TicketInfo getAdditionalInfo(Ticket ticket, String domainName, String ticketHolder, boolean hasDocuments) {
        Validator.assertNotNull(domainName, "domain name");

        // find previous domain holder
        Domain domain = domainDao.get(domainName);
        String currentHolder = domain != null ? domain.getHolder() : null;
        String previousHolder = domainDao
                .getPreviousHolder(new DomainSearchCriteria(domainName,
                        currentHolder));

        // find the domains owned by the same domain holder as ticketHolder (if
        // any)
        Collection<String> domainNames = new TreeSet<String>();
        Collection<String> pendingDomainNames = new TreeSet<String>();
        if (ticketHolder != null) {
            /*
                * List<Domain> domains = domainDao.find( new
                * DomainSearchCriteria(null, ticketHolder), 0, 50).getResults();
                * for (Domain d : domains) { String dn = d.getName(); if
                * (!domainName.equalsIgnoreCase(dn)) {
                * domainNames.add(d.getName()); } }
                */
            DomainSearchCriteria criteria = new DomainSearchCriteria();
            criteria.setExactDomainHolder(ticketHolder);
            List<String> domains = domainDao.findDomainNames(criteria, 0, MAX_DOMAINS_IN_TICKET);
            for (String dn : domains) {
                if (!domainName.equalsIgnoreCase(dn)) {
                    domainNames.add(dn);
                }
            }
            // feature #1223 - find pending domain names
            TicketSearchCriteria cr = new TicketSearchCriteria();
            cr.setDomainHolder(ticketHolder);
            cr.setTicketType(DomainOperation.DomainOperationType.REG);
            List<String> newDomains = ticketDao.findDomainNames(cr, 0, MAX_DOMAINS_IN_TICKET);
            for (String dn : newDomains) {
                if (!domainName.equalsIgnoreCase(dn)) {
                    pendingDomainNames.add(dn);
                }
            }
        }

        PaymentMethod paymentMethod = null;

        if (ticket.getOperation().getType() != DomainOperation.DomainOperationType.MOD && !ticket.isCharity()) {
            Reservation res = paymentService.getReservationForTicket(ticket.getId());
            if (res == null) {
                paymentMethod = PaymentMethod.ADP;
            } else {
                paymentMethod = res.getPaymentMethod();
            }
        }

        return new TicketInfo(domainName, ticketHolder, previousHolder,
                domainNames, pendingDomainNames, hasDocuments, paymentMethod);
    }

    private long _createTicket(AuthenticatedUser user, Ticket newTicket, ExceptionObserver observer)
            throws AccessDeniedException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDontMatchCategoryException, NicHandleNotFoundException, DomainNotFoundException, ValidationException,
            NicHandleRecreateException, DomainIsNotCharityException, DomainIsCharityException, DuplicatedAdminContact,
            pl.nask.crs.nichandle.exception.NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        validateLoggedIn(user);
        Validator.assertNotNull(newTicket, "new ticket");

        ValidationHelper.validateMandatoryFields(newTicket);

        validateAdminStatus(newTicket.getAdminStatus());
        validateTechStatus(newTicket.getTechStatus());

        String domainName = newTicket.getOperation().getDomainNameField().getNewValue();
        long accountId = newTicket.getOperation().getResellerAccountField().getNewValue().getId();

        switch (newTicket.getOperation().getType()) {
            case REG:
                validateRegTicket(newTicket);
                break;
            case DEL:
                validateDelTicket(domainName, accountId);
                break;
            case MOD:
                Domain domainToModify = domainSearchService.getDomain(domainName);
                validateModDomainTicket(newTicket, domainToModify);
                checkDomainContacts(accountId, newTicket.getOperation().getAdminContactsField(), 1, 2, ContactType.ADMIN);
                checkDomainContacts(accountId, newTicket.getOperation().getTechContactsField(), 1, 1, ContactType.TECH);
                break;
            case XFER:
                Domain domainToTransfer = domainSearchService.getDomain(domainName);
                validateCharityTransfer(newTicket, domainToTransfer);
                //FIXME admin contact and holder should be filled at all?
                completeTicket(newTicket, domainToTransfer, observer, user.getSuperUserName());
                validateTransferDomainTicket(user, newTicket, domainToTransfer);
                checkDomainContacts(accountId, newTicket.getOperation().getAdminContactsField(), 1, 2, ContactType.ADMIN);
                checkDomainContacts(accountId, newTicket.getOperation().getTechContactsField(), 1, 1, ContactType.TECH);
                break;
            default:
                throw new IllegalArgumentException("Unhandled ticket type: " + newTicket.getOperation().getType());
        }
        return ticketService.createTicket(newTicket);
    }

    private void validateCharityTransfer(Ticket ticket, Domain domainToTransfer) throws DomainIsNotCharityException, DomainIsCharityException {
        if (ticket.isCharity()) {
            if (!domainToTransfer.getDsmState().getDomainHolderType().equals(DomainHolderType.Charity)) {
                throw new DomainIsNotCharityException(domainToTransfer.getName());
            }
        } else {
            if (domainToTransfer.getDsmState().getDomainHolderType().equals(DomainHolderType.Charity)) {
                throw new DomainIsCharityException(domainToTransfer.getName());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long create(AuthenticatedUser user, Ticket newTicket)
            throws AccessDeniedException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDontMatchCategoryException, NicHandleNotFoundException, DomainNotFoundException,
            ValidationException, NicHandleRecreateException, DomainIsNotCharityException, DomainIsCharityException,
            DuplicatedAdminContact, pl.nask.crs.nichandle.exception.NicHandleNotFoundException,
            NicHandleNotActiveException, EmptyRemarkException {
        return _createTicket(user, newTicket, null);
    }


    private void validateTechStatus(TechStatus techStatus) {
        try {
            TechStatus status = techStatusDictionary.getEntry(techStatus.getId());
            if (status == null)
                throw new IllegalArgumentException("Invalid techStatus id: " + techStatus.getId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid techStatus id: " + techStatus.getId());
        }
    }

    private void validateAdminStatus(AdminStatus adminStatus) {

        try {
            AdminStatus status = adminStatusDictionary.getEntry(adminStatus.getId());
            if (status == null)
                throw new IllegalArgumentException("Invalid adminStatus id: " + adminStatus.getId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid adminStatus id: " + adminStatus.getId());
        }
    }

    private void recreateNicHandles(Ticket newTicket, ExceptionObserver observer, String superNicHandle) throws NicHandleNotFoundException, NicHandleRecreateException {

        long accountId = newTicket.getOperation().getResellerAccountField().getNewValue().getId();

        for (SimpleDomainFieldChange<Contact> adminContact : newTicket.getOperation().getAdminContactsField()) {
            Contact c = getFieldChangeValue(adminContact);
            NicHandle oldNH = null;
            NicHandle recreatedNH = null;
            if (c != null) {
                try {
                    oldNH = nicHandleSearchService.getNicHandle(c.getNicHandle());
                } catch (pl.nask.crs.nichandle.exception.NicHandleNotFoundException e) {
                    throw new NicHandleNotFoundException(e, c.getNicHandle());
                }

                String remark = "Created during API domain transfer request. Duplicated from " + c.getNicHandle() + " - API.";

                try {
                    recreatedNH = nicHandleService.createNicHandle(oldNH.getName(), oldNH.getCompanyName(), oldNH.getEmail(),
                            oldNH.getAddress(), oldNH.getCounty(), oldNH.getCountry(), accountId,
                            newTicket.getOperation().getResellerAccountField().getNewValue().getName(),
                            null, null, remark, newTicket.getCreator().getNicHandle(), null, true, superNicHandle, true, false);
                    adminContact.setNewValue(new Contact(recreatedNH.getNicHandleId()));
                } catch (NicHandleEmailException e) {
                    if (observer != null)
                        observer.notice(e);
//		        	try {
//						recreatedNH = nicHandleSearchService.getNicHandle(e.getNicHandleId());
//					} catch (pl.nask.crs.nichandle.exception.NicHandleNotFoundException e1) {
//						throw new NicHandleNotFoundException(e, e.getNicHandleId());
//					}
                } catch (Exception e) {
                    throw new NicHandleRecreateException(c.getNicHandle(), e);
                }
            }
        }
    }

    private void validateTransferDomainTicket(AuthenticatedUser user, Ticket newTicket, Domain domainToModify)
            throws DomainAlreadyManagedByResellerException,
            DomainDeletionPendingException, DomainModificationPendingException, DomainTransaferPendingException,
            TooFewNameserversException, TooManyNameserversException,
            DuplicatedNameserverException, NameserverNameSyntaxException,
            IpSyntaxException, GlueNotAllowedException, GlueRequiredException, DuplicatedAdminContact, DomainNotFoundException, pl.nask.crs.nichandle.exception.NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {

        String domainName = newTicket.getOperation().getDomainNameField().getNewValue();
        long accountId = newTicket.getOperation().getResellerAccountField().getNewValue().getId();

        if (isDirectToDirectTransfer(accountId, domainToModify)) {
            String oldBillingNH = domainToModify.getBillingContacts().get(0).getNicHandle();
            String newBillingNH = newTicket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
            if (oldBillingNH.equals(newBillingNH))
                throw new DomainAlreadyManagedByResellerException(domainName);
        } else {
            if (accountId == domainToModify.getResellerAccount().getId())
                throw new DomainAlreadyManagedByResellerException(domainName);
        }

        try {
            validateTicketPending(domainName);
        } catch (DomainModificationPendingException e) {
            ticketService.delete(internalGetTicketForDomain(e.getDomainName()).getId());
            domainService.modifyRemark(user, domainName, "Mod Ticket cancelled by Transfer Ticket");
        }

        ValidationHelper.checkDomainNsc(domainName, newTicket.getOperation().getNameserversField(), applicationConfig.getNameserverMinCount(), applicationConfig.getNameserverMaxCount());
    }

    private boolean isDirectToDirectTransfer(long accountId, Domain domain) {
        return accountId == DIRECT_ACCOUNT && domain.getResellerAccount().getId() == DIRECT_ACCOUNT;
    }

    private void completeTicket(Ticket ticket, Domain domain, ExceptionObserver observer, String superUserName) throws NicHandleNotFoundException, NicHandleRecreateException {
        ticket.getOperation().getDomainHolderField().setNewValue(domain.getHolder());
        ticket.getOperation().getDomainHolderCategoryField().setNewValue(domain.getHolderCategory());
        ticket.getOperation().getDomainHolderClassField().setNewValue(domain.getHolderClass());
        if (Validator.isEmpty(ticket.getOperation().getAdminContactsField())) {
            updateAdminContactsFromDomain(ticket, domain, observer, superUserName);
        }
        if (Validator.isEmpty(ticket.getOperation().getNameserversField())) {
            updateNsFromDomain(ticket, domain);
        }
    }

    public void updateNsFromDomain(Ticket ticket, Domain domain) {
        List<NameserverChange> ticketNss = new ArrayList<NameserverChange>();
        for (Nameserver ns : domain.getNameservers()) {
            ticketNss.add(new NameserverChange(new SimpleDomainFieldChange<String>(null, ns.getName()), new SimpleDomainFieldChange<String>(null, ns.getIpAddress().getAddress())));
        }
        ticket.getOperation().setNameserversField(ticketNss);
    }

    private void updateAdminContactsFromDomain(Ticket ticket, Domain domain, ExceptionObserver observer, String superUserName) throws NicHandleNotFoundException, NicHandleRecreateException {
        List<SimpleDomainFieldChange<Contact>> adminContacts = new ArrayList<SimpleDomainFieldChange<Contact>>();
        for (Contact c : domain.getAdminContacts()) {
            adminContacts.add(new SimpleDomainFieldChange<Contact>(null, c));
        }
        ticket.getOperation().getAdminContactsField().addAll(adminContacts);
        recreateNicHandles(ticket, observer, superUserName);
    }

    private void validateModDomainTicket(Ticket newTicket, Domain domainToModify)
            throws DomainManagedByAnotherResellerException,
            DomainInNRPException, DomainDeletionPendingException,
            DomainModificationPendingException, TooFewNameserversException,
            TooManyNameserversException, DuplicatedNameserverException,
            NameserverNameSyntaxException, IpSyntaxException,
            GlueNotAllowedException, GlueRequiredException,
            HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDontMatchCategoryException, DomainTransaferPendingException {

        String domainName = newTicket.getOperation().getDomainNameField().getNewValue();
        long accountId = newTicket.getOperation().getResellerAccountField().getNewValue().getId();

        validateAndAdjustHolderEntities(newTicket);

        ValidationHelper.validateAccountId(accountId, domainToModify);

        validateTicketPending(domainName);
    }

    private void validateDelTicket(String domainName, long accountId)
            throws DomainNotFoundException,
            DomainManagedByAnotherResellerException, DomainInNRPException,
            DomainDeletionPendingException, DomainModificationPendingException, DomainTransaferPendingException {
        // validate if domain is managed by proper reseller
        Domain domainToDelete = domainSearchService.getDomain(domainName);
        ValidationHelper.validateAccountId(accountId, domainToDelete);

        // validate if domain is in MSD state
        ValidationHelper.validateNRP(domainToDelete);

        // validate if other ticket for domain exists
        validateTicketPending(domainName);
    }

    private void validateRegTicket(Ticket newTicket) throws DomainNameSyntaxException,
            DomainNameExistsOrPendingException, TooFewNameserversException,
            TooManyNameserversException, DuplicatedNameserverException,
            NameserverNameSyntaxException, IpSyntaxException,
            GlueNotAllowedException, GlueRequiredException,
            TooFewAdminContactsException, TooFewTechContactsException,
            TooManyAdminContactsException, TooManyTechContactsException,
            DuplicatedContactException, ContactSyntaxException, NicHandleNotFoundException,
            DomainHolderMandatoryException, HolderCategoryMandatoryException, HolderClassMandatoryException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException {

        ValidationHelper.validateHolder(newTicket);
        validateAndAdjustHolderEntities(newTicket);

        String domainName = newTicket.getOperation().getDomainNameField().getNewValue();
        long accountId = newTicket.getOperation().getResellerAccountField().getNewValue().getId();

        try {
            DomainNameValidator.validateIedrName(domainName);
        } catch (InvalidDomainNameException e) {
            throw new DomainNameSyntaxException(e, domainName);
        }

        // validate if domain or tickets for domain already exists
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainName(domainName);
        if (domainSearchService.find(criteria, 0, 10, null).getTotalResults() != 0 || internalGetTicketForDomain(domainName) != null)
            throw new DomainNameExistsOrPendingException(domainName);


        // validate domain nameservers
        ValidationHelper.checkDomainNsc(domainName, newTicket.getOperation().getNameserversField(), applicationConfig.getNameserverMinCount(), applicationConfig.getNameserverMaxCount());

        // validate contacts
        checkDomainContacts(accountId, newTicket.getOperation().getAdminContactsField(), 1, 2, ContactType.ADMIN);
        checkDomainContacts(accountId, newTicket.getOperation().getTechContactsField(), 1, 1, ContactType.TECH);
    }

    private void validateAndAdjustHolderEntities(Ticket ticket) throws HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException {
        ClassCategoryEntity entity = entitiesService.checkHolderEntities(ticket.getOperation().getDomainHolderClassField().getNewValue(), ticket.getOperation().getDomainHolderCategoryField().getNewValue(), ticket.getCreator() != null ? ticket.getCreator().getNicHandle() : null);
        ticket.getOperation().getDomainHolderClassField().setNewValue(entity.getClassName());
        ticket.getOperation().getDomainHolderCategoryField().setNewValue(entity.getCategoryName());
    }

    @Override
    public Ticket getTicketForDomain(AuthenticatedUser user, String domainName) {
        return internalGetTicketForDomain(domainName);
    }

    @Override
    public Ticket internalGetTicketForDomain(String domainName) {
        TicketSearchCriteria crit = new TicketSearchCriteria();
        crit.setDomainName(domainName);
        LimitedSearchResult<Ticket> s = ticketSearchService.find(crit, 0, 1, null);
        if (s.getTotalResults() > 1) {
            throw new TooManyTicketsException("Too many tickets found for domain name = " + domainName);
        } else if (s.getTotalResults() == 1) {
            long id = s.getResults().get(0).getId();
            return ticketDao.get(id);
        } else {
            return null;
        }
    }

    private void checkDomainContacts(long accountId, List<SimpleDomainFieldChange<Contact>> contacts, int min, int max, ContactType type)
            throws TooFewAdminContactsException, TooFewTechContactsException, TooManyAdminContactsException, TooManyTechContactsException, DuplicatedContactException, ContactSyntaxException, NicHandleNotFoundException {
        // filter out empty contacts from the list!
        for (int i = contacts.size() - 1; i >= 0; i--) {
            if (Validator.isEmpty(contacts.get(i).getNewValue().getNicHandle()))
                contacts.remove(i);
        }
        checkDomainContactsSize(min, max, contacts.size(), type);

        Set<String> contactsSet = new HashSet<String>();
        for (SimpleDomainFieldChange<Contact> c : contacts) {
            if (!contactsSet.add(c.getNewValue().getNicHandle()))
                throw new DuplicatedContactException(c.getNewValue().getNicHandle());
            if (!c.getNewValue().getNicHandle().endsWith("-IEDR"))
                throw new ContactSyntaxException(c.getNewValue().getNicHandle());

            NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
            criteria.setNicHandleId(c.getNewValue().getNicHandle());
            criteria.setAccountNumber(accountId);
            LimitedSearchResult<NicHandle> searchResult = nicHandleSearchService.findNicHandle(criteria, 0, 10, null);
            if (searchResult.getTotalResults() == 0)
                throw new NicHandleNotFoundException(c.getNewValue().getNicHandle());
        }
    }

    private void checkDomainContactsSize(int min, int max, int size,
                                         ContactType type) throws TooManyAdminContactsException, TooManyTechContactsException, TooFewAdminContactsException, TooFewTechContactsException {
        if (size < min) {
            switch (type) {
                case ADMIN:
                    throw new TooFewAdminContactsException(min, size);
                case TECH:
                    throw new TooFewTechContactsException(min, size);
                default:
                    throw new IllegalArgumentException("Illegal contact type");
            }
        }
        if (size > max) {
            switch (type) {
                case ADMIN:
                    throw new TooManyAdminContactsException(max, size);
                case TECH:
                    throw new TooManyTechContactsException(max, size);
                default:
                    throw new IllegalArgumentException("Illegal contact type");
            }
        }
    }

    private void validateTicketPending(String domainName) throws DomainDeletionPendingException, DomainModificationPendingException, DomainTransaferPendingException {
        Ticket ticket = internalGetTicketForDomain(domainName);
        if (ticket != null) {
            switch (ticket.getOperation().getType()) {
                case MOD:
                    throw new DomainModificationPendingException(domainName);
                case XFER:
                    throw new DomainTransaferPendingException(domainName);
                case DEL:
                    throw new DomainDeletionPendingException(domainName);
                default:
                	// only MOD,XFER and DEL tickets are considered. REG tickets are 'safe'
            }
        }
    }

    private <T> T getFieldChangeValue(SimpleDomainFieldChange<T> field) {
        if (field.getNewValue() != null)
            return field.getNewValue();
        if (field.getCurrentValue() != null)
            return field.getCurrentValue();
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public void sendTicketExpirationEmails(AuthenticatedUser user) {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        int ticketExpirationPeriod = applicationConfig.getTicketExpirationPeriod();
        Date notificationDate = DateUtils.getCurrDate(-(ticketExpirationPeriod - TICKET_EXPIRATION_NOTIFICATION_PERIOD));
        criteria.setFrom(DateUtils.startOfDay(notificationDate));
        criteria.setTo(DateUtils.endOfDay(notificationDate));
        List<Ticket> tickets = ticketSearchService.findAll(criteria, null);
        for (Ticket ticket : tickets) {
            ticketService.sendTicketExpirationEmail(user, ticket, TICKET_EXPIRATION_NOTIFICATION_PERIOD);
        }
    }
}
