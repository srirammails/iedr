package pl.nask.crs.web.reports;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.CustomerType;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.InvoiceSearchCriteria;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoicesReportAction extends GenericSearchAction<Invoice, InvoiceSearchCriteria> {

    private static final String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";
    private final AccountSearchService accountSearchService;

    private boolean showAll;

    public InvoicesReportAction(final PaymentAppService paymentAppService, AccountSearchService accountSearchService) {
        super(new AppSearchService<Invoice, InvoiceSearchCriteria>() {
            public LimitedSearchResult<Invoice> search(AuthenticatedUser user, InvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return paymentAppService.findInvoices(user, criteria, offset, limit, orderBy);
            }
        });
        this.accountSearchService = accountSearchService;
    }

    @Override
    protected InvoiceSearchCriteria createSearchCriteria() {
        return new InvoiceSearchCriteria();
    }

    public String getDatePattern() {
        return YEAR_MONTH_DAY_PATTERN;
    }

    public List<CustomerType> getCustomerTypes() {
        return Arrays.asList(CustomerType.values());
    }

    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    protected void updateSearchCriteria(InvoiceSearchCriteria searchCriteria) {
        if (Validator.isEmpty(searchCriteria.getBillingNhId()))
            searchCriteria.setBillingNhId(null);
        if (Validator.isEmpty(searchCriteria.getAccountName()))
            searchCriteria.setAccountName(null);
        if (Validator.isEmpty(searchCriteria.getInvoiceNumber()))
            searchCriteria.setInvoiceNumber(null);
        if (Validator.isEmpty(searchCriteria.getInvoiceNumberFrom()))
            searchCriteria.setInvoiceNumberFrom(null);
        if (Validator.isEmpty(searchCriteria.getInvoiceNumberTo()))
            searchCriteria.setInvoiceNumberTo(null);
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
