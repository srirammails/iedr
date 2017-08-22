package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class FailureReasonsEditVO {
	
	@XmlElement(required=true)
    private SimpleDomainFieldChangeVO domainNameField;

	@XmlElement(required=true)
    private SimpleDomainFieldChangeVO domainHolderField;

	@XmlElement(required=true)
    private SimpleDomainFieldChangeVO domainHolderClassField;

	@XmlElement(required=true)
    private SimpleDomainFieldChangeVO domainHolderCategoryField;

	@XmlElement(required=true)
    private SimpleDomainFieldChangeVO resellerAccountField;

	@XmlElement(required=true)
    private List<SimpleDomainFieldChangeVO>adminContactsField;

	@XmlElement(required=true)
    private List<SimpleDomainFieldChangeVO> techContactsField;

	@XmlElement(required=true)
    private List<SimpleDomainFieldChangeVO> billingContactsField;

	@XmlElement(required=true)
    private List<NameserverChangeVO> nameservers;


    public FailureReasonsEditVO() {
    }

	public DomainOperation makeDomainOperations(DomainOperationType type, Date renewalDate) {
		return 
			new DomainOperation(
				type, 
				renewalDate, 
				domainNameField.makeStringFieldChange(Field.DOMAIN_NAME_FAIL), 
				domainHolderField.makeStringFieldChange(Field.DOMAIN_HOLDER_FAIL), 
				domainHolderClassField.makeStringFieldChange(Field.CLASS_FAIL), 
				domainHolderCategoryField.makeStringFieldChange(Field.CATEGORY_FAIL), 
				convertToAccount(resellerAccountField), 
				convertToContacts(adminContactsField, Field.ADMIN1_CONTACT1_FAIL, Field.ADMIN_CONTACT2_FAIL), 
				convertToContacts(techContactsField, Field.TECH_CONTACT_FAIL), 
				convertToContacts(billingContactsField, Field.BILLING_CONTACT_FAIL), 
				convertToNameservers(nameservers));
		
	}


	private List<NameserverChange> convertToNameservers(List<NameserverChangeVO> nameservers2) {
		
		
		List<NameserverChange> res = new ArrayList<NameserverChange>();
		for (NameserverChangeVO c: nameservers2) {			
			res.add(c.makeNameserverChange());
		}
		
		return res;
		
	}

	
	private List<SimpleDomainFieldChange<Contact>> convertToContacts(List<SimpleDomainFieldChangeVO> chng, Field...fields) {
		if (chng.size() > fields.length)
			throw new IllegalArgumentException("More contacts than fields");
		List<SimpleDomainFieldChange<Contact>> res = new ArrayList<SimpleDomainFieldChange<Contact>>();
		for (int i=0; i<chng.size(); i++) {
			SimpleDomainFieldChangeVO c = chng.get(i);
			res .add(new SimpleDomainFieldChange<Contact>(null, new Contact(c.getNewValue()), c.makeFailureReason(fields[i])));
		}
		return res;
	}


	private SimpleDomainFieldChange<Account> convertToAccount(SimpleDomainFieldChangeVO chng) {
		return new SimpleDomainFieldChange<Account>(null, new Account(Long.parseLong(chng.getNewValue())), chng.makeFailureReason(Field.ACCOUNT_NAME_FAIL));
	}


	public SimpleDomainFieldChangeVO getDomainNameField() {
		return domainNameField;
	}


	public SimpleDomainFieldChangeVO getDomainHolderField() {
		return domainHolderField;
	}


	public SimpleDomainFieldChangeVO getDomainHolderClassField() {
		return domainHolderClassField;
	}


	public SimpleDomainFieldChangeVO getDomainHolderCategoryField() {
		return domainHolderCategoryField;
	}


	public SimpleDomainFieldChangeVO getResellerAccountField() {
		return resellerAccountField;
	}


	public List<SimpleDomainFieldChangeVO> getAdminContactsField() {
		return adminContactsField;
	}


	public List<SimpleDomainFieldChangeVO> getTechContactsField() {
		return techContactsField;
	}


	public List<SimpleDomainFieldChangeVO> getBillingContactsField() {
		return billingContactsField;
	}


	public List<NameserverChangeVO> getNameservers() {
		return nameservers;
	}


}
