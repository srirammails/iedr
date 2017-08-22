package pl.nask.crs.web.reports;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.InternalLoginUser;
import pl.nask.crs.web.GenericSearchAction;

import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DOAReportAction extends GenericSearchAction<Deposit, DepositSearchCriteria> {

    private final AccountSearchService accountSearchService;

    private final UserAppService userAppService;

    private boolean showAll;

    public DOAReportAction(final PaymentAppService paymentAppService, AccountSearchService accountSearchService, UserAppService userAppService) {
        super(new AppSearchService<Deposit, DepositSearchCriteria>() {
            public LimitedSearchResult<Deposit> search(AuthenticatedUser user, DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return paymentAppService.findDepositWithHistory(user, criteria, offset, limit, orderBy);
            }
        });
        this.accountSearchService = accountSearchService;
        this.userAppService = userAppService;
    }

    @Override
    protected void updateSearchCriteria(DepositSearchCriteria searchCriteria) {
        if (Validator.isEmpty(searchCriteria.getNicHandleId()))
            searchCriteria.setNicHandleId(null);
        if (Validator.isEmpty(searchCriteria.getAccountBillNH()))
            searchCriteria.setAccountBillNH(null);
        if (Validator.isEmpty(searchCriteria.getCorrectorNH()))
            searchCriteria.setCorrectorNH(null);
    }

    @Override
    protected DepositSearchCriteria createSearchCriteria() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setTransactionType(DepositTransactionType.TOPUP);
        return criteria;
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

    public List<InternalLoginUser> getInternalUsers() {
        return userAppService.getInternalUsers();
    }
}
