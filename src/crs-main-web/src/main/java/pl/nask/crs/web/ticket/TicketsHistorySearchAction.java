package pl.nask.crs.web.ticket;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.utils.UserValidator;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityCategoryFactory;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketHistorySearchService;
import pl.nask.crs.web.GenericSearchAction;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketsHistorySearchAction extends
        GenericSearchAction<HistoricalObject<Ticket>, HistoricTicketSearchCriteria> {

    private final EntityClassFactory entityClassFactory;
    private final EntityCategoryFactory entityCategoryFactory;

    public TicketsHistorySearchAction(AccountSearchService accountSearchService,
                                      final TicketHistorySearchService ticketHistorySearchService, EntityClassFactory entityClassFactory, EntityCategoryFactory entityCategoryFactory) {
        super(new AppSearchService<HistoricalObject<Ticket>, HistoricTicketSearchCriteria>() {
            public LimitedSearchResult<HistoricalObject<Ticket>> search(AuthenticatedUser user, HistoricTicketSearchCriteria criteria, long offset, long limit,
                                                                        List<SortCriterion> orderBy) throws AccessDeniedException {
                UserValidator.validateLoggedIn(user);
                return ticketHistorySearchService.findTicketHistory(criteria, offset, limit, orderBy);
            }
        }
        );
        
        Validator.assertNotNull(entityClassFactory, "class factory");
        Validator.assertNotNull(entityCategoryFactory, "category factory");
        this.entityClassFactory = entityClassFactory;
        this.entityCategoryFactory = entityCategoryFactory;
        
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        this.accountSearchService = accountSearchService;
    }

    private AccountSearchService accountSearchService;

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    @Override
    protected void updateSearchCriteria(HistoricTicketSearchCriteria searchCriteria) {
        Long lall = -1l;
        if ("".equals(searchCriteria.getDomainHolder()))
            searchCriteria.setDomainHolder(null);
        if ("".equals(searchCriteria.getDomainName()))
            searchCriteria.setDomainName(null);
        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);
        if ("".equals(searchCriteria.getCategory())) {
            searchCriteria.setCategory(null);
        }
        if ("".equals(searchCriteria.getClazz())) {
            searchCriteria.setClazz(null);
        }
    }

    @Override
    protected HistoricTicketSearchCriteria createSearchCriteria() {
        return new HistoricTicketSearchCriteria();
    }
    
    public List<EnchancedEntityClass> getClassList() {
        return entityClassFactory.getEntries();
    }
    
    public List<EntityCategory> getCategoryList() {
        return entityCategoryFactory.getEntries();
    }
}
