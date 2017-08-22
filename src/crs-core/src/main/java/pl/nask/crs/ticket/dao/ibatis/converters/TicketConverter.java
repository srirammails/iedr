package pl.nask.crs.ticket.dao.ibatis.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ibatis.converters.ContactConverter;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalNameserver;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicketNameserver;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

/**
 * @author Patrycja Wegrzynowicz, Artur Gniadzik
 */
public class TicketConverter extends AbstractConverter<InternalTicket, Ticket> {
    private Dictionary<Integer, AdminStatus> adminStatusDictionary;

    private Dictionary<Integer, TechStatus> techStatusDictionary;

    private Dictionary<Integer, FailureReason> failureReasonDictionary;

    private GenericDAO<InternalDomain, String> domainDao;

    private boolean evaluateModification;

    public TicketConverter(Dictionary<Integer, AdminStatus> adminStatusDictionary, Dictionary<Integer, TechStatus> techStatusDictionary, Dictionary<Integer, FailureReason> failureReasonDictionary, GenericDAO<InternalDomain, String> domainDao) {
        Validator.assertNotNull(adminStatusDictionary, "admin status dictionary");
        Validator.assertNotNull(techStatusDictionary, "tech status dictionary");
        Validator.assertNotNull(failureReasonDictionary, "failure reason dictionary");
        Validator.assertNotNull(domainDao, "domain dao");
        this.adminStatusDictionary = adminStatusDictionary;
        this.techStatusDictionary = techStatusDictionary;
        this.failureReasonDictionary = failureReasonDictionary;
        this.domainDao = domainDao;
    }

    public void setEvaluateModification(boolean evaluateModification) {
        this.evaluateModification = evaluateModification;
    }

    protected Ticket _to(InternalTicket src) {
        Ticket t = new Ticket(src.getId(),
                createDomainChangeOperation(src),
                adminStatusDictionary.getEntry(src.getAdminStatus()),
                src.getAdminStatusChangeDate(),
                techStatusDictionary.getEntry(src.getTechStatus()),
                src.getTechStatusChangeDate(),
                src.getRequestersRemark(),
                src.getHostmastersRemark(),
                createContact(src.getCreatorNicHandle(), src.getCreatorName(), src.getCreatorEmail(), src.getCreatorCompanyName(), src.getCreatorCountry()),
                src.getCreationDate(),
                src.getChangeDate(),
                createContact(src.getCheckedOutToNicHandle(), src.getCheckedOutToName()),
                src.isClikPaid(),
                src.getDocumentsCount()>0,
                src.getDomainPeriod() == null ? null : Period.fromYears(src.getDomainPeriod()),
                src.getCharityCode(),
                FinancialStatusEnum.valueForId(src.getFinancialStatus()),
                src.getFinancialStatusChangeDate(),
                CustomerStatusEnum.valueForId(src.getCustomerStatus()),
                src.getCustomerStatusChangeDate()
        );
        
        return t;
    }

    private DomainOperation createDomainChangeOperation(InternalTicket src) {
        InternalDomain cur = new InternalDomain();
        if (evaluateModification && (src.getType() == DomainOperationType.MOD || src.getType() == DomainOperationType.XFER)) {
            cur = domainDao.get(src.getDomainName());
        }
        
        if (cur == null) {
        	cur = new InternalDomain();
        	String comment = "Domain name does not exist";
        	cur.setName(comment);
        	cur.setRemark(comment);
        	src.setHostmastersRemark(comment + ", " + src.getHostmastersRemark());
        }

        return new DomainOperation(
                src.getType(),
                src.getRenewalDate(),
                createDomainNameChangeField(src, cur),
                createDomainHolderChangeField(src, cur),
                createDomainHolderClassChangeField(src, cur),
                createDomainHolderCategoryChangeField(src, cur),
                createResellerAccountChangeField(src, cur),
                createAdminContactsChangeField(src, cur),
                createTechContactsChangeField(src, cur),
                createBillingContactsChangeField(src, cur),
                createNameserversChangeField(src, cur),
                src.getDataSetType() == InternalTicket.DataSet.FULL
        );
    }

