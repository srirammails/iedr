package pl.nask.crs.domains.services.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;

/**
 * @author Kasia Fulara, Artur Gniadzik
 */
public class DomainEmailParameters implements EmailParameters {
	private final static Logger log = Logger.getLogger(DomainEmailParameters.class);
    private final static String POSTMASTER_PREFIX = "postmaster@";
    private final static String WEBMASTER_PREFIX = "webmaster@";
    private final static String INFO_PREFIX = "info@";
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    private static List<ParameterName> paramList;

    private Domain domain;
    private Contact newBillingContact;
    private Contact currentBillingContact;
    private String username;
    private AccountRelatedNHEnum accountRelatedNHEnum;

    private MapBasedEmailParameters additionalParams = new MapBasedEmailParameters();

    static {
        paramList = ParameterNameEnum.asList();
    }

    public enum AccountRelatedNHEnum {
        GAINING_BILLC,
        BILLC
    }

    // used if there is a need of fetching additional Contact data
    private final NicHandleDAO nicHandleDAO; 

    public DomainEmailParameters(String username, NicHandleDAO nicHandleDAO, Domain domain, Ticket transferTicket) {
    	Validator.assertNotNull(domain, "domain");
    	Validator.assertNotNull(nicHandleDAO, "nicHandleDAO");
        this.domain = domain;
        this.username = username;
        this.nicHandleDAO = nicHandleDAO;
        this.accountRelatedNHEnum = AccountRelatedNHEnum.BILLC;
    	if (domain.getBillingContacts().isEmpty()) {
    		log.warn("No (old) billing contact set for domain " + domain.getName());
    	} else {
    		initCurrentBillingC(domain.getBillingContacts().get(0));
    	}
    	
    	if (transferTicket.getOperation().getBillingContactsField().isEmpty()) {
    		log.warn("No billing contact set for domain " + domain.getName());
    	} else {
    		initNewBillingC(transferTicket.getOperation().getBillingContactsField().get(0).getNewValue());
    	}
    	
    	setParameter(ParameterNameEnum.TICKET_ID, transferTicket.getId());
    }

    public DomainEmailParameters(String username, NicHandleDAO nicHandleDAO, Domain domain, Domain oldDomain, Ticket transferTicket) {
    	Validator.assertNotNull(domain, "domain");
    	Validator.assertNotNull(nicHandleDAO, "nicHandleDAO");
        this.domain = domain;
        this.username = username;
        this.nicHandleDAO = nicHandleDAO;
        this.accountRelatedNHEnum = AccountRelatedNHEnum.BILLC;
    	if (oldDomain.getBillingContacts().isEmpty()) {
    		log.warn("No (old) billing contact set for domain " + domain.getName());
    	} else {
    		initCurrentBillingC(oldDomain.getBillingContacts().get(0));
    	}
    	
    	if (transferTicket.getOperation().getBillingContactsField().isEmpty()) {
    		log.warn("No billing contact set for domain " + domain.getName());
    	} else {
    		initNewBillingC(transferTicket.getOperation().getBillingContactsField().get(0).getNewValue());
    	}
    	setParameter(ParameterNameEnum.TICKET_ID, transferTicket.getId());
    }

    public DomainEmailParameters(String username, NicHandleDAO nicHandleDAO, Domain domain) {
        Validator.assertNotNull(domain, "domain");
    	Validator.assertNotNull(nicHandleDAO, "nicHandleDAO");
        this.domain = domain;
        this.username = username;
        this.nicHandleDAO = nicHandleDAO;
        this.accountRelatedNHEnum = AccountRelatedNHEnum.BILLC;
        if (domain.getBillingContacts().isEmpty()) {
        	log.warn("No billing contact set for domain " + domain.getName());
        } else {
        	initCurrentBillingC(domain.getBillingContacts().get(0));
        }
    }

    public String getLoggedInNicHandle()
    {
        return this.username;
    }

    public void setAccountRelatedNicHandle(AccountRelatedNHEnum accountRelatedNHEnum){
        this.accountRelatedNHEnum = accountRelatedNHEnum;
    }

    public String getAccountRelatedNicHandle()
    {
        if (this.accountRelatedNHEnum == AccountRelatedNHEnum.GAINING_BILLC) {
            return newBillingContact == null ? null : newBillingContact.getNicHandle();
        }
        else {
        	return this.domain.getBillingContactNic();
        }
    }

