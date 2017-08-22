package pl.nask.crs.domains.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.commons.Authcode;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.domains.AuthCode;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.DomainNotification;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.NotificationType;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.dsm.events.CreateGIBODomain;
import pl.nask.crs.domains.dsm.events.DeletedDomainRemoval;
import pl.nask.crs.domains.dsm.events.DeletionDatePasses;
import pl.nask.crs.domains.dsm.events.EnterVoluntaryNRP;
import pl.nask.crs.domains.dsm.events.RemoveFromVoluntaryNRP;
import pl.nask.crs.domains.dsm.events.RenewalDatePasses;
import pl.nask.crs.domains.dsm.events.SetAutoRenew;
import pl.nask.crs.domains.dsm.events.SetNoAutoRenew;
import pl.nask.crs.domains.dsm.events.SetOnceAutoRenew;
import pl.nask.crs.domains.dsm.events.SuspensionDatePasses;
import pl.nask.crs.domains.email.AuthCodeEmailParameters;
import pl.nask.crs.domains.email.DSMForceEmailParams;
import pl.nask.crs.domains.email.DomainListEmailParams;
import pl.nask.crs.domains.email.DomainNotificationEmailParams;
import pl.nask.crs.domains.exceptions.AuthCodePortalLimitException;
import pl.nask.crs.domains.exceptions.DomainEmailException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.entities.ClassCategoryEntity;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.service.EntitiesService;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Kasia Fulara
 * @author Marianna Mysiorska
 */
public class DomainServiceImpl implements DomainService {

    private static final Logger LOG = Logger.getLogger(DomainSearchServiceImpl.class);

    private DomainServiceHelper serviceHelper;
    private DomainDAO domainDao;
    private NicHandleSearchService nicHandleSearchService;
    private EntitiesService entitiesService;
    private ApplicationConfig applicationConfig;

    private DomainStateMachine domainStateMachine;
    private EmailTemplateSender emailTemplateSender;
    private CountryFactory countryFactory;

    public DomainServiceImpl(DomainDAO domainDAO, 
                             HistoricalDomainDAO historicalDomainDAO,
                             NicHandleSearchService nicHandleSearchService, 
                             EntitiesService entitiesService,
                             ApplicationConfig applicationConfig,
                             DomainStateMachine domainStateMachine,
                             EmailTemplateSender emailTemplateSender,
                             CountryFactory countryFactory) {
        Validator.assertNotNull(domainDAO, "domain dao");
        Validator.assertNotNull(historicalDomainDAO, "historical domain dao");
        Validator.assertNotNull(nicHandleSearchService, "nichandle search service");
        Validator.assertNotNull(entitiesService, "entities service");
        Validator.assertNotNull(applicationConfig, "application config");
        Validator.assertNotNull(domainStateMachine, "domain state machine");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        Validator.assertNotNull(countryFactory, "countryFactory");
        this.domainDao = domainDAO;
        this.nicHandleSearchService = nicHandleSearchService;
        this.entitiesService = entitiesService;
        this.applicationConfig = applicationConfig;
        this.domainStateMachine = domainStateMachine;
        this.emailTemplateSender = emailTemplateSender;
        serviceHelper = new DomainServiceHelper(domainDAO, historicalDomainDAO);
        this.countryFactory = countryFactory;
    }

    @Override
    public void create(AuthenticatedUser user, NewDomain newDomain, OpInfo opInfo) throws NicHandleNotFoundException, NicHandleNotActiveException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, ClassCategoryPermissionException {
        Validator.assertNotNull(newDomain, "domain");
        completeEntities(newDomain);
        entitiesService.checkHolderEntitiesPermissions(newDomain.getHolderClass(), newDomain.getHolderCategory(), newDomain.getCreator());
        confirmContactsActive(newDomain);
        Date currDate = new Date();
        
        domainStateMachine.handleEvent(user, newDomain, new CreateGIBODomain(DateUtils.addYears(currDate, 1)), opInfo);
    }