    private SimpleDomainFieldChange<String> createDomainNameChangeField(InternalTicket src, InternalDomain cur) {
        return new SimpleDomainFieldChange<String>(
                cur.getName(),
                src.getDomainName(),
                getFailureReason(src.getDomainNameFailureReason())
        );
    }

    private SimpleDomainFieldChange<String> createDomainHolderChangeField(InternalTicket src, InternalDomain cur) {
        return new SimpleDomainFieldChange<String>(
                cur.getHolder(),
                src.getDomainHolder(),
                getFailureReason(src.getDomainHolderFailureReason())
        );
    }

    private SimpleDomainFieldChange<String> createDomainHolderClassChangeField(InternalTicket src, InternalDomain cur) {
        return new SimpleDomainFieldChange<String>(
                cur.getHolderClass(),
                src.getDomainHolderClass(),
                getFailureReason(src.getDomainHolderClassFailureReason())
        );
    }

    private SimpleDomainFieldChange<String> createDomainHolderCategoryChangeField(InternalTicket src, InternalDomain cur) {
        return new SimpleDomainFieldChange<String>(
                cur.getHolderCategory(),
                src.getDomainHolderCategory(),
                getFailureReason(src.getDomainHolderCategoryFailureReason())
        );
    }

    private FailureReason getFailureReason(Integer code) {
        return code == null ? null : failureReasonDictionary.getEntry(code);
    }

    private SimpleDomainFieldChange<Account> createResellerAccountChangeField(InternalTicket src, InternalDomain cur) {
    	Account newAccount = new Account(src.getResellerAccountId(), src.getResellerAccountName());
    	newAccount.setAgreementSigned(src.isResellerAccountAgreementSigned());
    	newAccount.setTicketEdit(src.isResellerAccountTicketEdit());
        return new SimpleDomainFieldChange<Account>(
                cur.getResellerAccount(),
                newAccount,
                getFailureReason(src.getResellerAccountFailureReason())
        );
    }

    private List<SimpleDomainFieldChange<Contact>> createAdminContactsChangeField(InternalTicket src, InternalDomain cur) {
        List<InternalContact> contacts = cur.getAdminContacts();
        // todo: match the contacts by name?
        ContactConverter converter = new ContactConverter();
        Contact curContact1 = converter.to(contacts.size() > 0 ? contacts.get(0) : null);
        Contact curContact2 = converter.to(contacts.size() > 1 ? contacts.get(1) : null);
        SimpleDomainFieldChange<Contact> adminContact1 = new SimpleDomainFieldChange<Contact>(
                curContact1,
                createContact(src.getAdminContact1NicHandle(), src.getAdminContact1Name(), src.getAdminContact1Email(), src.getAdminContact1CompanyName(), src.getAdminContact1Country()),
                getFailureReason(src.getAdminContact1FailureReason())
        );
        SimpleDomainFieldChange<Contact> adminContact2 = new SimpleDomainFieldChange<Contact>(
                curContact2,
                createContact(src.getAdminContact2NicHandle(), src.getAdminContact2Name(), src.getAdminContact2Email(), src.getAdminContact2CompanyName(), src.getAdminContact2Country()),
                getFailureReason(src.getAdminContact2FailureReason())
        );
        return Arrays.asList(adminContact1, adminContact2);
    }

    private List<SimpleDomainFieldChange<Contact>> createTechContactsChangeField(InternalTicket src, InternalDomain cur) {
        List<InternalContact> contacts = cur.getTechContacts();
        ContactConverter converter = new ContactConverter();
        Contact curContact = converter.to(contacts.size() > 0 ? contacts.get(0) : null);
        SimpleDomainFieldChange<Contact> techContact = new SimpleDomainFieldChange<Contact>(
                curContact,
                createContact(src.getTechContactNicHandle(), src.getTechContactName(), src.getTechContactEmail(), src.getTechContactCompanyName(), src.getTechContactCountry()),
                getFailureReason(src.getTechContactFailureReason())
        );
        return Arrays.asList(techContact);
    }

