package pl.nask.crs.ticket.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.dns.validator.DnsEntryValidator;
import pl.nask.crs.commons.dns.validator.NsGlueNotAllowedException;
import pl.nask.crs.commons.dns.validator.NsGlueRequiredException;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.contacts.exceptions.ContactNotActiveException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.TicketEmailType;
import pl.nask.crs.ticket.dao.HistoricTicketDAO;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.exceptions.InvalidStatusException;
import pl.nask.crs.ticket.exceptions.TicketAlreadyCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketCheckedOutToOtherException;
import pl.nask.crs.ticket.exceptions.TicketDuplicateNameserverException;
import pl.nask.crs.ticket.exceptions.TicketEditFlagException;
import pl.nask.crs.ticket.exceptions.TicketEmailException;
import pl.nask.crs.ticket.exceptions.TicketNameserverCountException;
import pl.nask.crs.ticket.exceptions.TicketNameserverException;
import pl.nask.crs.ticket.exceptions.TicketNotCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketService;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 * @author Marianna Mysiorska
 * @author Artur Gniadzik
 */
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = Logger.getLogger(TicketServiceImpl.class);

    private TicketDAO ticketDao;

    private HistoricTicketDAO historicTicketDao;

    private ContactDAO contactDAO;

    private EmailTemplateSender emailTemplateSender;

    private Dictionary<Integer, AdminStatus> adminStatusDictionary;

    private ApplicationConfig appConfig;

    public TicketServiceImpl(TicketDAO ticketDao, HistoricTicketDAO historicTicketDao, ContactDAO contactDAO, Dictionary<Integer, AdminStatus> adminStatusDictionary,
                             EmailTemplateSender emailTemplateSender, ApplicationConfig appConfig) {
        Validator.assertNotNull(ticketDao, "ticket dao");
        Validator.assertNotNull(historicTicketDao, "historic ticket dao");
        Validator.assertNotNull(contactDAO, "contact dao");
        Validator.assertNotNull(adminStatusDictionary, "admin status dictionary");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        this.ticketDao = ticketDao;
        this.historicTicketDao = historicTicketDao;
        this.contactDAO = contactDAO;
        this.adminStatusDictionary = adminStatusDictionary;
        this.emailTemplateSender = emailTemplateSender;
        this.appConfig = appConfig;
    }

    public void checkOut(long ticketId, String hostmasterHandle) throws TicketNotFoundException, TicketAlreadyCheckedOutException {
        Ticket ticket = lock(ticketId);
        // todo: verify whether hostmaster exists
        if (ticket.isCheckedOut() && !Validator.isEmpty(hostmasterHandle) && !hostmasterHandle.equals(ticket.getCheckedOutTo().getNicHandle())) {
            throw new TicketAlreadyCheckedOutException(ticket.getCheckedOutTo());
        }
        ticket.checkOut(new Contact(hostmasterHandle));
        updateTicket(ticket);
    }

    public void checkIn(long ticketId, AdminStatus adminStatus, String hostmasterHandle, String superHostmasterHandle) throws TicketNotFoundException, TicketNotCheckedOutException, TicketCheckedOutToOtherException {
        Validator.assertNotNull(adminStatus, "admin status");
        Validator.assertNotNull(hostmasterHandle, "hostmasterHandle");
        Ticket ticket = lock(ticketId);
        if (!ticket.isCheckedOut()) {
        	throw new TicketNotCheckedOutException();
        }
        validateCheckedOutTo(ticket, hostmasterHandle);
        ticket.checkIn();
        if (ticket.setAdminStatus(adminStatus)) {
            updateTicketAndHistory(ticket, prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
        } else {
            updateTicket(ticket);
        }
    }

    private String prepareHostmasterHandleForHistory(String hostmasterHandle, String superHostmasterHandle) {
        return superHostmasterHandle == null ? hostmasterHandle : superHostmasterHandle;
    }

    public void alterStatus(AuthenticatedUser user, long ticketId, AdminStatus adminStatus, String hostmasterHandle, String superHostmasterHandle) throws TicketNotFoundException,
            TicketCheckedOutToOtherException, TicketEmailException, TicketNotCheckedOutException {
        Validator.assertNotNull(adminStatus, "admin status");
        Validator.assertNotNull(hostmasterHandle, "hostmasterHandle");
        Ticket ticket = lock(ticketId);
        validateCheckedOutTo(ticket, hostmasterHandle);
        if (ticket.setAdminStatus(adminStatus)) {
            if (adminStatus.getId() == AdminStatusEnum.PASSED.getCode()) {
                accept(user, ticketId, null, null, hostmasterHandle, superHostmasterHandle);
            }
            updateTicketAndHistory(ticket, prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
        }
    }


    public void reassign(long ticketId, String hostmasterHandle) throws TicketNotFoundException, TicketNotCheckedOutException {
        Validator.assertNotNull(hostmasterHandle, "hostmaster handle");
        // todo: verify whether hostmaster exists
        Ticket ticket = lock(ticketId);
        if (!ticket.isCheckedOut()) {
            throw new TicketNotCheckedOutException();
        }
        Contact checkedOutTo = new Contact(hostmasterHandle);
        if (!checkedOutTo.equals(ticket.getCheckedOutTo())) {
            ticket.checkIn();
            ticket.checkOut(checkedOutTo);
            updateTicket(ticket);
        }
    }

	private void validateTicketEdit(Ticket ticket) throws TicketEditFlagException {
		if (!ticket.getOperation().getResellerAccountField().getNewValue().isTicketEdit())
			throw new TicketEditFlagException();
	}

	public void accept(AuthenticatedUser user, long ticketId, String requestersRemark, String hostmastersRemark, String hostmasterHandle, String superHostmasterHandle) throws TicketNotFoundException, TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException {
        Ticket ticket = lock(ticketId);
        validateCheckedOutTo(ticket, hostmasterHandle);

        ticket.setAdminStatus(adminStatusDictionary.getEntry(PASSED));
        ticket.getOperation().clearFailureReasons();
        ticket.setHostmastersRemark(hostmastersRemark);
        if (!Validator.isEmpty(requestersRemark))
        	ticket.setRequestersRemark(requestersRemark);
        ticket.checkIn();
        updateTicketAndHistory(ticket, prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
        sendEmail(user, ticket, TicketEmailType.Accept);
    }

    public void sendEmail(AuthenticatedUser user, Ticket ticket, TicketEmailType emailType) throws TicketEmailException {
    	String username = (user == null) ? null : user.getUsername();
        TicketEmailParameters parameters = new TicketEmailParameters(username, ticket);
        DomainOperation.DomainOperationType type = ticket.getOperation().getType();
        EmailTemplateNamesEnum template = null;
        try {
            switch (type) {
                case REG:
                    if (emailType == TicketEmailType.Accept) {
                        template = EmailTemplateNamesEnum.ACCEPT_REGISTRATION;
                    } else {
                        template = EmailTemplateNamesEnum.QUERY_REGISTRATION;
                    }
                    break;
                case XFER:
                    if (emailType == TicketEmailType.Accept) {
                        template = EmailTemplateNamesEnum.ACCEPT_TRANSFER;
                    } else {
                    	template = EmailTemplateNamesEnum.QUERY_MODIFICATION;
                    }
                    break;
                case MOD:
                	// different templates are to be used by Directs and the Registrars AND the contact type (billing or admin).
                	boolean createdByBillingC = ticket.isCreatedByBillingContact();
                    if (emailType == TicketEmailType.Accept) {                
                    	if (ticket.getOperation().getResellerAccountField().getNewValue().getId() == 1) { // Direct
                    		if (createdByBillingC) { 
                    			template = EmailTemplateNamesEnum.ACCEPT_MODIFICATION_DIRECT_B;
                    		} else {
                    			template = EmailTemplateNamesEnum.ACCEPT_MODIFICATION_DIRECT_A;
                    		}
                    	} else {
                    		if (createdByBillingC) { 
                    			template = EmailTemplateNamesEnum.ACCEPT_MODIFICATION_REG_B;
                    		} else {
                    			template = EmailTemplateNamesEnum.ACCEPT_MODIFICATION_REG_A;
                    		}
                    	}
                    } else {
                    	// Billing NH don't need to know, if the modification made by the Admin or Tech contact was queried.
                    	if (ticket.isCreatedByBillingContact()) {
                    		sendNotification(EmailTemplateNamesEnum.QUERY_MODIFICATION, parameters);
                    	} else {
                    		sendNotification(EmailTemplateNamesEnum.QUERY_MODIFICATION_A, parameters);
                    	}
                    }
                    break;
                default:
                    throw new IllegalArgumentException("unsupported ticket operation type: " + type);
            }
            if (template != null) {
            	sendNotification(template, parameters);
            }
        } catch (Exception e) {
            throw new TicketEmailException(e);
        }
    }

    public void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation failureReasons, String requestersRemark, String hostmastersRemark, String hostmasterHandle, String superHostmasterHandle)
            throws TicketNotFoundException, InvalidStatusException, EmptyRemarkException, TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException {
        Ticket ticket = lock(ticketId);
        validateCheckedOutTo(ticket, hostmasterHandle);
        validateRemark(hostmastersRemark);
        validateRejectionStatus(status);

        if (!Validator.isEmpty(requestersRemark))
        	ticket.setRequestersRemark(requestersRemark);
        ticket.getOperation().populateFailureReasons(failureReasons);
        ticket.setHostmastersRemark(hostmastersRemark);
        ticket.setAdminStatus(status);
        ticket.checkIn();
        updateTicketAndHistory(ticket, prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
        sendEmail(user, ticket, TicketEmailType.Query);
    }

    public void save(long ticketId, DomainOperation domainOperation, String requestersRemark, String hostmastersRemark, String hostmasterHandle, String superHostmasterHandle) throws TicketNotFoundException, EmptyRemarkException, TicketCheckedOutToOtherException, TicketNotCheckedOutException {
        Validator.assertNotNull(domainOperation, "domainOperation");
        Ticket ticket = lock(ticketId);
        validateCheckedOutTo(ticket, hostmasterHandle);
        validateRemark(hostmastersRemark);
        if (!Validator.isEmpty(requestersRemark))
            ticket.setRequestersRemark(requestersRemark);
        ticket.getOperation().populateFailureReasons(domainOperation);
        ticket.setHostmastersRemark(hostmastersRemark);
        updateTicketAndHistory(ticket, prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
    }

    public void update(long ticketId, DomainOperation operation, String requestersRemark, String hostmastersRemark, boolean clikPaid, String hostmasterHandle, String superHostmasterHandle, boolean forceUpdate)
            throws TicketNotFoundException, EmptyRemarkException, TicketCheckedOutToOtherException, TicketNotCheckedOutException, ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketNameserverException {
        Ticket ticket = lock(ticketId);
        validateCheckedOutTo(ticket, hostmasterHandle);
        update(ticket, operation, requestersRemark, hostmastersRemark, clikPaid, hostmasterHandle, superHostmasterHandle, forceUpdate);
    }

    private void validateNameservers(DomainOperation operation) throws TicketNameserverException, TicketDuplicateNameserverException {
        int nsCount = 0;
        Set<String> names = new HashSet<String>();
        for (NameserverChange nsChng : operation.getNameserversField()) {
            final String name = nsChng.getName().getNewValue();
            if (name != null) {
                nsCount++;
                if (names.contains(name)) {
                    throw new TicketDuplicateNameserverException("Duplicate nameserver " + name);
                }
                else {
                    names.add(name);
                }
            }
        }
        if (nsCount < appConfig.getNameserverMinCount())
            throw new TicketNameserverCountException("Too few nameservers set. Passed " + nsCount + " but " + appConfig.getNameserverMinCount() + " required");
        if (nsCount > appConfig.getNameserverMaxCount())
            throw new TicketNameserverCountException("Too many nameservers set. Passed " + nsCount + " but " + appConfig.getNameserverMinCount() + " allowed");
    }

    private void update(Ticket ticket, DomainOperation domainOperation, String requestersRemark, String hostmastersRemark, boolean clikPaid, String hostmasterHandle, String superHostmasterHandle, boolean forceUpdate)
            throws TicketNotFoundException, EmptyRemarkException, ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketNameserverException {
        if (!forceUpdate)
            validateTicketEdit(ticket);
        validateRemark(hostmastersRemark);
        validateNameservers(domainOperation);
        ticket.getOperation().populateValues(domainOperation);
        ticket.getOperation().populateFailureReasons(domainOperation);
        ticket.setHostmastersRemark(hostmastersRemark);
        ticket.setClikPaid(clikPaid);        
        if (!Validator.isEmpty(requestersRemark))
        	ticket.setRequestersRemark(requestersRemark);
        confirmContactsActive(ticket);
        updateTicketAndHistory(ticket, prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
    }

    @Override
    public void simpleUpdate(long ticketId, DomainOperation domainOperation, String requestersRemark, String hostmastersRemark, boolean clikPaid, String hostmasterHandle, String superHostmasterHandle, boolean forceUpdate)
            throws TicketNotFoundException, EmptyRemarkException, ContactNotActiveException, TicketEditFlagException, ContactNotFoundException, TicketAlreadyCheckedOutException, TicketNameserverException {
        Ticket ticket = lock(ticketId);
        validateAlreadyCheckedOutTo(ticket);
        update(ticket, domainOperation, requestersRemark, hostmastersRemark, clikPaid, hostmasterHandle, superHostmasterHandle, forceUpdate);
    }

    private void confirmContactsActive(Ticket ticket)
            throws ContactNotActiveException, ContactNotFoundException {
        List<Contact> contacts = ticket.getAllContactList();
        for (Contact contact: contacts) {
        	// don't check 'empty' contacts
        	if (Validator.isEmpty(contact.getNicHandle()))
        			continue;
        	
            String status = contactDAO.getContactStatus(contact.getNicHandle()); 
            if (status == null)
            	throw new ContactNotFoundException(contact.getNicHandle());
            if (status.equals("Deleted") || status.equals("Suspended"))
                throw new ContactNotActiveException(contact.getNicHandle());
        }
    }

    private Ticket lock(long ticketId) throws TicketNotFoundException {
        if (ticketDao.lock(ticketId)) {
            return ticketDao.get(ticketId);
        } else {
            throw new TicketNotFoundException(ticketId);
        }
    }

    private void validateCheckedOutTo(Ticket ticket, String hostmasterHandle) throws TicketCheckedOutToOtherException {
        if (ticket.isCheckedOut()) {
        	String checkedOutTo = ticket.getCheckedOutTo().getNicHandle();
        	if (!checkedOutTo.equalsIgnoreCase(hostmasterHandle)) {
        		throw new TicketCheckedOutToOtherException(checkedOutTo, hostmasterHandle);
        	}           
        }
    }

    private void validateAlreadyCheckedOutTo(Ticket ticket) throws TicketAlreadyCheckedOutException {
        if (ticket.isCheckedOut()) {
            throw new TicketAlreadyCheckedOutException(ticket.getCheckedOutTo());
        }
    }

    private void validateRemark(String remark) throws EmptyRemarkException {
        if (Validator.isEmpty(remark)) {
            throw new EmptyRemarkException();
        }
    }

    private void validateRejectionStatus(AdminStatus status) throws InvalidStatusException {
        if (status == null) {
            throw new InvalidStatusException();
        }
        if (status.getId() == NEW || status.getId() == PASSED) {
            throw new InvalidStatusException();
        }
    }

    private void updateTicketAndHistory(Ticket ticket, String hostmasterHandle) {
        if (!hasHistory(ticket)) {
            historicTicketDao.create(ticket.getId(), ticket.getCreationDate(), ticket.getCreator().getNicHandle());
        }

        ticket.updateChangeDate();
        ticketDao.update(ticket);
        historicTicketDao.create(ticket.getId(), ticket.getChangeDate(), hostmasterHandle);
    }

    private boolean hasHistory(Ticket ticket) {
        HistoricTicketSearchCriteria ticketCriteria = new HistoricTicketSearchCriteria();
        ticketCriteria.setTicketId(ticket.getId());
        LimitedSearchResult<HistoricalObject<Ticket>> ticketHistory = historicTicketDao.find(ticketCriteria, 0, 0, null);
        return ticketHistory.getTotalResults() > 0;
    }

    private void updateTicket(Ticket ticket) {
        ticketDao.update(ticket);
    }
   
    public long createTicket(Ticket ticket) {
    	long ticketId = ticketDao.createTicket(ticket);
        return ticketId;
    }


    @Override
	public void delete(long ticketId) {
		updateTicketAndHistory(ticketDao.get(ticketId), "");				
		
		ticketDao.deleteById(ticketId);
	}

    @Override
    public void deleteAndNotify(AuthenticatedUser user, long ticketId) throws TicketEmailException{
        Ticket ticket = ticketDao.get(ticketId);
        delete(ticketId);
        sendTicketRemovedEmail(user, ticket);
    }

    private void sendTicketRemovedEmail(AuthenticatedUser user, Ticket ticket) throws TicketEmailException {
        String username = (user == null) ? null : user.getUsername();
        TicketEmailParameters parameters = new TicketEmailParameters(username, ticket);
        sendNotification(EmailTemplateNamesEnum.TICKET_CLEANUP, parameters);

        if (ticket.isCanceled()) {
            return;
        }

        DomainOperationType type = ticket.getOperation().getType();
        if (type == DomainOperationType.XFER) {
            // TRANSFER email has different semantics of BILL_C (it's loosing one) than all other mails
            EmailParameters transferParameters = new TransferTicketEmailParameters(username, ticket);
            sendNotification(EmailTemplateNamesEnum.TRANSFER_REQUEST_EXPIRED, transferParameters);
        } else if (type == DomainOperationType.MOD) {
            sendNotification(EmailTemplateNamesEnum.MOD_REQUEST_EXPIRED, parameters);
        } else if (type == DomainOperationType.REG) {
            sendNotification(EmailTemplateNamesEnum.REG_REQUEST_EXPIRED, parameters);
        }
    }

    private void sendNotification(EmailTemplateNamesEnum template, EmailParameters parameters) {
        try {
            emailTemplateSender.sendEmail(template.getId(), parameters);
        } catch (Exception e) {
            LOGGER.error("Error sending email id=" + template.getId(), e);
        }
    }

    @Override
    public void updateFinanacialStatus(long ticketId, FinancialStatus newFinancialStatus, String hostmasterHandle) throws TicketNotFoundException {
        updateStatuses(ticketId, null, null, newFinancialStatus, hostmasterHandle, null);
    }

    @Override
    public void updateTechStatus(long ticketId, TechStatus newTechStatus, String hostmasterHandle) throws TicketNotFoundException {
        updateStatuses(ticketId, null, newTechStatus, null, hostmasterHandle, null);
    }

    @Override
    public void updateAdminStatus(long ticketId, AdminStatus newAdminStatus, String hostmasterHandle) throws TicketNotFoundException {
        updateAdminStatus(ticketId, newAdminStatus, hostmasterHandle, null);
    }

    @Override
    public void updateAdminStatus(long ticketId, AdminStatus newAdminStatus, String hostmasterHandle, String hostmasterRemarks) throws TicketNotFoundException {
        updateStatuses(ticketId, newAdminStatus, null, null,hostmasterHandle, hostmasterRemarks);
    }

    @Override
    public void updateCustomerStatus(long ticketId, CustomerStatus newCustomerStatus, String hostmasterHandle) throws TicketNotFoundException {
        Ticket ticket = lock(ticketId);

        ticket.setCustomerStatus(newCustomerStatus);
        ticket.setRequestersRemark("Customer status changed");
        updateTicketAndHistory(ticket, hostmasterHandle);
    }

    public void updateStatuses(long ticketId, AdminStatus as, TechStatus ts, FinancialStatus fs, String hostmasterHandle, String hostmasterRemark) throws TicketNotFoundException {
        Ticket ticket = lock(ticketId);
        if (as != null) {
            ticket.updateStatus(as, hostmasterRemark == null ? "Admin status changed" : hostmasterRemark);
        }

        if (ts != null) {
            ticket.updateStatus(ts, hostmasterRemark == null ? "Tech status changed" : hostmasterRemark);
        }

        if (fs != null) {
            ticket.updateStatus(fs, hostmasterRemark == null ? "Financial status changed" : hostmasterRemark);
        }

        updateTicketAndHistory(ticket, hostmasterHandle);
    }

    @Override
    public void sendTicketExpirationEmail(AuthenticatedUser user, Ticket ticket, int daysRemaining) {
        String username = user == null ? null : user.getUsername();
        TicketEmailParameters parameters = new TicketEmailParameters(username, ticket, daysRemaining);
        switch (ticket.getOperation().getType()) {
            case REG:
                sendNotification(EmailTemplateNamesEnum.REG_TICKET_EXPIRATION, parameters);
                break;
            case XFER:
                sendNotification(EmailTemplateNamesEnum.XFER_TICKET_EXPIRATION, parameters);
                break;
            case MOD:
            	sendNotification(EmailTemplateNamesEnum.MOD_TICKET_EXPIRATION, parameters);
            default:
                LOGGER.warn("Invalid ticket type to send expiration email: " + ticket.getOperation().getType());
        }
    }
}
