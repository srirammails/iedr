package pl.nask.crs.domains.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.ShortNRPStatus;

/**
 * @author Kasia Fulara
 */
public class DomainSearchCriteria implements SearchCriteria<Domain> {

    private String domainName;
    private String domainHolder;
    private String exactDomainHolder;
    private String nicHandle;
    private Long accountId;
    private Long notAccountId;
    private Date registrationFrom;
    private Date registrationTo;
    private Date renewFrom;
    private Date renewTo;
    private Date suspFrom;
    private Date suspTo;
    private Date delFrom;
    private Date delTo;
    private Date authcExpFrom;
    private Date authcExpTo;
    private Date renewalDate;
    private String holderClass;
    private String holderCategory;
    // can be used only if nicHandle is set
    private List<String> contactType;
	private List<RenewalMode> renewalModes;
	private List<NRPStatus> nrpStatuses;
	private List<DomainHolderType> holderTypes;
	
	private String secondContact;
	private List<String> secondContactType;

	private Date transferFrom;
    private Date transferTo;

    /**
     * to make it work use {@link #filterValues()}
     */
    private Boolean activeFlag;

    private CustomerType type;
    private String billingNH;

    private ShortNRPStatus shortNRPStatus;
    private Integer renewalMonth;
    
    private Boolean attachReservationInfo = false;

    private Boolean authCodeFromPortal = false;

    public DomainSearchCriteria() {
    }

    public DomainSearchCriteria(String domainName, String domainHolder) {
        this.domainHolder = getNotEmptyString(domainHolder);
        this.domainName = getNotEmptyString(domainName);
    }

    public Date getRegistrationFrom() {
        return registrationFrom;
    }

    public void setRegistrationFrom(Date registrationFrom) {
    	this.registrationFrom = DateUtils.startOfDay(registrationFrom);        
    }

    public Date getRegistrationTo() {
        return registrationTo;
    }

    public void setRegistrationTo(Date registrationTo) {
    	this.registrationTo = DateUtils.endOfDay(registrationTo);
    }

    public Date getRenewFrom() {
        return renewFrom;
    }

    public void setRenewFrom(Date renewFrom) {
        this.renewFrom = DateUtils.startOfDay(renewFrom);
    }

    public Date getRenewTo() {
        return renewTo;
    }

    public void setRenewTo(Date renewTo) {
        this.renewTo = DateUtils.endOfDay(renewTo);
    }

    public Date getSuspFrom() {
        return suspFrom;
    }

    public void setSuspFrom(Date suspFrom) {
        this.suspFrom = suspFrom;
    }

    public Date getSuspTo() {
        return suspTo;
    }

    public void setSuspTo(Date suspTo) {
        this.suspTo = suspTo;
    }

    public Date getDelFrom() {
        return delFrom;
    }

    public void setDelFrom(Date delFrom) {
        this.delFrom = delFrom;
    }

    public Date getDelTo() {
        return delTo;
    }

    public void setDelTo(Date delTo) {
        this.delTo = delTo;
    }

    public Date getAuthcExpFrom() {
        return authcExpFrom;
    }

    public void setAuthcExpFrom(Date authcExpFrom) {
        this.authcExpFrom = authcExpFrom;
    }

    public Date getAuthcExpTo() {
        return authcExpTo;
    }

    public void setAuthcExpTo(Date authcExpTo) {
        this.authcExpTo = authcExpTo;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = getNotEmptyString(domainHolder);
    }

    public String getExactDomainHolder() {
        return exactDomainHolder;
    }

    public void setExactDomainHolder(String exactDomainHolder) {
        this.exactDomainHolder = getNotEmptyString(exactDomainHolder);
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = getNotEmptyString(domainName);
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = getNotEmptyString(nicHandle);
    }

