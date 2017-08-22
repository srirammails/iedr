package pl.nask.crs.domains.dao.ibatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactUtils;
import pl.nask.crs.contacts.dao.ibatis.InternalContactIBatisDAO;
import pl.nask.crs.contacts.dao.ibatis.converters.ContactConverter;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.DomainNotification;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.domains.DsmStateStub;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.NotificationType;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.ibatis.converters.NameserverConverter;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalNameserver;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;

/**
 * @author Kasia Fulara
 */
public class ConvertingDomainDAO extends ConvertingGenericDAO<InternalDomain, Domain, String> implements DomainDAO {

    private InternalDomainIBatisDAO dao;
    private InternalContactIBatisDAO contactDAO;

    public ConvertingDomainDAO(InternalDomainIBatisDAO internalDao, Converter<InternalDomain, Domain> internalConverter,
                               InternalContactIBatisDAO contactDAO) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        this.dao = internalDao;
        Validator.assertNotNull(contactDAO, "contact dao");
        this.contactDAO = contactDAO;
    }

    @Override
    public String getPreviousHolder(SearchCriteria<Domain> domain) {
        return dao.getPreviousHolder(domain);
    }

    private void deleteDNS(String domainName, String nameserver) {
        dao.deleteDNS(domainName, nameserver);
    }

    private void createDNS(InternalNameserver nameserver) {
        dao.createDNS(nameserver);
    }

    @Override
    public void update(Domain domain) {
        super.update(domain);
        InternalDomain internalDomain = getInternalConverter().from(domain);
        updateContacts(internalDomain.getContacts(), internalDomain.getName());
        updateNameservers(internalDomain.getNameservers(), internalDomain.getName());
    }
    
    @Override
    public void update(Domain domain, int dsmState) {
    	InternalDomain internalDomain = getInternalConverter().from(domain);
    	internalDomain.setDsmState(new DsmStateStub(dsmState));
    	getInternalDao().update(internalDomain);
        updateContacts(internalDomain.getContacts(), internalDomain.getName());
        updateNameservers(internalDomain.getNameservers(), internalDomain.getName());
    }

    private void updateNameservers(List<InternalNameserver> nameservers, String domainName) {
        InternalDomain existingDomain = dao.get(domainName);
        for (InternalNameserver n : existingDomain.getNameservers()) {
            deleteDNS(domainName, n.getName());
        }
        for (InternalNameserver n : nameservers) {
            n.setDomainName(domainName);
            createDNS(n);
        }
    }

    private void updateContacts(List<InternalContact> contacts, String domainName) {
        // delete all contacts for a given domain
        InternalContact domainContact = new InternalContact();
        domainContact.setDomainName(domainName);
        contactDAO.delete(domainContact);
        // create new contacts for a given domain
        for (InternalContact c : contacts) {
            if (!c.getNicHandle().equals("")) {
                c.setDomainName(domainName);
                contactDAO.create(c);
            }
        }
    }

    @Override
    public List<String> findDomainNames(SearchCriteria<Domain> domainSearchCriteria, int offset, int limit) {
        return dao.findDomainNames(domainSearchCriteria, offset, limit);        
    }

    @Override
    public List<Domain> findAll(DomainSearchCriteria domainSearchCriteria, List<SortCriterion> sortBy) {
        return getInternalConverter().to(dao.findAll(domainSearchCriteria, sortBy));
    }

    @Override
    public LimitedSearchResult<Domain> findTransferedDomains(String billingNHId, SearchCriteria<Domain> searchCriteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalDomain> result = dao.findTransferedDomains(billingNHId, searchCriteria, offset, limit, sortBy);
        List<Domain> domains = getInternalConverter().to(result.getResults());
        return new LimitedSearchResult<Domain>(searchCriteria, sortBy, limit, offset, domains, result.getTotalResults());
    }

    @Override
    public void createDomain(Domain domain) {
        dao.createDomain(getInternalConverter().from(domain));
    }
    
    @Override
    public void createDomain(NewDomain domain) {
    	// TODO: this violates the ConvertingDAO pattern...
        ContactConverter adminConverter = new ContactConverter(InternalContact.ADMIN);
        ContactConverter techConverter = new ContactConverter(InternalContact.TECH);
        ContactConverter billingConverter = new ContactConverter(InternalContact.BILLING);
        ContactConverter creatorConverter = new ContactConverter(InternalContact.CREATOR);
        NameserverConverter nameserverConverter = new NameserverConverter();
    	
        InternalDomain ret = new InternalDomain();
        ret.setName(domain.getName());
        ret.setHolder(domain.getHolder());
        ret.setHolderCategory(domain.getHolderCategory());
        ret.setHolderClass(domain.getHolderClass());
        ret.setResellerAccountId(domain.getResellerAccountId());
        ret.setRemark(domain.getRemark());       
        List<InternalContact> contacts = new ArrayList<InternalContact>();
        contacts.addAll(adminConverter.from(ContactUtils.stringsAsContacts(domain.getAdminContacts())));
        contacts.addAll(billingConverter.from(ContactUtils.stringsAsContacts(domain.getBillingContacts())));
        contacts.addAll(techConverter.from(ContactUtils.stringsAsContacts(domain.getTechContacts())));
        InternalContact creator = creatorConverter.from(new Contact(domain.getCreator()));
        if (creator != null) {
            contacts.add(creator);
        }
        ret.setContacts(contacts);
        List<InternalNameserver> nameservers = new ArrayList<InternalNameserver>();
        nameservers.addAll(nameserverConverter.from(domain.getNameservers()));
        ret.setNameservers(nameservers);
        // FIXME: a workaround to support old db model - will be deprecated with dsm
        Date now = new Date();
        ret.setRegistrationDate(now);
        ret.setRenewDate(now);
        ret.setDsmState(DsmState.initialState());
        dao.createDomain(ret);
        
        // FIXME: ugly
        updateContacts(ret.getContacts(), ret.getName());
        updateNameservers(ret.getNameservers(), ret.getName());
    }

    @Override
    public LimitedSearchResult<Domain> fullDomainFind(SearchCriteria<Domain> criteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalDomain> res = dao.fullDomainFind((SearchCriteria) criteria, offset, limit, sortBy);
        List<Domain> ret = getInternalConverter().to(res.getResults());
        return new LimitedSearchResult<Domain>(criteria, sortBy, res.getLimit(), res.getOffset(), ret, res.getTotalResults());
    }

    @Override
    public LimitedSearchResult<DeletedDomain> findDeletedDomains(SearchCriteria<DeletedDomain> criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return dao.findDeletedDomains((SearchCriteria) criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<Domain> findDomainsForCurrentRenewal(String nicHandleId, Date renewFrom, Date renewTo, String domainName, long offset, long limit, List<SortCriterion> sortBy) {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setBillingNH(nicHandleId);
        criteria.setRenewFrom(renewFrom);
        criteria.setRenewTo(renewTo);
        criteria.setDomainName(domainName);
        criteria.setActive(true);
        criteria.removeNRPStatus(NRPStatus.PostTransactionAudit);
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew);
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        criteria.setAttachReservationInfo(true);
        LimitedSearchResult<InternalDomain> res = dao.findDomainsForCurrentRenewal(criteria, offset, limit, sortBy);
        List<Domain> ret = getInternalConverter().to(res.getResults());
        return new LimitedSearchResult<Domain>(null, null, res.getLimit(), res.getOffset(), ret, res.getTotalResults());
    }

    @Override
    public LimitedSearchResult<Domain> findDomainsForFutureRenewal(String nicHandleId, Integer month, String domainName, long offset, long limit, List<SortCriterion> sortBy) {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setBillingNH(nicHandleId);
        criteria.setRenewalMonth(month);
        criteria.setDomainName(domainName);
        criteria.setActive(true);
        criteria.removeNRPStatus(NRPStatus.PostTransactionAudit);
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew);
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        criteria.setAttachReservationInfo(true);
        LimitedSearchResult<InternalDomain> res = dao.findDomainsForFutureRenewal(criteria, offset, limit, sortBy);
        List<Domain> ret = getInternalConverter().to(res.getResults());
        return new LimitedSearchResult<Domain>(null, null, res.getLimit(), res.getOffset(), ret, res.getTotalResults());
    }

    @Override
    public void zonePublish(String domainName) {
    	dao.zonePublish(domainName);
    }
    
    @Override
    public void zoneUnpublish(String domainName) {
    	dao.zoneUnpublish(domainName);
    }

	@Override
	public void zoneCommit() {
		dao.zoneCommit();
	}

    @Override
    public List<Integer> getDsmStates() {
        return dao.getDsmStates();
    }
    
    public void deleteById(String domainName, Date deletionDate) {
        dao.deleteDomain(domainName, deletionDate);
    }
    
    @Override
    public boolean exists(String domainName) {    
    	return dao.exists(domainName);
    }

    @Override
    public DomainNotification getDomainNotification(String domainName, NotificationType notificationType, int period) {
        return dao.getNotification(domainName, notificationType, period);
    }

    @Override
    public void createNotification(DomainNotification notification) {
        dao.createNotification(notification);
    }

    @Override
    public List<DomainNotification> getAllNotifications() {
        return dao.getAllNotifications();
    }

    @Override
    public LimitedSearchResult<NsReport> getNsReport(String billingNH, NsReportSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return dao.getNsReports(billingNH, criteria, offset, limit, sortBy);
    }
    
    @Override
    public void createTransferRecord(String domainName, Date transferDate, String oldBillC, String newBillC) {        	
    	dao.createTransferRecord(domainName, transferDate, oldBillC, newBillC);
    }

    @Override
    public String getDomainHolderForTicket(long ticketId) {
        return dao.getDomainHolderForTicket(ticketId);
    }
}
