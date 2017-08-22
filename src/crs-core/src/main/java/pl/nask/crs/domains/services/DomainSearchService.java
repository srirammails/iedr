package pl.nask.crs.domains.services;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainStatus;
import pl.nask.crs.domains.TransferedDomain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;
import pl.nask.crs.domains.search.TransferedDomainSearchCriteria;

/**
 * @author Kasia Fulara
 */
public interface DomainSearchService {

    Domain getDomain(String name) throws DomainNotFoundException;

    LimitedSearchResult<Domain> find(DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy);

    List<Domain> findAll(DomainSearchCriteria criteria, List<SortCriterion> sortBy);

    LimitedSearchResult<Domain> fullFind(DomainSearchCriteria criteria,
                                            long offset, long limit, List<SortCriterion> orderBy);

    List<DomainStatus> getDomainStatuses();

    LimitedSearchResult<Domain> findTransferedDomains(String billingNHId, TransferedDomainSearchCriteria searchCriteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<DeletedDomain> findDeletedDomains(DeletedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy);

	List<String> findDomainNames(DomainSearchCriteria criteria, int offset, int limit);

    /**
     * Returns domain for renewal. If renewFrom or renewTo param is set returns domain for given params otherwise returns domain with renewal date <= current month last day
     *
     * @param nicHandleId
     * @param renewFrom
     * @param renewTo
     * @param offset
     * @param limit
     * @return
     */
    LimitedSearchResult<Domain> findDomainsForCurrentRenewal(String nicHandleId, Date renewFrom, Date renewTo, String domainName, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<Domain> findDomainsForFutureRenewal(String nicHandleId, int month, String domainName, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<NsReport> getNsReports(String billingNH, NsReportSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    @Deprecated
    String getDomainHolderForTicket(long ticketId);
}
