package pl.nask.crs.app.domains.wrappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.app.domains.ExtendedDomainInfo;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.web.ticket.wrappers.EntityWrapper;

/**
 * @author Kasia Fulara, Artur Gniadzik
 */
public class DomainWrapper extends AbstractDomainWrapper {

    private final boolean documents;
    private final boolean tickets;

    private EntityWrapper entityWrapper;
    private String remark;
    private String domainHolder;
    private Collection<String> relatedDomainNames;    
    private Collection<String> pendingDomainNames;


    @Override
    public Domain getDomain() {
        Domain d = super.getDomain();
        d.setHolder(domainHolder);
        return d;
    }
    

    /**
     * Use this constructor if you plan to use {get|set}holder{class|category}id
     * 
     * @param domainInfo
     * @param factory 
     */
    public DomainWrapper(ExtendedDomainInfo domainInfo, EntityClassFactory factory ) {
    	super(domainInfo.getDomain());
    	this.entityWrapper = new EntityWrapper(factory);
        this.domainHolder = domainInfo.getDomain().getHolder();
        this.documents = domainInfo.isDocuments();
        this.tickets = domainInfo.isTickets();
        this.relatedDomainNames = domainInfo.getRelatedDomainNames();
        this.pendingDomainNames = domainInfo.getPendingDomainNames();
	}
    
    /**
     * Use this constructor if you DON'T plan to use {get|set}holder{class|category}id
     * 
     * @param domainInfo
     * @param factory 
     */
    public DomainWrapper(ExtendedDomainInfo domainInfo) {
    	super(domainInfo.getDomain());
        this.domainHolder = domainInfo.getDomain().getHolder();
        this.documents = domainInfo.isDocuments();
        this.tickets = domainInfo.isTickets();
        this.relatedDomainNames = domainInfo.getRelatedDomainNames();
        this.pendingDomainNames = domainInfo.getPendingDomainNames();
	}

	// domain holder cannot be null in the Domain, so we have to keep it here
    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }
    
    @Override
    public String getDomainHolder() {
        return StringEscapeUtils.escapeHtml(this.domainHolder);
    }

    @Override
    public String getHolder() {
        return getDomainHolder();
    }
     
    
    public Long getHolderClassId() {
    	checkEntityWrapper();
        return entityWrapper.getClass(getWrappedDomain().getHolderClass()).getId();
    }

    public Long getHolderCategoryId() {
    	checkEntityWrapper();
        return entityWrapper.getCategory(getWrappedDomain().getHolderClass(), getWrappedDomain().getHolderCategory()).getId();
    }

    public void setHolderClassId(Long holderClassId) {
    	checkEntityWrapper();
        getWrappedDomain().setHolderClass(entityWrapper.getClass(holderClassId).getName());
    }

    public void setHolderCategoryId(Long holderCategoryId) {
    	checkEntityWrapper();
        getWrappedDomain().setHolderCategory(entityWrapper.getCategory(holderCategoryId).getName());
    }

    
    private void checkEntityWrapper() {
    	if (entityWrapper == null)
    		throw new IllegalStateException("EntityWrapper not present. EntityClassFactory was not provided in the constructor of this DomainWrapper");
	}


	public String getAdminContact1() {
        return getContact(getWrappedDomain().getAdminContacts(), 0);
    }

    public String getAdminContact2() {
        return getContact(getWrappedDomain().getAdminContacts(), 1);
    }

    private String getContact(List<Contact> l, int index) {
        if (l == null)
            return null;
        Contact c = l.get(index);
        if (c == null)
            return null;
        return c.getNicHandle();
    }

    public void setAdminContact1(String adminContact) {
        setAdminContact(0, adminContact);
    }

    public void setAdminContact2(String adminContact) {
        setAdminContact(1, adminContact);
    }

    private void setAdminContact(int index, String adminContact) {
        List<Contact> contacts = getWrappedDomain().getAdminContacts();
        if (contacts == null) {
            contacts = new ArrayList<Contact>();
            getWrappedDomain().setAdminContacts(contacts);
        }
        setContact(contacts, index, adminContact);
    }

    private void setContact(List<Contact> contacts, int index, String nichandle) {
        Contact contact = new Contact(nichandle);
        if (contacts.size() > index) {
            contacts.set(index, contact);
        } else {
            contacts.add(contact);
        }
    }

    public String getTechContact() {
        return getContact(getWrappedDomain().getTechContacts(), 0);
    }

    public void setTechContact(String techContact) {
        List<Contact> contacts = getWrappedDomain().getTechContacts();
        if (contacts == null) {
            contacts = new ArrayList<Contact>();
            getWrappedDomain().setTechContacts(contacts);
        }
        setContact(contacts, 0, techContact);
    }

    public String getBillContact() {
        return getContact(getWrappedDomain().getBillingContacts(), 0);
    }

    public void setBillContact(String billContact) {
        List<Contact> contacts = getWrappedDomain().getBillingContacts();
        if (contacts == null) {
            contacts = new ArrayList<Contact>();
            getWrappedDomain().setBillingContacts(contacts);
        }
        setContact(contacts, 0, billContact);
    }

    public Long getAccountNo() {
        Domain d = getWrappedDomain();
        if (d != null && d.getResellerAccount() != null)
            return d.getResellerAccount().getId();
        else
            return null;
    }

    public void setAccountNo(Long accountNo) {
        getWrappedDomain().setResellerAccount(new Account(accountNo));
    }

    public String getNewRemark() {
        return remark;
    }
    
    public void setNewRemark(String remark) {
        this.remark = remark;
        setRemark(remark);
    }

    public String getRemark() {
        return StringEscapeUtils.escapeHtml(getWrappedDomain().getRemark());
    }

    public void setRemark(String remark) {
        getWrappedDomain().setRemark(remark);
    }

    public String getNoDotsDomainName() {
        return getWrappedDomain().getName().replaceAll("\\.", "");
    }

    public boolean isDocuments() {
        return documents;
    }

    public boolean isTickets() {
        return tickets;
    }
    
    public String getRelatedDomainNamesAsString() {
        StringBuilder ret = new StringBuilder();
        if (relatedDomainNames.isEmpty() && pendingDomainNames.isEmpty())
            return "";

        ret.append("<table>");
        appendDomainNames(relatedDomainNames, ret, null);


        if (!pendingDomainNames.isEmpty()) {
            appendDomainNames(pendingDomainNames, ret, "#C72222");
        }
        ret.append("</table>");
        return ret.toString();
    }

    private void appendDomainNames(Collection<String> domainNames, StringBuilder ret, String colorCode) {
        if (domainNames.isEmpty())
            return;
        int i = 0;
        String openTd = "<td>";
        if (colorCode != null)
            openTd = "<td style=\"color:" + colorCode + ";\"/>";

        ret.append("<tr>");
        for (String rdn : domainNames) {
            i++;
            if (i % 3 == 1)
                ret.append("<tr>");
            ret.append(openTd).append(rdn).append("</td>");

            if (i % 3 == 0)
                ret.append("</tr>");
        }

        if (i % 3 != 0)
            ret.append("</tr>");
    }
}
