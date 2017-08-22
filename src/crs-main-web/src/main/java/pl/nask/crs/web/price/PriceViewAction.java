package pl.nask.crs.web.price;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceViewAction extends GenericSearchAction<DomainPrice, SearchCriteria<DomainPrice>> {

    private final int PAGE_SIZE = 15;

    private static final String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";

    private String datePattern = YEAR_MONTH_DAY_PATTERN;

    public PriceViewAction(final PaymentAppService paymentAppService) {
        super(new AppSearchService<DomainPrice, SearchCriteria<DomainPrice>>() {
            public LimitedSearchResult<DomainPrice> search(AuthenticatedUser user, SearchCriteria<DomainPrice> criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return paymentAppService.findAllPrices(user, offset, limit, orderBy);
            }
        });
    }

    @Override
    protected SearchCriteria<DomainPrice> createSearchCriteria() {
        return null;
    }

    public String getDatePattern() {
        return datePattern;
    }

    @Override
    protected int getPageSize() {
        return PAGE_SIZE;
    }
}
