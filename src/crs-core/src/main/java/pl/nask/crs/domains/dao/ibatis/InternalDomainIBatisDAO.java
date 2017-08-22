package pl.nask.crs.domains.dao.ibatis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainNotification;
import pl.nask.crs.domains.NotificationType;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalNameserver;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;


/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public class InternalDomainIBatisDAO extends GenericIBatisDAO<InternalDomain, String> {

	private Map<String, String> domainSortMap = new HashMap<String, String>();
	{
		domainSortMap.put("name", "domainName");
		domainSortMap.put("domainName", "domainName");
		domainSortMap.put("holder", "holder");
        domainSortMap.put("domainHolder","holder");
        domainSortMap.put("holderClass", "holderClass");
		domainSortMap.put("holderCategory", "holderCategory");
		domainSortMap.put("resellerAccountId", "resellerAccountId");
		domainSortMap.put("resellerAccountName","resellerAccountName");     
		domainSortMap.put("domainStatusDate", "domainStatusDate");
		domainSortMap.put("registrationDate", "registrationDate");
		domainSortMap.put("renewDate", "renewDate");
        domainSortMap.put("renewalDate", "renewDate");
        domainSortMap.put("changeDate", "changeDate");
		domainSortMap.put("suspensionDate", "suspensionDate");
		domainSortMap.put("deletionDate", "deletionDate");
		domainSortMap.put("transferDate", "transferDate");
        domainSortMap.put("pendingReservations", "pendingReservations");
        domainSortMap.put("dsmNrpStatus", "dsmNrpStatus");
        domainSortMap.put("billingNH", "C2.Contact_NH");
    }

    private Map<String, String> deletedDomainSortMap = new HashMap<String, String>();
    {
        deletedDomainSortMap.put("domainName", "domainName");
        deletedDomainSortMap.put("domainHolder", "domainHolder");
        deletedDomainSortMap.put("billingName", "billingName");
        deletedDomainSortMap.put("billingNic", "billingNic");
        deletedDomainSortMap.put("registrationDate", "registrationDate");
        deletedDomainSortMap.put("renewalDate", "renewalDate");
        deletedDomainSortMap.put("deletionDate", "deletionDate");
        deletedDomainSortMap.put("holderClass", "holderClass");
        deletedDomainSortMap.put("holderCategory", "holderCategory");
    }

    private Map<String, String> dnsDomainSortMap = new HashMap<String, String>();
	private ApplicationConfig config;
    {
        dnsDomainSortMap.put("domainName", "domainName");
        dnsDomainSortMap.put("holderName", "holderName");
        dnsDomainSortMap.put("registrationDate", "registrationDate");
        dnsDomainSortMap.put("renewalDate", "renewalDate");
        dnsDomainSortMap.put("dnsName", "dnsName");
        dnsDomainSortMap.put("dnsOrder", "dnsOrder");
        dnsDomainSortMap.put("dnsIpAddress", "dnsIpAddress");
    }

    public InternalDomainIBatisDAO(ApplicationConfig config) {
        this.config = config;
		setGetQueryId("domain.getDomainByName");
        setFindQueryId("domain.findDomain");
        setLimitedFindQueryId("domain.findDomain");
        setCountFindQueryId("domain.countFindDomain");
        setLockQueryId("domain.getLockedDomainByName");
        setUpdateQueryId("domain.updateDomain");
        setSortMapping(domainSortMap);
    }

    public String getPreviousHolder(SearchCriteria<Domain> domain) {
        return (String) performQueryForObject("domain.getPreviousHolder", domain);
    }

    public void deleteDNS(String domainName, String nameserver) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("domainName", domainName);
        param.put("nameserverName", nameserver);
        performDelete("domain.deleteDNS", param);
    }

    public void createDNS(InternalNameserver internalNameserver) {
        performInsert("domain.createDNS", internalNameserver);
    }

    public List<String> findDomainNames(SearchCriteria<Domain> criteria, int offset, int limit) {
    	// ugly workaround to make sure, that the criteria will not contain null values in the collections
    	prepare(criteria);
    	FindParameters params = new FindParameters(criteria).setLimit(offset, limit);
        List<String> res = performQueryForList("domain.findDomainNames", params);
        return res;
    }

    private void prepare(SearchCriteria<Domain> criteria) {
    	if (criteria instanceof DomainSearchCriteria) {
    		((DomainSearchCriteria) criteria).removeNullValues();
    	}
	}

	public LimitedSearchResult<InternalDomain> findTransferedDomains(String billingNHId, SearchCriteria<Domain> criteria, long offset, long limit, List<SortCriterion> sortBy) {
        prepare(criteria);
		FindParameters params = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy);
        Map<String, Object> parameterMap = params.getMap();
        parameterMap.put("billingNHId", billingNHId);
        List<InternalDomain> res = Collections.emptyList();
        Integer total = performQueryForObject("domain.findTransferedDomainsCount", parameterMap);
        if (total > 0) {
            res = performQueryForList("domain.findTransferedDomains", parameterMap);
        }
        return new LimitedSearchResult<InternalDomain>(null, null, limit, offset, res, total);
    }

    public void createDomain(InternalDomain internalDomain) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("internalDomain", internalDomain);
        param.put("discount", "N");
        performInsert("domain.createDomain", param);
    }

    public LimitedSearchResult<InternalDomain> fullDomainFind(SearchCriteria<InternalDomain> criteria,
                                                              long offset, long limit, List<SortCriterion> sortBy) {
    	return performFind("domain.findFullDomain", "domain.countFullFindDomain", criteria, offset, limit, sortBy);
    }

    public LimitedSearchResult<DeletedDomain> findDeletedDomains(SearchCriteria<DeletedDomain> criteria, long offset, long limit, List<SortCriterion> sortBy) {
    	long finalDsmState = config.getEligibleForDeletionDomainState();
    	FindParameters params = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy, deletedDomainSortMap);
    	params.addSpecialParameter("finalDsmState", finalDsmState);
        List<DeletedDomain> list = null;
        Integer total = performQueryForObject("domain.countFindDeleted", params);
        if (total == 0) {
            list = new ArrayList<DeletedDomain>(0);
        } else {
            list = performQueryForList("domain.findDeletedDomains", params);
        }
        return new LimitedSearchResult<DeletedDomain>(criteria, sortBy, limit, offset, list, total);
    }

    LimitedSearchResult<InternalDomain> findDomainsForCurrentRenewal(DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
    	prepare(criteria);
    	FindParameters parameters = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy);
        return  performFind("domain.findDomainForCurrentRenewal", "domain.findDomainForCurrentRenewalCount", parameters);
    }

    LimitedSearchResult<InternalDomain> findDomainsForFutureRenewal(DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
    	prepare(criteria);
    	FindParameters parameters = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy);
        return  performFind("domain.findDomainForFutureRenewal", "domain.findDomainForFutureRenewalCount", parameters);
    }

    public void zoneUnpublish(String domainName) {
		performDelete("domain.domainUnpublished", domainName);
	}

	public void zonePublish(String domainName) {
		performInsert("domain.domainPublished", domainName);
	}

	public void zoneCommit() {
		performUpdate("domain.zoneCommit", null);
	}

    List<InternalDomain> findAll(DomainSearchCriteria criteria, List<SortCriterion> sortBy) {
    	prepare(criteria);
    	FindParameters findParameters = new FindParameters(criteria).setOrderBy(sortBy);
        return performQueryForList("domain.findFullDomain", findParameters);
    }

    public List<Integer> getDsmStates() {
        return performQueryForList("domain.getDsmStates");
    }

    public void deleteDomain(String domainName, Date deletionDate) {
        performDelete("domain.deleteAssociatedDNS", domainName);
        performDelete("domain.deleteAssociatedContact", domainName);
        performDelete("domain.deleteDomain", domainName);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("domainName", domainName);
        params.put("deletionDate", deletionDate);
		performInsert("domain.recordDeletion", params );
    }

    public DomainNotification getNotification(String domainName, NotificationType notificationType, int period) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("domainName", domainName);
        param.put("notificationType", notificationType);
        param.put("notificationPeriod", period);
        return performQueryForObject("domain.getNotification", param);
    }

    public void createNotification(DomainNotification notification) {
    	performInsert("domain.createNotification", notification);
    }

    public List<DomainNotification> getAllNotifications() {
        return performQueryForList("domain.getAllNotifications");
    }

	public boolean exists(String domainName) {
		return performQueryForObject("domain.exists", domainName) != null;
	}

    public LimitedSearchResult<NsReport> getNsReports(String billingNH, NsReportSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        FindParameters parameters = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy, dnsDomainSortMap);
        parameters.getMap().put("billingNH", billingNH);
        Integer total = performQueryForObject("domain.getNsReportsCount", parameters);
        List<NsReport> reports = null;
        if (total > 0) {
             reports = performQueryForList("domain.getNsReports", parameters);
        } else {
            reports = Collections.emptyList();
        }
        return new LimitedSearchResult<NsReport>(criteria, sortBy, limit, offset, reports, total);
    }

	public void createTransferRecord(String domainName, Date transferDate, String oldBillC, String newBillC) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("domainName", domainName);
		params.put("transferDate", transferDate);
		params.put("oldBillC", oldBillC);
		params.put("newBillC", newBillC);
		performInsert("domain.createTransferHistRecord", params );
	}

    public String getDomainHolderForTicket(long ticketId) {
        return performQueryForObject("domain.getDomainHolderForTicket", ticketId);
    }
}
