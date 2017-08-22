package pl.nask.crs.domains.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainStatus;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;
import pl.nask.crs.domains.search.TransferedDomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;

/**
 * @author Kasia Fulara
 */
public class DomainSearchServiceImpl implements DomainSearchService {

    private DomainDAO domainDAO;
    private List<DomainStatus> domainStatuses = new ArrayList<DomainStatus>();

    public DomainSearchServiceImpl(DomainDAO domainDAO) {
        Validator.assertNotNull(domainDAO, "domain dao");
        this.domainDAO = domainDAO;
        domainStatuses.add(DomainStatus.Active);
        domainStatuses.add(DomainStatus.Deleted);
        domainStatuses.add(DomainStatus.Suspended);
    }

    public Domain getDomain(String name) throws DomainNotFoundException {
        Domain ret = domainDAO.get(name);
        if (ret == null) {
            throw new DomainNotFoundException(name);
        }
        return ret;
    }

    public LimitedSearchResult<Domain> find(DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
        return domainDAO.find(criteria, offset, limit, orderBy);
    }

    @Override
    public List<Domain> findAll(DomainSearchCriteria criteria, List<SortCriterion> sortBy) {
        return domainDAO.findAll(criteria, sortBy);
    }

    public LimitedSearchResult<Domain> fullFind(DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
        return domainDAO.fullDomainFind(criteria, offset, limit, orderBy);
    }

    public List<DomainStatus> getDomainStatuses() {
        return domainStatuses;
    }

    @Override
    public LimitedSearchResult<Domain> findTransferedDomains(String billingNHId, TransferedDomainSearchCriteria searchCriteria, long offset, long limit, List<SortCriterion> sortBy) {
        return domainDAO.findTransferedDomains(billingNHId, searchCriteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<DeletedDomain> findDeletedDomains(DeletedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
        return domainDAO.findDeletedDomains(criteria, offset, limit, orderBy);
    }
    
    @Override
    public List<String> findDomainNames(DomainSearchCriteria criteria, int offset, int limit) {    
    	return domainDAO.findDomainNames(criteria, offset, limit);
    }

    @Override
    public LimitedSearchResult<Domain> findDomainsForCurrentRenewal(String nicHandleId, Date renewFrom, Date renewTo, String domainName, long offset, long limit, List<SortCriterion> sortBy) {
        return domainDAO.findDomainsForCurrentRenewal(nicHandleId, renewFrom, renewTo, domainName, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<Domain> findDomainsForFutureRenewal(String nicHandleId, int month, String domainName, long offset, long limit, List<SortCriterion> sortBy) {
        return domainDAO.findDomainsForFutureRenewal(nicHandleId, month, domainName, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<NsReport> getNsReports(String billingNH, NsReportSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return domainDAO.getNsReport(billingNH, criteria, offset, limit, sortBy);
    }

    @Override
    public String getDomainHolderForTicket(long ticketId) {
        return domainDAO.getDomainHolderForTicket(ticketId);
    }
}
