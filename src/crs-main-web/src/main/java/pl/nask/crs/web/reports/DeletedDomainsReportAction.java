package pl.nask.crs.web.reports;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class DeletedDomainsReportAction extends GenericSearchAction<DeletedDomain, DeletedDomainSearchCriteria> {

    private final static List<CustomerType> customerTypes = Arrays.asList(CustomerType.Direct, CustomerType.Registrar);
    private final AccountSearchService accountSearchService;
    private boolean showAll;

    public DeletedDomainsReportAction(final DomainAppService domainAppService, AccountSearchService accountSearchService) {
        super(new AppSearchService<DeletedDomain, DeletedDomainSearchCriteria>() {
            @Override
            public LimitedSearchResult<DeletedDomain> search(AuthenticatedUser user, DeletedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return domainAppService.findDeletedDomains(user, criteria, offset, limit, orderBy);
            }
        });
        this.accountSearchService = accountSearchService;
    }

    @Override
    protected DeletedDomainSearchCriteria createSearchCriteria() {
        return new DeletedDomainSearchCriteria();
    }

    @Override
    protected void updateSearchCriteria(DeletedDomainSearchCriteria searchCriteria) {
        Long lall = -1l;
        if (Validator.isEmpty(searchCriteria.getDomainName()))
            searchCriteria.setDomainName(null);
        if (Validator.isEmpty(searchCriteria.getBillingNH()))
            searchCriteria.setBillingNH(null);
        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);
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

    public List<CustomerType> getCustomerTypes() {
        return customerTypes;
    }

    public List<Account> getAccountsByName() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public List<Account> getAccountsByNic() {
        return accountSearchService.getAccountsWithExclusion(null, Arrays.asList(new SortCriterion("billingContactId", true)));
    }

}
