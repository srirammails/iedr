package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.api.converter.Converter;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.BillingStatus;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.nameservers.Nameserver;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainVO {
    private String name;

    private String holder;

    private String holderClass;

    private String holderCategory;

    private AccountVO resellerAccount;

    private String billingStatus;

    private Date registrationDate;
    private Date renewDate;
    private Date changeDate;
    private Date suspensionDate;
    private Date deletionDate;
    private Date transferDate;

    private String remark;
    private String authCode;
    private Date authCodeExpirationDate;

    private Integer dateRoll = null;

    private ContactVO creator;

    @XmlElement
    private List<ContactVO> adminContacts = new ArrayList<ContactVO>();
    private List<ContactVO> techContacts = new ArrayList<ContactVO>();
    private List<ContactVO> billingContacts = new ArrayList<ContactVO>();

    private List<NameserverVO> nameservers = new ArrayList<NameserverVO>();
    
    private DsmStateVO dsmState;

    private Boolean pendingCCReservations;
    private Boolean pendingADPReservations;

    public DomainVO() {
    }

    public DomainVO(Domain domain) {
        this(domain, true);
    }

    public DomainVO(Domain domain, boolean allData) {
        this.name = domain.getName();
        this.holder = domain.getHolder();
        this.holderClass = domain.getHolderClass();
        this.holderCategory = domain.getHolderCategory();
        this.renewDate = domain.getRenewDate();
        this.dsmState = new DsmStateVO(domain.getDsmState(), allData);

        if (allData) {
            this.authCode = domain.getAuthCode();
            this.authCodeExpirationDate = domain.getAuthCodeExpirationDate();

            setResellerAccount(domain.getResellerAccount());
            this.registrationDate = domain.getRegistrationDate();
            this.changeDate = domain.getChangeDate();

            this.remark = domain.getRemark();
            this.dateRoll = domain.getDateRoll();
            setCreator(domain.getCreator());

            setAdminContacts(domain.getAdminContacts());
            setTechContacts(domain.getTechContacts());
            setBillingContacts(domain.getBillingContacts());
            setNameservers(domain.getNameservers());
            this.suspensionDate = domain.getSuspensionDate();
            this.deletionDate = domain.getDeletionDate();
            this.transferDate = domain.getTransferDate();
            this.pendingCCReservations = domain.hasPendingCCReservations();
            this.pendingADPReservations = domain.hasPendingADPReservations();
        }
    }

    void setBillingStatus(BillingStatus billingStatus2) {
        if (billingStatus2 != null)
            this.billingStatus = billingStatus2.getDescription();
    }

    void setResellerAccount(Account resellerAccount) {        
        if (resellerAccount != null)
            this.resellerAccount = new AccountVO(resellerAccount);
    }

    public String getName() {
        return name;
    }

    public String getHolder() {
        return holder;
    }

    public String getHolderClass() {
        return holderClass;
    }

    public String getHolderCategory() {
        return holderCategory;
    }

    public AccountVO getResellerAccount() {
        return resellerAccount;
    }

    public String getBillingStatus() {
        return billingStatus;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Date getRenewDate() {
        return renewDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public String getRemark() {
        return remark;
    }

    public String getAuthCode() {
        return authCode;
    }

    public Date getAuthCodeExpirationDate() {
        return authCodeExpirationDate;
    }

    public Integer getDateRoll() {
        return dateRoll;
    }

    public ContactVO getCreator() {
        return creator;
    }

    void setCreator(Contact creator) {
        if (creator != null)
            this.creator = new ContactVO(creator);
    }

    public List<ContactVO> getAdminContacts() {
        return adminContacts;
    }

    void setAdminContacts(List<Contact> adminContacts) {
        this.adminContacts = convertContacts(adminContacts);
    }

    public List<ContactVO> getTechContacts() {
        return techContacts;
    }

    void setTechContacts(List<Contact> techContacts) {
        this.techContacts = convertContacts(techContacts);
    }

    public List<ContactVO> getBillingContacts() {
        return billingContacts;
    }

    void setBillingContacts(List<Contact> billingContacts) {
        this.billingContacts = convertContacts(billingContacts);
    }

    private List<ContactVO> convertContacts(List<Contact> contacts) {
        if (contacts == null)            
            return null;
        else {
            ArrayList<ContactVO> res = new ArrayList<ContactVO>(contacts.size());
            for (Contact c: contacts) {
                if (c != null)
                    res.add(new ContactVO(c));
                else
                    res.add(null);
            }
            return res;
        }
    }

    public List<NameserverVO> getNameservers() {
        return nameservers;
    }

    void setNameservers(List<Nameserver> nameservers) {
        this.nameservers = Converter.toNameserverVOList(nameservers);
        if (nameservers != null) {        
            this.nameservers = new ArrayList<NameserverVO>(nameservers.size());
            for (Nameserver ns: nameservers) {
                if (ns != null)
                    this.nameservers.add(new NameserverVO(ns));
                else
                    this.nameservers.add(null);
            }
        } else {
            this.nameservers = null;
        }
    }

    public DsmStateVO getDsmState() {
        return dsmState;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public Date getSuspensionDate() {
        return suspensionDate;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setRemoveFromVoluntaryNRPPossible(boolean flag) {
        this.dsmState.setRemoveFromVoluntaryNRPPossible(flag);
    }

    public void setEnterVoluntaryNRPPossible(boolean flag) {
        this.dsmState.setEnterVoluntaryNRPPossible(flag);
    }
}
