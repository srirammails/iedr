package pl.nask.crs.domains.dao.ibatis.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;
import pl.nask.crs.domains.DsmState;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public class InternalDomain {

    private String name;

    private String holder;
    private String holderClass;
    private String holderCategory;

    private Long resellerAccountId;
    private String resellerAccountName;

    private Date registrationDate;
    private Date renewDate;
    private Date changeDate;

    private String remark;

    private List<InternalContact> contacts;

    private boolean clikPaid;

    private List<InternalNameserver> nameservers;

    private Integer dateRoll;
    
    private DsmState dsmState;
    
    // the date until which the financial check of the newly registered GIBO domain must be passed.
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
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

    public Long getResellerAccountId() {
        return resellerAccountId;
    }

    public void setResellerAccountId(Long resellerAccountId) {
        this.resellerAccountId = resellerAccountId;
    }

    public String getResellerAccountName() {
        return resellerAccountName;
    }

    public void setResellerAccountName(String resellerAccountName) {
        this.resellerAccountName = resellerAccountName;
    }

    public List<InternalNameserver> getNameservers() {
        return nameservers == null ? Collections.EMPTY_LIST : nameservers;
    }

    public void setNameservers(List<InternalNameserver> nameservers) {
        this.nameservers = nameservers;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public boolean isClikPaid() {
        return clikPaid;
    }

    public void setClikPaid(boolean clikPaid) {
        this.clikPaid = clikPaid;
    }

    public List<InternalContact> getContacts() {
        return contacts == null ? Collections.EMPTY_LIST : contacts;
    }

    public void setContacts(List<InternalContact> contacts) {
        this.contacts = contacts;
    }

    public List<InternalContact> getAdminContacts() {
        return getContactsOfType(InternalContact.ADMIN);
    }

    public List<InternalContact> getTechContacts() {
        return getContactsOfType(InternalContact.TECH);
    }

    public List<InternalContact> getBillingContacts() {
        return getContactsOfType(InternalContact.BILLING);
    }

    public InternalContact getCreator() {
        final List<InternalContact> creatorContacts = getContactsOfType(InternalContact.CREATOR);
        if (creatorContacts.isEmpty())
            return null;
        else return creatorContacts.get(0);
    }

    private List<InternalContact> getContactsOfType(String type) {
        List<InternalContact> ret = new ArrayList<InternalContact>();
        for (InternalContact contact : getContacts()) {
            if (!contact.isEmpty() && type.equalsIgnoreCase(contact.getType())) {
                ret.add(contact);
            }
        }
        return ret;
    }

    public InternalNameserver getNameserver(String name) {
        for (InternalNameserver ns : getNameservers()) {
            if (name.equalsIgnoreCase(ns.getName())) return ns;
        }
        return null;
    }

    public int getNsCount() {
        return getNameservers().size();
    }

    public Account getResellerAccount() {
        return resellerAccountId == null ? null : new Account(resellerAccountId, resellerAccountName);
    }

    public Integer getDateRoll() {
        return dateRoll;
    }

    public void setDateRoll(Integer dateRoll) {
        this.dateRoll = dateRoll;
    }

	public DsmState getDsmState() {		
		return dsmState;
	}
	
	public void setDsmState(DsmState dsmState) {
		this.dsmState = dsmState;
	}
	
	public Date getGiboRetryTimeout() {
		return giboRetryTimeout;
	}
	
	public void setGiboRetryTimeout(Date giboRetryTimeout) {
		this.giboRetryTimeout = giboRetryTimeout;
	}

	public void setSuspensionDate(Date suspensionDate) {
		this.suspensionDate = suspensionDate;
	}

	public Date getSuspensionDate() {
		return suspensionDate;
	}
	
	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	
	public Date getDeletionDate() {
		return deletionDate;
	}

	public boolean isZonePublished() {
		return zonePublished;
	}

	public void setZonePublished(boolean zonePublished) {
		this.zonePublished = zonePublished;
	}
	
	public Date getTransferDate() {
		return transferDate;
	}
	
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public boolean isPendingCCReservations() {
		return pendingCCReservations;
	}

	public void setPendingCCReservations(boolean pendingCCReservations) {
		this.pendingCCReservations = pendingCCReservations;
	}

	public boolean isPendingADPReservations() {
		return pendingADPReservations;
	}

	public void setPendingADPReservations(boolean pendingADPReservations) {
		this.pendingADPReservations = pendingADPReservations;
	}

}