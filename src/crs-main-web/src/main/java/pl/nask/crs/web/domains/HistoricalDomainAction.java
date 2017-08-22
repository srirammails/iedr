package pl.nask.crs.web.domains;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.wrappers.AbstractDomainWrapper;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.domains.services.HistoricalDomainService;
import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityCategoryFactory;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;
import pl.nask.crs.web.displaytag.TableParams;
import pl.nask.crs.web.displaytag.TicketsPaginatedList;

/**
 * @author Piotr Tkaczyk
 */
public class HistoricalDomainAction extends GenericSearchAction<HistoricalObject<Domain>, HistoricalDomainSearchCriteria> {
	private static final long serialVersionUID = -457172489323355241L;

	private static final String VIEW = "view";

    private DomainAppService domainAppService;

    private AccountSearchService accountSearchService;

    private HistoricalObject<Domain> historicalDomain;

    private EntityClassFactory entityClassFactory;


    private int selected = -1;
    private int selectedPage = 1;
    private boolean firstSearch = false;
    private int rowNum = -1;
    private long changeId;

    private String previousAction = "historical-domain-search";

    private EntityCategoryFactory entityCategoryFactory;

	private HistoricalDomainWrapper wrapper;

    //fix for bug #1417
    private final static List<SortCriterion> DESCENDING_ORDER = Arrays.asList(new SortCriterion[]{new SortCriterion("changeId", false)});

    public HistoricalDomainAction(DomainAppService domainAppService, final HistoricalDomainService historicalDomainService,
                                  AccountSearchService accountSearchService, EntityClassFactory entityClassFactory, EntityCategoryFactory entityCategoryFactory) {
        this(domainAppService, historicalDomainService, accountSearchService);
        Validator.assertNotNull(entityCategoryFactory, "entity category factory");
        this.entityCategoryFactory = entityCategoryFactory;
        Validator.assertNotNull(entityClassFactory, "entity class factory");
        this.entityClassFactory = entityClassFactory;
    }

    public HistoricalDomainAction(DomainAppService domainAppService, final HistoricalDomainService historicalDomainService,
                                  AccountSearchService accountSearchService) {
        // adapting to GenericSearchAction
        super(new AppSearchService<HistoricalObject<Domain>, HistoricalDomainSearchCriteria>() {
            public LimitedSearchResult<HistoricalObject<Domain>> search(AuthenticatedUser user, HistoricalDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                if (orderBy == null)
                    orderBy = DESCENDING_ORDER;
                return historicalDomainService.findHistory(criteria, (int) offset, (int) limit, orderBy);
            }
        });
        Validator.assertNotNull(domainAppService, "domain application service");
        Validator.assertNotNull(accountSearchService, "account search service");
        this.domainAppService = domainAppService;
        this.accountSearchService = accountSearchService;
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public String view() throws Exception {
        int pageNo = getTableParams().getPage();
        setResetSearch(true);
        resetSearch();
        getTableParams().setPage(pageNo);
        refreshSearch();

        findSelected(new TableParams());
        return VIEW;
    }


    private void findSelected(TableParams tableParams) throws Exception {
        TicketsPaginatedList<HistoricalObject<Domain>> pResult = performSearch(getHiddenSearchCriteria(), tableParams);
        if (pResult.getFullListSize() / getPageSize() >= (tableParams.getPage() - 1)) {
            if (findDomain(changeId, pResult)) {
                if (paginatedResult.getList() == null || (!findDomain(changeId, paginatedResult) && firstSearch)) {
                    paginatedResult = pResult;
                    firstSearch = false;
                    getTableParams().setPage(tableParams.getPage());
                }
                selectedPage = tableParams.getPage();
            } else {
                tableParams.setPage(tableParams.getPage() + 1);
                findSelected(tableParams);
            }
        }
    }

    private boolean findDomain(long changeId, TicketsPaginatedList<HistoricalObject<Domain>> pResult) throws Exception {
        for (int i = 0; i < pResult.getList().size(); i++) {
            HistoricalObject<Domain> histDomain = pResult.getList().get(i);
            if (changeId == histDomain.getChangeId()) {
                selected = i;
                setHistoricalDomain(histDomain);
                return true;
            }
        }
        return false;
    }

    public List<EnchancedEntityClass> getClassList() {
        return entityClassFactory.getEntries();
    }

    public List<EntityCategory> getCategoryList() {
        return entityCategoryFactory.getEntries();
    }

    public HistoricalObject<Domain> getHistoricalDomain() {
        return historicalDomain;
    }

    public void setHistoricalDomain(HistoricalObject<Domain> historicalDomain) {
        this.historicalDomain = historicalDomain;
        this.wrapper = new HistoricalDomainWrapper(historicalDomain);
    }

    public void setChangeId(long date) {
        changeId = date;
    }

    public long getChangeId() {
        return changeId;
    }

    public void setFirstSearch(boolean firstSearch) {
        this.firstSearch = firstSearch;
    }

    public AbstractDomainWrapper getDomainWrapper() {
    	return wrapper;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public void setSelectedPage(int selectedPage) {
        this.selectedPage = selectedPage;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setHistoricalDomainName(String domainName) {
        if (getHiddenSearchCriteria() == null) {
            setHiddenSearchCriteria(new HistoricalDomainSearchCriteria());
        }

        getHiddenSearchCriteria().setDomainName(domainName);
    }

    public boolean isHistory() {
        return historicalDomain != null;
    }

    public boolean hasCurrent() throws DomainNotFoundException, AccessDeniedException {
        return domainAppService.view(getUser(), getHiddenSearchCriteria().getDomainName()) != null;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public String makeContactInfo(Contact contact) {
        return ContactHelper.makeContactInfo(contact);
    }

    @Override
    protected HistoricalDomainSearchCriteria createSearchCriteria() {
        return new HistoricalDomainSearchCriteria();
    }
}