    private void completeEntities(NewDomain newDomain) throws HolderClassNotExistException, ClassDontMatchCategoryException, HolderCategoryNotExistException {
        ClassCategoryEntity entity = entitiesService.checkHolderEntities(newDomain.getHolderClass(), newDomain.getHolderCategory(), newDomain.getCreator());
        newDomain.setHolderClass(entity.getClassName());
        newDomain.setHolderCategory(entity.getCategoryName());
    }

    public void save(Domain domain, OpInfo opInfo)
            throws DomainNotFoundException, EmptyRemarkException, DuplicatedAdminContact, NicHandleNotFoundException, NicHandleNotActiveException {
        Validator.assertNotNull(domain, "domain");
        Validator.assertNotNull(opInfo, "opInfo");
        domain.validate();
        checkNotWipo(domain);

        //TODO what about class/category ns validation ?
        validateContacts(domain.getBillingContacts());
        validateContacts(domain.getAdminContacts());
        validateContacts(domain.getTechContacts());
        checkContactsForDuplicates(domain.getAdminContacts());
        confirmContactsActive(domain);
        validateRemark(domain.getRemark());

        lock(domain.getName());
        serviceHelper.updateDomainAndHistory(domain, opInfo);
    }

    private void checkNotWipo(Domain domain) {
        if (domain.getDsmState().getWipoDispute() != null && domain.getDsmState().getWipoDispute()) {
            throw new IllegalStateException("Cannot modify wipo domain!");
        }
    }

    /**
     * checks, if the domain has valid contacts: - admin1 != admin2
     *
     * @param c
     * @throws DuplicatedAdminContact
     */
    private void checkContactsForDuplicates(List<Contact> c)
            throws DuplicatedAdminContact {
        if (c.size() == 2) {
            // check
            Contact a1 = c.get(0);
            Contact a2 = c.get(1);
            if (a1 != null && a2 != null) {
                String n1 = a1.getNicHandle();
                String n2 = a2.getNicHandle();
                if (n1.equals(n2))
                    throw new DuplicatedAdminContact();
            }
        }
    }

    /**
     * Throws NicHandleDeletedException if any of domain contacts has not active status.
     *
     * @param domain domain to check
     */
    private void confirmContactsActive(Domain domain)
            throws NicHandleNotActiveException, NicHandleNotFoundException {
    	confirmContactsActive(domain.getAdminContacts());
    	confirmContactsActive(domain.getTechContacts());
    	confirmContactsActive(domain.getBillingContacts());    	        
    }
    
    private void confirmContactsActive(NewDomain domain)
    	throws NicHandleNotActiveException, NicHandleNotFoundException {
    	confirmNicHandlesActive(domain.getAdminContacts());
    	confirmNicHandlesActive(domain.getTechContacts());
    	confirmNicHandlesActive(domain.getBillingContacts());    	        
    }

    private void confirmContactsActive(List<Contact> contacts) throws NicHandleNotFoundException, NicHandleNotActiveException {
    	for (Contact contact : contacts) {
            if (!Validator.isEmpty(contact.getNicHandle()))
                nicHandleSearchService.confirmNicHandleActive(contact.getNicHandle());
        }		
	}
    
    private void confirmNicHandlesActive(List<String> contacts) throws NicHandleNotFoundException, NicHandleNotActiveException {
    	for (String contact : contacts) {
            if (!Validator.isEmpty(contact))
                nicHandleSearchService.confirmNicHandleActive(contact);
        }		
	}

    private Domain lock(String domainName) throws DomainNotFoundException {
        if (domainDao.lock(domainName)) {
            return domainDao.get(domainName);
        } else {
            throw new DomainNotFoundException(domainName);
        }
    }

    private void validateRemark(String remark) throws EmptyRemarkException {
        if (Validator.isEmpty(remark)) {
            throw new EmptyRemarkException();
        }
    }
    
