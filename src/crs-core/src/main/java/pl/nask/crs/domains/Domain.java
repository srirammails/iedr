package pl.nask.crs.domains;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.Nameserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public class Domain {

    private String name;

    private String holder;

    private String holderClass;

    private String holderCategory;

    private Account resellerAccount;

    private Date registrationDate;
    private Date renewDate;
    private Date changeDate;

    private String remark;
    private boolean clikPaid;

    private Integer dateRoll = null;

    private Contact creator;

    private List<Contact> adminContacts = new ArrayList<Contact>();
    private List<Contact> techContacts = new ArrayList<Contact>();
    private List<Contact> billingContacts = new ArrayList<Contact>();

    private List<Nameserver> nameservers = new ArrayList<Nameserver>();

	private DsmState dsmState;

	private Date giboRetryTimeout;

	private Date suspensionDate;

	private Date deletionDate;
	
	private boolean zonePublished;
	
	private Date transferDate;

    private String authCode;
    private Date authCodeExpirationDate;
    private Integer authCodePortalCount;

    private boolean pendingCCReservations;
    private boolean pendingADPReservations;

    @Deprecated
    public Domain(String name, String holder, String holderClass, String holderCategory, Contact creator, Account resellerAccount,
                  Date registrationDate, Date renewDate, String remark, Date changeDate, boolean clikPaid,
                  List<Contact> techContacts, List<Contact> billingContacts, List<Contact> adminContacts,
                  List<Nameserver> nameservers, DsmState dsmState) {
        this.name = name;
        this.holder = holder;
        this.holderClass = holderClass;
        this.holderCategory = holderCategory;
        this.creator = creator;
        this.resellerAccount = resellerAccount;
        this.registrationDate = registrationDate;
        this.renewDate = renewDate;
        this.remark = remark;
        this.changeDate = changeDate;
        this.clikPaid = clikPaid;
        this.adminContacts = adminContacts;
        this.techContacts = techContacts;
        this.billingContacts = billingContacts;
        this.nameservers = nameservers;
        this.dsmState = dsmState;
        validate();
    }

    public Domain(String name, String holder, String holderClass, String holderCategory, Contact creator, Account resellerAccount,
            Date registrationDate, Date renewDate, String remark, Date changeDate, boolean clikPaid,
            List<Contact> techContacts, List<Contact> billingContacts, List<Contact> adminContacts,
            List<Nameserver> nameservers, DsmState dsmState, boolean zonePublished, boolean pendingCCReservations, boolean pendingADPReservations) {
        this.name = name;
        this.holder = holder;
        this.holderClass = holderClass;
        this.holderCategory = holderCategory;
        this.creator = creator;
        this.resellerAccount = resellerAccount;
        this.registrationDate = registrationDate;
        this.renewDate = renewDate;
        this.remark = remark;
        this.changeDate = changeDate;
        this.clikPaid = clikPaid;
        this.adminContacts = adminContacts;
        this.techContacts = techContacts;
        this.billingContacts = billingContacts;
        this.nameservers = nameservers;
        this.dsmState = dsmState;
        this.zonePublished = zonePublished;
        this.pendingCCReservations = pendingCCReservations;
        this.pendingADPReservations = pendingADPReservations;
        validate();
    }

    public Domain(String name, String holder, String holderClass, String holderCategory, Contact creator, Account resellerAccount,
            Date registrationDate, Date renewDate, String remark, Date changeDate, boolean clikPaid,
            List<Contact> techContacts, List<Contact> billingContacts, List<Contact> adminContacts,
            List<Nameserver> nameservers) {
        this.name = name;
        this.holder = holder;
        this.holderClass = holderClass;
        this.holderCategory = holderCategory;
        this.creator = creator;
        this.resellerAccount = resellerAccount;
        this.registrationDate = registrationDate;
        this.renewDate = renewDate;
        this.remark = remark;
        this.changeDate = changeDate;
        this.clikPaid = clikPaid;
        this.adminContacts = adminContacts;
        this.techContacts = techContacts;
        this.billingContacts = billingContacts;
        this.nameservers = nameservers;
        this.dsmState = DsmState.initialState();
        validate();
    }

    public void validate() {
        Validator.assertNotEmpty(this.name, "domain name");
        Validator.assertNotEmpty(this.holder, "domain holder");
        Validator.assertNotEmpty(this.holderClass, "domain holder class");
//        Validator.assertNotEmpty(holderCategory, "domain holder category");  todo: in database there are domains with empty domain holder
        Validator.assertNotNull(this.registrationDate, "domain registration date");
        Validator.assertNotNull(this.renewDate, "domain renew date");
        /*     Validator.assertNotEmpty(remark, "domain remark");*/ //todo: in database there are domains with empty remarks!!!
        Validator.assertNotNull(this.changeDate, "domain creation date");
        Validator.assertNotNull(this.clikPaid, "clik paid");
        Validator.assertNotNull(this.adminContacts, "admin contacts");
        Validator.assertNotNull(this.techContacts, "tech contacts");
        Validator.assertNotNull(this.billingContacts, "billing contacts");
        Validator.assertNotNull(this.nameservers, "nameservers");      
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Validator.assertNotEmpty(name, "domain name");
        this.name = name;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        Validator.assertNotEmpty(holder, "domain holder");
        this.holder = holder;
    }

    public String getHolderClass() {
        return holderClass;
    }

    public void setHolderClass(String holderClass) {
        this.holderClass = holderClass;
    }

    public String getHolderCategory() {
        return holderCategory;
    }

    public void setHolderCategory(String holderCategory) {
        this.holderCategory = holderCategory;
    }

    public Account getResellerAccount() {
        return resellerAccount;
    }

    public void setResellerAccount(Account resellerAccount) {
        this.resellerAccount = resellerAccount;
    }

    public Contact getCreator() {
        return creator;
    }

    public void setCreator(Contact creator) {
        this.creator = creator;
    }

    public List<Contact> getAdminContacts() {
        return adminContacts;
    }       

    public List<Contact> getTechContacts() {
        return techContacts;
    }

    public List<Contact> getBillingContacts() {
        return billingContacts;
    }    

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(Date renewDate) {
        this.renewDate = renewDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public void updateChangeDate() {
        setChangeDate(new Date());
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isClikPaid() {
        return clikPaid;
    }

    public void setClikPaid(boolean clikPaid) {
        this.clikPaid = clikPaid;
    }

    public List<Nameserver> getNameservers() {
        return nameservers;
    }

    public Nameserver getNameserver(String name) {
        Validator.assertNotNull(name, "name server name");
        for (Nameserver ns : nameservers) {
            if (name.equalsIgnoreCase(ns.getName())) return ns;
        }
        return null;
    }

    public void updateRemark(String hostmasterHandle) {
        remark += " by " + hostmasterHandle + " on " + new Date();
    }

    public void setAdminContacts(List<Contact> adminContacts) {
        this.adminContacts = adminContacts;
    }

    public void setTechContacts(List<Contact> techContacts) {
        this.techContacts = techContacts;
    }

    public void setBillingContacts(List<Contact> billingContacts) {
        this.billingContacts = billingContacts;
    }

    public void setNameservers(List<Nameserver> nameservers) {
        this.nameservers = nameservers;
    }

    public Integer getDateRoll() {
        return dateRoll;
    }

    public void setDateRoll(Integer dateRoll) {
        this.dateRoll = dateRoll;
    }
    
    @Override
    public String toString() {    
    	return name;
    }
    
    public boolean isNRP() {
    	return dsmState.getNRPStatus().isNRP();
    }	

    public DsmState getDsmState() {
        return dsmState;
    }

    public void setGiboRetryTimeout(Date newGIBORetryTimeout) {
        this.giboRetryTimeout = newGIBORetryTimeout;
    }

    public Date getGiboRetryTimeout() {
        return giboRetryTimeout;
    }

    public void setSuspensionDate(Date newDate) {
        this.suspensionDate = newDate;
    }

    public Date getSuspensionDate() {
        return suspensionDate;
    }

    public void setDeletionDate(Date newDate) {
        this.deletionDate = newDate;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public boolean isPublished() {
        return zonePublished;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Date getAuthCodeExpirationDate() {
        return authCodeExpirationDate;
    }

    public void setAuthCodeExpirationDate(Date authCodeExpirationDate) {
        this.authCodeExpirationDate = authCodeExpirationDate;
    }

    public Integer getAuthCodePortalCount() {
        return authCodePortalCount;
    }

    public void setAuthCodePortalCount(Integer authCodePortalCount) {
        this.authCodePortalCount = authCodePortalCount;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    private Contact getContact(List<Contact> list, int index) {
        if (list!=null && list.size() > index) {
            return list.get(index);
        } else {
            return null;
        }
    }

    private String getContactNic(List<Contact> list, int index) {
        if (list!=null && list.size() > index) {
            Contact c = list.get(index);
            if (c == null) {
            	return null;
            } else {
            	return c.getNicHandle();
            }
        } else {
            return null;
        }	
	}

    public Contact getBillingContact() {
    	return getContact(billingContacts, 0);
    }
    
    public String getBillingContactNic() {
    	return getContactNic(billingContacts, 0);
    }
    
    public String getFirstAdminContactNic() {
		return getContactNic(adminContacts, 0);
	}

	public String getSecondAdminContactNic() {
		return getContactNic(adminContacts, 1);
	}
	

	public Contact getFirstAdminContact() {
		return getContact(adminContacts, 0);
	}

	public Contact getSecondAdminContact() {
		return getContact(adminContacts, 1);
	}
		

	public String getTechContactNic() {
		return getContactNic(techContacts, 0);
	}
	
	public Contact getTechContact() {
		return getContact(techContacts, 0);
	}    
    
    public boolean hasPendingCCReservations() {
        return pendingCCReservations;
    }
    
    public boolean hasPendingADPReservations() {
        return pendingADPReservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Domain domain = (Domain) o;

        if (name != null ? !name.equals(domain.name) : domain.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}