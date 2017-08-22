package pl.nask.crs.web.reports;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransactionsHistoryReportAction extends GenericSearchAction<Deposit, DepositSearchCriteria> {

    private final Integer PAGE_SIZE = 15;

    private String nicHandleId;

    private boolean showAll;

    public TransactionsHistoryReportAction(final PaymentAppService paymentAppService) {
        super(new AppSearchService<Deposit, DepositSearchCriteria>() {
            public LimitedSearchResult<Deposit> search(AuthenticatedUser user, DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return paymentAppService.findDepositWithHistory(user, criteria, offset, limit, orderBy);
            }
        });
        setDefaultSortBy("id");
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public List<DepositTransactionType> getDepositTransactionTypes() {
        return  Arrays.asList(DepositTransactionType.values());
    }

    @Override
    protected void updateSearchCriteria(DepositSearchCriteria searchCriteria) {
        searchCriteria.setNicHandleId(nicHandleId);
        if (Validator.isEmpty(searchCriteria.getRemark()))
            searchCriteria.setRemark(null);
        if (searchCriteria.getTransactionDateFrom() == null && searchCriteria.getTransactionDateTo() == null)
            searchCriteria.setTransactionDateFrom(DateUtils.startOfYear(new Date()));
    }

    @Override
    protected DepositSearchCriteria createSearchCriteria() {
        return new DepositSearchCriteria();
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    protected int getPageSize() {
        return showAll ? Integer.MAX_VALUE : PAGE_SIZE;
    }
}