    public String getDomainName()
    {
        return this.domain.getName();
    }

    private void initCurrentBillingC(Contact contact) {
        this.currentBillingContact = contact;
	}

    private void initNewBillingC(Contact contact) {
        this.newBillingContact = contact;
    }

    public List<ParameterName> getParameterNames() {
        return paramList;
    }

    public String getParameterValue(String name, boolean html) {

        Validator.assertNotNull(name, "parameter name");
        ParameterNameEnum domainName = ParameterNameEnum.forName(name);
        switch (domainName) {
            case DOMAIN:
                return domain.getName();
            case REGISTRATION_DATE:
                return FORMATTER.format(domain.getRegistrationDate());
            case RENEWAL_DATE:
                return FORMATTER.format(domain.getRenewDate());
            case SUSPENSION_DATE:
                return domain.getSuspensionDate() == null ? "" : FORMATTER.format(domain.getSuspensionDate());
            case DELETION_DATE:
                return domain.getDeletionDate() == null ? "" : FORMATTER.format(domain.getDeletionDate());
            case ADMIN_C_EMAIL:
                String email1 = getAdminEmail(0);
                String email2 = getAdminEmail(1);
                if (email2 == null) {
                	return email1;
                } else {
                	return email1 + "," + email2;
                }
            case ADMIN_C_NAME:
                return getAdminName(0);
            case BILL_C_CO_NAME:
            	return currentBillingContact == null ? null : currentBillingContact.getCompanyName();
            case GAINING_BILL_C_NAME:
                return newBillingContact == null ? null : newBillingContact.getName();
            case BILL_C_NIC:
                return currentBillingContact == null ? null : currentBillingContact.getNicHandle();
            case GAINING_BILL_C_EMAIL:
            	return newBillingContact == null ? null : newBillingContact.getEmail();
            case GAINING_BILL_C_NIC:
            	return newBillingContact == null ? null : newBillingContact.getNicHandle();
            case BILL_C_TEL:
                return getBillingTel();
            case BILL_C_EMAIL:
            case LOSING_BILL_C_EMAIL:
                return currentBillingContact == null ? null : currentBillingContact.getEmail();
            case BILL_C_NAME:
            case REGISTRAR_NAME:
            case LOSING_BILL_C_NAME:
                return currentBillingContact == null ? null : currentBillingContact.getName();
            case POSTMASTER:
                return POSTMASTER_PREFIX + domain.getName();
            case WEBMASTER:
                return WEBMASTER_PREFIX + domain.getName();
            case INFO:
                return INFO_PREFIX + domain.getName();
            case TECH_C_EMAIL:
                return getTechEmail(0);
            case TECH_C_NAME:
                return getTechName(0);                
            case HOLDER:
                return domain.getHolder();
            default:
            	return additionalParams.getParameterValue(name, html);
        }
    }

    private String getBillingTel() {
    	if (currentBillingContact == null)
    		return null;
    	
    	NicHandle nh = nicHandleDAO.get(currentBillingContact.getNicHandle());
    	if (nh == null) {
    		return null;
    	}
    	
    	return nh.getPhonesAsString();
	}

	private String getAdminName(int number) {
        List<Contact> adminList = domain.getAdminContacts();
        if (adminList.isEmpty())
            return null;
        Contact contact = adminList.get(number);
        return getName(contact);
    }

    private String getAdminEmail(int number) {
        List<Contact> adminList = domain.getAdminContacts();
        if (adminList.isEmpty() || adminList.size() <= number)
            return null;
        Contact contact = adminList.get(number);
        return getEmail(contact);
    }

    private String getTechEmail(int number) {
        List<Contact> adminList = domain.getTechContacts();
        if (adminList.isEmpty())
            return null;
        Contact contact = adminList.get(number);
        return getEmail(contact);
    }
    
    private String getTechName(int number) {
        List<Contact> adminList = domain.getTechContacts();
        if (adminList.isEmpty())
            return null;
        Contact contact = adminList.get(number);
        return getName(contact);
    }

    private String getEmail(Contact contact) {
        if (contact == null) {
            return null;
        } else {
            return contact.getEmail();
        }
    }

    private String getName(Contact contact) {
        if (contact == null) {
            return null;
        } else {
            return contact.getName();
        }
    }

	public void setParameter(ParameterNameEnum name, Object value) {
		this.additionalParams.set(name, value);
	}

}
