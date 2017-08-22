package pl.nask.crs.web;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.web.displaytag.TableParams;
import pl.nask.crs.web.displaytag.TicketsPaginatedList;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public abstract class GenericSearchAction<T, SEARCH_CRITERIA_TYPE extends SearchCriteria<T>>
        extends
        AuthenticatedUserAwareAction {
    protected final static Logger log = Logger.getLogger(GenericSearchAction.class);

    protected AppSearchService<T, SEARCH_CRITERIA_TYPE> searchService;

    // determines maximum number of rows on one page
    private final static int PAGE_SIZE = 10;

    // search criteria
    private SEARCH_CRITERIA_TYPE searchCriteria;

    // hidden search criteria (feature #747)
    private SEARCH_CRITERIA_TYPE hiddenSearchCriteria;
    // search result
    protected TicketsPaginatedList<T> paginatedResult;

    // displaytag table params
    private TableParams tableParams = new TableParams();

    private boolean resetSearch = false;

    private String defaultSortBy;

    public GenericSearchAction() {
    	searchCriteria = createSearchCriteria();
    }
    
    public GenericSearchAction(AppSearchService<T, SEARCH_CRITERIA_TYPE> searchService) {        
        this.searchService = searchService;
        searchCriteria = createSearchCriteria();
    }

    public void setDefaultSortBy(String defaultSortBy) {
        this.defaultSortBy = defaultSortBy;
    }

    /**
     * initialize search criteria here (create an instance, etc)
     *
     * @return initialized instance of SearchCriteria
     */
    protected abstract SEARCH_CRITERIA_TYPE createSearchCriteria();

    /**
     * Action method. Refreshes search criteria and performs search.
     *
     * @return always SUCCESS
     * @throws Exception
     */
    public String search() throws Exception {
        resetSearch();
        updateSearchCriteria(searchCriteria);                
        refreshSearch();
        clearSearchSriteria(); // feature #747
        return SUCCESS;
    }

    protected void resetSearch() {
        if (resetSearch) {
            hiddenSearchCriteria = searchCriteria;
            tableParams = new TableParams();
        }
    }
    
    public void setResetSearch(boolean b) {
        this.resetSearch = b;
    }

    protected int getPageSize() {
        return PAGE_SIZE;
    }

    /**
     * called by the search and input methods before search is performed
     *
     * @param searchCriteria
     */
    protected void updateSearchCriteria(SEARCH_CRITERIA_TYPE searchCriteria) {

    }

    @Override
    public String input() throws Exception {
        resetSearch();
        updateSearchCriteria(searchCriteria);
        refreshSearch();
        clearSearchSriteria(); // feature #747
        return super.input();
    }

    private void clearSearchSriteria() {        
        searchCriteria = createSearchCriteria();        
    }

    protected TicketsPaginatedList<T> performSearch(SEARCH_CRITERIA_TYPE searchCriteria, TableParams tableParams, int pageSize) throws Exception {
        List<SortCriterion> orderBy = tableParams.createSortingCriteria(defaultSortBy);
        // feature #747 - use hidden search criteria to perform search
        if (searchService == null)
        	throw new IllegalStateException("searchService was not provided by the constructor, you must re-implement this method");
        LimitedSearchResult<T> searchResult = searchService.search(getUser(), searchCriteria, ((long) (tableParams.getPage() - 1))
                * pageSize, pageSize, orderBy);
        return new TicketsPaginatedList<T>(searchResult.getResults(), (int) searchResult
                .getTotalResults(),
                tableParams, pageSize);
    }
    
    protected TicketsPaginatedList<T> performSearch(SEARCH_CRITERIA_TYPE searchCriteria, TableParams tableParams) throws Exception {
        return performSearch(searchCriteria, tableParams, getPageSize());
    }

    protected void refreshSearch() throws Exception {
        paginatedResult = performSearch(hiddenSearchCriteria, tableParams);
    }

    /*
    * simple getters and setters
    */

    public SEARCH_CRITERIA_TYPE getSearchCriteria() {
        if (searchCriteria == null)
            searchCriteria = createSearchCriteria();
        return searchCriteria;
    }

    public void setSearchCriteria(SEARCH_CRITERIA_TYPE searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public SEARCH_CRITERIA_TYPE getHiddenSearchCriteria() {
        return hiddenSearchCriteria;
    }

    public void setHiddenSearchCriteria(SEARCH_CRITERIA_TYPE hiddenSearchCriteria) {
        this.hiddenSearchCriteria = hiddenSearchCriteria;
    }

    public TicketsPaginatedList<T> getPaginatedResult() {
        return paginatedResult;
    }

    public void setTableParams(TableParams tableParams) {
        this.tableParams = tableParams;
    }

    public TableParams getTableParams() {
        return tableParams;
    }
    
    public String getDefaultSortBy() {
		return defaultSortBy;
	}

}
