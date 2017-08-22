package pl.nask.crs.web.reports;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityCategoryFactory;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.ReportTypeGranulation;
import pl.nask.crs.reports.TotalDomainsPerDate;
import pl.nask.crs.reports.search.TotalDomainsPerDateCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TotalDomainsPerDateReportAction extends GenericSearchAction<TotalDomainsPerDate, TotalDomainsPerDateCriteria> {

    public final SimpleDateFormat REG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String YEAR_MONTH_PATTERN = "yyyy-MM";
    private static final String YEAR_PATTERN = "yyyy";
    private static final int PAGE_SIZE = 15;

    private AccountSearchService accountSearchService;
    private EntityClassFactory entityClassFactory;
    private EntityCategoryFactory entityCategoryFactory;

    private String datePattern;
    private List<ReportTimeGranulation> reportTimeGranulation;
    private List<ReportTypeGranulation> reportTypeGranulation;
    private ReportTypeGranulation actualReportType;
    private ReportTimeGranulation actualReportTime;
    private boolean showAll;

    public TotalDomainsPerDateReportAction(final ReportsAppService reportsAppService, AccountSearchService accountSearchService,
                                           EntityClassFactory entityClassFactory, EntityCategoryFactory entityCategoryFactory) {
        super(new AppSearchService<TotalDomainsPerDate, TotalDomainsPerDateCriteria>() {
            public LimitedSearchResult<TotalDomainsPerDate> search(AuthenticatedUser user, TotalDomainsPerDateCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return reportsAppService.findTotalDomainsPerDate(user, criteria, offset, limit, orderBy);
            }
        });
        this.accountSearchService = accountSearchService;
        this.entityClassFactory = entityClassFactory;
        this.entityCategoryFactory = entityCategoryFactory;
        initAction();
    }

    private void initAction() {
        actualReportType = ReportTypeGranulation.REGISTRAR;
        actualReportTime = ReportTimeGranulation.YEAR;
        reportTimeGranulation = Arrays.asList(ReportTimeGranulation.YEAR, ReportTimeGranulation.MONTH);
        reportTypeGranulation = Arrays.asList(ReportTypeGranulation.ALLREGISTRARS, ReportTypeGranulation.REGISTRAR);
        datePattern = YEAR_PATTERN;
    }


    @Override
    protected TotalDomainsPerDateCriteria createSearchCriteria() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        //default search
        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        return criteria;
    }

    @Override
    protected void updateSearchCriteria(TotalDomainsPerDateCriteria searchCriteria) {
        Long lall = -1l;
        if (Validator.isEmpty(searchCriteria.getHolderCategory()))
            searchCriteria.setHolderCategory(null);
        if (Validator.isEmpty(searchCriteria.getHolderClass()))
            searchCriteria.setHolderClass(null);
        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);
    }

    public String getDatePattern() {
        return datePattern;
    }

    public List<ReportTimeGranulation> getReportTimeGranulation() {
        return reportTimeGranulation;
    }

    public List<ReportTypeGranulation> getReportTypeGranulation() {
        return reportTypeGranulation;
    }

    @Override
    public String search() throws Exception {
        updateActualReportGranulation();
        updateDateFormatPattern();
        return super.search();
    }

    private void updateActualReportGranulation() {
        this.actualReportType = getSearchCriteria().getReportTypeGranulation();
        this.actualReportTime = getSearchCriteria().getReportTimeGranulation();
    }

    private void updateDateFormatPattern() {
        switch (getSearchCriteria().getReportTimeGranulation()) {
            case MONTH:
                datePattern = YEAR_MONTH_PATTERN;
                break;
            case YEAR:
                datePattern = YEAR_PATTERN;
                break;
        }

    }

    public boolean isPerRegistrarSearch() {
        return actualReportType.equals(ReportTypeGranulation.REGISTRAR);
    }

    public List<TotalDomainsPerDateCriteria.CustomerType> getCustomerTypes() {
        return Arrays.asList(TotalDomainsPerDateCriteria.CustomerType.values());
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public List<EnchancedEntityClass> getClassList() {
        return entityClassFactory.getEntries();
    }

    public List<EntityCategory> getCategoryList() {
        return entityCategoryFactory.getEntries();
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

    public String getRegDateFrom(Date date) {
        switch (actualReportTime) {
            case YEAR:
                Date startOfYear = DateUtils.startOfYear(date);
                return REG_DATE_FORMAT.format(startOfYear);
            case MONTH:
                Date startOfMonth = DateUtils.startOfMonth(date);
                return REG_DATE_FORMAT.format(startOfMonth);
            default:
                throw new IllegalArgumentException("Invalid report time granulation: " + getSearchCriteria().getReportTimeGranulation());
        }
    }

    public String getRegDateTo(Date date) {
        switch (actualReportTime) {
            case YEAR:
                Date endOfYear = DateUtils.endOfYear(date);
                return REG_DATE_FORMAT.format(endOfYear);
            case MONTH:
                Date endOfMonth = DateUtils.endOfMonth(date);
                return REG_DATE_FORMAT.format(endOfMonth);
            default:
                throw new IllegalArgumentException("Invalid report time granulation: " + getSearchCriteria().getReportTimeGranulation());
        }
    }

}