    private List<SimpleDomainFieldChange<Contact>> createBillingContactsChangeField(InternalTicket src, InternalDomain cur) {
        List<InternalContact> contacts = cur.getBillingContacts();
        ContactConverter converter = new ContactConverter();
        Contact curContact = converter.to(contacts.size() > 0 ? contacts.get(0) : null);
        SimpleDomainFieldChange<Contact> billingContact = new SimpleDomainFieldChange<Contact>(
                curContact,
                createContact(src.getBillingContactNicHandle(), src.getBillingContactName(), src.getBillingContactEmail(), src.getBillingContactCompanyName(), src.getBillingContactCountry()),
                getFailureReason(src.getBillingContactFailureReason())
        );
        return Arrays.asList(billingContact);
    }
    
    private List<NameserverChange> createNameserversChangeField(InternalTicket src, InternalDomain cur) {
        // cur cannot be null here.
        if (src.getDataSetType() == InternalTicket.DataSet.SIMPLE) {
            return Collections.emptyList();
        }

        List<InternalNameserver> dlist = new ArrayList<InternalNameserver>(cur.getNameservers());
        List<NameserverChange> changeList = new ArrayList<NameserverChange>();
        for (InternalTicketNameserver internal_ticket_ns : src.getNameservers()) {
            changeList.add(makeNameserverChange(internal_ticket_ns, cur, dlist));
        }

        /*
         *  the changelist is not complete: deletion changes are not filled yet.
         *  every nameserver left on the dlist should be marked as deleted, so:
         *  1. every empty change (new value is null) should get a proper old value from the dlist
         *  2. for other nameservers left in the dlist a new nameserver change should be created.  
         */

        //first check NameserverChange with not empty newValue
        for (int i=0; i<changeList.size(); i++) {
        	NameserverChange nsc = changeList.get(i);
        	if (!Validator.isEmpty(nsc.getName().getNewValue()) && Validator.isEmpty(nsc.getName().getCurrentValue())) {
                if (dlist.size() > 0) {
        			// use the first from the list
        			InternalNameserver ns = dlist.remove(0);
        			nsc = makeNameServerChange(ns, nsc);        			      	    
        			changeList.set(i, nsc);
        		} else {
        			// no more nameservers left in dlist
        			break;
        		}
        	}
        }
        //then empty change
        for (int i=0; i<changeList.size(); i++) {
        	NameserverChange nsc = changeList.get(i);
        	if (Validator.isEmpty(nsc.getName().getNewValue())) {
                if (dlist.size() > 0) {
        			// use the first from the list
        			InternalNameserver ns = dlist.remove(0);
        			nsc = makeNameServerChange(ns, nsc);
        			changeList.set(i, nsc);
        		} else {
        			// no more nameservers left in dlist
        			break;
        		}
        	}
        }
        
        return Collections.unmodifiableList(changeList);
    }

	private NameserverChange makeNameServerChange(InternalNameserver ns, NameserverChange nsc) {
		return  new NameserverChange(
    			new SimpleDomainFieldChange<String>(
    					ns.getName(),
    					nsc.getName().getNewValue(),
    					nsc.getName().getFailureReason()
    			),
    			new SimpleDomainFieldChange<String>(
    					ns.getIpAddress(),
    					nsc.getIpAddress().getNewValue(),
    					nsc.getIpAddress().getFailureReason())
    			);  
	}

