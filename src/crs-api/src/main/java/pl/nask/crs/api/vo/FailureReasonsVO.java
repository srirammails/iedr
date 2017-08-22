package pl.nask.crs.api.vo;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class FailureReasonsVO {

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
    private List<SimpleDomainContactChangeVO> adminContactsField;

	@XmlElement(required=true)
    private List<SimpleDomainContactChangeVO> techContactsField;

	@XmlElement(required=true)
    private List<SimpleDomainContactChangeVO> billingContactsField;

	@XmlElement(required=true)
    private List<NameserverChangeVO> nameservers;


    public FailureReasonsVO() {
    }


	public FailureReasonsVO(DomainOperation op) {
		domainNameField = new SimpleDomainFieldChangeVO(op.getDomainNameField());
		domainHolderField = new SimpleDomainFieldChangeVO(op.getDomainHolderField());
		domainHolderClassField = new SimpleDomainFieldChangeVO(op.getDomainHolderClassField());
		domainHolderCategoryField = new SimpleDomainFieldChangeVO(op.getDomainHolderCategoryField());
		resellerAccountField = SimpleDomainFieldChangeVO.newAccountChangeVO((op.getResellerAccountField()));
		adminContactsField = convertFromContacts(op.getAdminContactsField());
		techContactsField = convertFromContacts(op.getTechContactsField());
		billingContactsField = convertFromContacts(op.getBillingContactsField());
		nameservers = convertFromNameservers(op.getNameserversField());
	}


	private List<NameserverChangeVO> convertFromNameservers(List<NameserverChange> nameservers) {
		List<NameserverChangeVO> res = new ArrayList<NameserverChangeVO>();

		for (NameserverChange ns: nameservers) {
            if (!isEmptyNameserverChange(ns))
			    res.add(new NameserverChangeVO(ns));
		}

		return res ;
	}

    private boolean isEmptyNameserverChange(NameserverChange ns) {
        return (ns.getName() == null || ValidationHelper.isEmptySimpleDomainFieldChange(ns.getName()))
                && (ns.getIpAddress() == null || ValidationHelper.isEmptySimpleDomainFieldChange(ns.getIpAddress()));
    }

	private List<SimpleDomainContactChangeVO> convertFromContacts(List<SimpleDomainFieldChange<Contact>> contacts) {
		List<SimpleDomainContactChangeVO> res = new ArrayList<SimpleDomainContactChangeVO>();

		for (SimpleDomainFieldChange<Contact> c:contacts) {
            if (!isEmptyContactFieldChange(c))
			    res.add(new SimpleDomainContactChangeVO(c));
		}

		return res ;
	}

    private boolean isEmptyContactFieldChange(SimpleDomainFieldChange<Contact> c) {
        return (c.getCurrentValue() == null || Validator.isEmpty(c.getCurrentValue().getNicHandle()))
                && (c.getNewValue() == null || Validator.isEmpty(c.getNewValue().getNicHandle()));
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

    public List<SimpleDomainContactChangeVO> getAdminContactsField() {
        return adminContactsField;
    }

    public List<SimpleDomainContactChangeVO> getTechContactsField() {
        return techContactsField;
    }

    public List<SimpleDomainContactChangeVO> getBillingContactsField() {
        return billingContactsField;
    }

    public List<NameserverChangeVO> getNameservers() {
        return nameservers;
    }
}