    private void validateRemark(OpInfo opInfo) throws EmptyRemarkException {
        if (Validator.isEmpty(opInfo.getRemark())) {
            throw new EmptyRemarkException();
        }
    }

    private void validateContacts(List<Contact> contacts) throws NicHandleNotFoundException {
        if ((contacts == null) || (contacts.size() == 0) || (contacts.get(0) == null) ||
                (nicHandleSearchService.getNicHandle(contacts.get(0).getNicHandle()) == null))
            throw new NicHandleNotFoundException("empty contact in domain");
    }
    
    private void runDsmEvents(AuthenticatedUser user, OpInfo opInfo, DsmEvent e, String... domainNames) {
    	for (String d: domainNames) {
    		domainStateMachine.handleEvent(user, d, e, opInfo);
    	}
    }

    @Override
    public void enterVoluntaryNRP(AuthenticatedUser user, OpInfo opInfo, String... domainNames) {
    	runDsmEvents(user, opInfo, EnterVoluntaryNRP.getInstance(), domainNames);
    }
    
    @Override
    public void removeFromVoluntaryNRP(AuthenticatedUser user, OpInfo opInfo, String... domainNames) {
    	// validate domain's renew date: uc011sc01
    	for (String domainName: domainNames) {
    		Domain d = domainDao.get(domainName);
    		if (d.getDsmState().getNRPStatus().isVoluntaryNRP() && 
    			d.getRenewDate().before(pl.nask.crs.commons.utils.DateUtils.startOfDay(new Date())) &&
    			d.getDsmState().getDomainHolderType() != DomainHolderType.Charity &&
    			d.getDsmState().getDomainHolderType() != DomainHolderType.NonBillable
    			) {
    			throw new DomainIllegalStateException("Payment required for " + domainName, d);
    		}
    	}
    	runDsmEvents(user, opInfo, RemoveFromVoluntaryNRP.getInstance(), domainNames);
    }
    
    @Override
    public boolean isEventValid(String domainName, DsmEventName eventName) {
    	return domainStateMachine.validateEvent(domainName, eventName);
    }
    
    @Override
    public void zonePublished(List<String> domainNames) {
    	for (String d: domainNames) {
    		domainDao.zonePublish(d);
    	}
    }
    
    @Override
    public void zoneCommit() {
    	domainDao.zoneCommit();
    }
    
    @Override
    public void zoneUnpublished(List<String> domainNames) {
    	for (String d: domainNames) {
    		domainDao.zoneUnpublish(d);
    	}
    }

    private void runDsmEvent(AuthenticatedUser user, String domainName, DsmEvent event, OpInfo opInfo) {
    	if (domainStateMachine.validateEvent(domainName, event)) {
            domainStateMachine.handleEvent(user, domainName, event, opInfo);
        } else {
            LOG.warn(event.getName() + " is not valid event for domain: " + domainName);
        }
    }
    