    public Long getAccountId() {
        return (accountId == null || accountId < 0) ? null : accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getNotAccountId() {
        return notAccountId;
    }

    public void setNotAccountId(Long notAccountId) {
        this.notAccountId = notAccountId;
    }

    public String getHolderClass() {
        return holderClass;
    }

    public void setHolderClass(String holderClass) {
        if ("(select class)".equals(holderClass)) {
            this.holderClass = null;
        } else {
            this.holderClass = getNotEmptyString(holderClass);
        }
    }

    public String getHolderCategory() {
        return holderCategory;
    }

    public void setHolderCategory(String holderCategory) {
        if ("(select category)".equals(holderCategory)) {
            this.holderCategory = null;
        } else {
            this.holderCategory = getNotEmptyString(holderCategory);
        }
    }

    public List<String> getContactType() {
        return contactType;
    }

    public void setContactType(List<String> contactType) {
        this.contactType = contactType;
    }

    /**
     * setDomainRenewalModes and setRenewalModes refer to the same field: renewalModes
     */
	public void setDomainRenewalModes(RenewalMode... renewalModes) {
		if (renewalModes == null) {
			this.renewalModes = null;
		} else {
			this.renewalModes = Arrays.asList(renewalModes);
		}
	}
	
	/**
     * setDomainRenewalModes and setRenewalModes refer to the same field: renewalModes
     */
	public void setRenewalModes(List<RenewalMode> modes) {
		this.renewalModes = modes;
	}

    public void setRenewalMode(String renewalCode) {
        if (!Validator.isEmpty(renewalCode)) {
            this.renewalModes = Arrays.asList(RenewalMode.forCode(renewalCode));
        }
    }

	public List<RenewalMode> getRenewalModes() {
		return renewalModes;
	}

	/**
	 * search for Active or NRP domains
	 */
	public void setActive(Boolean active) {
		if (active == null) {
			nrpStatuses = null;
		} else {
			List<NRPStatus> list = new ArrayList<NRPStatus>();
			for (NRPStatus st: NRPStatus.values()) {
				if (!active && st.isNRP()) {
					list.add(st);
				} else if (active && !st.isNRP()) {
					list.add(st);
				}
			}		
			nrpStatuses = list;
            this.activeFlag = active;
		}
	}

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public List<NRPStatus> getNrpStatuses() {
		return nrpStatuses;
	}

    public void setNrpStatuses(List<NRPStatus> nrpStatuses) {
        this.nrpStatuses = nrpStatuses;
    }

    public Date getTransferFrom() {
		return transferFrom;
	}

	public void setTransferFrom(Date transferFrom) {
		this.transferFrom = transferFrom;
	}

	public Date getTransferTo() {
		return transferTo;
	}

	public void setTransferTo(Date transferTo) {
		this.transferTo = transferTo;
	}

    private String getNotEmptyString(String val) {
        if (Validator.isEmpty(val)) {
            return null;
        } else {
            return val;
        }
    }
    
    /**
     * setDomainHolderTypes and setHolderTypes refer to the same field 'holderTypes' 
     */
    public void setHolderTypes(List<DomainHolderType> holderTypes) {
        this.holderTypes = holderTypes;
    }
    
    /**
     * setDomainHolderTypes and setHolderTypes refer to the same field 'holderTypes' 
     */
    public void setDomainHolderTypes(DomainHolderType... holderType) {
        if (holderType == null) {
            this.holderTypes = null;
        } else {
            this.holderTypes = Arrays.asList(holderType);
        }
    }

    public void setHolderType(String holderCode) {
        if (!Validator.isEmpty(holderCode)) {
            this.holderTypes = Arrays.asList(DomainHolderType.forCode(holderCode));
        }
    }

    public List<DomainHolderType> getHolderTypes() {
        return holderTypes;
    }

    public void setNrpStatus(String statusCode) {
        if (!Validator.isEmpty(statusCode)) {
            this.nrpStatuses = Arrays.asList(NRPStatus.forCode(statusCode));
        }
    }

    public void setNrpStatusForName(String name) {
        if (!Validator.isEmpty(name)) {
            this.nrpStatuses = Arrays.asList(NRPStatus.forName(name));
        }
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

    public ShortNRPStatus getShortNRPStatus() {
        return shortNRPStatus;
    }

    public void setShortNRPStatus(ShortNRPStatus shortNRPStatus) {
        this.shortNRPStatus = shortNRPStatus;
    }

    public Integer getRenewalMonth() {
        return renewalMonth;
    }

    public void setRenewalMonth(Integer renewalMonth) {
        this.renewalMonth = renewalMonth;
    }

    /**
     * remove
     */
    public void removeNullValues() {
    	if (contactType != null)
    		contactType.remove(null);
    	if (secondContactType != null)
    		secondContactType.remove(null);
    	if (renewalModes != null)
    		renewalModes.remove(null);
    	if (nrpStatuses != null)
    		nrpStatuses.remove(null);
    	if (holderTypes != null)
    		holderTypes.remove(null);
    }
    
    public void filterValues() {
    	removeNullValues();
    	if (nrpStatuses == null || nrpStatuses.size() == 0)
            setActive(activeFlag);
        if (shortNRPStatus != null)
            nrpStatuses = shortNRPStatus.toNRPStatuses();

    }

    public void removeNRPStatus(NRPStatus... nrpStatuses) {
        if (this.nrpStatuses != null && nrpStatuses.length != 0) {
            for (NRPStatus toRemove : nrpStatuses) {
                this.nrpStatuses.remove(toRemove);
            }
        }
    }

	public void setSecondContact(String nh) {
		this.secondContact = nh;
	}
	
	public void setSecondContactType(List<String> secondContactType) {
		this.secondContactType = secondContactType;
	}
	
	public String getSecondContact() {
		return secondContact;
	}
	
	public List<String> getSecondContactType() {
		return secondContactType;
	}

	public boolean isAttachReservationInfo() {
		return attachReservationInfo == null ? false : attachReservationInfo;
	}

	/*
	 * getAttachReservationInfo is required by jax-ws to see the attachReservationInfo property
	 */
	public Boolean getAttachReservationInfo() {
		return attachReservationInfo == null ? false : attachReservationInfo;
	}

	public void setAttachReservationInfo(Boolean attachReservationInfo) {
		this.attachReservationInfo = attachReservationInfo;
	}

    public Boolean getAuthCodeFromPortal() {
        return authCodeFromPortal == null ? false : authCodeFromPortal;
    }

    public void setAuthCodeFromPortal(Boolean authCodeFromPortal) {
        this.authCodeFromPortal = authCodeFromPortal;
    }

}

