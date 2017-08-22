package pl.nask.crs.web.reports;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.ExtendedInvoice;
import pl.nask.crs.payment.ExtendedInvoiceSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

import java.util.List;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class ExtendedInvoiceReportAction extends GenericSearchAction<ExtendedInvoice, ExtendedInvoiceSearchCriteria>{

    private static final String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";
    private boolean showAll;

    public ExtendedInvoiceReportAction(final PaymentAppService paymentAppService) {
        super(new AppSearchService<ExtendedInvoice, ExtendedInvoiceSearchCriteria>() {
            @Override
            public LimitedSearchResult<ExtendedInvoice> search(AuthenticatedUser user, ExtendedInvoiceSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return paymentAppService.findExtendedInvoices(user, criteria, offset, limit, orderBy);
            }
        });
    }

    @Override
    protected ExtendedInvoiceSearchCriteria createSearchCriteria() {
        return new ExtendedInvoiceSearchCriteria();
    }

    public String getDatePattern() {
        return YEAR_MONTH_DAY_PATTERN;
    }

    protected void updateSearchCriteria(ExtendedInvoiceSearchCriteria searchCriteria) {
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

}