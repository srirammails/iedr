package pl.nask.crs.web.reports;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityCategoryFactory;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.reports.DomainsPerClass;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainsPerClassReportAction extends GenericSearchAction<DomainsPerClass, DomainsPerClassSearchCriteria> {

    private final int PAGE_SIZE = Integer.MAX_VALUE;
    private EntityClassFactory entityClassFactory;
    private EntityCategoryFactory entityCategoryFactory;
    private AccountSearchService accountSearchService;

    public DomainsPerClassReportAction(final ReportsAppService reportsAppService, EntityClassFactory entityClassFactory, EntityCategoryFactory entityCategoryFactory, AccountSearchService accountSearchService) {
        super(new AppSearchService<DomainsPerClass, DomainsPerClassSearchCriteria>() {
            public LimitedSearchResult<DomainsPerClass> search(AuthenticatedUser user, DomainsPerClassSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return reportsAppService.findTotalDomainsPerClass(user, criteria, offset, limit, orderBy);
            }
        });
        this.entityClassFactory = entityClassFactory;
        this.entityCategoryFactory = entityCategoryFactory;
        this.accountSearchService = accountSearchService;
    }

    @Override
    protected DomainsPerClassSearchCriteria createSearchCriteria() {
        return new DomainsPerClassSearchCriteria();
    }

    @Override
    protected void updateSearchCriteria(DomainsPerClassSearchCriteria searchCriteria) {
        Long lall = -1L;
        if (Validator.isEmpty(searchCriteria.getHolderClass()))
            searchCriteria.setHolderClass(null);
        if (Validator.isEmpty(searchCriteria.getHolderCategory()))
            searchCriteria.setHolderCategory(null);
        if (Validator.isEmpty(searchCriteria.getBillingNH()))
            searchCriteria.setBillingNH(null);
        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);
    }

    @Override
    protected int getPageSize() {
        return PAGE_SIZE;
    }

    public List<EnchancedEntityClass> getClassList() {
        return entityClassFactory.getEntries();
    }

    public List<EntityCategory> getCategoryList() {
        return entityCategoryFactory.getEntries();
    }

    public List<Account> getAccountsByName() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public List<Account> getAccountsByNic() {
        return accountSearchService.getAccountsWithExclusion(null, Arrays.asList(new SortCriterion("billingContactId", true)));
    }

}