	private NameserverChange makeNameserverChange(InternalTicketNameserver ticketNs, InternalDomain cur, List<InternalNameserver> dlist) {
    	String nsName2 = null;
    	String ip2 = null;
    	
    	if (!Validator.isEmpty(ticketNs.getName())) {
    		InternalNameserver ns = cur.getNameserver(ticketNs.getName());
    		if (ns != null) {
    			// ticket modifies entry
    			nsName2 = ns.getName();
    			ip2 = ns.getIpAddress();
    			// remove handled nameserver from the list so it would be possible to handle deleted entries
    			dlist.remove(ns);
    		}
    		/*  
    		 * else: ticket adds a new entry - this must be handled outside of this method
    		 */
    	}
    	/*
    	 * else: ticket deletes dns entry - this must be handled outside of this method  
    	 */
    	
    	return new NameserverChange(
    			new SimpleDomainFieldChange<String>(
    					nsName2,
    					ticketNs.getName(),
    					getFailureReason(ticketNs.getNameFailureReason())
    			),
    			new SimpleDomainFieldChange<String>(
    					ip2,
    					ticketNs.getIp(),
    					getFailureReason(ticketNs.getIpFailureReason())
    			)
    	);            	                
	}

	private Contact createContact(String nicHandle, String name) {
        if (nicHandle == null && name != null) {
            Logger.getLogger(getClass()).warn("nic handle is null but name not null - " + name);
        }
        return nicHandle == null ? null : new Contact(nicHandle, name);
    }

    private Contact createContact(String nicHandle, String name, String email, String companyName, String country) {
        if (nicHandle == null && name != null) {
            Logger.getLogger(getClass()).warn("nic handle is null but name not null - " + name);
        }
        return nicHandle == null ? null : new Contact(nicHandle, name, email, companyName, country, null);
    }