    @Override
    @Transactional
    public void runRenewalDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo) {
        runDsmEvent(user, domainName, new RenewalDatePasses(), opInfo);
    }

    @Override
    @Transactional
    public void runSuspensionDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo) {
        runDsmEvent(user, domainName, new SuspensionDatePasses(), opInfo);
    }

    @Override
    @Transactional
    public void runDeletionDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo) {
        runDsmEvent(user, domainName, new DeletionDatePasses(), opInfo);
    }

    @Override
    @Transactional
    public void runDeletedDomainRemoval(AuthenticatedUser user, String domainName, OpInfo opInfo) {
        runDsmEvent(user, domainName, new DeletedDomainRemoval(), opInfo);
    }

    @Override
    public void forceDSMEvent(AuthenticatedUser user, List<String> domainNames, DsmEventName eventName, OpInfo opInfo) throws DomainNotFoundException, EmptyRemarkException, DomainIllegalStateException {
        validateRemark(opInfo);
        String username = (user == null) ? null : user.getUsername();
        for (String domainName : domainNames) {
            domainStateMachine.handleEvent(user, domainName, eventName, opInfo);
            String nicHandle = domainDao.get(domainName).getBillingContactNic();
            sendDSMEventForcedEmail(new DSMForceEmailParams(domainName, eventName, opInfo, nicHandle, username));
        }
    }

    private void sendDSMEventForcedEmail(DSMForceEmailParams params) {
        try {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.FORCE_DSM_EVENT.getId(), params);
        } catch (Exception e) {
            LOG.error("Error while sending force dsm event email", e);
        }
    }

    @Override
    public void forceDSMState(List<String> domainNames, int state, OpInfo opInfo) throws DomainNotFoundException, EmptyRemarkException {
        validateRemark(opInfo);
        for (String domainName : domainNames) {
            Domain domain = lock(domainName);
            domain.setRemark(opInfo.getRemark());
            serviceHelper.updateDomainAndHistory(domain, state, opInfo);
        }
    }

    @Override
    public List<Integer> getDsmStates() {
        return domainDao.getDsmStates();
    }
    
    @Override
    public void updateHolderType(AuthenticatedUser user, String domainName, DomainHolderType newHolderType, OpInfo opInfo) throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException {
    	validateRemark(opInfo);
    	DsmEventName eventName;
		switch (newHolderType) {
		case Billable:
			eventName = DsmEventName.SetBillable;
			break;
		case Charity:
			eventName = DsmEventName.SetCharity;
			break;
		case IEDRPublished:
			eventName = DsmEventName.SetIEDRPublished;
			break;
		case IEDRUnpublished:
			eventName = DsmEventName.SetIEDRUnpublished;
			break;
		case NonBillable:
			eventName = DsmEventName.SetNonBillable;
			break;
		case NA:
    	default:
    		throw new IllegalArgumentException("Holder type not supported: " + newHolderType);
    	}
    	/*
    	 * bug #11847 - the event may be performed only if there are no pending reservations!
    	 */
		assertNoPendingReservations(domainName, "Cannot change holder type - there are reservations waiting for settlement (payment is already initiated)");
		
    	domainStateMachine.handleEvent(user, domainName, eventName, opInfo);
    }
    
    private void assertNoPendingReservations(String domainName, String errorMsg) {
    	DomainSearchCriteria criteria = new DomainSearchCriteria();
    	criteria.setDomainName(domainName);
    	criteria.setAttachReservationInfo(true);
		SearchResult<Domain> res = domainDao.find(criteria );
		if (res.getResults().size() != 1) {
			throw new DomainNotFoundException(domainName);
		}
		Domain d = res.getResults().get(0);
    	if (d.hasPendingADPReservations() || d.hasPendingCCReservations()) {
    		throw new DomainIllegalStateException(errorMsg, d);
    	}
	}

	@Override
    public void enterWipo(AuthenticatedUser user, String domainName, OpInfo opInfo) throws EmptyRemarkException,
    		DomainIllegalStateException, DomainNotFoundException {    
    	validateRemark(opInfo);
    	domainStateMachine.handleEvent(user, domainName, DsmEventName.EnterWIPOArbitration, opInfo);
    }
       
    @Override
    public void exitWipo(AuthenticatedUser user, String domainName, OpInfo opInfo) throws EmptyRemarkException,
    		DomainIllegalStateException, DomainNotFoundException {    
    	validateRemark(opInfo);
    	domainStateMachine.handleEvent(user, domainName, DsmEventName.ExitWIPOArbitration, opInfo);
    }
    
    @Override
    public void revertToBillable(AuthenticatedUser user, List<String> domainNames, OpInfo opInfo) {    	
    	for (String domainName: domainNames) {
    		domainStateMachine.handleEvent(user, domainName, DsmEventName.SetBillable, opInfo);
    	}
    }
    
    @Override
    public void sendNotification(Domain domain, NotificationType type, AuthenticatedUser user, int period) throws DomainEmailException {
        DomainNotification notification = domainDao.getDomainNotification(domain.getName(), type, period);
        if (notification == null) {
            sendNotificationEmails(type, domain, user, period);
            Date expirationDate = DateUtils.addDays(new Date(), period);
            notification = new DomainNotification(domain.getName(), type, period, expirationDate);
            domainDao.createNotification(notification);
        } else {
            LOG.info("Notification: " + notification.toString() + " already sent.");
        }
    }

    @Override
    public void sendNotification(Domain domain, NotificationType type, AuthenticatedUser user) throws DomainEmailException {
        sendNotificationEmails(type, domain, user, null);
    }

    private void sendNotificationEmails(NotificationType type, Domain domain, AuthenticatedUser user, Integer period) throws DomainEmailException {
        try {
            sendEmailToContact(type, domain, user, period);
        } catch (Exception e) {
            throw new DomainEmailException("Error while sending notification email for domain: " + domain.getName() ,e);
        }
    }

    private void sendEmailToContact(NotificationType type, Domain domain, AuthenticatedUser user, Integer period) throws EmailSendingException, TemplateNotFoundException, TemplateInstantiatingException {
    	String username = (user == null) ? null : user.getUsername();
        DomainNotificationEmailParams params = new DomainNotificationEmailParams(domain, username);
        if (period == null) {
            switch (type) {
                case RENEWAL:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DOMAIN_EXPIRED.getId(), params);
                    break;
                default:
                    throw new IllegalArgumentException("Wrong notification type: " + type);
            }
        } else {
            switch (type) {
                case RENEWAL:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.RENEWAL_NOTIFICATION.getId(), params);
                    break;
                default:
                    throw new IllegalArgumentException("Wrong notification type: " + type);
            }
        }
    }

    @Override
    public List<String> checkEventAvailable(List<String> domainNames, DsmEventName eventName) {
        List<String> invalidDomains = new ArrayList<String>();
        for (String domainName : domainNames) {
            if (!domainStateMachine.validateEvent(domainName, eventName)) {
                invalidDomains.add(domainName + " (" + domainDao.get(domainName).getDsmState().getNRPStatus().shortDescription() + ")");
            }
        }
        return invalidDomains;
    }

    @Override
    public void modifyRenewalMode(AuthenticatedUser user, List<String> domainNames, RenewalMode renewalMode, OpInfo opInfo) {
        for (String domainName : domainNames) {
            Domain domain = domainDao.get(domainName);
            if (renewalMode != null && !renewalMode.equals(domain.getDsmState().getRenewalMode())) {
                switch (renewalMode) {
                    case NoAutorenew:
                        domainStateMachine.handleEvent(user, domainName, new SetNoAutoRenew(), opInfo);
                        break;
                    case RenewOnce:
                        domainStateMachine.handleEvent(user, domainName, new SetOnceAutoRenew(), opInfo);
                        break;
                    case Autorenew:
                        domainStateMachine.handleEvent(user, domainName, new SetAutoRenew(), opInfo);
                        break;
                    case NA:
                    default:
                        throw new IllegalStateException("Illegal renewal mode: " + renewalMode);
                }
            }
        }
    }

    @Override
    public List<Country> getCountries() {
        return countryFactory.getEntries();
    }

    @Override
    public void modifyRemark(AuthenticatedUser user, String domainName, String remark) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        Domain domain = domainDao.get(domainName);
        domain.setRemark(remark);
        save(domain, new OpInfo(user.getUsername()));
    }

    @Override
    public void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode) throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        verifyAuthCode(user, domainName, authCode, 0);
    }

    @Override
    public void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode, int failureCount) throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        String domainAuthCode = domainDao.get(domainName).getAuthCode();
        if (authCode == null || domainAuthCode == null || !authCode.equals(domainAuthCode)) {
            int failureLimit = applicationConfig.getAuthCodeFailureLimit();
            if (failureCount >= failureLimit) {
                sendViolationEmail(user, domainName);
            }
            LOG.warn("Incorrect authcode: " + authCode + " entered for the domain: " + domainName);
            throw new InvalidAuthCodeException(domainName);
        };
    }

    @Override
    public void sendAuthCodeByEmail(AuthenticatedUser user, String domainName) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        getOrCreateAuthCode(user.getUsername(), domainName);
        AuthCodeEmailParameters params = new AuthCodeEmailParameters(user.getUsername(), domainDao.get(domainName));
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.AUTHCODE_ON_DEMAND.getId(), params);
    }

    private void sendViolationEmail(AuthenticatedUser user, String domainName) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        AuthCodeEmailParameters params = new AuthCodeEmailParameters(user.getUsername(), domainDao.get(domainName));
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.AUTHCODE_VIOLATION.getId(), params);
    }

    @Override
    public void sendAuthCodeFromPortal(String domainName, String emailAddress) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException, AuthCodePortalLimitException {
        Domain domain = domainDao.get(domainName);
        int portalLimit = applicationConfig.getAuthCodePortalLimit();
        if (domain.getAuthCodePortalCount() >= portalLimit) {
            throw new AuthCodePortalLimitException();
        }
        getOrCreateAuthCode("Portal", domain, true);
        AuthCodeEmailParameters params = new AuthCodeEmailParameters(null, domainDao.get(domainName), emailAddress);
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.AUTHCODE_FROM_PORTAL.getId(), params);
    }

    @Override
    public AuthCode getOrCreateAuthCode(String username, String domainName) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        Domain domain = domainDao.get(domainName);
        return getOrCreateAuthCode(username, domain, false);
    }

    private AuthCode getOrCreateAuthCode(String username, Domain domain, boolean portal) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        String authCode = domain.getAuthCode();
        Date oldExpirationDate = domain.getAuthCodeExpirationDate();
        int expirationPeriod = applicationConfig.getAuthCodeExpirationPeriod();
        Date newExpirationDate = DateUtils.addDays(new Date(), expirationPeriod);
        String remark = "";
        if (authCode == null) {
            authCode = Authcode.createCode();
            domain.setAuthCode(authCode);
            remark = "Authcode generated";
        } else if (!DateUtils.isSameDay(oldExpirationDate, newExpirationDate)) {
            remark = "Authcode validity prolonged";
        } else {
            remark = "Authcode queried";
        }
        domain.setAuthCodeExpirationDate(newExpirationDate);
        if (portal) {
            int count = domain.getAuthCodePortalCount();
            domain.setAuthCodePortalCount(count + 1);
            remark.concat(" from Portal");
        }
        domain.setRemark(remark);
        save(domain, new OpInfo(username));
        return new AuthCode(authCode, newExpirationDate);
    }

    @Override
    public void clearAuthCode(String domainName, OpInfo opInfo) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        Domain domain = domainDao.get(domainName);
        domain.setAuthCode(null);
        domain.setAuthCodeExpirationDate(null);
        domain.setRemark("Authcode expired");
        save(domain, opInfo);
    }

    @Override
    public void clearAuthCodePortalCount(String domainName, OpInfo opInfo) throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
        Domain domain = domainDao.get(domainName);
        domain.setAuthCodePortalCount(0);
        domain.setRemark("Authcode Portal count cleared");
        save(domain, opInfo);
    }

    @Override
    public void sendBulkAuthCodesByEmail(AuthenticatedUser user, Contact billingContact,
            Contact adminContact, List<Domain> domainList) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        DomainListEmailParams params = new DomainListEmailParams(user.getUsername(), billingContact, adminContact, domainList);
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.AUTHCODE_BULK_EXPORT.getId(), params);
    }

}
