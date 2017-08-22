package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.utils.TicketConverter;
import pl.nask.crs.commons.Period;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.IPAddress;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.TechStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractTicketRequestVO implements TicketRequest {

    @XmlElement(required = true, nillable = false)
    private String domainName;

    @XmlElement(required = true, nillable = false)
    private String domainHolder;

    @XmlElement(required = true, nillable = false)
    private String domainHolderClass;

    @XmlElement(required = true, nillable = false)
    private String domainHolderCategory;

    @XmlElement(required = true, nillable = false)
    private String adminContact1NicHandle;

    private String adminContact2NicHandle;

    @XmlElement(required = true, nillable = false)
    private String techContactNicHandle;

    @XmlElement(required = true, nillable = false)
    private List<NameserverVO> nameservers = new ArrayList<NameserverVO>();

    @XmlElement(required = true, nillable = false)
    private String requestersRemark;

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getDomainName()
      */
    public String getDomainName() {
        return domainName.toLowerCase();
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getDomainHolder()
      */
    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getDomainHolderClass()
      */
    public String getDomainHolderClass() {
        return domainHolderClass;
    }

    public void setDomainHolderClass(String domainHolderClass) {
        this.domainHolderClass = domainHolderClass;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getDomainHolderCategory()
      */
    public String getDomainHolderCategory() {
        return domainHolderCategory;
    }

    public void setDomainHolderCategory(String domainHolderCategory) {
        this.domainHolderCategory = domainHolderCategory;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getAdminContact1NicHandle()
      */
    public String getAdminContact1NicHandle() {
        return adminContact1NicHandle;
    }

    public void setAdminContact1NicHandle(String adminContact1NicHandle) {
        this.adminContact1NicHandle = adminContact1NicHandle;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getAdminContact2NicHandle()
      */
    public String getAdminContact2NicHandle() {
        return adminContact2NicHandle;
    }

    public void setAdminContact2NicHandle(String adminContact2NicHandle) {
        this.adminContact2NicHandle = adminContact2NicHandle;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getTechContactNicHandle()
      */
    public String getTechContactNicHandle() {
        return techContactNicHandle;
    }

    public void setTechContactNicHandle(String techContactNicHandle) {
        this.techContactNicHandle = techContactNicHandle;
    }

    public void setNameservers(List<NameserverVO> nameservers) {
        this.nameservers.clear();
        this.nameservers.addAll(nameservers);
    }

    public void updateNameservers(List<NameserverVO> newNameservers) {
        int i = 0;
        for (NameserverVO nsVO : newNameservers)
            nameservers.set(i++, nsVO);
    }

    public void resetToNameservers(List<Nameserver> newNameservers) {
        if (newNameservers == null)
            return;
        nameservers.clear();
        for (Nameserver ns : newNameservers) {
            nameservers.add(new NameserverVO(ns.getName(), ns.getIpAddressAsString()));
        }
    }

    @Override
    public Nameserver getNameserver(int order) {
        NameserverVO nsVO = nameservers.get(order);

        final String name = nsVO.getName();
        final String ip = nsVO.getIpAddress();
        return new Nameserver(name, ip == null ? new IPAddress("") : new IPAddress(ip), order);
    }


    @Override
    public List<Nameserver> getNameservers() {
        List<Nameserver> res = new ArrayList<Nameserver>();

        for (int i = 0; i < nameservers.size(); i++) {
            Nameserver ns = getNameserver(i);
            if (ns != null) {
                res.add(ns);
            }
        }

        return res;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getRequestersRemark()
      */
    public String getRequestersRemark() {
        return requestersRemark;
    }


    public void setRequestersRemark(String requestersRemark) {
        this.requestersRemark = requestersRemark;
    }

    @Override
    public List<String> getAdminContacts() {
        List<String> l = new ArrayList<String>();
        if (getAdminContact1NicHandle() != null) {
            l.add(getAdminContact1NicHandle());
        }
        if (getAdminContact2NicHandle() != null) {
            l.add(getAdminContact2NicHandle());
        }

        return l;
    }

    protected Ticket _toTicket(String creatorNh, long accountId, String billingContactNh, DomainOperation.DomainOperationType ticketType, Period p, String charityCode, AdminStatus adminStatus) {
        Date crDate = new Date();

        List<SimpleDomainFieldChange<Contact>> adminList = TicketConverter.asDomainFieldChangeContactList(getAdminContact1NicHandle(), getAdminContact2NicHandle());

        List<SimpleDomainFieldChange<Contact>> techList = TicketConverter.asDomainFieldChangeContactList(getTechContactNicHandle());

        List<SimpleDomainFieldChange<Contact>> billingList = TicketConverter.asDomainFieldChangeContactList(billingContactNh);

        List<NameserverChange> nsList = TicketConverter.asNameserverChangeList(getNameservers());

        DomainOperation domainOp = TicketConverter.asDomainOperation(ticketType, crDate, getDomainName(), getDomainHolder(), getDomainHolderClass(), getDomainHolderCategory(), accountId, null,
                adminList, techList, billingList, nsList);

        TechStatus tStatus = TechStatusEnum.NEW;

        AdminStatus aStatus = adminStatus == null ? AdminStatusEnum.NEW : adminStatus;

        return new Ticket(domainOp, aStatus, crDate, tStatus, crDate,
                getRequestersRemark(), "", new Contact(creatorNh),
                crDate, crDate, null, false, false, p, charityCode, FinancialStatusEnum.NEW, crDate);
    }

    protected Ticket _toTicket(String creatorNh, long accountId, String billingContactNh, DomainOperation.DomainOperationType ticketType, Period p, String charityCode) {
        return _toTicket(creatorNh, accountId, billingContactNh, ticketType, p, charityCode, null);
    }

}
