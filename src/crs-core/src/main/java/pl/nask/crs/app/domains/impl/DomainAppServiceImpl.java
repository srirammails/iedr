package pl.nask.crs.app.domains.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.DomainAvailability;
import pl.nask.crs.app.domains.ExtendedDomainInfo;
import pl.nask.crs.app.utils.UserValidator;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.country.Country;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.BulkExportAuthCodesException;
import pl.nask.crs.domains.exceptions.BulkExportAuthCodesTotalException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.TransferedDomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.entities.ClassCategoryEntity;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.service.EntitiesService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;

/**
 * @author Kasia Fulara
 * @author Piotr Tkaczyk
 * @author Artur Gniadzik
 */
public class DomainAppServiceImpl implements DomainAppService {

    private final static Logger LOG = Logger.getLogger(DomainAppServiceImpl.class);

    public static final long DIRECT_ACCOUNT = 1;

    private DomainSearchService domainSearchService;
    private DomainService domainService;
    private DocumentService documentService;
    private TicketSearchService ticketSearchService;
    private AccountSearchService accountSearchService;
    private NicHandleSearchService nicHandleSearchService;
    private Dictionary<String, BillingStatus> billingStatusDictionary;
    private ApplicationConfig applicationConfig;
    private EntitiesService entitiesService;