    protected InternalTicket _from(Ticket ticket) {
        InternalTicket ret = new InternalTicket();
        ret.setId(ticket.getId());
        ret.setType(ticket.getOperation().getType());
        ret.setHostmastersRemark(ticket.getHostmastersRemark());
        ret.setCheckedOut(ticket.isCheckedOut() ? "Y" : "N");
        if (ticket.isCheckedOut()) {
            ret.setCheckedOutToNicHandle(ticket.getCheckedOutTo().getNicHandle());
        } else {
            ret.setCheckedOutToNicHandle(null);
        }
        ret.setAdminStatus(ticket.getAdminStatus().getId());
        ret.setAdminStatusChangeDate(ticket.getAdminStatusChangeDate());
        ret.setTechStatus(ticket.getTechStatus().getId());
        ret.setTechStatusChangeDate(ticket.getTechStatusChangeDate());
        ret.setChangeDate(ticket.getChangeDate());
        // domain name
        SimpleDomainFieldChange<String> domainName = ticket.getOperation().getDomainNameField();
        ret.setDomainName(domainName.getNewValue());
        ret.setDomainNameFailureReason(getFailureReason(domainName.getFailureReason()));
        // domain holder
        SimpleDomainFieldChange<String> domainHolder = ticket.getOperation().getDomainHolderField();
        ret.setDomainHolderFailureReason(getFailureReason(domainHolder.getFailureReason()));
        ret.setDomainHolder(domainHolder.getNewValue());
        // domain holder class
        SimpleDomainFieldChange<String> domainHolderClass = ticket.getOperation().getDomainHolderClassField();
        ret.setDomainHolderClassFailureReason(getFailureReason(domainHolderClass.getFailureReason()));
        ret.setDomainHolderClass(domainHolderClass.getNewValue());
        // domain holder category
        SimpleDomainFieldChange<String> domainHolderCategory = ticket.getOperation().getDomainHolderCategoryField();
        ret.setDomainHolderCategoryFailureReason(getFailureReason(domainHolderCategory.getFailureReason()));
        ret.setDomainHolderCategory(domainHolderCategory.getNewValue());
        // reseller account
        SimpleDomainFieldChange<Account> resellerAccount = ticket.getOperation().getResellerAccountField();
        ret.setResellerAccountFailureReason(getFailureReason(resellerAccount.getFailureReason()));
        ret.setResellerAccountId(resellerAccount.getNewValue().getId());
        // admin contacts
        List<SimpleDomainFieldChange<Contact>> adminContacts = ticket.getOperation().getAdminContactsField();
        SimpleDomainFieldChange<Contact> ac1 = getElement(adminContacts, 0);
        SimpleDomainFieldChange<Contact> ac2 = getElement(adminContacts, 1);
        if (ac1 != null) {
            ret.setAdminContact1NicHandle(getNicHandle(ac1));
            ret.setAdminContact1FailureReason(getFailureReason(ac1.getFailureReason()));
        } else {
            ret.setAdminContact1NicHandle(null);
            ret.setAdminContact1FailureReason(null);
        }
        if (ac2 != null) {
            ret.setAdminContact2NicHandle(getNicHandle(ac2));
            ret.setAdminContact2FailureReason(getFailureReason(ac2.getFailureReason()));
        } else {
            ret.setAdminContact2NicHandle(null);
            ret.setAdminContact2FailureReason(null);
        }
        // tech contact
        List<SimpleDomainFieldChange<Contact>> techContacts = ticket.getOperation().getTechContactsField();
        SimpleDomainFieldChange<Contact> tc = getElement(techContacts, 0);
        if (tc != null) {
            ret.setTechContactNicHandle(getNicHandle(tc));
            ret.setTechContactFailureReason(getFailureReason(tc.getFailureReason()));
        } else {
            ret.setTechContactNicHandle(null);
            ret.setTechContactFailureReason(null);
        }
        // billing contact
        List<SimpleDomainFieldChange<Contact>> billingContacts = ticket.getOperation().getBillingContactsField();
        SimpleDomainFieldChange<Contact> bc = getElement(billingContacts, 0);
        if (bc != null) {
            ret.setBillingContactNicHandle(getNicHandle(bc));
            ret.setBillingContactFailureReason(getFailureReason(bc.getFailureReason()));
        } else {
            ret.setBillingContactNicHandle(null);
            ret.setBillingContactFailureReason(null);
        }
        //creator contact
        ret.setCreatorNicHandle(ticket.getCreator().getNicHandle());
        //ns
        List<NameserverChange> nameservers = ticket.getOperation().getNameserversField();
        List<InternalTicketNameserver> internalNameservers = new ArrayList<InternalTicketNameserver>();
        for (NameserverChange ns : nameservers) {
            final SimpleDomainFieldChange<String> name = ns.getName();
            final FailureReason nameFailureReason = name.getFailureReason();
            final SimpleDomainFieldChange<String> ipAddress = ns.getIpAddress();
            final FailureReason ipFailureReason = ipAddress.getFailureReason();
            final String nameValue = name.getNewValue();
            if (nameValue != null && !nameValue.isEmpty()) {
                internalNameservers.add(new InternalTicketNameserver(
                        nameValue,
                        nameFailureReason == null ? null : nameFailureReason.getId(),
                        ipAddress.getNewValue(),
                        ipFailureReason == null ? null : ipFailureReason.getId()));
            }
        }
        ret.setNameservers(internalNameservers);
        
        ret.setClikPaid(ticket.isClikPaid());
        ret.setCreationDate(ticket.getCreationDate());
        ret.setRenewalDate(ticket.getOperation().getRenewalDate());
        ret.setRequestersRemark(ticket.getRequestersRemark());
        ret.setHostmastersRemark(ticket.getHostmastersRemark());
        ret.setDomainPeriod(ticket.getDomainPeriod() == null ? null : ticket.getDomainPeriod().getYears());
        ret.setCharityCode(ticket.getCharityCode());
        
        ret.setFinancialStatus(ticket.getFinancialStatus().getId());
        ret.setFinancialStatusChangeDate(ticket.getFinancialStatusChangeDate());
        ret.setCustomerStatus(ticket.getCustomerStatus().getId());
        ret.setCustomerStatusChangeDate(ticket.getCustomerStatusChangeDate());
        return ret;
    }

    private <T> T getElement(List<T> list, int index) {
        return list.size() > index ? list.get(index) : null;
    }

    private Integer getFailureReason(FailureReason fr) {
        return fr == null ? null : fr.getId();
    }

    private String getNicHandle(SimpleDomainFieldChange<Contact> change) {
        return change.getNewValue() == null ? null : change.getNewValue().getNicHandle();
    }

}
