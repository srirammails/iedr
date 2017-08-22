package pl.nask.crs.web.reports;

import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.web.GenericSearchAction;
import pl.nask.crs.web.displaytag.TableParams;
import pl.nask.crs.web.displaytag.TicketsPaginatedList;
import pl.nask.crs.reports.ReportType;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public class ReportsSearchAction extends GenericSearchAction<TicketReport, ReportsSearchCriteria> {

    private static final String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";
    private static final String YEAR_MONTH_PATTERN = "yyyy-MM";
    private static final String YEAR_PATTERN = "yyyy";

    private List<ReportType> reportTypes;
    private List<ReportTimeGranulation> reportTimeGranulation;
    private String datePattern = YEAR_MONTH_DAY_PATTERN;
    private ReportType actualReportType = ReportType.ALL;
	private ReportsAppService reportsAppService;

    public ReportsSearchAction(ReportsAppService reportsAppService) {
        super(reportsAppService);
		this.reportsAppService = reportsAppService;
        initAction();
    }

    // needed to override since permission check will not work for the declared type of the app service in the supertype
    @Override
    protected TicketsPaginatedList<TicketReport> performSearch(
    		ReportsSearchCriteria searchCriteria, TableParams tableParams,
    		int pageSize) throws Exception {
    	List<SortCriterion> orderBy = tableParams.createSortingCriteria(getDefaultSortBy());
    	// feature #747 - use hidden search criteria to perform search
    	LimitedSearchResult<TicketReport> searchResult = reportsAppService.search(getUser(), searchCriteria, ((long) (tableParams.getPage() - 1))
    			* pageSize, pageSize, orderBy);
    	return new TicketsPaginatedList<TicketReport>(searchResult.getResults(), (int) searchResult
    			.getTotalResults(),
    			tableParams, pageSize);
    }

    private void initAction() {
        reportTypes = Arrays.asList(ReportType.values());
        reportTimeGranulation = Arrays.asList(ReportTimeGranulation.values());
    }

    protected ReportsSearchCriteria createSearchCriteria() {
        return createDefaultCriteria();
    }

    private ReportsSearchCriteria createDefaultCriteria() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        criteria.setReportType(ReportType.ALL);
        return criteria;
    }


    protected void updateSearchCriteria(ReportsSearchCriteria searchCriteria) {
        if (Validator.isEmpty(searchCriteria.getHostmasterName()))
            updateCriteriaWithNewHostmasterName(searchCriteria, null);
    }

    private void updateCriteriaWithNewHostmasterName(ReportsSearchCriteria criteria, String hostmasterName) {
        criteria.setHostmasterName(hostmasterName);
    }

    public String search() throws Exception {
        updateDateFormatPattern();
        updateActualReportType();
        return super.search();
    }

    private void updateDateFormatPattern() {
        switch (getSearchCriteria().getReportTimeGranulation()) {
            case DAY:
                datePattern = YEAR_MONTH_DAY_PATTERN;
                break;
            case MONTH:
                datePattern = YEAR_MONTH_PATTERN;
                break;
            case YEAR:
                datePattern = YEAR_PATTERN;
                break;
        }

    }

    private void updateActualReportType() {
        this.actualReportType = getSearchCriteria().getReportType();
    }

    public List<ReportType> getReportTypes() {
        return reportTypes;
    }

    public List<ReportTimeGranulation> getReportTimeGranulation() {
        return reportTimeGranulation;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public boolean isNotTicketRevisionsSearch() {
        return !actualReportType.equals(ReportType.TICKET_REVISIONS);
    }

    public boolean isNotDocumentsLoggedSearch() {
        return !actualReportType.equals(ReportType.DOCUMENTS_LOGGED);
    }
}
