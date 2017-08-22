package pl.nask.crs.web.ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityCategoryFactory;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.statuses.Status;
import pl.nask.crs.ticket.CustomerStatusEnum;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.user.User;
import pl.nask.crs.user.search.UserSearchCriteria;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.web.GenericSearchAction;

/**
 * Action for tickets SEARCH. Action keep it's state (search criteria, result
 * table state) in the session.
 * <p/>
 * Action methods:
 * <ul>
 * <li>{@link #input()}</li>
 * <li>{@link #search()}</li>
 * </ul>
 *
 * @author Patrycja Wegrzynowicz, Artur Gniadzik
 */
public class TicketsSearchAction extends GenericSearchAction<Ticket, TicketSearchCriteria> {

    private final static List<Status> ADMIN_STATUSES_NULL = new ArrayList<Status>(0);
    private List<Status> adminStatuses = ADMIN_STATUSES_NULL;

    private final static List<Status> TECH_STATUSES_NULL = new ArrayList<Status>(0);
    private List<Status> techStatuses = TECH_STATUSES_NULL;

    private List<FinancialStatusEnum> financialStatuses = Arrays.asList(FinancialStatusEnum.values());

    private final static List<Account> ACCOUNTS_NULL = new ArrayList<Account>(0);
    private List<Account> accounts = ACCOUNTS_NULL;

    private final static List<User> HOSTMASTERS_NULL = new ArrayList<User>(0);
    private List<User> hostmasters = HOSTMASTERS_NULL;

    private static final List<EntityCategory> CATEGORY_LIST_NULL = new ArrayList<EntityCategory>(0);
    private List<EntityCategory> categoryList = CATEGORY_LIST_NULL;
    
    private static final List<EnchancedEntityClass> CLASS_LIST_NULL = new ArrayList<EnchancedEntityClass>(0);
    private List<EnchancedEntityClass> classList = CLASS_LIST_NULL;

    private Ticket processedTicket;
    private final TicketSearchService ticketSearchService;

    public TicketsSearchAction(TicketAppService ticketAppService, TicketSearchService ticketSearchService,
                               AccountSearchService accountSearchService,
                               UserSearchService userSearchService, Dictionary<Integer, Status> adminStatusFactory,
                               Dictionary<Integer, Status> techStatusFactory, EntityCategoryFactory entityCategoryFactory, EntityClassFactory entityClassFactory) {
        super(ticketAppService);
        Validator.assertNotNull(ticketSearchService, "ticketSearchService");
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        Validator.assertNotNull(userSearchService, "userSearchService");
        Validator.assertNotNull(adminStatusFactory, "adminStatusFactory");
        Validator.assertNotNull(techStatusFactory, "techStatusFactory");
        Validator.assertNotNull(entityCategoryFactory, "entityCategoryFactory");
        Validator.assertNotNull(entityClassFactory, "entityClassFactory");
        this.ticketSearchService = ticketSearchService;
        initializeDictionaries(accountSearchService, userSearchService, adminStatusFactory, techStatusFactory,
                entityCategoryFactory, entityClassFactory);
    }

    @Override
    protected TicketSearchCriteria createSearchCriteria() {
        return new TicketSearchCriteria();
    }

    private void initializeDictionaries(AccountSearchService accountSearchService, UserSearchService userSearchService,
                                        Dictionary<Integer, Status> adminStatusFactory, Dictionary<Integer, Status> techStatusFactory,
                                        EntityCategoryFactory entityCategoryFactory, EntityClassFactory entityClassFactory) {
        try {
            adminStatuses = adminStatusFactory.getEntries();
            techStatuses = techStatusFactory.getEntries();

            UserSearchCriteria usc = new UserSearchCriteria();
            // usc.setHostmaster(true);
            SearchResult<User> r = userSearchService.find(usc);
            hostmasters = r.getResults();

            accounts = accountSearchService.getAccountsWithExclusion();
            categoryList = entityCategoryFactory.getEntries();
            classList = entityClassFactory.getEntries();

        } catch (Exception e) {
            log.error("Error initializing dictionaries", e);
            addActionError("Error initializing dictionaries");
        }
    }

    @Override
    protected void updateSearchCriteria(TicketSearchCriteria searchCriteria) {

        Integer all = -1;
        Long lall = -1l;
        if (all.equals(searchCriteria.getAdminStatus()))
            searchCriteria.setAdminStatus(null);

        if (all.equals(searchCriteria.getTechStatus()))
            searchCriteria.setTechStatus(null);

        if ("".equals(searchCriteria.getNicHandle()))
            searchCriteria.setNicHandle(null);

        if ("".equals(searchCriteria.getDomainHolder()))
            searchCriteria.setDomainHolder(null);

        if ("".equals(searchCriteria.getDomainName())) {
            searchCriteria.setDomainName(null);
        }

        if ("".equals(searchCriteria.getCategory())) {
            searchCriteria.setCategory(null);
        }
        if ("".equals(searchCriteria.getClazz())) {
            searchCriteria.setClazz(null);
        }

        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);

        searchCriteria.setCustomerStatus(CustomerStatusEnum.NEW);
        
        super.updateSearchCriteria(searchCriteria);
    }

    /**
     * @return true, if the ticket may be checked in, false if not
     */
    public boolean allowCheckOut(Ticket t) {
        return t != null && !t.isCheckedOut();
    }

    public boolean allowCheckIn(Ticket t) {
        return t != null && t.isCheckedOut() && owns(t);
    }

    /**
     * checks, if current (logged-in) hostmaster owns the ticket given as an
     * argument
     *
     * @param t
     * @return
     */
    private boolean owns(Ticket t) {
        Contact c = t.getCheckedOutTo();
        String username = getUsername();
        if (c == null)
            return false;
        if (username == null)
            return false;
        
        return username.equals(c.getNicHandle());
    }

    public boolean allowReassign(Ticket t) {
        return t != null && t.isCheckedOut();
    }

    /**
     * returns ticket, which id is stored in the session and removes this id
     * from the session.
     *
     * @return ticket with id stored under TICKET_ID or null, if no ticket with
     *         such id was found
     */
    public Ticket getProcessedTicket() {
        return processedTicket;
    }

    /**
     * tries to fetch previously edited ticket
     *
     * @param tid
     */
    public void setPreviousTicketId(long tid) {
        try {
            processedTicket = ticketSearchService.getTicket(tid);
        } catch (TicketNotFoundException e) {
            addActionError(e.getMessage());
        }
    }

    public List<Ticket> getProcessedTicketsList() {
        List<Ticket> l = new ArrayList<Ticket>();
        Ticket t = getProcessedTicket();
        if (t != null)
            l.add(t);
        return l;
    }

    /**
     * @return true, if the ticket may be revised, false if not
     */
    public boolean allowRevise(Ticket t) {
        return t != null && owns(t);
    }

    public boolean allowAlterStatus(Ticket t) {
        return t != null && owns(t);
    }

    public List<Status> getAdminStatuses() {
        return adminStatuses;
    }

    public List<Status> getTechStatuses() {
        return techStatuses;
    }

    public List<FinancialStatusEnum> getFinancialStatuses() {
        return financialStatuses;
    }

    public List<User> getHostmasters() {
        return hostmasters;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<EntityCategory> getCategoryList() {
        return categoryList;
    }

    public List<EnchancedEntityClass> getClassList() {
        return classList;
    }

}