    public DomainAppServiceImpl(DomainSearchService domainSearchService, DocumentService documentService, TicketSearchService ticketSearchService,
                                DomainService domainService, AccountSearchService accountSearchService, NicHandleSearchService nicHandleSearchService,
                                Dictionary<String, BillingStatus> billingStatusDictionary, ApplicationConfig applicationConfig, EntitiesService entitiesService) {

        Validator.assertNotNull(domainSearchService, "domain search service");
        Validator.assertNotNull(documentService, "document service");
        Validator.assertNotNull(ticketSearchService, "ticket search service");
        Validator.assertNotNull(domainService, "domain service");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        this.domainSearchService = domainSearchService;
        this.documentService = documentService;
        this.ticketSearchService = ticketSearchService;
        this.domainService = domainService;
        this.accountSearchService = accountSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.billingStatusDictionary = billingStatusDictionary;
        this.applicationConfig = applicationConfig;
        this.entitiesService = entitiesService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(AuthenticatedUser user, NewDomain domain) throws AccessDeniedException, NicHandleNotFoundException,
            NicHandleNotActiveException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, ClassCategoryPermissionException {
        UserValidator.validateLoggedIn(user);
        domainService.create(user, domain, new OpInfo(user));
    }

    @Override
    @Transactional(readOnly = true)
    public ExtendedDomainInfo view(AuthenticatedUser user, String domainName) throws AccessDeniedException, DomainNotFoundException {
        UserValidator.validateLoggedIn(user);
        return getDomainWrapper(domainName);
    }

	@Override
    @Transactional(readOnly = true)
	public Domain viewPlain(AuthenticatedUser user, String domainName)
			throws AccessDeniedException, DomainNotFoundException {
		UserValidator.validateLoggedIn(user);
		return domainSearchService.getDomain(domainName);
	}

    private ExtendedDomainInfo getDomainWrapper(String domainName) throws DomainNotFoundException {
        Domain domain = domainSearchService.getDomain(domainName);
        ExtendedDomainInfo domainWrapper = new ExtendedDomainInfo(domain);
        domainWrapper.setDocuments(checkDocumentsForDomain(domainName));
        domainWrapper.setTickets(checkTicketsForDomain(domainName));
        addDomainHoldersDomains(domainName, domain.getHolder(), domainWrapper);
        return domainWrapper;
    }

    private boolean checkDocumentsForDomain(String domainName) {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDomainName(domainName);
        LimitedSearchResult<Document> docs = documentService.findDocuments(
                criteria, 0, 0, null);
        return docs.getTotalResults() > 0;
    }

    private boolean checkTicketsForDomain(String domainName) {
        //find tickets
        TicketSearchCriteria ticketsForDomain = new TicketSearchCriteria();
        ticketsForDomain.setDomainName(domainName);
        LimitedSearchResult<Ticket> tickets = ticketSearchService.find(ticketsForDomain, 0, 0, null);
        return tickets.getTotalResults() > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public ExtendedDomainInfo edit(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainNotFoundException {
        UserValidator.validateLoggedIn(user);
        return getDomainWrapper(domainName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AuthenticatedUser user, Domain domain)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, NicHandleNotFoundException, NicHandleNotActiveException {
        UserValidator.validateLoggedIn(user);
        domainService.save(domain, new OpInfo(user));
    }

    public Dictionary<String, BillingStatus> getBillingStatusDictionary() {
        return billingStatusDictionary;
    }

    public void setBillingStatusDictionary(Dictionary<String, BillingStatus> billingStatusDictionary) {
        this.billingStatusDictionary = billingStatusDictionary;
    }

    public void setAccountSearchService(AccountSearchService accountSearchService) {
        this.accountSearchService = accountSearchService;
    }

    public AccountSearchService getAccountSearchService() {
        return accountSearchService;
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Domain> search(AuthenticatedUser user, DomainSearchCriteria criteria, long offset, long limit,
                                              List<SortCriterion> orderBy)
            throws AccessDeniedException {
        UserValidator.validateLoggedIn(user);
        return domainSearchService.find(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Domain> searchFull(AuthenticatedUser user, DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws AccessDeniedException {
        UserValidator.validateLoggedIn(user);
        return domainSearchService.fullFind(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Domain> findTransferedDomains(AuthenticatedUser user, TransferedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        UserValidator.validateLoggedIn(user);
        return domainSearchService.findTransferedDomains(user.getUsername(), criteria, offset, limit, sortBy);
    }

    private void addDomainHoldersDomains(String domainName, String domainHolder, ExtendedDomainInfo domainWrapper) {
        Validator.assertNotNull(domainName, "domain name");

        // find the domains owned by the same domain holder as domainHolder
        Collection<String> domainNames = new TreeSet<String>();
        Collection<String> pendingDomainNames = new TreeSet<String>();
        if (domainHolder != null) {
            // return maximum 100 domain names
            List<Domain> domains = domainSearchService.find(new DomainSearchCriteria(null, domainHolder), 0, 100, null).getResults();
            for (Domain dn : domains) {
                if (!domainName.equalsIgnoreCase(dn.getName())) {
                    domainNames.add(dn.getName());
                }
            }
            // feature #1223 - find pending domain names
            TicketSearchCriteria cr = new TicketSearchCriteria();
            cr.setDomainHolder(domainHolder);
            cr.setTicketType(DomainOperation.DomainOperationType.REG);
            List<String> newDomains = ticketSearchService.findDomainNames(cr, 0 , 100);
            for (String dn : newDomains) {
                if (!domainName.equalsIgnoreCase(dn)) {
                    pendingDomainNames.add(dn);
                }
            }
        }
        domainWrapper.setRelatedDomainNames(domainNames);
        domainWrapper.setPendingDomainNames(pendingDomainNames);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkDomainExists(AuthenticatedUser user, String domainName) {
    	UserValidator.validateLoggedIn(user);
    	return isDomainCreated(domainName);
    }

    @Override
    @Transactional
    public void enterVoluntaryNRP(AuthenticatedUser user, String... domainNames) {
    	UserValidator.validateLoggedIn(user);
    	domainService.enterVoluntaryNRP(user, new OpInfo(user), domainNames);
    }

    @Override
    @Transactional
    public void removeFromVoluntaryNRP(AuthenticatedUser user, String... domainNames) {
    	UserValidator.validateLoggedIn(user);
    	domainService.removeFromVoluntaryNRP(user, new OpInfo(user), domainNames);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEventValid(AuthenticatedUser user, String domainName,
    		DsmEventName eventName) {
    	UserValidator.validateLoggedIn(user);
    	return domainService.isEventValid(domainName, eventName);
    }

    @Override
    @Transactional(readOnly = true)
    public DomainAvailability checkAvailability(AuthenticatedUser user, String domainName) {
        UserValidator.validateLoggedIn(user);
        try {
            boolean domainCreated = isDomainCreated(domainName);
            boolean domainManagedByUser = domainCreated && isManagedByUser(domainName, user.getUsername());
            return new DomainAvailability(domainName, domainCreated, isRegTicketCreated(domainName), user.getUsername(), domainManagedByUser, false);
        } catch (Exception e) {
            LOG.error("Error while checking domain availability: " + domainName, e);
            return new DomainAvailability(domainName, false, false, user.getUsername(), false, true);
        }
    }

	private boolean isRegTicketCreated(String domainName) {
		TicketSearchCriteria crit = new TicketSearchCriteria();
		crit.setDomainName(domainName);
		crit.setTicketType(DomainOperationType.REG);
		List<String> res = ticketSearchService.findDomainNames(crit, 0, 1);
		return !res.isEmpty();
	}

	private boolean isDomainCreated(String domainName) {
		try {
    		domainSearchService.getDomain(domainName);
    		return true;
    	} catch (DomainNotFoundException e) {
    		return false;
    	}
	}

    private boolean isManagedByUser(String domainName, String userName) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(userName);
            Domain domain = domainSearchService.getDomain(domainName);
            long userAccountId = nicHandle.getAccount().getId();
            if (isDirectUser(userAccountId)) {
                String domainBillingNH = domain.getBillingContacts().get(0).getNicHandle();
                return domainBillingNH.equalsIgnoreCase(userName);
            } else {
                return userAccountId == domain.getResellerAccount().getId();
            }
        } catch (DomainNotFoundException e) {
            return false;
        } catch (NicHandleNotFoundException e) {
            // should never happen
            throw new IllegalStateException(e);
        }
    }

    private boolean isDirectUser(long accountId) {
        return accountId == DIRECT_ACCOUNT;
    }

    @Override
    public void pushQ(AuthenticatedUser user, OpInfo opInfo) {
        Date now  = new Date();
        deleteDatePushQ(user, opInfo, now);
        suspensionDatePushQ(user, opInfo, now);
        renewDatePushQ(user, opInfo);
        deleteDomainPushQ(user, opInfo);
    }

    private void deleteDomainPushQ(AuthenticatedUser user, OpInfo opInfo) {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNrpStatuses(Arrays.asList(NRPStatus.Deleted));
        List<Domain> domains = domainSearchService.findAll(criteria, null);
        for (Domain domain : domains) {
            runDeleteProcess(user, opInfo, domain.getName());
        }
    }

    private void runDeleteProcess(AuthenticatedUser user, OpInfo opInfo, String domainName) {
        try {
            domainService.runDeletedDomainRemoval(user, domainName, opInfo);
        } catch (Exception e) {
            LOG.error("Exception during running DeletedDomainRemoval event!", e);
        }
    }

    private void renewDatePushQ(AuthenticatedUser user, OpInfo opInfo) {
        Date yesterday = DateUtils.getCurrDate(-1);
        Date yesterdayYesterday = DateUtils.getCurrDate(-2);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setAttachReservationInfo(true);
        criteria.setRenewTo(yesterday);
        criteria.setNrpStatuses(Arrays.asList(NRPStatus.Active, NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended));
        List<Domain> domains = domainSearchService.findAll(criteria, null);
        for (Domain domain : domains) {
        	if ((domain.hasPendingADPReservations() || domain.hasPendingCCReservations()) && domain.getRenewDate().after(yesterdayYesterday)) {
        		LOG.info("Domain " + domain.getName() + " has unsettled reservation, skipping");
        	} else {
        		runRenewalDatePasses(user, opInfo, domain.getName());
        	}
        }
    }

	private void runRenewalDatePasses(AuthenticatedUser user, OpInfo opInfo, String domainName) {
        try {
            domainService.runRenewalDatePasses(user, domainName, opInfo);
        } catch (Exception e) {
            LOG.error("Exception during running RenewaDatePasses event!", e);
        }
    }

    private void suspensionDatePushQ(AuthenticatedUser user, OpInfo opInfo, Date now) {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setSuspTo(now);
        criteria.setNrpStatuses(Arrays.asList(NRPStatus.InvoluntaryMailed, NRPStatus.VoluntaryMailed));
        List<Domain> domains = domainSearchService.findAll(criteria, null);
        for (Domain domain : domains) {
            runSuspensionDatePasses(user, opInfo, domain.getName());
        }
    }

    private void runSuspensionDatePasses(AuthenticatedUser user, OpInfo opInfo, String domainName) {
        try {
            domainService.runSuspensionDatePasses(user, domainName, opInfo);
        } catch (Exception e) {
            LOG.error("Exception during running SuspensionDatePasses event!", e);
        }
    }

    private void deleteDatePushQ(AuthenticatedUser user, OpInfo opInfo, Date now) {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDelTo(now);
        criteria.setNrpStatuses(Arrays.asList(NRPStatus.InvoluntarySuspended, NRPStatus.VoluntarySuspended));
        List<Domain> domains = domainSearchService.findAll(criteria, null);
        for (Domain domain : domains) {
            runDeletionDatePasses(user, opInfo, domain.getName());
        }
    }

    private void runDeletionDatePasses(AuthenticatedUser user, OpInfo opInfo, String domainName) {
        try {
            domainService.runDeletionDatePasses(user, domainName, opInfo);
        } catch (Exception e) {
            LOG.error("Exception during running DeletionDatePasses event!", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void forceDSMEvent(AuthenticatedUser user, List<String> domainNames, DsmEventName eventName, String remark) throws DomainNotFoundException, EmptyRemarkException, DomainIllegalStateException {
        UserValidator.validateLoggedIn(user);
        domainService.forceDSMEvent(user, domainNames, eventName, new OpInfo(user, remark));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void forceDSMState(AuthenticatedUser user, List<String> domainNames, int state, String remark) throws DomainNotFoundException, EmptyRemarkException {
        UserValidator.validateLoggedIn(user);
        domainService.forceDSMState(domainNames, state, new OpInfo(user, remark));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getDsmStates(AuthenticatedUser user) {
        UserValidator.validateLoggedIn(user);
        return domainService.getDsmStates();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHolderType(AuthenticatedUser user, String domainName, DomainHolderType newHolderType, String remark) throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException {
    	domainService.updateHolderType(user, domainName, newHolderType, new OpInfo(user, remark)); 
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enterWipo(AuthenticatedUser user, String domainName, String hostmastersRemark) throws EmptyRemarkException ,DomainIllegalStateException ,DomainNotFoundException {
    	domainService.enterWipo(user, domainName, new OpInfo(user, hostmastersRemark));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exitWipo(AuthenticatedUser user, String domainName,
    		String hostmastersRemark) throws EmptyRemarkException,
    		DomainIllegalStateException, DomainNotFoundException {
    	domainService.exitWipo(user, domainName, new OpInfo(user, hostmastersRemark));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revertToBillable(AuthenticatedUser user,
    		List<String> domainNames) {
    	domainService.revertToBillable(user, domainNames, new OpInfo(user));
    }

    @Override
    public void runNotificationProcess(AuthenticatedUser user) {
//        List<Integer> suspensionNotificationPeriods = applicationConfig.getSuspensionNotificationPeriods();
//        sendNotificationsForExpiringDomains(NotificationType.SUSPENSION, suspensionNotificationPeriods);
        List<Integer> renewalNotificationPeriods = applicationConfig.getRenewalNotificationPeriods();
        sendNotificationForExpiredDomains(NotificationType.RENEWAL, user);
        List<Integer> periods = new ArrayList<Integer>();
        Collections.sort(renewalNotificationPeriods);
        periods.add(0);
        periods.addAll(renewalNotificationPeriods);
        sendNotificationsForExpiringDomains(NotificationType.RENEWAL, user, periods);
    }

    private void sendNotificationForExpiredDomains(NotificationType type, AuthenticatedUser user) {
        List<Domain> domainToNotify = getExpiredDomains(type);
        LOG.info("Found " + domainToNotify.size() + " expired domains. ");
        LOG.info("Expired domains are: " + domainToNotify);
        for (Domain domain : domainToNotify) {
            sendNotification(domain, type, user);
        }
    }

    private List<Domain> getExpiredDomains(NotificationType type) {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setType(CustomerType.Direct);
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        switch (type) {
            case RENEWAL:
                criteria.setRenewalDate(new Date());
                break;
            default:
                throw new IllegalArgumentException("Invalid notification type" + type);

        }
        return domainSearchService.findAll(criteria, null);
    }

    private void sendNotification(Domain domain, NotificationType type, AuthenticatedUser user) {
        try {
            domainService.sendNotification(domain, type, user);
        } catch (Exception e) {
            LOG.error("Exception during sending notification!", e);
        }
    }

    private void sendNotificationsForExpiringDomains(NotificationType type, AuthenticatedUser user, List<Integer> notificationPeriods) {
        for (int i=1; i < notificationPeriods.size(); i++) {
            sendNotificationForPeriod(notificationPeriods.get(i-1), notificationPeriods.get(i), type, user);
        }
    }

    private void sendNotificationForPeriod(int periodStart, int periodEnd, NotificationType type, AuthenticatedUser user) {
        List<Domain> domainToNotify = getExpiringDomains(periodStart, periodEnd, type);
        LOG.info("Sending close expiry notification (" + periodEnd + " days) for the following (" + domainToNotify.size() + " domains: " );
        LOG.info(domainToNotify);
        for (Domain domain : domainToNotify) {
        	sendNotification(domain, type, user, periodEnd);
        }
    }

    private List<Domain> getExpiringDomains(int periodStart, int periodEnd, NotificationType type) {
        Date notificationDate = DateUtils.getCurrDate(periodEnd);
        Date notificationDateMin = DateUtils.getCurrDate(periodStart + 1);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setType(CustomerType.Direct);
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        switch (type) {
            case RENEWAL:
                criteria.setRenewTo(notificationDate);
                criteria.setRenewFrom(notificationDateMin);
                break;
            case SUSPENSION:
                criteria.setSuspTo(notificationDate);
                criteria.setSuspFrom(notificationDateMin);
                break;
            default:
                throw new IllegalArgumentException("Invalid notification type" + type);

        }
        return domainSearchService.findAll(criteria, null);
    }

    private void sendNotification(Domain domain, NotificationType type, AuthenticatedUser user, int period) {
        try {
            domainService.sendNotification(domain, type, user, period);
        } catch (Exception e) {
            LOG.error("Exception during sending notification!", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClassCategoryEntity> getClassCategory(AuthenticatedUser user) {
        UserValidator.validateLoggedIn(user);
        return entitiesService.getClassCategory();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> checkPayAvailable(AuthenticatedUser user, List<String> domainNames) {
        UserValidator.validateLoggedIn(user);
        return domainService.checkEventAvailable(domainNames, DsmEventName.PaymentInitiated);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyRenewalMode(AuthenticatedUser user, List<String> domainNames, RenewalMode renewalMode) {
        UserValidator.validateLoggedIn(user);
        domainService.modifyRenewalMode(user, domainNames, renewalMode, new OpInfo(user));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Country> getCountries(AuthenticatedUser user) {
    	// does not need the user to be logged-in
//        UserValidator.validateLoggedIn(user);
        return domainService.getCountries();
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean isCharity(AuthenticatedUser user, String domainName) throws DomainNotFoundException {
    	Domain d = domainSearchService.getDomain(domainName);
    	return d.getDsmState().getDomainHolderType() == DomainHolderType.Charity;
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<DeletedDomain> findDeletedDomains(AuthenticatedUser user, DeletedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
        UserValidator.validateLoggedIn(user);
        return domainSearchService.findDeletedDomains(criteria, offset, limit, orderBy);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void authCodeCleanup(OpInfo opInfo) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setAuthcExpTo(new Date());
        List<Domain> domains = domainSearchService.findAll(criteria, null);
        for (Domain domain : domains) {
            domainService.clearAuthCode(domain.getName(), opInfo);
        }
        authCodePortalCleanup(opInfo);
    }

    private void authCodePortalCleanup(OpInfo opInfo) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setAuthCodeFromPortal(true);
        List<Domain> domains = domainSearchService.findAll(criteria, null);
        for (Domain domain : domains) {
            domainService.clearAuthCodePortalCount(domain.getName(), opInfo);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendAuthCodeByEmail(AuthenticatedUser user, String domainName) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        domainService.sendAuthCodeByEmail(user, domainName);
    }

    @Transactional(noRollbackFor = BulkExportAuthCodesException.class)
    @Override
    public void bulkExportAuthCodes(AuthenticatedUser user, List<String> domainNames) throws BulkExportAuthCodesException, BulkExportAuthCodesTotalException {
        List<Domain> domainList = new ArrayList<Domain>();
        List<String> errorList = new ArrayList<String>();
        UserValidator.validateLoggedIn(user);
        for (String domainName : domainNames) {
            try {
                domainService.getOrCreateAuthCode(user.getUsername(), domainName);
                domainList.add(domainSearchService.getDomain(domainName));
            } catch (Exception e) {
                LOG.error("Exception during generaring an authcode for a domain: " + domainName, e);
                errorList.add(domainName + ": " + e.getMessage());
            }
        }
        if (domainList.isEmpty()) {
            throw new BulkExportAuthCodesTotalException(errorList.toString());
        }
        Map<Contact, List<Domain>> billCMap = mapDomainsByBillC(domainList);
        for (Map.Entry<Contact, List<Domain>> billCEntry : billCMap.entrySet()) {
            Contact billC = billCEntry.getKey();
            List<Domain> billCDomainList = billCEntry.getValue();
            Map<Contact, List<Domain>> adminMap = mapDomainsByAdmin(billCDomainList);
            for (Map.Entry<Contact, List<Domain>> entry : adminMap.entrySet()) {
                Contact admin = entry.getKey();
                List<Domain> adminDomainList = entry.getValue();
                try {
                    domainService.sendBulkAuthCodesByEmail(user, billC, admin, adminDomainList);
                } catch (Exception e) {
                    LOG.error("Exception during exporting authcodes (billC: " + billC.getNicHandle() + ", adminC: " + admin.getNicHandle() + ")", e);
                    errorList.add(billC.getNicHandle() + ", " + admin.getNicHandle() + ": " + e.getMessage());
                }
            }
        }
        if (!errorList.isEmpty()) {
            throw new BulkExportAuthCodesException(errorList.toString(), domainList.size(), domainNames.size());
        }
    }

    private Map<Contact, List<Domain>> mapDomainsByBillC(List<Domain> domainList) {
        Map<Contact, List<Domain>> billCMap = new HashMap<Contact, List<Domain>>();
        for (Domain domain : domainList) {
            Contact billC = domain.getBillingContact();
            List<Domain> list = billCMap.get(billC);
            if (list == null) {
                list = new ArrayList<Domain>();
                list.add(domain);
                billCMap.put(billC, list);
            } else {
                list.add(domain);
            }
        }
        return billCMap;
    }

    private Map<Contact, List<Domain>> mapDomainsByAdmin(List<Domain> domainList) {
        Map<Contact, List<Domain>> adminMap = new HashMap<Contact, List<Domain>>();
        for (Domain domain : domainList) {
            for (Contact admin : domain.getAdminContacts()) {
                List<Domain> list = adminMap.get(admin);
                if (list == null) {
                    list = new ArrayList<Domain>();
                    list.add(domain);
                    adminMap.put(admin, list);
                } else {
                    list.add(domain);
                }
            }
        }
        return adminMap;
    }
}
