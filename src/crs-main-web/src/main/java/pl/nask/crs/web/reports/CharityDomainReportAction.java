package pl.nask.crs.web.reports;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class CharityDomainReportAction extends GenericSearchAction<Domain, DomainSearchCriteria>{

    private final AccountSearchService accountSearchService;
    private final List<CustomerType> customerTypes;
    private final List<NRPStatus> inNRPStatuses;
    private boolean showAll;

    public CharityDomainReportAction(final DomainAppService searchService, AccountSearchService accountSearchService) {
        super(new AppSearchService<Domain, DomainSearchCriteria>() {
            public LimitedSearchResult<Domain> search(AuthenticatedUser user, DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return searchService.searchFull(user, criteria, offset, limit, orderBy);
            }
        });
        customerTypes = prepareCustomerTypes();
        inNRPStatuses = prepareInNRPStatuses();
        this.accountSearchService = accountSearchService;
    }

    private List<CustomerType> prepareCustomerTypes() {
        return Arrays.asList(CustomerType.Direct, CustomerType.Registrar);
    }

    private List<NRPStatus> prepareInNRPStatuses() {
        List<NRPStatus> inNRP = new ArrayList<NRPStatus>();
        for (NRPStatus status : NRPStatus.values()) {
            if (status.isNRP()) {
                inNRP.add(status);
            }
        }
        return inNRP;
    }

    @Override
    protected DomainSearchCriteria createSearchCriteria() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainHolderTypes(DomainHolderType.Charity);
        return criteria;
    }

    @Override
    protected void updateSearchCriteria(DomainSearchCriteria searchCriteria) {
        Long lall = 1l;
        if (Validator.isEmpty(searchCriteria.getDomainName()))
            searchCriteria.setDomainName(null);
        if (Validator.isEmpty(searchCriteria.getBillingNH()))
            searchCriteria.setBillingNH(null);
        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);
    }

    public List<CustomerType> getCustomerTypes() {
        return customerTypes;
    }

    public List<NRPStatus> getInNrpStatuses() {
        return inNRPStatuses;
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    protected int getPageSize() {
        return showAll ? Integer.MAX_VALUE : super.getPageSize();
    }

    public List<Account> getAccountsByName() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public List<Account> getAccountsByNic() {
        return accountSearchService.getAccountsWithExclusion(null, Arrays.asList(new SortCriterion("billingContactId", true)));
    }

}
