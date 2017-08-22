package pl.nask.crs.app.domains.wrappers;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DsmState;

public abstract class AbstractDomainWrapper {

    private Domain domain;
    private DnsWrapper dnsWrapper;

    public AbstractDomainWrapper(Domain hDomain) {
        Validator.assertNotNull(hDomain, "historical domain");
        this.domain = hDomain;
    }

    protected Domain getWrappedDomain() {
        return domain;
    }

    /**
     * returns updated (if needed) domain object. use
     * {@link #getWrappedDomain()} to access plain domain object
     */
    public Domain getDomain() {
        // refresh dns-list...
        domain.setNameservers(getDnsWrapper().createNameserversList());
        return domain;
    }

    public String getDomainHolder() {
        return StringEscapeUtils.escapeHtml(domain.getHolder());
    }

    public String getName() {
        return domain.getName();
    }

    public String getRemark() {
        String remark = domain.getRemark();
        return (remark == null || remark.trim().length() == 0) ? "(none)" : StringEscapeUtils.escapeHtml(remark);
    }

    public boolean isClikPaid() {
        return domain.isClikPaid();
    }

    public boolean getClikPaid() {
        return domain.isClikPaid();
    }

    public DnsWrapper getDnsWrapper() {
        if (domain == null)
            return null;
        if (dnsWrapper == null)
            dnsWrapper = new DnsWrapper(domain.getNameservers());

        return dnsWrapper;
    }

    public String getHolder() {
        return domain.getHolder();
    }

    public String getHolderClass() {
        return domain.getHolderClass();
    }

    public String getHolderCategory() {
        return domain.getHolderCategory();
    }

    public Account getResellerAccount() {
        return domain.getResellerAccount();
    }

    public List<Contact> getAdminContacts() {
        return domain.getAdminContacts();
    }

    public List<Contact> getTechContacts() {
        return domain.getTechContacts();
    }

    public List<Contact> getBillingContacts() {
        return domain.getBillingContacts();
    }

    public Date getRegistrationDate() {
        return domain.getRegistrationDate();
    }

    public Date getRenewDate() {
        return domain.getRenewDate();
    }

    public Date getChangeDate() {
        return domain.getChangeDate();
    }
    
    public Date getSuspensionDate() {
    	return domain.getSuspensionDate();
    }
    
    public Date getDeletionDate() {
    	return domain.getDeletionDate();
    }
    
    public DsmState getDsmState() {
    	return domain.getDsmState();
    }

    public boolean isNrp() {
        return domain.getDsmState().getNRPStatus().isNRP();
    }
}
