package pl.nask.crs.domains.dao;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;

/**
 * @author Kasia Fulara
 */
public interface DomainDAO extends GenericDAO<Domain, String> {

    String getPreviousHolder(SearchCriteria<Domain> domain);

/*    void deleteDNS(String domainName, String nameserver);

    void createDNS(Nameserver nameserver);*/

    /**
     * Find all domain names matching the search criteria
     * 
     * @param domainSearchCriteria
     * @param offset
     * @param limit
     * @return search result containing list of found domain names, never null.
     */
    List<String> findDomainNames(SearchCriteria<Domain> domainSearchCriteria, int offset, int limit);

    List<Domain> findAll(DomainSearchCriteria domainSearchCriteria, List<SortCriterion> sortBy);

    LimitedSearchResult<Domain> findTransferedDomains(String billingNHId, SearchCriteria<Domain> searchCriteria, long offset, long limit, List<SortCriterion> sortBy);

    void createDomain(Domain domain);
    
    void createDomain(NewDomain domain);

    LimitedSearchResult<Domain> fullDomainFind(SearchCriteria<Domain> criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<DeletedDomain> findDeletedDomains(SearchCriteria<DeletedDomain> criteria, long offset, long limit, List<SortCriterion> sortBy);

	void update(Domain domain, int targetState);

    LimitedSearchResult<Domain> findDomainsForCurrentRenewal(String nicHandleId, Date renewFrom, Date renewTo, String domainName, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<Domain> findDomainsForFutureRenewal(String nicHandleId, Integer month, String domainName, long offset, long limit, List<SortCriterion> sortBy);

    void zonePublish(String domainName);
    
    void zoneUnpublish(String domainName);

	void zoneCommit();

    List<Integer> getDsmStates();

    DomainNotification getDomainNotification(String domainName, NotificationType notificationType, int period);

    void createNotification(DomainNotification notification);

    List<DomainNotification> getAllNotifications();

	boolean exists(String domainName);

    LimitedSearchResult<NsReport> getNsReport(String billingNH, NsReportSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

	void createTransferRecord(String domainName, Date transferDate, String oldBillC, String newBillC);
	
	void deleteById(String domainName, Date deletionDate);

    String getDomainHolderForTicket(long ticketId);
}
